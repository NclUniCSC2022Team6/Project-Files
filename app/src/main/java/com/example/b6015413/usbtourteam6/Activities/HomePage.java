package com.example.b6015413.usbtourteam6.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.b6015413.usbtourteam6.R;


public class HomePage extends Fragment {

    private Button welcomeButton, groundFloor, firstFloor, secondFloor, thirdFloor,
            fourthFloor, fifthFloor, sixthFloor, findARoom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_page, container, false);
        // toolbar title
        ((FrameworkMain) getActivity())
                .setActionBarTitle("Welcome to the USB");

        super.onCreate(savedInstanceState);



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
                        .replace(R.id.fragment_container, new SearchTutor())
                        .addToBackStack(null)
                        .commit();
            }
        });

        //endregion
        return view;
    }

    // Sends a broadcast to the FrameworkMain Activity
    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent();
        intent.setAction("drawer.listener");
        getActivity().sendBroadcast(intent);
    }
}
