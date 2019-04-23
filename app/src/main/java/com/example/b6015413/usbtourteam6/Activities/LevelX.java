package com.example.b6015413.usbtourteam6.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b6015413.usbtourteam6.Adapter.RoomAdapter;
import com.example.b6015413.usbtourteam6.Adapter.TutorRoomAdapter;
import com.example.b6015413.usbtourteam6.Helper_Classes.DatabaseHelper;
import com.example.b6015413.usbtourteam6.Helper_Classes.ShowImage;
import com.example.b6015413.usbtourteam6.R;
import com.example.b6015413.usbtourteam6.Table_Models.Room;
import com.example.b6015413.usbtourteam6.Table_Models.Tutor;

import java.util.List;
import java.util.Locale;

public class LevelX extends Fragment {

    private TextView levelX, tutorRoomsTitle, studySpacesTitle, otherRoomsTitle;
    private RecyclerView tutorRoomRV, studySpaceRV, otherRoomsRV;
    private Button floorPlan, expandBtn, expandBtnSS, expandBtnOR;
    private List<Tutor> tutorRoomItems;
    private List<Room> studySpaceItems;
    private List<Room> otherRoomItems;
    private String floorValue;
    private RelativeLayout tutorRoomsRL, studySpacesRL, otherRoomsRL;
    private int level;
    private TutorRoomAdapter tutorRoomAdapter;
    private RoomAdapter studySpaceAdapter, otherRoomAdapter;
    private Context context;
    private Activity activity;
    private View view;
    private Boolean expanded = false;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_level_x, container, false);

        super.onCreate(savedInstanceState);

        context = getContext();
        activity = getActivity();

        //to find out which level was clicked on and set globals
        floorValue = activity.getIntent().getStringExtra("floor value");
        level = activity.getIntent().getIntExtra("level", 0);

        //region importing fonts
        AssetManager am = context.getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));
        //endregion

        //region findViewByIDs
        final Button button = view.findViewById(R.id.expandBtn);
        final Button buttonSS = view.findViewById(R.id.expandBtnSS);
        final Button buttonOR = view.findViewById(R.id.expandBtnOR);
        levelX = view.findViewById(R.id.levelXTxt);
        tutorRoomsTitle = view.findViewById(R.id.tutorRoomTitle);
        studySpacesTitle = view.findViewById(R.id.studySpaceTitle);
        otherRoomsTitle = view.findViewById(R.id.otherRoomsTitle);
        floorPlan = view.findViewById(R.id.floorPlanBtn);
        expandBtn = view.findViewById(R.id.expandBtn);
        expandBtnSS = view.findViewById(R.id.expandBtnSS);
        expandBtnOR = view.findViewById(R.id.expandBtnOR);
        tutorRoomsRL = view.findViewById(R.id.tutorRoomsRL);
        studySpacesRL = view.findViewById(R.id.studySpacesRL);
        otherRoomsRL = view.findViewById(R.id.otherRoomsRL);
        //endregion

        //region setting Typefaces
        levelX.setTypeface(robotoLight);
        tutorRoomsTitle.setTypeface(robotoLight);
        studySpacesTitle.setTypeface(robotoLight);
        otherRoomsTitle.setTypeface(robotoLight);
        levelX.setTypeface(robotoLight);
        floorPlan.setTypeface(robotoLight);
        expandBtn.setTypeface(robotoLight);
        expandBtnSS.setTypeface(robotoLight);
        expandBtnOR.setTypeface(robotoLight);
        //endregion

        //region setting button font sizes // todo make it so when large does not break
        float fontSize = Settings.getFontSize(context);
        button.setTextSize(fontSize - 5f);
        buttonSS.setTextSize(fontSize - 5f);
        buttonOR.setTextSize(fontSize - 5f);

        // get items from database
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        tutorRoomItems = dbHelper.getTutorOnLevel(level);
        studySpaceItems = dbHelper.getStudySpacesOnLevel(level);
        otherRoomItems = dbHelper.getOtherRoomsOnLevel(level);

        if (tutorRoomItems.isEmpty()) {
            tutorRoomItems.add(new Tutor(0, "None", "", "no tutor rooms on this floor"));
            button.setText("");
        }
        if (tutorRoomItems.size() <= 2) button.setText("");

        if (studySpaceItems.isEmpty()) {
            studySpaceItems.add(new Room("no study spaces on this floor", 0, "", "0,0", "None"));
            buttonSS.setText("");
        }
        if (studySpaceItems.size() <= 2) buttonSS.setText("");

        if (otherRoomItems.isEmpty()) {
            otherRoomItems.add(new Room("no other rooms on this floor", 0, "", "0,0", "None"));
            buttonOR.setText("");
        }
        if (otherRoomItems.size() <= 2) buttonOR.setText("");

        //region RecyclerViews
        //Tutor Rooms RV
        tutorRoomRV = view.findViewById(R.id.tutorRV);
        //set the layout of the recycler view as the
        tutorRoomRV.setLayoutManager(new LinearLayoutManager(context));
        //populate the recycler view with this class as context and items as data
        tutorRoomAdapter = new TutorRoomAdapter(getActivity(), context, tutorRoomItems, TutorRoomAdapter.COLAPSED_MAX, floorValue);
        tutorRoomRV.setAdapter(tutorRoomAdapter);

        //Study Spaces RV
        studySpaceRV = view.findViewById(R.id.studySpaceRV);
        studySpaceRV.setLayoutManager(new LinearLayoutManager(context));

        studySpaceAdapter = new RoomAdapter(getActivity(), context, studySpaceItems, RoomAdapter.COLLAPSED_MAX, floorValue);
        studySpaceRV.setAdapter(studySpaceAdapter);
        //endregion

        //Tutor Rooms RV
        otherRoomsRV = view.findViewById(R.id.otherRoomsRV);
        //set the layout of the recycler view as the
        otherRoomsRV.setLayoutManager(new LinearLayoutManager(context));
        //populate the recycler view with this class as context and items as data
        otherRoomAdapter = new RoomAdapter(getActivity(), context, otherRoomItems, RoomAdapter.COLLAPSED_MAX, floorValue);
        otherRoomsRV.setAdapter(otherRoomAdapter);

        //Change the layout of page based on which floor is selected
        floorSelected(floorValue);

        // Toolbar title
        // Placed here to get the floorValue for the toolbar title.
        ((FrameworkMain) getActivity())
                .setActionBarTitle(floorValue);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tutorRoomAdapter = new TutorRoomAdapter(getActivity(), context, tutorRoomItems,
                        ((tutorRoomAdapter.getMaxItems() == TutorRoomAdapter.COLAPSED_MAX) ? -1 : TutorRoomAdapter.COLAPSED_MAX), floorValue);
                tutorRoomRV.setAdapter(tutorRoomAdapter);
                if (tutorRoomItems.size() <= 2) { //== 1 && tutorRoomItems.get(0).getSurname().equals("None")
                } else {
                    if (expanded) {
                        button.setText("Show more...");
                        expanded = false;
                    } else {
                        button.setText("Show less");
                        expanded = true;
                    }
                }
            }
        });


        buttonSS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                studySpaceAdapter = new RoomAdapter(getActivity(), context, studySpaceItems,
                        ((studySpaceAdapter.getMaxItems() == RoomAdapter.COLLAPSED_MAX) ? -1 : RoomAdapter.COLLAPSED_MAX), floorValue);
                studySpaceRV.setAdapter(studySpaceAdapter);
                if (studySpaceItems.size() <= 2) {
                } else {
                    if (expanded) {
                        buttonSS.setText("Show more...");
                        expanded = false;
                    } else {
                        buttonSS.setText("Show less");
                        expanded = true;
                    }
                }
            }
        });


        buttonOR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                otherRoomAdapter = new RoomAdapter(getActivity(), context, otherRoomItems,
                        ((otherRoomAdapter.getMaxItems() == RoomAdapter.COLLAPSED_MAX) ? -1 : RoomAdapter.COLLAPSED_MAX), floorValue);
                otherRoomsRV.setAdapter(otherRoomAdapter);
                if (otherRoomItems.size() <= 2) {
                } else {
                    if (expanded) {
                        buttonOR.setText("Show more...");
                        expanded = false;
                    } else if (!expanded) {
                        buttonOR.setText("Show less");
                        expanded = true;
                    }
                }
            }
        });

        final Button buttonOpenFP = view.findViewById(R.id.floorPlanBtn);
        buttonOpenFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFloorPlan();
            }
        });
        return view;
    }

    public void openFloorPlan() {
        Dialog builder = new Dialog(context);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));


        // region get id of level floor plan
        ImageView imageView = new ImageView(context);
        int id = 0;
        switch (level) {
            case 0:
                Toast.makeText(context, "No floor plan exists for ground floor", Toast.LENGTH_LONG).show();
                return;
            case 1:
                id = R.drawable.floor_1_room_numbers;
                break;
            case 2:
                id = R.drawable.floor_2_room_numbers;
                break;
            case 3:
                id = R.drawable.floor_3_room_numbers;
                break;
            case 4:
                id = R.drawable.floor_4_room_numbers;
                break;
            case 5:
                id = R.drawable.floor_5_room_numbers;
                break;
            case 6:
                id = R.drawable.floor_6_room_numbers;
                break;

        }
        // endregion

        // variables to zoom out and move floor plan to centre
        float scale = 0.09f;
        int py = activity.getWindow().getDecorView().getHeight() / 4;

        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        imageView.setImageDrawable(activity.getDrawable(id));

        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        imageView.setOnTouchListener(new ShowImage(scale, scale, 0, py, imageView));

        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    private void floorSelected(String floorValue) {

        //change text at top of page
        levelX.setText(floorValue);

        //change colour of views on page

        switch (floorValue) {
            case "Ground Floor":
                // default colour
                break;
            case "First Floor":
                tutorRoomsRL.setBackground(activity.getDrawable(R.drawable.orange_rounded));
                studySpacesRL.setBackground(activity.getDrawable(R.drawable.orange_rounded));
                otherRoomsRL.setBackground(activity.getDrawable(R.drawable.orange_rounded));
                floorPlan.setBackground(activity.getDrawable(R.drawable.floorplanorange));
                break;
            case "Second Floor":
                tutorRoomsRL.setBackground(activity.getDrawable(R.drawable.yellow_rounded));
                studySpacesRL.setBackground(activity.getDrawable(R.drawable.yellow_rounded));
                otherRoomsRL.setBackground(activity.getDrawable(R.drawable.yellow_rounded));
                floorPlan.setBackground(activity.getDrawable(R.drawable.floorplanyellow));
                break;
            case "Third Floor":
                tutorRoomsRL.setBackground(activity.getDrawable(R.drawable.yellow_rounded));
                studySpacesRL.setBackground(activity.getDrawable(R.drawable.yellow_rounded));
                otherRoomsRL.setBackground(activity.getDrawable(R.drawable.yellow_rounded));
                floorPlan.setBackground(activity.getDrawable(R.drawable.floorplanyellow));
                break;
            case "Fourth Floor":
                tutorRoomsRL.setBackground(activity.getDrawable(R.drawable.green_rounded));
                studySpacesRL.setBackground(activity.getDrawable(R.drawable.green_rounded));
                otherRoomsRL.setBackground(activity.getDrawable(R.drawable.green_rounded));
                floorPlan.setBackground(activity.getDrawable(R.drawable.floorplangreen));
                break;
            case "Fifth Floor":
                tutorRoomsRL.setBackground(activity.getDrawable(R.drawable.green_rounded));
                studySpacesRL.setBackground(activity.getDrawable(R.drawable.green_rounded));
                otherRoomsRL.setBackground(activity.getDrawable(R.drawable.green_rounded));
                floorPlan.setBackground(activity.getDrawable(R.drawable.floorplangreen));
                break;
            case "Sixth Floor":
                tutorRoomsRL.setBackground(activity.getDrawable(R.drawable.dark_green_rounded));
                studySpacesRL.setBackground(activity.getDrawable(R.drawable.dark_green_rounded));
                otherRoomsRL.setBackground(activity.getDrawable(R.drawable.dark_green_rounded));
                floorPlan.setBackground(activity.getDrawable(R.drawable.floorplandarkgreen));
                changeTextColour();
                break;
        }
    }

    private void changeTextColour() {
        tutorRoomsTitle.setTextColor(getResources().getColor(R.color.white));
        studySpacesTitle.setTextColor(getResources().getColor(R.color.white));
        otherRoomsTitle.setTextColor(getResources().getColor(R.color.white));
        expandBtn.setTextColor(getResources().getColor(R.color.white));
        expandBtnSS.setTextColor(getResources().getColor(R.color.white));
        expandBtnOR.setTextColor(getResources().getColor(R.color.white));
    }

    // Sends a broadcast to the FrameworkMain Activity
    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent();
        intent.setAction("drawer.listener");
        getActivity().sendBroadcast(intent);
    }

    // Runs garbage collection when fragment is destroyed.
    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

}

