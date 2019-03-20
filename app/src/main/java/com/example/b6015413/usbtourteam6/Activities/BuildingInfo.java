package com.example.b6015413.usbtourteam6.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.example.b6015413.usbtourteam6.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class BuildingInfo extends Fragment {

    TextView title, generalInfoTxt, transportTxt;
    Button openingHours, contactInfo, metroBtn, busBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // toolbar title
        ((FrameworkMain) getActivity())
                .setActionBarTitle("Building Information");

        View view = inflater.inflate(R.layout.activity_building_info, container, false);

        Context context = getContext();

        super.onCreate(savedInstanceState);

        //region Add fonts
        AssetManager am = context.getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));
        Typeface robotoBlack = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Black.ttf"));
        //endregion

        //region findViewById's
        //title = view.findViewById(R.id.title);
        generalInfoTxt = view.findViewById(R.id.generalInfoTitle);
        transportTxt = view.findViewById(R.id.transportLinksTitle);
        openingHours = view.findViewById(R.id.openingHoursBtn);
        contactInfo = view.findViewById(R.id.contactInfoBtn);
        metroBtn = view.findViewById(R.id.metroLinkBtn);
        busBtn = view.findViewById(R.id.busLinkBtn);
        //endregion

        //region set Typefaces
        //title.setTypeface(robotoLight);
        generalInfoTxt.setTypeface(robotoLight);
        transportTxt.setTypeface(robotoLight);
        openingHours.setTypeface(robotoLight);
        contactInfo.setTypeface(robotoLight);
        metroBtn.setTypeface(robotoLight);
        busBtn.setTypeface(robotoLight);
        //endregion

        metroBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });


//        findARoom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, new FindARoom())
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });

        return view;
    }

    public void getInformation() {
        BufferedReader reader = null;
        String mLine = "";
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getContext().getAssets().open("BuildingInformation.txt")));
            while ((mLine = reader.readLine()) != null) {
                // todo do something with it
            }
        } catch (Exception e) {
            // throw new exception with which line caused the exception and the original exception
            throw new IllegalArgumentException("Error with line: " + mLine + "\n" + e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // fail quietly
                }
            }
        }
    }
}
