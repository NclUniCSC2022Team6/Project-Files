package com.example.b6015413.usbtourteam6;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

public class SearchResults extends AppCompatActivity {

    //region recycler view stuff

    //this recycler view works on a string array - tweak it to work on either text file or db
    //straight up copied this video:
    //https://www.youtube.com/watch?v=aqJ6AQdjKOU
    RecyclerView recyclerView;
    //perhaps change this string[] to be a List populated from a text file?
    //would require changing the constructor in SearchRowAdapter class
    //the idea is that Items is filled with the search results - a string[] is probably not the best type to use
    String[] Items = {"Tutor 1 - Room - Floor","Tutor 2 - Room - Floor",
            "Tutor 3 - Room - Floor","Tutor 4 - Room - Floor",
            "Tutor 5 - Room - Floor", "Tutor 6 - Room - Floor", "Tutor 7 - Room - Floor"};

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        handleIntent(getIntent());

        recyclerView = (RecyclerView) findViewById(R.id.searchResultsRV);
        //set the layout of the recycler view as the
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //populate the recycler view with this class as context and string[] Items as data
        recyclerView.setAdapter(new SearchRowAdapter(this,Items));

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
