package com.example.b6015413.usbtourteam6;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

public class HomePage extends AppCompatActivity {

    Button welcomeButton, groundFloor, firstFloor, secondFloor, thirdFloor,
            fourthFloor, fifthFloor, sixthFloor, findARoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //region set IDs
        welcomeButton = (Button)findViewById(R.id.welcomeBtn);
        groundFloor = (Button)findViewById(R.id.groundFloor);
        firstFloor = (Button)findViewById(R.id.firstFloor);
        secondFloor = (Button)findViewById(R.id.secondFloor);
        thirdFloor = (Button)findViewById(R.id.thirdFloor);
        fourthFloor = (Button)findViewById(R.id.fourthFloor);
        fifthFloor = (Button)findViewById(R.id.fifthFloor);
        sixthFloor = (Button)findViewById(R.id.sixthFloor);
        findARoom = (Button)findViewById(R.id.findARoom);
        //endregion

        //test button click to make sure SearchResults page loads properly
        welcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, SearchResults.class);
                startActivity(intent);
            }
        });

        groundFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, FindARoom.class);
                startActivity(intent);
            }
        });

        firstFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, NavigationDrawer.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
}
