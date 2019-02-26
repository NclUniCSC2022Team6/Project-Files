package com.example.b6015413.usbtourteam6;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.SearchView;

import com.example.b6015413.usbtourteam6.Database.DatabaseHelper;
import com.example.b6015413.usbtourteam6.Table_Models.Room;

import java.util.List;
import java.util.Locale;

public class FindARoom extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Room> items;

    Button findARoomBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_aroom);

        AssetManager am = this.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am,String.format(Locale.UK,"fonts/%s","Roboto-Light.ttf"));

        findARoomBtn = findViewById(R.id.findARoomBtn);

        findARoomBtn.setTypeface(robotoLight);

        handleIntent(getIntent());

        recyclerView = (RecyclerView) findViewById(R.id.findARoomRV);
        //set the layout of the recycler view as the
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //populate the recycler view with this class as context and items as data
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        items = dbHelper.getAllRooms();
        recyclerView.setAdapter(new FindARoomAdapter(this,items));

    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
}
