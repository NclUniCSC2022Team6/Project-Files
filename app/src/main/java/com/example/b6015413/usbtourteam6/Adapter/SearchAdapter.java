package com.example.b6015413.usbtourteam6.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.b6015413.usbtourteam6.Activities.GetDirections;
import com.example.b6015413.usbtourteam6.Activities.Settings;
import com.example.b6015413.usbtourteam6.Table_Models.Room;
import com.example.b6015413.usbtourteam6.Table_Models.Tutor;
import com.example.b6015413.usbtourteam6.R;

import java.util.List;
import java.util.Locale;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context context;
    private List<Room> rooms;

    public SearchAdapter(Context context, List<Room> rooms) {
        this.context = context;
        this.rooms = rooms;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item, parent, false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        final int j = position;
        holder.description.setText(rooms.get(position).getDescription());
        holder.name.setText(rooms.get(position).getName());
        holder.level.setText("Level" + rooms.get(position).getLevel());
        holder.getDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GetDirections.class);
                intent.putExtra("directionsTo", rooms.get(j).getName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }


    class SearchViewHolder extends RecyclerView.ViewHolder {

        public TextView description, name, level, roomTxt;
        Button getDirections;

        //region importing fonts
        AssetManager am = context.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));
        Typeface robotoBlack = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Black.ttf"));
        //endregion

        public SearchViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.surname);
            level = itemView.findViewById(R.id.room);
            roomTxt = itemView.findViewById(R.id.roomTxt);
            getDirections = itemView.findViewById(R.id.getDirectionsBtn);

            description.setTypeface(robotoLight);
            name.setTypeface(robotoLight);
            level.setTypeface(robotoLight);
            roomTxt.setTypeface(robotoLight);
            getDirections.setTypeface(robotoBlack);

            description.setTextSize(Settings.fontSize);
            name.setTextSize(Settings.fontSize);
            level.setTextSize(Settings.fontSize);
            roomTxt.setTextSize(Settings.fontSize);
            getDirections.setTextSize(Settings.fontSize);

        }

    }
}