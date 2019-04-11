package com.example.b6015413.usbtourteam6.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b6015413.usbtourteam6.Adapter.FindARoomAdapter;
import com.example.b6015413.usbtourteam6.Helper_Classes.DatabaseHelper;
import com.example.b6015413.usbtourteam6.R;
import com.example.b6015413.usbtourteam6.Table_Models.Room;

import java.util.List;
import java.util.Locale;

public class FindARoom extends Fragment {

    RecyclerView recyclerView;
    List<Room> items;

    TextView findARoomTxt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inputFragmentView = inflater.inflate(R.layout.activity_find_aroom, container, false);
        // Toolbar title
        ((FrameworkMain) getActivity())
                .setActionBarTitle("Find a Room");

        Context context = getContext();
        Activity activity = getActivity();
        View view = inputFragmentView;

        super.onCreate(savedInstanceState);


        AssetManager am = context.getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));

        findARoomTxt = view.findViewById(R.id.findARoomTitle);

        findARoomTxt.setTypeface(robotoLight);

        recyclerView = view.findViewById(R.id.findARoomRV);
        //set the layout of the recycler view as the
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //populate the recycler view with this class as context and items as data
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        items = dbHelper.getAllRooms();
        recyclerView.setAdapter(new FindARoomAdapter(activity, context, items));

        return inputFragmentView;

    }

}

