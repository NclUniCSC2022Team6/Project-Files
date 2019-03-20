package com.example.b6015413.usbtourteam6.Activities;

import android.content.Context;
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

import com.example.b6015413.usbtourteam6.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BuildingInfo extends Fragment {

    TextView title, generalInfoTxt, transportTxt;
    Button openingHours, contactInfo, metroBtn, busBtn;
    Map<String, String> info;

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

        getInformation();
        // todo set the  recyclers - maybe a info adapter?
        view.findViewById(R.id.generalInfoRL);
        view.findViewById(R.id.transportRL);

        return view;
    }


    public void getInformation() {
        info = new HashMap<>();
        BufferedReader reader = null;
        String mLine = "";
        String[] split;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getContext().getAssets().open("BuildingInformation.txt")));
            while ((mLine = reader.readLine()) != null) {
                split = mLine.split(":");
                info.put(split[0], split[1]);
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
