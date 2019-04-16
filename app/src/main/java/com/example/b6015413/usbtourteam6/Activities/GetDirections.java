package com.example.b6015413.usbtourteam6.Activities;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.b6015413.usbtourteam6.Adapter.GetDirectionsAdapter;
import com.example.b6015413.usbtourteam6.Helper_Classes.DatabaseHelper;
import com.example.b6015413.usbtourteam6.R;
import com.example.b6015413.usbtourteam6.Table_Models.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GetDirections extends Fragment {

    Button stairsBtn, elevatorBtn;
    RecyclerView directionsRV;
    List<Route> getDirectionsItems;
    Spinner firstLocationSpinner, secondLocationSpinner;
    private DatabaseHelper databaseHelper;
    boolean sfa = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View inputFragmentView = inflater.inflate(R.layout.activity_get_directions, container, false);
        super.onCreate(savedInstanceState);

        //TODO This class has been converted to a fragment. The buttons which open the directions need to inflate this layout.

        ((FrameworkMain) getActivity())
                .setActionBarTitle("Directions");

        View view = inputFragmentView;

        AssetManager am = getActivity().getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));

        stairsBtn = view.findViewById(R.id.stairsBtn);
        elevatorBtn = view.findViewById(R.id.elevatorBtn);
        firstLocationSpinner = view.findViewById(R.id.firstLocationSpinner);
        secondLocationSpinner = view.findViewById(R.id.secondLocationSpinner);

        stairsBtn.setTypeface(robotoLight);
        elevatorBtn.setTypeface(robotoLight);

        stairsBtn.setTextSize(Settings.getFontSize() + 2f);
        elevatorBtn.setTextSize(Settings.getFontSize() + 2f);

        databaseHelper = new DatabaseHelper(getActivity());

        //Tutor Rooms RV
        directionsRV = view.findViewById(R.id.getDirectionsRV);
        //set the layout of the recycler view as the
        directionsRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        //populate the recycler view with this class as context and list of routes as data
        getDirectionsItems = new ArrayList<Route>();
        directionsRV.setAdapter(new GetDirectionsAdapter(getActivity(), getDirectionsItems));

        //region button presses
        stairsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change stairs button colour and text colour
                stairsBtn.setBackground(getActivity().getDrawable(R.drawable.clicked_button));
                stairsBtn.setTextColor(getResources().getColor(R.color.lightBlue));
                //change elevator button colour and text colour
                elevatorBtn.setBackground(getActivity().getDrawable(R.drawable.not_clicked_button));
                elevatorBtn.setTextColor(getResources().getColor(R.color.darkGrey));

                sfa = false;
                updateRoute();
            }
        });
        elevatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change elevator button colour and text colour
                elevatorBtn.setBackground(getActivity().getDrawable(R.drawable.clicked_button));
                elevatorBtn.setTextColor(getResources().getColor(R.color.lightBlue));
                //change stairs button colour and text colour
                stairsBtn.setBackground(getActivity().getDrawable(R.drawable.not_clicked_button));
                stairsBtn.setTextColor(getResources().getColor(R.color.darkGrey));

                sfa = true;
                updateRoute();

            }
        });
        //endregion

        //region Spinners
        //create an ArrayAdapter using formatted room names
        ArrayAdapter<CharSequence> adapter =
                new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item, databaseHelper.getRoomsAsStrings());

        //set what spinner looks like when it's dropdown
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstLocationSpinner.setAdapter(adapter);
        secondLocationSpinner.setAdapter(adapter);

        firstLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateRoute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        secondLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateRoute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //endregion

        stairsBtn.callOnClick(); // make one of the buttons selected
        // select right rooms that was passed in
        secondLocationSpinner.setSelection(databaseHelper.getIndexOfRoom(getActivity().getIntent().getStringExtra("directionsTo")));

        return inputFragmentView;
    }

    private void updateRoute() {
        try {
            String firstRoomCode = firstLocationSpinner.getSelectedItem().toString().split(": ")[1],
                    secondRoomCode = secondLocationSpinner.getSelectedItem().toString().split(": ")[1];
            getDirectionsItems = databaseHelper.getRoute(firstRoomCode,
                    secondRoomCode, sfa);
            directionsRV.setAdapter(new GetDirectionsAdapter(getActivity(), getDirectionsItems));
        } catch (IllegalArgumentException e) {// error will be thrown by DatabaseHelper TODO REMOVE TOAST OF ERROR BEFORE RELEASE
            Toast.makeText(getActivity(), "Check the start and end are valid rooms!\n" + e, Toast.LENGTH_LONG).show();
        }
    }
}
