package com.example.b6015413.usbtourteam6.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.b6015413.usbtourteam6.R;


public class TourGuide extends Fragment {

    Button prevFloor, nextFloor;
    TextView currentFloorInfo, currentFloorNum;
    private int level;
    private String[] levelNumToText = new String[]{"0", "1", "2", "3", "4-5", "6"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tour_guide, container, false);

        //region findViewByIds
        prevFloor = view.findViewById(R.id.pageLeft);
        nextFloor = view.findViewById(R.id.pageRight);
        currentFloorInfo = view.findViewById(R.id.infoText);
        currentFloorNum = view.findViewById(R.id.pageNumber);
        //endregion

        level = 0;

        prevFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (level == 0) return;
                level--;
                currentFloorNum.setText(levelNumToText[level]);
                currentFloorInfo.setText(getLevelText(level));
            }
        });
        nextFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (level == 5) return;
                level++;
                currentFloorNum.setText(levelNumToText[level]);
                currentFloorInfo.setText(getLevelText(level));
            }
        });
        currentFloorInfo.setText(getLevelText(level));


        return view;
    }


    int getLevelText(int level) {
        int textId;
        switch (level) {
            case 0:
                textId = R.string.groundFloorTourText;
                break;
            case 1:
                textId = R.string.firstFloorTourText;
                break;
            case 2:
                textId = R.string.secondFloorTourText;
                break;
            case 3:
                textId = R.string.threeFourFloorTourText;
                break;
            case 4:
                textId = R.string.fifthFloorTourText;
                break;
            case 5:
                textId = R.string.sixthFloorTourText;
                break;
            default:
                throw new IllegalArgumentException("invalid level " + level);
        }
        return textId;
    }

}
