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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.b6015413.usbtourteam6.R;

import java.util.Locale;

public class Settings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static float fontSize = 15f;

    TextView settings, lookAndFeel, darkModeTxt, fontSizeTxt, about, appInfo, developerInfo;
    View line1, line2;
    ToggleButton darkModeBtn;
    private DrawerLayout drawer;

    public static void setFontSize(float fontSize) {
        Settings.fontSize = fontSize;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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
        Typeface robotoLight = Typeface.createFromAsset(am,String.format(Locale.UK,"fonts/%s","Roboto-Light.ttf"));
        Typeface robotoBlack = Typeface.createFromAsset(am,String.format(Locale.UK,"fonts/%s","Roboto-Black.ttf"));
        //endregion

        //region findViewById's
        settings = findViewById(R.id.settingsTxt);
        lookAndFeel = findViewById(R.id.lookAndFeel);
        darkModeTxt = findViewById(R.id.darkMode);
        darkModeBtn = findViewById(R.id.darkModeBtn);
        fontSizeTxt = findViewById(R.id.fontSize);
        line1 = findViewById(R.id.line1);
        about = findViewById(R.id.about);
        appInfo = findViewById(R.id.appInfo);
        line2 = findViewById(R.id.line2);
        developerInfo = findViewById(R.id.developerInfo);
        //endregion

        //region setting Typefaces
        settings.setTypeface(robotoLight);
        lookAndFeel.setTypeface(robotoBlack);
        darkModeTxt.setTypeface(robotoLight);
        darkModeBtn.setTypeface(robotoLight);
        fontSizeTxt.setTypeface(robotoLight);
        about.setTypeface(robotoBlack);
        appInfo.setTypeface(robotoLight);
        developerInfo.setTypeface(robotoLight);
        //endregion

        darkModeBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //this is where we will toggle dark mode on
                    darkModeBtn.setTextOn("On");
                } else {
                    //this is where we will toggle dark mode off
                    darkModeBtn.setTextOff("Off");
                }
            }
        });

    }

    // Provides intents to correct pages
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent h= new Intent(Settings.this,HomePage.class);
                startActivity(h);
                break;
            case R.id.nav_ground_floor:
                Intent groundF= new Intent(Settings.this,LevelX.class);
                groundF.putExtra("floor value", "Ground Floor");
                groundF.putExtra("level", 0);
                startActivity(groundF);
                break;
            case R.id.nav_first_floor:
                Intent firstF= new Intent(Settings.this,LevelX.class);
                firstF.putExtra("floor value", "First Floor");
                firstF.putExtra("level", 1);
                startActivity(firstF);
                break;
            case R.id.nav_second_floor:
                Intent secondF= new Intent(Settings.this,LevelX.class);
                secondF.putExtra("floor value", "Second Floor");
                secondF.putExtra("level", 2);
                startActivity(secondF);
                break;
            case R.id.nav_third_floor:
                Intent thirdF= new Intent(Settings.this,LevelX.class);
                thirdF.putExtra("floor value", "Third Floor");
                thirdF.putExtra("level", 3);
                startActivity(thirdF);
                break;
            case R.id.nav_fourth_floor:
                Intent fourthF= new Intent(Settings.this,LevelX.class);
                fourthF.putExtra("floor value", "Fourth Floor");
                fourthF.putExtra("level", 4);
                startActivity(fourthF);
                break;
            case R.id.nav_fifth_floor:
                Intent fifthF= new Intent(Settings.this,LevelX.class);
                fifthF.putExtra("floor value", "Fifth Floor");
                fifthF.putExtra("level", 5);
                startActivity(fifthF);
                break;
            case R.id.nav_sixth_floor:
                Intent sixthF= new Intent(Settings.this,LevelX.class);
                sixthF.putExtra("floor value", "Sixth Floor");
                sixthF.putExtra("level", 6);
                startActivity(sixthF);
                break;
            case R.id.nav_find_room:
                Intent i= new Intent(Settings.this,FindARoom.class);
                startActivity(i);
                break;
            case R.id.nav_building_info:
                Intent b= new Intent(Settings.this,BuildingInfo.class);
                startActivity(b);
                break;
            case R.id.nav_settings:
                Intent g= new Intent(Settings.this,Settings.class);
                startActivity(g);
                break;
            // this is done, now let us go and intialise the home page.
            // after this lets start copying the above.
            // FOLLOW MEEEEE>>>
        }
        // Closes nav drawer once a new activity has been opened.
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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
