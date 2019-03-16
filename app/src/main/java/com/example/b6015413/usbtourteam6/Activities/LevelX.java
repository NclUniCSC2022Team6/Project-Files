package com.example.b6015413.usbtourteam6.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    TextView levelX, tutorRoomsTitle, studySpacesTitle, otherRoomsTitle;
    RecyclerView tutorRoomRV, studySpaceRV, otherRoomsRV;
    Button floorPlan, expandBtn;
    List<Tutor> tutorRoomItems;
    List<Room> studySpaceItems;
    List<Room> otherRoomItems;
    String floorValue;
    RelativeLayout tutorRoomsRL, studySpacesRL, otherRoomsRL;
    int level;
    TutorRoomAdapter tutorRoomAdapter;
    RoomAdapter studySpaceAdapter, otherRoomAdapter;
    Context context;
    Activity activity;
    View view;
    private DrawerLayout drawer;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_level_x, container, false);
        // Toolbar title
        ((FrameworkMain) getActivity())
                .setActionBarTitle("Level IX");
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
        levelX = view.findViewById(R.id.levelXTxt);
        tutorRoomsTitle = view.findViewById(R.id.tutorRoomTitle);
        studySpacesTitle = view.findViewById(R.id.studySpaceTitle);
        otherRoomsTitle = view.findViewById(R.id.otherRoomsTitle);
        floorPlan = view.findViewById(R.id.floorPlanBtn);
        expandBtn = view.findViewById(R.id.expandBtn);
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
        //endregion

        // get items from database
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        tutorRoomItems = dbHelper.getTutorOnLevel(level);
        studySpaceItems = dbHelper.getStudySpacesOnLevel(level);
        otherRoomItems = dbHelper.getOtherRoomsOnLevel(level);

        if (tutorRoomItems.isEmpty())
            tutorRoomItems.add(new Tutor(0, "None", "", "no tutor rooms on this floor"));
        if (studySpaceItems.isEmpty())
            studySpaceItems.add(new Room("no study spaces on this floor", 0, "", "0,0", "None"));
        if (otherRoomItems.isEmpty())
            otherRoomItems.add(new Room("no other rooms on this floor", 0, "", "0,0", "None"));

        //region RecyclerViews
        //Tutor Rooms RV
        tutorRoomRV = view.findViewById(R.id.tutorRV);
        //set the layout of the recycler view as the
        tutorRoomRV.setLayoutManager(new LinearLayoutManager(context));
        //populate the recycler view with this class as context and items as data
        tutorRoomAdapter = new TutorRoomAdapter(activity, context, tutorRoomItems, TutorRoomAdapter.COLAPSED_MAX);
        tutorRoomRV.setAdapter(tutorRoomAdapter);

        //Study Spaces RV
        studySpaceRV = view.findViewById(R.id.studySpaceRV);
        studySpaceRV.setLayoutManager(new LinearLayoutManager(context));

        studySpaceAdapter = new RoomAdapter(activity, context, studySpaceItems, RoomAdapter.COLAPSED_MAX);
        studySpaceRV.setAdapter(studySpaceAdapter);
        //endregion

        //Tutor Rooms RV
        otherRoomsRV = view.findViewById(R.id.otherRoomsRV);
        //set the layout of the recycler view as the
        otherRoomsRV.setLayoutManager(new LinearLayoutManager(context));
        //populate the recycler view with this class as context and items as data
        otherRoomAdapter = new RoomAdapter(activity, context, otherRoomItems, RoomAdapter.COLAPSED_MAX);
        otherRoomsRV.setAdapter(otherRoomAdapter);

        //Change the layout of page based on which floor is selected
        floorSelected(floorValue);

        final Button button = view.findViewById(R.id.expandBtn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (floorValue.equals("Ground Floor")) {
                    tutorRoomAdapter = new TutorRoomAdapter(activity, context, tutorRoomItems,
                            ((tutorRoomAdapter.getMaxItems() == TutorRoomAdapter.COLAPSED_MAX) ? -1 : TutorRoomAdapter.COLAPSED_MAX));
                    tutorRoomRV.setAdapter(otherRoomAdapter);
                } else {
                    tutorRoomAdapter = new TutorRoomAdapter(activity, context, tutorRoomItems,
                            ((tutorRoomAdapter.getMaxItems() == TutorRoomAdapter.COLAPSED_MAX) ? -1 : TutorRoomAdapter.COLAPSED_MAX));
                    tutorRoomRV.setAdapter(tutorRoomAdapter);
                }
            }
        });

        final Button buttonSS = view.findViewById(R.id.expandBtnSS);
        buttonSS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                studySpaceAdapter = new RoomAdapter(activity, context, studySpaceItems,
                        ((studySpaceAdapter.getMaxItems() == RoomAdapter.COLAPSED_MAX) ? -1 : RoomAdapter.COLAPSED_MAX));
                studySpaceRV.setAdapter(studySpaceAdapter);
            }
        });

        final Button buttonOR = view.findViewById(R.id.expandBtnOR);
        buttonOR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                otherRoomAdapter = new RoomAdapter(activity, context, otherRoomItems,
                        ((otherRoomAdapter.getMaxItems() == RoomAdapter.COLAPSED_MAX) ? -1 : RoomAdapter.COLAPSED_MAX));
                otherRoomsRV.setAdapter(otherRoomAdapter);
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
                tutorRoomsRL.setBackground(activity.getDrawable(R.drawable.green_rounded));
                studySpacesRL.setBackground(activity.getDrawable(R.drawable.green_rounded));
                otherRoomsRL.setBackground(activity.getDrawable(R.drawable.green_rounded));
                floorPlan.setBackground(activity.getDrawable(R.drawable.floorplangreen));
                break;
        }
    }
}
