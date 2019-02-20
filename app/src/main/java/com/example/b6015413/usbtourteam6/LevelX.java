package com.example.b6015413.usbtourteam6;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class LevelX extends AppCompatActivity {

    TextView levelX, tutorRoomsTitle, row1TR, row2TR, studySpacesTitle, row1SS, row2SS;
    Button floorPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_x);

        AssetManager am = this.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am,String.format(Locale.UK,"fonts/%s","Roboto-Light.ttf"));

        levelX = findViewById(R.id.levelXTxt);
        tutorRoomsTitle = findViewById(R.id.tutorRoomTitle);
        row1TR = findViewById(R.id.firstRow);
        row2TR = findViewById(R.id.secondRow);
        studySpacesTitle = findViewById(R.id.studySpaceTitle);
        row1SS = findViewById(R.id.firstRowSS);
        row2SS = findViewById(R.id.secondRowSS);
        floorPlan = findViewById(R.id.floorPlanBtn);

        levelX.setTypeface(robotoLight);
        tutorRoomsTitle.setTypeface(robotoLight);
        row1TR.setTypeface(robotoLight);
        levelX.setTypeface(robotoLight);
        row2TR.setTypeface(robotoLight);
        row1SS.setTypeface(robotoLight);
        row2SS.setTypeface(robotoLight);
        floorPlan.setTypeface(robotoLight);

    }
}
