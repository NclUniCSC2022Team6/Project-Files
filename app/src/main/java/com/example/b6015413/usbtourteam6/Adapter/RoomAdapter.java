package com.example.b6015413.usbtourteam6.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b6015413.usbtourteam6.Activities.GetDirections;
import com.example.b6015413.usbtourteam6.Helper_Classes.DatabaseHelper;
import com.example.b6015413.usbtourteam6.Helper_Classes.ShowRoom;
import com.example.b6015413.usbtourteam6.R;
import com.example.b6015413.usbtourteam6.Table_Models.Room;
import com.example.b6015413.usbtourteam6.Table_Models.Tutor;

import java.util.List;
import java.util.Locale;

public class RoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Room> items;
    Activity activity;
    int maxItems;
    public static final int COLAPSED_MAX = 2;

    public RoomAdapter(Activity activity, Context context, List<Room> items, int maxItems) {
        this.activity = activity;
        this.context = context;
        this.items = items;
        this.maxItems = maxItems;
    }

    public int getMaxItems() {
        return maxItems;
    }

    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View levelXRow = inflater.inflate(R.layout.level_x_row, viewGroup, false);

        Item levelXItem = new Item(levelXRow);

        return levelXItem;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((Item) viewHolder).studySpaceTxt.setText(items.get(i).getDescription() + ": " + items.get(i).getName());

    }

    @Override
    public int getItemCount() {
        // limits number of items to maxItems (global var). -1 always returns items.size()
        if (maxItems == -1) return items.size();
        return (items.size() >= maxItems ? maxItems : items.size());
    }

    public class Item extends RecyclerView.ViewHolder {
        //defining objects in the custom row xml files
        TextView studySpaceTxt;

        //adding font for recycler view to use
        AssetManager am = context.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));

        public Item(View itemView) {
            super(itemView);
            //findViewById for all objects defined above
            //Find A Room
            studySpaceTxt = itemView.findViewById(R.id.infoTxt);

            //setting font for objects
            //Find A Room
            studySpaceTxt.setTypeface(robotoLight);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    //make sure the item actually exists
                    if (position != RecyclerView.NO_POSITION) {
                        showMessageDialog(items.get(position));
                        //mListener.OnItemClick(position);
                    }
                }
            });

        }


        public void showMessageDialog(final Room room) {
            if (room.getDescription().equals("None")) return;
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle(room.getDescription() + " " + room.getName())
                    .setMessage("Level " + room.getLevel())
                    .setPositiveButton("Get directions", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(context, GetDirections.class);
                            intent.putExtra("directionsTo", room.getName());
                            context.startActivity(intent);
                        }
                    })
                    .setNegativeButton("Show on map", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new ShowRoom(activity, context, room);
                        }
                    })
                    .show();

        }
    }

}
