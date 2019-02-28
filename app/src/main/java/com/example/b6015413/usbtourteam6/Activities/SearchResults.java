package com.example.b6015413.usbtourteam6.Activities;

import android.app.SearchManager;
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
import android.view.MenuItem;
import android.widget.TextView;

import com.example.b6015413.usbtourteam6.Adapter.SearchRowAdapter;
import com.example.b6015413.usbtourteam6.Database.DatabaseHelper;
import com.example.b6015413.usbtourteam6.R;
import com.example.b6015413.usbtourteam6.Table_Models.Tutor;

import java.util.List;
import java.util.Locale;

public class SearchResults extends AppCompatActivity {

    //region recycler view stuff

    //straight up copied this video:
    //https://www.youtube.com/watch?v=aqJ6AQdjKOU
    RecyclerView recyclerView;

    List<Tutor> items;

    TextView showingResultsTitle;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        AssetManager am = this.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));

        showingResultsTitle = findViewById(R.id.showingResultsTitle);

        showingResultsTitle.setTypeface(robotoLight);

        handleIntent(getIntent());
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        items = dbHelper.getTutors();

        recyclerView = (RecyclerView) findViewById(R.id.searchResultsRV);
        //set the layout of the recycler view as the
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //populate the recycler view with this class as context and string[] Items as data
        recyclerView.setAdapter(new SearchRowAdapter(this, items));

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
