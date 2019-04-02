package com.example.b6015413.usbtourteam6.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.b6015413.usbtourteam6.R;


public class FrameworkMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framework_main);

        //Toolbar (replaces action bar)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Nav drawer, slide in
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Syncs the navigation drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Opens homepage if no other fragment is open
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomePage()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    // Sets the correct toolbar title
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    // Provides the navigation for the navigation drawer
    // If button is pressed, it will replace the current fragment with the one the user has pressed
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomePage()).addToBackStack(null).commit();
                break;
                // Intents are used to access the correct floor
                // putExtra is how additional info is passed through into the levelX class
            case R.id.nav_ground_floor:
                Intent groundF = this.getIntent();
                groundF.putExtra("floor value", "Ground Floor");
                groundF.putExtra("level", 0);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LevelX()).addToBackStack(null).commit();
                break;
            case R.id.nav_first_floor:
                Intent firstF = this.getIntent();
                firstF.putExtra("floor value", "First Floor");
                firstF.putExtra("level", 1);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LevelX()).addToBackStack(null).commit();
                break;
            case R.id.nav_second_floor:
                Intent secondF = this.getIntent();
                secondF.putExtra("floor value", "Second Floor");
                secondF.putExtra("level", 2);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LevelX()).addToBackStack(null).commit();
                break;
            case R.id.nav_third_floor:
                Intent thirdF = this.getIntent();
                thirdF.putExtra("floor value", "Third Floor");
                thirdF.putExtra("level", 3);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LevelX()).addToBackStack(null).commit();
                break;
            case R.id.nav_fourth_floor:
                Intent fourthF = this.getIntent();
                fourthF.putExtra("floor value", "Fourth Floor");
                fourthF.putExtra("level", 4);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LevelX()).addToBackStack(null).commit();
                break;
            case R.id.nav_fifth_floor:
                Intent fifthF = this.getIntent();
                fifthF.putExtra("floor value", "Fifth Floor");
                fifthF.putExtra("level", 5);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LevelX()).addToBackStack(null).commit();
                break;
            case R.id.nav_sixth_floor:
                Intent sixthF = this.getIntent();
                sixthF.putExtra("floor value", "Sixth Floor");
                sixthF.putExtra("level", 6);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LevelX()).addToBackStack(null).commit();
                break;
            case R.id.nav_find_room:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FindARoom()).addToBackStack(null).commit();
                break;
            case R.id.nav_building_info:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BuildingInfo()).addToBackStack(null).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Settings()).addToBackStack(null).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchTutor()).addToBackStack(null).commit();

        }

        return super.onOptionsItemSelected(item);
    }

        // Syncs the navigation drawer
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

}