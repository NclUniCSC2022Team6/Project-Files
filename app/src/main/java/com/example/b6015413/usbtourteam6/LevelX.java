package com.example.b6015413.usbtourteam6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LevelX extends AppCompatActivity {

    TextView levelX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_x);

        levelX = (TextView) findViewById(R.id.levelXTxt);

    }
}
