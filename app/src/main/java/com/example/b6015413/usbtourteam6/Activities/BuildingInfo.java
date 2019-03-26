package com.example.b6015413.usbtourteam6.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.b6015413.usbtourteam6.Helper_Classes.DatabaseHelper;
import com.example.b6015413.usbtourteam6.Helper_Classes.ShowImage;
import com.example.b6015413.usbtourteam6.Helper_Classes.ShowRoom;
import com.example.b6015413.usbtourteam6.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BuildingInfo extends Fragment {

    TextView title, generalInfoTxt, transportTxt;
    Button openingHours, contactInfo, metroBtn, busBtn, cafe;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // toolbar title
        ((FrameworkMain) getActivity())
                .setActionBarTitle("Building Information");

        View view = inflater.inflate(R.layout.activity_building_info, container, false);

        final Context context = getContext();

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

        cafe = view.findViewById(R.id.cafe);
        //endregion

        //region set Typefaces
        //title.setTypeface(robotoLight);
        generalInfoTxt.setTypeface(robotoLight);
        transportTxt.setTypeface(robotoLight);
        openingHours.setTypeface(robotoLight);
        contactInfo.setTypeface(robotoLight);
        metroBtn.setTypeface(robotoLight);
        busBtn.setTypeface(robotoLight);
        cafe.setTypeface(robotoLight);
        //endregion

        //region set text sizes
        generalInfoTxt.setTextSize(Settings.fontSize);
        transportTxt.setTextSize(Settings.fontSize);
        openingHours.setTextSize(Settings.fontSize);
        contactInfo.setTextSize(Settings.fontSize);
        metroBtn.setTextSize(Settings.fontSize);
        busBtn.setTextSize(Settings.fontSize);
        cafe.setTextSize(Settings.fontSize);
        //endregion

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
                                intent.putExtra("locationLat", 54.9735049);
                                intent.putExtra("locationLng", -1.6253759);
                                intent.putExtra("title", "Urban Sciences Building");
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
                intent.putExtra("title", "Metro St James");
                startActivity(intent);
            }
        });

        busBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("locationLat", 54.9745148);
                intent.putExtra("locationLng", -1.6227728);
                intent.putExtra("title", "Bus station");
                startActivity(intent);
            }
        });

        final Activity activity = getActivity();

        cafe.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Eat@Urban Cafe")
                        .setMessage(R.string.cafe_info) // todo this is some random menu i found on the internet
                        .setPositiveButton("View menu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Dialog builder = new Dialog(context);
                                builder.getWindow().setBackgroundDrawable(
                                        new ColorDrawable(android.graphics.Color.TRANSPARENT));


                                // region get id of level floor plan
                                ImageView imageView = new ImageView(context);
                                int id = R.drawable.cafe_menu;

                                // variables to zoom out and move floor plan to centre
                                float scale = 0.3f;
                                int py = activity.getWindow().getDecorView().getHeight() / 4;
                                int px = activity.getWindow().getDecorView().getWidth() / 10;

                                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                imageView.setImageDrawable(activity.getDrawable(id));

                                imageView.setScaleType(ImageView.ScaleType.MATRIX);
                                imageView.setOnTouchListener(new ShowImage(scale, scale, px, py, imageView));

                                builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT));
                                builder.show();
                            }
                        })
                        .show();
            }
        });
        //endregion
        return view;
    }
}

