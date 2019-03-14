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
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

public class LevelX extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_x);

        context = this;
        activity = this;

        //to find out which level was clicked on and set globals
        floorValue = getIntent().getStringExtra("floor value");
        level = getIntent().getIntExtra("level", 0);

        //region importing fonts
        AssetManager am = this.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));
        //endregion

        //TODO Nav bar for the levelX needs finishing
        //Toolbar (replaces action bar)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Nav drawer, slide in
        // Not working 100 percent
        drawer = findViewById(R.id.drawer_layout);
        //      NavigationView navigationView = findViewById(R.id.nav_view);
        //     navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //region findViewByIDs
        levelX = findViewById(R.id.levelXTxt);
        tutorRoomsTitle = findViewById(R.id.tutorRoomTitle);
        studySpacesTitle = findViewById(R.id.studySpaceTitle);
        otherRoomsTitle = findViewById(R.id.otherRoomsTitle);
        floorPlan = findViewById(R.id.floorPlanBtn);
        expandBtn = findViewById(R.id.expandBtn);
        tutorRoomsRL = findViewById(R.id.tutorRoomsRL);
        studySpacesRL = findViewById(R.id.studySpacesRL);
        otherRoomsRL = findViewById(R.id.otherRoomsRL);
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
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        tutorRoomItems = dbHelper.getTutorOnLevel(level);
        studySpaceItems = dbHelper.getStudySpacesOnLevel(level);
        otherRoomItems = dbHelper.getOtherRoomsOnLevel(level);

        if (tutorRoomItems.isEmpty())
            tutorRoomItems.add(new Tutor(0, "None", "", "no tutor rooms on this floor"));
        if (studySpaceItems.isEmpty())
            studySpaceItems.add(new Room("no study spaces on this floor", 0, "", "", "None"));
        if (otherRoomItems.isEmpty())
            otherRoomItems.add(new Room("no other rooms on this floor", 0, "", "", "None"));

        //region RecyclerViews
        //Tutor Rooms RV
        tutorRoomRV = findViewById(R.id.tutorRV);
        //set the layout of the recycler view as the
        tutorRoomRV.setLayoutManager(new LinearLayoutManager(this));
        //populate the recycler view with this class as context and items as data
        tutorRoomAdapter = new TutorRoomAdapter(this, this, tutorRoomItems, TutorRoomAdapter.COLAPSED_MAX);
        tutorRoomRV.setAdapter(tutorRoomAdapter);

        //Study Spaces RV
        studySpaceRV = findViewById(R.id.studySpaceRV);
        studySpaceRV.setLayoutManager(new LinearLayoutManager(this));

        studySpaceAdapter = new RoomAdapter(this, this, studySpaceItems, RoomAdapter.COLAPSED_MAX);
        studySpaceRV.setAdapter(studySpaceAdapter);
        //endregion

        //Tutor Rooms RV
        otherRoomsRV = findViewById(R.id.otherRoomsRV);
        //set the layout of the recycler view as the
        otherRoomsRV.setLayoutManager(new LinearLayoutManager(this));
        //populate the recycler view with this class as context and items as data
        otherRoomAdapter = new RoomAdapter(this, this, otherRoomItems, RoomAdapter.COLAPSED_MAX);
        otherRoomsRV.setAdapter(otherRoomAdapter);

        //Change the layout of page based on which floor is selected
        floorSelected(floorValue);

        final Button button = findViewById(R.id.expandBtn);
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

        final Button buttonSS = findViewById(R.id.expandBtnSS);
        buttonSS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                studySpaceAdapter = new RoomAdapter(activity, context, studySpaceItems,
                        ((studySpaceAdapter.getMaxItems() == RoomAdapter.COLAPSED_MAX) ? -1 : RoomAdapter.COLAPSED_MAX));
                studySpaceRV.setAdapter(studySpaceAdapter);
            }
        });

        final Button buttonOR = findViewById(R.id.expandBtnOR);
        buttonOR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                otherRoomAdapter = new RoomAdapter(activity, context, otherRoomItems,
                        ((otherRoomAdapter.getMaxItems() == RoomAdapter.COLAPSED_MAX) ? -1 : RoomAdapter.COLAPSED_MAX));
                otherRoomsRV.setAdapter(otherRoomAdapter);
            }
        });

        final Button buttonOpenFP = findViewById(R.id.floorPlanBtn);
        buttonOpenFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFloorPlan();
            }
        });

    }

    public void openFloorPlan() {
        Dialog builder = new Dialog(this);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));


        // region get id of level floor plan
        ImageView imageView = new ImageView(this);
        int id = 0;
        switch (level) {
            case 0:
                Toast.makeText(this, "No floor plan exists for ground floor", Toast.LENGTH_LONG).show();
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
        int py = this.getWindow().getDecorView().getHeight() / 4;

        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        imageView.setImageDrawable(getDrawable(id));

        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        imageView.setOnTouchListener(new ShowImage(scale, scale, 0, py, imageView));

        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //TODO use the query to search your data somehow
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent h = new Intent(LevelX.this, HomePage.class);
                startActivity(h);
            case R.id.nav_ground_floor:
                Intent groundF = new Intent(LevelX.this, LevelX.class);
                groundF.putExtra("floor value", "Ground Floor");
                groundF.putExtra("level", 0);
                startActivity(groundF);
                break;
            case R.id.nav_first_floor:
                Intent firstF = new Intent(LevelX.this, LevelX.class);
                firstF.putExtra("floor value", "First Floor");
                firstF.putExtra("level", 1);
                startActivity(firstF);
                break;
            case R.id.nav_second_floor:
                Intent secondF = new Intent(LevelX.this, LevelX.class);
                secondF.putExtra("floor value", "Second Floor");
                secondF.putExtra("level", 2);
                startActivity(secondF);
                break;
            case R.id.nav_third_floor:
                Intent thirdF = new Intent(LevelX.this, LevelX.class);
                thirdF.putExtra("floor value", "Third Floor");
                thirdF.putExtra("level", 3);
                startActivity(thirdF);
                break;
            case R.id.nav_fourth_floor:
                Intent fourthF = new Intent(LevelX.this, LevelX.class);
                fourthF.putExtra("floor value", "Fourth Floor");
                fourthF.putExtra("level", 4);
                startActivity(fourthF);
                break;
            case R.id.nav_fifth_floor:
                Intent fifthF = new Intent(LevelX.this, LevelX.class);
                fifthF.putExtra("floor value", "Fifth Floor");
                fifthF.putExtra("level", 5);
                startActivity(fifthF);
                break;
            case R.id.nav_sixth_floor:
                Intent sixthF = new Intent(LevelX.this, LevelX.class);
                sixthF.putExtra("floor value", "Sixth Floor");
                sixthF.putExtra("level", 6);
                startActivity(sixthF);
                break;
            case R.id.nav_find_room:
                Intent i = new Intent(LevelX.this, FindARoom.class);
                startActivity(i);
                break;
            case R.id.nav_building_info:
                Intent b = new Intent(LevelX.this, BuildingInfo.class);
                startActivity(b);
                break;
            case R.id.nav_settings:
                Intent g = new Intent(LevelX.this, Settings.class);
                startActivity(g);
                break;
            // this is done, now let us go and intialise the home page.
            // after this lets start copying the above.
            // FOLLOW MEEEEE>>>
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

    private void floorSelected(String floorValue) {
        //change text at top of page
        levelX.setText(floorValue);

        //change colour of views on page

        switch (floorValue) {
            case "Ground Floor":
                // same colour scheme as first floor
            case "First Floor":
                tutorRoomsRL.setBackground(getDrawable(R.drawable.orange_rounded));
                studySpacesRL.setBackground(getDrawable(R.drawable.orange_rounded));
                otherRoomsRL.setBackground(getDrawable(R.drawable.orange_rounded));
                floorPlan.setBackground(getDrawable(R.drawable.floorplanorange));
                break;
            case "Second Floor":
                tutorRoomsRL.setBackground(getDrawable(R.drawable.yellow_rounded));
                studySpacesRL.setBackground(getDrawable(R.drawable.yellow_rounded));
                otherRoomsRL.setBackground(getDrawable(R.drawable.yellow_rounded));
                floorPlan.setBackground(getDrawable(R.drawable.floorplanyellow));
                break;
            case "Third Floor":
                tutorRoomsRL.setBackground(getDrawable(R.drawable.yellow_rounded));
                studySpacesRL.setBackground(getDrawable(R.drawable.yellow_rounded));
                otherRoomsRL.setBackground(getDrawable(R.drawable.yellow_rounded));
                floorPlan.setBackground(getDrawable(R.drawable.floorplanyellow));
                break;
            case "Fourth Floor":
                tutorRoomsRL.setBackground(getDrawable(R.drawable.green_rounded));
                studySpacesRL.setBackground(getDrawable(R.drawable.green_rounded));
                otherRoomsRL.setBackground(getDrawable(R.drawable.green_rounded));
                floorPlan.setBackground(getDrawable(R.drawable.floorplangreen));
                break;
            case "Fifth Floor":
                tutorRoomsRL.setBackground(getDrawable(R.drawable.green_rounded));
                studySpacesRL.setBackground(getDrawable(R.drawable.green_rounded));
                otherRoomsRL.setBackground(getDrawable(R.drawable.green_rounded));
                floorPlan.setBackground(getDrawable(R.drawable.floorplangreen));
                break;
            case "Sixth Floor":
                tutorRoomsRL.setBackground(getDrawable(R.drawable.green_rounded));
                studySpacesRL.setBackground(getDrawable(R.drawable.green_rounded));
                otherRoomsRL.setBackground(getDrawable(R.drawable.green_rounded));
                floorPlan.setBackground(getDrawable(R.drawable.floorplangreen));
                break;
        }


        //change data in recycler view
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
