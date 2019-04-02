package com.example.b6015413.usbtourteam6.Activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.b6015413.usbtourteam6.Adapter.GetDirectionsAdapter;
import com.example.b6015413.usbtourteam6.Helper_Classes.DatabaseHelper;
import com.example.b6015413.usbtourteam6.R;
import com.example.b6015413.usbtourteam6.Table_Models.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GetDirections extends AppCompatActivity {

    Button stairsBtn, elevatorBtn;
    RecyclerView directionsRV;
    List<Route> getDirectionsItems;
    Spinner firstLocationSpinner, secondLocationSpinner;
    private DatabaseHelper databaseHelper;
    boolean sfa = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_directions);

        AssetManager am = this.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));

        stairsBtn = findViewById(R.id.stairsBtn);
        elevatorBtn = findViewById(R.id.elevatorBtn);
        firstLocationSpinner = findViewById(R.id.firstLocationSpinner);
        secondLocationSpinner = findViewById(R.id.secondLocationSpinner);

        stairsBtn.setTypeface(robotoLight);
        elevatorBtn.setTypeface(robotoLight);

        stairsBtn.setTextSize(Settings.fontSize + 2f);
        elevatorBtn.setTextSize(Settings.fontSize + 2f);

        databaseHelper = new DatabaseHelper(this);

        //Tutor Rooms RV
        directionsRV = findViewById(R.id.getDirectionsRV);
        //set the layout of the recycler view as the
        directionsRV.setLayoutManager(new LinearLayoutManager(this));

        //populate the recycler view with this class as context and list of routes as data
        getDirectionsItems = new ArrayList<Route>();
        directionsRV.setAdapter(new GetDirectionsAdapter(this, getDirectionsItems));

        //region button presses
        stairsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change stairs button colour and text colour
                stairsBtn.setBackground(getDrawable(R.drawable.clicked_button));
                stairsBtn.setTextColor(getResources().getColor(R.color.lightBlue));
                //change elevator button colour and text colour
                elevatorBtn.setBackground(getDrawable(R.drawable.not_clicked_button));
                elevatorBtn.setTextColor(getResources().getColor(R.color.darkGrey));

                sfa = false;
                updateRoute();
            }
        });
        elevatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change elevator button colour and text colour
                elevatorBtn.setBackground(getDrawable(R.drawable.clicked_button));
                elevatorBtn.setTextColor(getResources().getColor(R.color.lightBlue));
                //change stairs button colour and text colour
                stairsBtn.setBackground(getDrawable(R.drawable.not_clicked_button));
                stairsBtn.setTextColor(getResources().getColor(R.color.darkGrey));

                sfa = true;
                updateRoute();

            }
        });
        //endregion

        //region Spinners
        //create an ArrayAdapter using formatted room names
        ArrayAdapter<CharSequence> adapter =
                new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, databaseHelper.getRoomsAsStrings());

        //set what spinner looks like when it's dropdown
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstLocationSpinner.setAdapter(adapter);
        secondLocationSpinner.setAdapter(adapter);

        firstLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateRoute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        secondLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateRoute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //endregion

        stairsBtn.callOnClick(); // make one of the buttons selected
        // select right rooms that was passed in
        secondLocationSpinner.setSelection(databaseHelper.getIndexOfRoom(getIntent().getStringExtra("directionsTo")));
    }

    private void updateRoute() {
        try {
            String firstRoomCode = firstLocationSpinner.getSelectedItem().toString().split(": ")[1],
                    secondRoomCode = secondLocationSpinner.getSelectedItem().toString().split(": ")[1];
            getDirectionsItems = databaseHelper.getRoute(firstRoomCode,
                    secondRoomCode, sfa);
            directionsRV.setAdapter(new GetDirectionsAdapter(this, getDirectionsItems));
        } catch (IllegalArgumentException e) {// error will be thrown by DatabaseHelper TODO REMOVE TOAST OF ERROR BEFORE RELEASE
            Toast.makeText(this, "Check the start and end are valid rooms!\n" + e, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_btn:
                startActivity(new Intent(this, SearchTutor.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
