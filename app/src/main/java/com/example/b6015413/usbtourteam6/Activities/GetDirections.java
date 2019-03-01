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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.b6015413.usbtourteam6.Adapter.GetDirectionsAdapter;
import com.example.b6015413.usbtourteam6.Adapter.TutorRoomAdapter;
import com.example.b6015413.usbtourteam6.Database.DatabaseHelper;
import com.example.b6015413.usbtourteam6.R;
import com.example.b6015413.usbtourteam6.Table_Models.Room;
import com.example.b6015413.usbtourteam6.Table_Models.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GetDirections extends AppCompatActivity {

    EditText firstLocation, secondLocation;
    Button stairsBtn, elevatorBtn;
    RecyclerView directionsRV;
    //Temp List to populate recycer view - need to remove additions in OnCreate
    List<Route> getDirectionsItems;
    private DatabaseHelper databaseHelper;

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

        // populate list of routes
        updateRoute();

        //Tutor Rooms RV
        directionsRV = findViewById(R.id.getDirectionsRV);
        //set the layout of the recycler view as the
        directionsRV.setLayoutManager(new LinearLayoutManager(this));
        //populate the recycler view with this class as context and string[] Items as data
        directionsRV.setAdapter(new GetDirectionsAdapter(this, getDirectionsItems));


    }

    private void updateRoute() {
        try {
            getDirectionsItems = new ArrayList<>();
            getDirectionsItems.add(new Route("", "", "go left"));
            //getDirectionsItems = databaseHelper.getRoute(firstLocation.getText().toString(), secondLocation.getText().toString(),
              //      false); // TODO sfa needs to be based on button selection
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, "Check the start and end are valid rooms!",
                    Toast.LENGTH_LONG).show();
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
