package com.example.b6015413.usbtourteam6.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.b6015413.usbtourteam6.Adapter.SearchAdapter;
import com.example.b6015413.usbtourteam6.Helper_Classes.DatabaseHelper;
import com.example.b6015413.usbtourteam6.R;
import com.mancj.materialsearchbar.MaterialSearchBar;


import java.util.ArrayList;
import java.util.List;

public class SearchTutor extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter adapter;

    MaterialSearchBar materialSearchBar;
    List<String> suggestList = new ArrayList<>();

    DatabaseHelper database;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View inputFragmentView = inflater.inflate(R.layout.activity_search_tutor, container, false);
        super.onCreate(savedInstanceState);

        ((FrameworkMain) getActivity())
                .setActionBarTitle("Search for a Room");

        View view = inputFragmentView;


        //init view
        recyclerView = view.findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        materialSearchBar = view.findViewById(R.id.search_bar);

        //init database
        database = new DatabaseHelper(getActivity());

        //setup search bar
        materialSearchBar.setHint("Search");
        materialSearchBar.setCardViewElevation(10);
        loadSuggestList();
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<String> suggest = new ArrayList<>();
                for (String search : suggestList) {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final Activity activity = getActivity();
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled)
                    adapter = new SearchAdapter(getActivity(), getActivity().getBaseContext(), database.getAllRooms());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        //init adatper default set all result
        adapter = new SearchAdapter(getActivity(), getActivity(), database.getAllRooms());
        recyclerView.setAdapter(adapter);

        return inputFragmentView;
    }

    private void startSearch(String text) {
        adapter = new SearchAdapter(getActivity(), getActivity(), database.getAllRoomsMatching(text));
        recyclerView.setAdapter(adapter);
    }

    private void loadSuggestList() {
        suggestList = database.getNames();
        materialSearchBar.setLastSuggestions(suggestList);
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
