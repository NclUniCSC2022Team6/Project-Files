package com.example.b6015413.usbtourteam6.Activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.b6015413.usbtourteam6.R;

import java.util.Locale;

public class GetDirections extends AppCompatActivity {

    EditText firstLocation, secondLocation;
    Button stairsBtn, elevatorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_directions);

        AssetManager am = this.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am,String.format(Locale.UK,"fonts/%s","Roboto-Light.ttf"));

        firstLocation = findViewById(R.id.firstLocation);
        secondLocation = findViewById(R.id.secondLocation);
        stairsBtn = findViewById(R.id.stairsBtn);
        elevatorBtn = findViewById(R.id.elevatorBtn);

        firstLocation.setTypeface(robotoLight);
        secondLocation.setTypeface(robotoLight);
        stairsBtn.setTypeface(robotoLight);
        elevatorBtn.setTypeface(robotoLight);



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
