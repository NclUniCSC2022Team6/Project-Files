package com.example.b6015413.usbtourteam6.Activities;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b6015413.usbtourteam6.Adapter.StudySpaceAdapter;
import com.example.b6015413.usbtourteam6.Adapter.TutorRoomAdapter;
import com.example.b6015413.usbtourteam6.Database.DatabaseHelper;
import com.example.b6015413.usbtourteam6.R;
import com.example.b6015413.usbtourteam6.Table_Models.Room;
import com.example.b6015413.usbtourteam6.Table_Models.Tutor;

import java.util.List;
import java.util.Locale;

public class LevelX extends AppCompatActivity {

    TextView levelX, tutorRoomsTitle, studySpacesTitle;
    RecyclerView tutorRoomRV, studySpaceRV;
    Button floorPlan;
    List<Tutor> tutorRoomItems;
    List<Room> studySpaceItems;
    String floorValue;
    RelativeLayout tutorRoomsRL, studySpacesRL;
    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_x);

        //to find out which level was clicked on and set globals
        floorValue = getIntent().getStringExtra("floor value");
        level = getIntent().getIntExtra("level",0);

        //region importing fonts
        AssetManager am = this.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am,String.format(Locale.UK,"fonts/%s","Roboto-Light.ttf"));
        //endregion

        //region findViewByIDs
        levelX = findViewById(R.id.levelXTxt);
        tutorRoomsTitle = findViewById(R.id.tutorRoomTitle);
        studySpacesTitle = findViewById(R.id.studySpaceTitle);
        floorPlan = findViewById(R.id.floorPlanBtn);
        tutorRoomsRL = findViewById(R.id.tutorRoomsRL);
        studySpacesRL = findViewById(R.id.studySpacesRL);
        //endregion

        //region setting Typefaces
        levelX.setTypeface(robotoLight);
        tutorRoomsTitle.setTypeface(robotoLight);
        studySpacesTitle.setTypeface(robotoLight);
        levelX.setTypeface(robotoLight);
        floorPlan.setTypeface(robotoLight);
        //endregion

        // get items from database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        tutorRoomItems = dbHelper.getTutorOnLevel(level);
        studySpaceItems = dbHelper.getStudySpacesOnLevel(level);
        // TODO add in otherroom recycler view
        //        OtherRoomItems = dbHelper.getOtherRoomsOnLevel(level);

        //region RecyclerViews
        //Tutor Rooms RV
        tutorRoomRV = findViewById(R.id.tutorRV);
        //set the layout of the recycler view as the
        tutorRoomRV.setLayoutManager(new LinearLayoutManager(this));
        //populate the recycler view with this class as context and string[] Items as data
        tutorRoomRV.setAdapter(new TutorRoomAdapter(this, tutorRoomItems));

        //Study Spaces RV
        studySpaceRV = findViewById(R.id.studySpaceRV);
        studySpaceRV.setLayoutManager(new LinearLayoutManager(this));
        studySpaceRV.setAdapter(new StudySpaceAdapter(this,studySpaceItems));
        //endregion

        //Change the layout of page based on which floor is selected
        floorSelected(floorValue);

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
        levelX. setText(floorValue);

        //change colour of views on page

        switch (floorValue) {
            case "First Floor":
                tutorRoomsRL.setBackground(getDrawable(R.drawable.orange_rounded));
                studySpacesRL.setBackground(getDrawable(R.drawable.orange_rounded));
                floorPlan.setBackground(getDrawable(R.drawable.floorplanorange));
                break;
            case "Second Floor":
                tutorRoomsRL.setBackground(getDrawable(R.drawable.yellow_rounded));
                studySpacesRL.setBackground(getDrawable(R.drawable.yellow_rounded));
                floorPlan.setBackground(getDrawable(R.drawable.floorplanyellow));
                break;
            case "Third Floor":
                tutorRoomsRL.setBackground(getDrawable(R.drawable.yellow_rounded));
                studySpacesRL.setBackground(getDrawable(R.drawable.yellow_rounded));
                floorPlan.setBackground(getDrawable(R.drawable.floorplanyellow));
                break;
            case "Fourth Floor":
                tutorRoomsRL.setBackground(getDrawable(R.drawable.green_rounded));
                studySpacesRL.setBackground(getDrawable(R.drawable.green_rounded));
                floorPlan.setBackground(getDrawable(R.drawable.floorplangreen));
                break;
            case "Fifth Floor":
                tutorRoomsRL.setBackground(getDrawable(R.drawable.green_rounded));
                studySpacesRL.setBackground(getDrawable(R.drawable.green_rounded));
                floorPlan.setBackground(getDrawable(R.drawable.floorplangreen));
                break;
            case "Sixth Floor":
                tutorRoomsRL.setBackground(getDrawable(R.drawable.green_rounded));
                studySpacesRL.setBackground(getDrawable(R.drawable.green_rounded));
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
