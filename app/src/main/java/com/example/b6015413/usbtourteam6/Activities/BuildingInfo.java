package com.example.b6015413.usbtourteam6.Activities;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.b6015413.usbtourteam6.R;

import java.util.Locale;

public class BuildingInfo extends AppCompatActivity {

    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_info);

        //region Add fonts
        AssetManager am = this.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am,String.format(Locale.UK,"fonts/%s","Roboto-Light.ttf"));
        Typeface robotoBlack = Typeface.createFromAsset(am,String.format(Locale.UK,"fonts/%s","Roboto-Black.ttf"));
        //endregion

        //region findViewById's
        title = findViewById(R.id.title);
        //endregion

        //region set Typefaces
        title.setTypeface(robotoLight);
        //endregion
    }
}
