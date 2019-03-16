package com.example.b6015413.usbtourteam6.Activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.b6015413.usbtourteam6.R;

import java.util.Locale;

public class HomePage extends AppCompatActivity{

    Button welcomeButton, groundFloor, firstFloor, secondFloor, thirdFloor,
            fourthFloor, fifthFloor, sixthFloor, findARoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        AssetManager am = this.getApplicationContext().getAssets();
        Typeface robotoBlack = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Black.ttf"));

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


        Configuration c = getResources().getConfiguration();
        float scale = c.fontScale;
        if (scale > 1) {
            Settings.setFontSize(20);
        }


        //region button clicks

        //test button click to make sure SearchResults page loads properly
        welcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, BuildingInfo.class);
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




    public static class PlaceholderFragment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View inputFragmentView = inflater.inflate(R.layout.activity_home_page, container, false);
            getActivity().setTitle("<your title>");
            return inputFragmentView;
        }
    }
}
