package com.example.b6015413.usbtourteam6.Activities;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b6015413.usbtourteam6.Adapter.FindARoomAdapter;
import com.example.b6015413.usbtourteam6.Helper_Classes.DatabaseHelper;
import com.example.b6015413.usbtourteam6.Helper_Classes.ShowImage;
import com.example.b6015413.usbtourteam6.R;
import com.example.b6015413.usbtourteam6.Table_Models.Room;

import java.util.List;
import java.util.Locale;

public class FindARoom extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    List<Room> items;
    public static final int RADIUS = 300; // todo maybe this should be an int[] to vary based on the level
    private DrawerLayout drawer;

    TextView findARoomTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_aroom);

        AssetManager am = this.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));

        //Toolbar (replaces action bar)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Nav drawer, slide in
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        findARoomTxt = findViewById(R.id.findARoomTitle);

        findARoomTxt.setTypeface(robotoLight);

        handleIntent(getIntent());

        recyclerView = (RecyclerView) findViewById(R.id.findARoomRV);
        //set the layout of the recycler view as the
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //populate the recycler view with this class as context and items as data
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        items = dbHelper.getAllRooms();
        recyclerView.setAdapter(new FindARoomAdapter(this, items));

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

    // Provides intents to correct pages
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent h= new Intent(FindARoom.this,HomePage.class);
                startActivity(h);
                break;
            case R.id.nav_ground_floor:
                Intent groundF= new Intent(FindARoom.this,LevelX.class);
                groundF.putExtra("floor value", "Ground Floor");
                groundF.putExtra("level", 0);
                startActivity(groundF);
                break;
            case R.id.nav_first_floor:
                Intent firstF= new Intent(FindARoom.this,LevelX.class);
                firstF.putExtra("floor value", "First Floor");
                firstF.putExtra("level", 1);
                startActivity(firstF);
                break;
            case R.id.nav_second_floor:
                Intent secondF= new Intent(FindARoom.this,LevelX.class);
                secondF.putExtra("floor value", "Second Floor");
                secondF.putExtra("level", 2);
                startActivity(secondF);
                break;
            case R.id.nav_third_floor:
                Intent thirdF= new Intent(FindARoom.this,LevelX.class);
                thirdF.putExtra("floor value", "Third Floor");
                thirdF.putExtra("level", 3);
                startActivity(thirdF);
                break;
            case R.id.nav_fourth_floor:
                Intent fourthF= new Intent(FindARoom.this,LevelX.class);
                fourthF.putExtra("floor value", "Fourth Floor");
                fourthF.putExtra("level", 4);
                startActivity(fourthF);
                break;
            case R.id.nav_fifth_floor:
                Intent fifthF= new Intent(FindARoom.this,LevelX.class);
                fifthF.putExtra("floor value", "Fifth Floor");
                fifthF.putExtra("level", 5);
                startActivity(fifthF);
                break;
            case R.id.nav_sixth_floor:
                Intent sixthF= new Intent(FindARoom.this,LevelX.class);
                sixthF.putExtra("floor value", "Sixth Floor");
                sixthF.putExtra("level", 6);
                startActivity(sixthF);
                break;
            case R.id.nav_find_room:
                Intent i= new Intent(FindARoom.this,FindARoom.class);
                startActivity(i);
                break;
            case R.id.nav_building_info:
                Intent b= new Intent(FindARoom.this,BuildingInfo.class);
                startActivity(b);
                break;
            case R.id.nav_settings:
                Intent g= new Intent(FindARoom.this,Settings.class);
                startActivity(g);
                break;
        }
        // Closes nav drawer once a new activity has been opened.
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Used to close/open the navigation drawer
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    // the drawing over an image was thanks to https://stackoverflow.com/questions/18520287/draw-a-circle-on-an-existing-image
    public void openFloorPlan(Room room) {
        Dialog builder = new Dialog(this);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));


        // region get id of level floor plan
        ImageView imageView = new ImageView(this);
        int id = 0;
        switch (room.getLevel()) {
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

        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inDither = true;
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id, myOptions);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10f);
        paint.setStyle(Paint.Style.STROKE);

        Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Canvas canvas = new Canvas(mutableBitmap);

        int[] coords = room.getCoords();

        canvas.drawCircle(coords[0], coords[1], RADIUS, paint);

        imageView.setAdjustViewBounds(false);
        imageView.setImageBitmap(mutableBitmap);

        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        imageView.setOnTouchListener(new ShowImage(scale, scale, 0, py, imageView));

        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }


}
