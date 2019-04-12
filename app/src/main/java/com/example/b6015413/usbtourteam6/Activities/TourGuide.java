package com.example.b6015413.usbtourteam6.Activities;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.b6015413.usbtourteam6.R;

import java.util.Locale;


public class TourGuide extends Fragment implements AdapterView.OnItemSelectedListener {

    Button prevFloor, nextFloor;
    TextView selectFloorTxt, view1Title, view1Text, view2Title, view2Text;
    Spinner floorSelect;
    RelativeLayout view1, view2;
    String currentFloor;
    //TextView currentFloorInfo, currentFloorNum;
    private int level;
    private String[] levelNumToText = new String[]{"0", "1", "2", "3", "4-5", "6"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tour_guide, container, false);

        // toolbar title
        ((FrameworkMain) getActivity())
                .setActionBarTitle("Tour Guide");

        //region findViewByIds
        selectFloorTxt = view.findViewById(R.id.selectAFloor);
        view1Title = view.findViewById(R.id.view1Title);
        view1Text = view.findViewById(R.id.view1Text);
        view2Title = view.findViewById(R.id.view2Title);
        view2Text = view.findViewById(R.id.view2Text);
        floorSelect = view.findViewById(R.id.spinner);
        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        //endregion

        //region set typefaces
        AssetManager am = getActivity().getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));

        selectFloorTxt.setTypeface(robotoLight);
        view1Title.setTypeface(robotoLight);
        view1Text.setTypeface(robotoLight);
        view2Title.setTypeface(robotoLight);
        view2Text.setTypeface(robotoLight);
        //endregion

        //region spinner
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.floors,
//                android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter =
                new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item,
                        getResources().getTextArray(R.array.floors));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorSelect.setAdapter(adapter);
        floorSelect.setOnItemSelectedListener(this);
        //endregion

        return view;
    }

    private void setFloorText(String floor) {
        switch (floor) {
            case "Ground Floor":
                view2.setVisibility(View.GONE);
                view1Title.setText(R.string.groundFloorTourTitle);
                view1Text.setText(R.string.groundFloorTourText);
                break;
            case "First Floor":
                view1Title.setText(R.string.firstFloorTourTitle);
                view1Text.setText(R.string.firstFloorTourText);
                view2.setVisibility(View.VISIBLE);
                view2Title.setText(R.string.firstFloorTourTitle2);
                view2Text.setText(R.string.firstFloorTourText2);
                break;
            case "Second Floor":
                view1Title.setText(R.string.secondFloorTourTitle);
                view1Text.setText(R.string.secondFloorTourText);
                view2.setVisibility(View.VISIBLE);
                view2Title.setText(R.string.secondFloorTourTitle2);
                view2Text.setText(R.string.secondFloorTourText2);
                break;
            case "Third Floor":
                view2.setVisibility(View.GONE);
                view1Title.setText(R.string.thirdFloorTourTitle);
                view1Text.setText(R.string.thirdFloorTourText);
                break;
            case "Fourth Floor":
                view2.setVisibility(View.GONE);
                view1Title.setText(R.string.fifthFloorTourTitle);
                view1Text.setText(R.string.fifthFloorTourText);
                break;
            case "Fifth Floor":
                view2.setVisibility(View.GONE);
                view1Title.setText(R.string.fifthFloorTourTitle);
                view1Text.setText(R.string.fifthFloorTourText);
                break;
            case "Sixth Floor":
                view2.setVisibility(View.GONE);
                view1Title.setText(R.string.sixthFloorTourTitle);
                view1Text.setText(R.string.sixthFloorTourText);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        currentFloor = adapterView.getItemAtPosition(i).toString();
        setFloorText(currentFloor);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
