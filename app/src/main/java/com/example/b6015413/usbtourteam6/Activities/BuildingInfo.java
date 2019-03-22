package com.example.b6015413.usbtourteam6.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.b6015413.usbtourteam6.Helper_Classes.DatabaseHelper;
import com.example.b6015413.usbtourteam6.Helper_Classes.ShowRoom;
import com.example.b6015413.usbtourteam6.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BuildingInfo extends Fragment {

    TextView title, generalInfoTxt, transportTxt, currentFloorNum, currentFloorInfo;
    Button openingHours, contactInfo, metroBtn, busBtn, prevFloor, nextFloor;
    Map<Integer, String> floorGuides;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // toolbar title
        ((FrameworkMain) getActivity())
                .setActionBarTitle("Building Information");

        View view = inflater.inflate(R.layout.activity_building_info, container, false);

        final Context context = getContext();
        getInformation();
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
        prevFloor = view.findViewById(R.id.downFloor);
        nextFloor = view.findViewById(R.id.upFloor);
        currentFloorNum = view.findViewById(R.id.currentFloorNumber);
        currentFloorInfo = view.findViewById(R.id.currentFloorInfo);
        //endregion

        //region set Typefaces
        //title.setTypeface(robotoLight);
        generalInfoTxt.setTypeface(robotoLight);
        transportTxt.setTypeface(robotoLight);
        openingHours.setTypeface(robotoLight);
        contactInfo.setTypeface(robotoLight);
        metroBtn.setTypeface(robotoLight);
        busBtn.setTypeface(robotoLight);
        prevFloor.setTypeface(robotoLight);
        nextFloor.setTypeface(robotoLight);
        currentFloorNum.setTypeface(robotoLight);
        currentFloorInfo.setTypeface(robotoLight);
        //endregion

        // todo font sizes

        //region Button clicks

        openingHours.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Opening Hours:")
                        .setMessage(R.string.opening_hours)
                        .show();
            }
        });

        contactInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Contact Information:")
                        .setMessage(R.string.contact_info)
                        .setPositiveButton("Show on map", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getActivity(), MapsActivity.class);
                                intent.putExtra("locationLat", 54.9733868); // todo get the right lat lng
                                intent.putExtra("locationLng", -1.6257051);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Call", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:+44191 208 7972"));
                                //check permissions
                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    //request permission
                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[]{Manifest.permission.CALL_PHONE}, 10);
                                    return;
                                } else {     //have got permission
                                    try {
                                        startActivity(callIntent);  //call activity and make phone call
                                    } catch (android.content.ActivityNotFoundException ex) {
                                        Toast.makeText(context, "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        })
                        .show();
            }
        });

        //Metro stop button
        metroBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("locationLat", 54.974178);
                intent.putExtra("locationLng", -1.620940);
                startActivity(intent);
            }
        });

        busBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("locationLat", 54.974178); // todo get the right lat lng
                intent.putExtra("locationLng", -1.620940);
                startActivity(intent);
            }
        });

        prevFloor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(currentFloorNum.getText().toString()) == 0) return;
                currentFloorNum.setText(Integer.valueOf(currentFloorNum.getText().toString()) - 1 + "");
                currentFloorInfo.setText(floorGuides.get(Integer.valueOf(currentFloorNum.getText().toString())));
            }
        });
        nextFloor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(currentFloorNum.getText().toString()) == 5) return;
                currentFloorNum.setText(Integer.valueOf(currentFloorNum.getText().toString()) + 1 + "");
                currentFloorInfo.setText(floorGuides.get(Integer.valueOf(currentFloorNum.getText().toString())));
            }
        });
        currentFloorInfo.setText(floorGuides.get(Integer.valueOf(currentFloorNum.getText().toString())));
        //endregion
        return view;
    }

    /**
     * read in floor info from file and save to hash map
     */
    public void getInformation() {
        BufferedReader reader = null;
        String mLine = "";
        int currentFloor = -1;
        floorGuides = new HashMap<>();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getContext().getAssets().open("BuildingInformation.txt")));
            while ((mLine = reader.readLine()) != null) {
                if (mLine.contains("Floor ./.")) {
                    currentFloor++;
                    floorGuides.put(currentFloor, "");
                } else {
                    floorGuides.put(currentFloor, floorGuides.get(currentFloor) + "\n" + mLine);
                }
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

