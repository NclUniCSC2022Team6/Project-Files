package com.example.b6015413.usbtourteam6.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.b6015413.usbtourteam6.Activities.GetDirections;
import com.example.b6015413.usbtourteam6.R;
import com.example.b6015413.usbtourteam6.Table_Models.Room;

import java.util.List;
import java.util.Locale;

public class FindARoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Room> items;

    public FindARoomAdapter(Context context, List<Room> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View findARoomRow = inflater.inflate(R.layout.find_a_room_row, viewGroup, false);

        Item findARoomRowItem = new Item(findARoomRow);

        return findARoomRowItem;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final int j = i; // the activity handler is stupid so the variable has to be final
        ((Item)viewHolder).roomTextFARR.setText(items.get(i).getDescription()+": "+items.get(i).getName());
        ((Item)viewHolder).getDirectionsFARR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GetDirections.class);
                intent.putExtra("directionsTo", items.get(j).getName());
                context.startActivity(intent);
            }
        });
        //TODO: another button press for show on map - to be implemented later
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Item extends RecyclerView.ViewHolder {
        //defining objects in the custom row xml files
        TextView roomTextFARR;
        Button getDirectionsFARR, showOnMapFARR;

        //adding font for recycler view to use
        AssetManager am = context.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am,String.format(Locale.UK,"fonts/%s","Roboto-Light.ttf"));
        Typeface robotoBlack = Typeface.createFromAsset(am,String.format(Locale.UK,"fonts/%s","Roboto-Black.ttf"));

        public Item(View itemView) {
            super(itemView);
            //findViewById for all objects defined above
            //Find A Room
            roomTextFARR = itemView.findViewById(R.id.roomText);
            getDirectionsFARR = itemView.findViewById(R.id.getDirectionsBtn);
            showOnMapFARR = itemView.findViewById(R.id.showOnMapBtn);

            //setting font for objects
            //Find A Room
            roomTextFARR.setTypeface(robotoLight);
            getDirectionsFARR.setTypeface(robotoBlack);
            showOnMapFARR.setTypeface(robotoBlack);


        }
    }

}
