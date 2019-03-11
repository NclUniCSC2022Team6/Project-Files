package com.example.b6015413.usbtourteam6.Activities;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class LevelX extends AppCompatActivity {

    TextView levelX, tutorRoomsTitle, studySpacesTitle, otherRoomsTitle;
    RecyclerView tutorRoomRV, studySpaceRV, otherRoomsRV;
    Button floorPlan;
    List<Tutor> tutorRoomItems;
    List<Room> studySpaceItems;
    List<Room> otherRoomItems;
    String floorValue;
    RelativeLayout tutorRoomsRL, studySpacesRL, otherRoomsRL;
    int level;
    TutorRoomAdapter tutorRoomAdapter;
    RoomAdapter studySpaceAdapter, otherRoomAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_x);

        context = this;

        //to find out which level was clicked on and set globals
        floorValue = getIntent().getStringExtra("floor value");
        level = getIntent().getIntExtra("level", 0);

        //region importing fonts
        AssetManager am = this.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));
        //endregion

        //region findViewByIDs
        levelX = findViewById(R.id.levelXTxt);
        tutorRoomsTitle = findViewById(R.id.tutorRoomTitle);
        studySpacesTitle = findViewById(R.id.studySpaceTitle);
        otherRoomsTitle = findViewById(R.id.otherRoomsTitle);
        floorPlan = findViewById(R.id.floorPlanBtn);
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

        if(tutorRoomItems.isEmpty()) tutorRoomItems.add(new Tutor(0, "None", "", "no tutor rooms on this floor"));
        if(studySpaceItems.isEmpty()) studySpaceItems.add(new Room("no study spaces on this floor", 0, "", "", "None"));
        if(otherRoomItems.isEmpty()) otherRoomItems.add(new Room("no other rooms on this floor", 0, "", "", "None"));

        //region RecyclerViews
        //Tutor Rooms RV
        tutorRoomRV = findViewById(R.id.tutorRV);
        //set the layout of the recycler view as the
        tutorRoomRV.setLayoutManager(new LinearLayoutManager(this));
        //populate the recycler view with this class as context and items as data
        tutorRoomAdapter = new TutorRoomAdapter(this, tutorRoomItems, TutorRoomAdapter.COLAPSED_MAX);
        tutorRoomRV.setAdapter(tutorRoomAdapter);


        //Study Spaces RV
        studySpaceRV = findViewById(R.id.studySpaceRV);
        studySpaceRV.setLayoutManager(new LinearLayoutManager(this));

        studySpaceAdapter = new RoomAdapter(this, studySpaceItems, RoomAdapter.COLAPSED_MAX);
        studySpaceRV.setAdapter(studySpaceAdapter);
        //endregion

        //Tutor Rooms RV
        otherRoomsRV = findViewById(R.id.otherRoomsRV);
        //set the layout of the recycler view as the
        otherRoomsRV.setLayoutManager(new LinearLayoutManager(this));
        //populate the recycler view with this class as context and items as data
        otherRoomAdapter = new RoomAdapter(this, otherRoomItems, RoomAdapter.COLAPSED_MAX);
        otherRoomsRV.setAdapter(otherRoomAdapter);

        //Change the layout of page based on which floor is selected
        floorSelected(floorValue);

        final Button button = findViewById(R.id.expandBtn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tutorRoomAdapter = new TutorRoomAdapter(context, tutorRoomItems,
                        ((tutorRoomAdapter.getMaxItems() == TutorRoomAdapter.COLAPSED_MAX) ? -1 : TutorRoomAdapter.COLAPSED_MAX));
                tutorRoomRV.setAdapter(tutorRoomAdapter);
            }
        });

        final Button buttonSS = findViewById(R.id.expandBtnSS);
        buttonSS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                studySpaceAdapter = new RoomAdapter(context, studySpaceItems,
                        ((studySpaceAdapter.getMaxItems() == RoomAdapter.COLAPSED_MAX) ? -1 : RoomAdapter.COLAPSED_MAX));
                studySpaceRV.setAdapter(studySpaceAdapter);
            }
        });

        final Button buttonOR = findViewById(R.id.expandBtnOR);
        buttonOR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                otherRoomAdapter = new RoomAdapter(context, otherRoomItems,
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

    private void floorSelected(String floorValue) {
        //change text at top of page
        levelX.setText(floorValue);

        //change colour of views on page

        switch (floorValue) {
            case "Ground Floor":
                //replace top RecyclerView with Other Rooms recycler view
                //tutor rooms aren't needed
                tutorRoomsTitle.setText("Rooms");
                otherRoomAdapter = new RoomAdapter(this, otherRoomItems, RoomAdapter.COLAPSED_MAX);
                tutorRoomRV.setAdapter(otherRoomAdapter);
                //hide OtherRooms view
                otherRoomsRL.setVisibility(View.GONE);
                otherRoomsTitle.setVisibility(View.GONE);
                otherRoomsRV.setVisibility(View.GONE);
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
