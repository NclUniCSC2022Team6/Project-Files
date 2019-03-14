package com.example.b6015413.usbtourteam6.Activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.b6015413.usbtourteam6.Adapter.GetDirectionsAdapter;
import com.example.b6015413.usbtourteam6.Helper_Classes.DatabaseHelper;
import com.example.b6015413.usbtourteam6.R;
import com.example.b6015413.usbtourteam6.Table_Models.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GetDirections extends AppCompatActivity {

    EditText firstLocation, secondLocation;
    Button stairsBtn, elevatorBtn;
    RecyclerView directionsRV;
    List<Route> getDirectionsItems;
    Boolean StairsClicked, elevatorClicked;
    private DatabaseHelper databaseHelper;
    boolean sfa = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_directions);

        AssetManager am = this.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));

        firstLocation = findViewById(R.id.firstLocation);
        secondLocation = findViewById(R.id.secondLocation);
        stairsBtn = findViewById(R.id.stairsBtn);
        elevatorBtn = findViewById(R.id.elevatorBtn);

        firstLocation.setTypeface(robotoLight);
        secondLocation.setTypeface(robotoLight);
        stairsBtn.setTypeface(robotoLight);
        elevatorBtn.setTypeface(robotoLight);

        databaseHelper = new DatabaseHelper(this);

        // set destination as value that has been selected
        secondLocation.setText(getIntent().getStringExtra("directionsTo"));

        //Tutor Rooms RV
        directionsRV = findViewById(R.id.getDirectionsRV);
        //set the layout of the recycler view as the
        directionsRV.setLayoutManager(new LinearLayoutManager(this));

        // populate list of routes
        updateRoute();

        //populate the recycler view with this class as context and list of routes as data
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

                //might be an idea to reverse this when the user changes the search queries
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

                //might be an idea to reverse this when the user changes the search queries
                sfa = true;
                updateRoute();

            }
        });
        //endregion


    }

    private void updateRoute() { // TODO make this work not sample data
        try {
            getDirectionsItems = new ArrayList<>();
            getDirectionsItems.add(new Route("", "", "go left " + sfa));
//            getDirectionsItems = databaseHelper.getRoute(firstLocation.getText().toString(), secondLocation.getText().toString(),
//                    sfa);
            directionsRV.setAdapter(new GetDirectionsAdapter(this, getDirectionsItems));
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, "Check the start and end are valid rooms!",
                    Toast.LENGTH_LONG).show(); // error will be thrown by DatabaseHelper
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
