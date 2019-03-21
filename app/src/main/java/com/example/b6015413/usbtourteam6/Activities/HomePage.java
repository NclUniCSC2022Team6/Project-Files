package com.example.b6015413.usbtourteam6.Activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.b6015413.usbtourteam6.R;

import java.util.Locale;

public class HomePage extends Fragment {

    Button welcomeButton, groundFloor, firstFloor, secondFloor, thirdFloor,
            fourthFloor, fifthFloor, sixthFloor, findARoom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_page, container, false);
        // toolbar title
        ((FrameworkMain) getActivity())
                .setActionBarTitle("Home Page");

        super.onCreate(savedInstanceState);

        AssetManager am = getContext().getAssets();
        Typeface robotoBlack = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Black.ttf"));


        //region set IDs
        welcomeButton = view.findViewById(R.id.welcomeBtn);
        groundFloor = view.findViewById(R.id.groundFloor);
        firstFloor = view.findViewById(R.id.firstFloor);
        secondFloor = view.findViewById(R.id.secondFloor);
        thirdFloor = view.findViewById(R.id.thirdFloor);
        fourthFloor = view.findViewById(R.id.fourthFloor);
        fifthFloor = view.findViewById(R.id.fifthFloor);
        sixthFloor = view.findViewById(R.id.sixthFloor);
        findARoom = view.findViewById(R.id.findARoom);
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

        welcomeButton.setTextSize(Settings.fontSize);
        groundFloor.setTextSize(Settings. fontSize);
        firstFloor.setTextSize(Settings.fontSize);
        secondFloor.setTextSize(Settings.fontSize);
        thirdFloor.setTextSize(Settings.fontSize);
        fourthFloor.setTextSize(Settings.fontSize);
        fifthFloor.setTextSize(Settings.fontSize);
        sixthFloor.setTextSize(Settings.fontSize);
        findARoom.setTextSize(Settings.fontSize);


        Configuration c = getResources().getConfiguration();
        float scale = c.fontScale;
        if (scale > 1) {
            Settings.setFontSize(20);
        }

        //region button clicks

        //test button click to make sure SearchResults page loads properly
        // Replaces fragments
        welcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new BuildingInfo())
                        .addToBackStack(null)
                        .commit();
            }
        });

        groundFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getActivity().getIntent();
                intent.putExtra("floor value", "Ground Floor");
                intent.putExtra("level", 0);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new LevelX())
                        .addToBackStack(null)
                        .commit();
            }
        });

        firstFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getActivity().getIntent();
                intent.putExtra("floor value", "First Floor");
                intent.putExtra("level", 1);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new LevelX())
                        .addToBackStack(null)
                        .commit();
            }
        });

        secondFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getActivity().getIntent();
                intent.putExtra("floor value", "Second Floor");
                intent.putExtra("level", 2);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new LevelX())
                        .addToBackStack(null)
                        .commit();
            }
        });

        thirdFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getActivity().getIntent();
                intent.putExtra("floor value", "Third Floor");
                intent.putExtra("level", 3);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new LevelX())
                        .addToBackStack(null)
                        .commit();
            }
        });

        fourthFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getActivity().getIntent();
                intent.putExtra("floor value", "Fourth Floor");
                intent.putExtra("level", 4);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new LevelX())
                        .addToBackStack(null)
                        .commit();
            }
        });

        fifthFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getActivity().getIntent();
                intent.putExtra("floor value", "Fifth Floor");
                intent.putExtra("level", 5);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new LevelX())
                        .addToBackStack(null)
                        .commit();
            }
        });

        sixthFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getActivity().getIntent();
                intent.putExtra("floor value", "Sixth Floor");
                intent.putExtra("level", 6);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new LevelX())
                        .addToBackStack(null)
                        .commit();
            }
        });

        findARoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new FindARoom())
                        .addToBackStack(null)
                        .commit();
            }
        });

        //endregion
        return view;
    }
}
