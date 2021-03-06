package com.example.b6015413.usbtourteam6.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b6015413.usbtourteam6.Activities.GetDirections;
import com.example.b6015413.usbtourteam6.Activities.Settings;
import com.example.b6015413.usbtourteam6.Helper_Classes.DatabaseHelper;
import com.example.b6015413.usbtourteam6.Helper_Classes.ShowRoom;
import com.example.b6015413.usbtourteam6.R;
import com.example.b6015413.usbtourteam6.Table_Models.Tutor;

import java.util.List;
import java.util.Locale;

public class TutorRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Tutor> items;
    private int maxItems;
    private FragmentActivity activity;
    String floorValue;
    public static final int COLAPSED_MAX = 2;


    public TutorRoomAdapter(FragmentActivity activity, Context context, List<Tutor> items, int maxItems, String floorValue) {
        this.activity = activity;
        this.context = context;
        this.items = items;
        this.maxItems = maxItems;
        this.floorValue = floorValue;
    }

    public int getMaxItems() {
        return maxItems;
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
        ((Item) viewHolder).tutorInfoTxt.setText(items.get(i).getFirstname() + " " + items.get(i).getSurname() + ": " + items.get(i).getRoom());

    }

    @Override
    public int getItemCount() {
        //limits number of items to maxItems (global var). -1 always returns items.size()
        if (maxItems == -1) return items.size();
        return (items.size() >= maxItems ? maxItems : items.size());
    }

    public class Item extends RecyclerView.ViewHolder {
        //defining objects in the custom row xml files
        public TextView tutorInfoTxt;
        View view;

        //adding font for recycler view to use
        AssetManager am = context.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));

        public Item(View itemView) {
            super(itemView);
            //findViewById for all objects defined above
            //Find A Room
            tutorInfoTxt = itemView.findViewById(R.id.infoTxt);
            view = itemView.findViewById(R.id.divisor);

            //setting font for objects
            //Find A Room
            tutorInfoTxt.setTypeface(robotoLight);
            tutorInfoTxt.setTextSize(Settings.getFontSize(context));

            setTextColour(floorValue);

            //OnClickListener for clicking on an item
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

        public void showMessageDialog(final Tutor tutor) {
            if (tutor.getSurname().equals("None")) return;
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setMessage(tutor.getFirstname() + " " + tutor.getSurname() + ": " + tutor.getRoom())
                    .setPositiveButton("Get directions", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent =activity.getIntent();
                            intent.putExtra("directionsTo", tutor.getRoom());
                            activity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, new GetDirections())
                                    .addToBackStack(null)
                                    .commit();
                        }
                    })
                    .setNegativeButton("Show on map", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new ShowRoom(activity, context, new DatabaseHelper(context).getRoomByName(tutor.getRoom()));
                        }
                    })
                    .show();

            // set size of message to bigger than text in buttons
            TextView textView = dialog.findViewById(android.R.id.message);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setTextSize(Settings.getFontSize(context) + 2f);

            textView = dialog.findViewById(android.R.id.button1);
            textView.setTextSize(Settings.getFontSize(context));
            textView = dialog.findViewById(android.R.id.button2);
            textView.setTextSize(Settings.getFontSize(context));

        }

        void setTextColour(String floorValue) {
            if (floorValue.equals("Sixth Floor")) {
                tutorInfoTxt.setTextColor(Color.WHITE);
                view.setBackgroundColor(Color.WHITE);
            }
        }
    }

}
