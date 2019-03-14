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
import android.widget.TextView;

import com.example.b6015413.usbtourteam6.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class BuildingInfo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView title;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_info);

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

        //region Add fonts
        AssetManager am = this.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));
        Typeface robotoBlack = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Black.ttf"));
        //endregion

        //region findViewById's
        title = findViewById(R.id.title);
        //endregion

        //region set Typefaces
        title.setTypeface(robotoLight);
        //endregion
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent h = new Intent(BuildingInfo.this, HomePage.class);
                startActivity(h);
                break;
            case R.id.nav_ground_floor:
                Intent groundF = new Intent(BuildingInfo.this, LevelX.class);
                groundF.putExtra("floor value", "Ground Floor");
                groundF.putExtra("level", 0);
                startActivity(groundF);
                break;
            case R.id.nav_first_floor:
                Intent firstF = new Intent(BuildingInfo.this, LevelX.class);
                firstF.putExtra("floor value", "First Floor");
                firstF.putExtra("level", 1);
                startActivity(firstF);
                break;
            case R.id.nav_second_floor:
                Intent secondF = new Intent(BuildingInfo.this, LevelX.class);
                secondF.putExtra("floor value", "Second Floor");
                secondF.putExtra("level", 2);
                startActivity(secondF);
                break;
            case R.id.nav_third_floor:
                Intent thirdF = new Intent(BuildingInfo.this, LevelX.class);
                thirdF.putExtra("floor value", "Third Floor");
                thirdF.putExtra("level", 3);
                startActivity(thirdF);
                break;
            case R.id.nav_fourth_floor:
                Intent fourthF = new Intent(BuildingInfo.this, LevelX.class);
                fourthF.putExtra("floor value", "Fourth Floor");
                fourthF.putExtra("level", 4);
                startActivity(fourthF);
                break;
            case R.id.nav_fifth_floor:
                Intent fifthF = new Intent(BuildingInfo.this, LevelX.class);
                fifthF.putExtra("floor value", "Fifth Floor");
                fifthF.putExtra("level", 5);
                startActivity(fifthF);
                break;
            case R.id.nav_sixth_floor:
                Intent sixthF = new Intent(BuildingInfo.this, LevelX.class);
                sixthF.putExtra("floor value", "Sixth Floor");
                sixthF.putExtra("level", 6);
                startActivity(sixthF);
                break;
            case R.id.nav_find_room:
                Intent i = new Intent(BuildingInfo.this, FindARoom.class);
                startActivity(i);
                break;
            case R.id.nav_building_info:
                Intent b = new Intent(BuildingInfo.this, BuildingInfo.class);
                startActivity(b);
                break;
            case R.id.nav_settings:
                Intent g = new Intent(BuildingInfo.this, Settings.class);
                startActivity(g);
                break;
            // this is done, now let us go and intialise the home page.
            // after this lets start copying the above.
            // FOLLOW MEEEEE>>>
            // TODO what is going on here
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void getInformation() {
        BufferedReader reader = null;
        String mLine = "";
        try {
            reader = new BufferedReader(
                    new InputStreamReader(this.getAssets().open("BuildingInformation.txt")));
            while ((mLine = reader.readLine()) != null) {
                // todo do something with it
            }
        } catch (Exception e) {
            // throw new exception with which line caused the exception and the original exception
            throw new IllegalArgumentException("Error with line: " + mLine + "\n" + e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // fail quietly
                }
            }
        }
    }
}
