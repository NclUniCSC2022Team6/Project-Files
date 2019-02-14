/**
 * THIS CLASS IS AN ADAPTER CLASS FOR RECYCLER VIEWS
 * LEGIT COPIED STRIAGHT FROM THIS VIDEO:
 * https://www.youtube.com/watch?v=aqJ6AQdjKOU
 */

package com.example.b6015413.usbtourteam6;

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

import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    String[] items; //this is what will populate the recycler view - currently a string[] in SearchResults

    public Adapter(Context context, String[] items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.search_row, viewGroup, false);
        Item item = new Item(row);

        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        //sets the text of the TextView in search_row to be i (the position of the string[] in SearchResults)
        ((Item)viewHolder).searchTextSR.setText(items[i]);
        ((Item)viewHolder).getDirectionsSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GetDirections.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class Item extends RecyclerView.ViewHolder {
        //defining objects in custom row layouts
        TextView searchTextSR;
        Button getDirectionsSR;

        //adding font for recycler view to use
        AssetManager am = context.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am,String.format(Locale.UK,"fonts/%s","Roboto-Light.ttf"));

        public Item(View itemView) {
            super(itemView);
            //findViewById for all objects defined above
            searchTextSR = itemView.findViewById(R.id.searchText);
            getDirectionsSR = itemView.findViewById(R.id.directionsBtn);

            //setting font for objects
            searchTextSR.setTypeface(robotoLight);
            getDirectionsSR.setTypeface(robotoLight);

        }
    }

}
