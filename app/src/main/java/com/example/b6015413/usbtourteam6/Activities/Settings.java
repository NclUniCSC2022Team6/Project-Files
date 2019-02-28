package com.example.b6015413.usbtourteam6.Activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Settings extends AppCompatActivity {

    TextView settings, lookAndFeel, darkModeTxt, fontSizeTxt, about, appInfo, developerInfo;
    View line1, line2;
    ToggleButton darkModeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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
