package com.example.b6015413.usbtourteam6.Activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.b6015413.usbtourteam6.R;

import java.util.Locale;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    Button welcomeButton, groundFloor, firstFloor, secondFloor, thirdFloor,
            fourthFloor, fifthFloor, sixthFloor, findARoom;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        AssetManager am = this.getApplicationContext().getAssets();
        Typeface robotoBlack = Typeface.createFromAsset(am,String.format(Locale.UK,"fonts/%s","Roboto-Black.ttf"));

        //Toolbar (replaces action bar)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Nav drawer, slide in
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //region set IDs
        welcomeButton = findViewById(R.id.welcomeBtn);
        groundFloor = findViewById(R.id.groundFloor);
        firstFloor = findViewById(R.id.firstFloor);
        secondFloor = findViewById(R.id.secondFloor);
        thirdFloor = findViewById(R.id.thirdFloor);
        fourthFloor = findViewById(R.id.fourthFloor);
        fifthFloor = findViewById(R.id.fifthFloor);
        sixthFloor = findViewById(R.id.sixthFloor);
        findARoom = findViewById(R.id.findARoom);
        //endregion

        //region setting Typeface
        welcomeButton.setTypeface(robotoBlack);
        groundFloor.setTypeface(robotoBlack);
        firstFloor.setTypeface(robotoBlack);
        secondFloor.setTypeface(robotoBlack);
        thirdFloor.setTypeface(robotoBlack);
        fourthFloor.setTypeface(robotoBlack);
        fifthFloor.setTypeface(robotoBlack);
        sixthFloor.setTypeface(robotoBlack);
        findARoom.setTypeface(robotoBlack);
        //endregion

        //region button clicks

        //test button click to make sure SearchResults page loads properly
        welcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, Settings.class);
                startActivity(intent);
            }
        });

        groundFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, LevelX.class);
                intent.putExtra("floor value", "Ground Floor");
                intent.putExtra("level", 0);
                startActivity(intent);
            }
        });

        firstFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, LevelX.class);
                intent.putExtra("floor value", "First Floor");
                intent.putExtra("level", 1);
                startActivity(intent);
            }
        });

        secondFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, LevelX.class);
                intent.putExtra("floor value", "Second Floor");
                intent.putExtra("level", 2);
                startActivity(intent);
            }
        });

        thirdFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, LevelX.class);
                intent.putExtra("floor value", "Third Floor");
                intent.putExtra("level", 3);
                startActivity(intent);
            }
        });

        fourthFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, LevelX.class);
                intent.putExtra("floor value", "Fourth Floor");
                intent.putExtra("level", 4);
                startActivity(intent);
            }
        });

        fifthFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, LevelX.class);
                intent.putExtra("floor value", "Fifth Floor");
                intent.putExtra("level", 5);
                startActivity(intent);
            }
        });

        sixthFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, LevelX.class);
                intent.putExtra("floor value", "Sixth Floor");
                intent.putExtra("level", 6);
                startActivity(intent);
            }
        });

        findARoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, FindARoom.class);
                startActivity(intent);
            }
        });

        //endregion

    }

    // Provides intents to correct pages

    // Used to close/open the navigation drawer
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

//        // Associate searchable configuration with the SearchView
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Intent h= new Intent(HomePage.this,HomePage.class);
                    startActivity(h);
                case R.id.nav_ground_floor:
                    Intent groundF= new Intent(HomePage.this,LevelX.class);
                    groundF.putExtra("floor value", "Ground Floor");
                    groundF.putExtra("level", 0);
                    startActivity(groundF);
                    break;
                case R.id.nav_first_floor:
                    Intent firstF= new Intent(HomePage.this,LevelX.class);
                    firstF.putExtra("floor value", "First Floor");
                    firstF.putExtra("level", 1);
                    startActivity(firstF);
                    break;
                case R.id.nav_second_floor:
                    Intent secondF= new Intent(HomePage.this,LevelX.class);
                    secondF.putExtra("floor value", "Second Floor");
                    secondF.putExtra("level", 2);
                    startActivity(secondF);
                    break;
                case R.id.nav_third_floor:
                    Intent thirdF= new Intent(HomePage.this,LevelX.class);
                    thirdF.putExtra("floor value", "Third Floor");
                    thirdF.putExtra("level", 3);
                    startActivity(thirdF);
                    break;
                case R.id.nav_fourth_floor:
                    Intent fourthF= new Intent(HomePage.this,LevelX.class);
                    fourthF.putExtra("floor value", "Fourth Floor");
                    fourthF.putExtra("level", 4);
                    startActivity(fourthF);
                    break;
                case R.id.nav_fifth_floor:
                    Intent fifthF= new Intent(HomePage.this,LevelX.class);
                    fifthF.putExtra("floor value", "Fifth Floor");
                    fifthF.putExtra("level", 5);
                    startActivity(fifthF);
                    break;
                case R.id.nav_sixth_floor:
                    Intent sixthF= new Intent(HomePage.this,LevelX.class);
                    sixthF.putExtra("floor value", "Sixth Floor");
                    sixthF.putExtra("level", 6);
                    startActivity(sixthF);
                    break;
                case R.id.nav_find_room:
                    Intent i= new Intent(HomePage.this,FindARoom.class);
                    startActivity(i);
                    break;
                case R.id.nav_building_info:
                    Intent b= new Intent(HomePage.this,BuildingInfo.class);
                    startActivity(b);
                    break;
                case R.id.nav_settings:
                    Intent g= new Intent(HomePage.this,Settings.class);
                    startActivity(g);
                    break;
            }
            // Closes nav drawer once a new activity has been opened.
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }


}
