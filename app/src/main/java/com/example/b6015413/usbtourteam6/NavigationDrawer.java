package com.example.b6015413.usbtourteam6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

//https://www.youtube.com/watch?v=zYVEMCiDcmY
//this dude says to do everything we've done...what a g

public class NavigationDrawer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //this could be wrong cos casting
        setSupportActionBar(toolbar);

    }
}
