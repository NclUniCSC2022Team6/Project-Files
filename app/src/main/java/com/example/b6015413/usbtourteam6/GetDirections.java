package com.example.b6015413.usbtourteam6;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class GetDirections extends AppCompatActivity {

    private static final String TAG = "GetDirections";

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

        getIncomingIntent();

    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intent");
    }
}
