package com.example.b6015413.usbtourteam6;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b6015413.usbtourteam6.Table_Models.Room;

import java.util.List;
import java.util.Locale;

public class StudySpaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Room> items;
    int maxItems = 5; // TODO will be passed in, so can be expanded

    public StudySpaceAdapter(Context context, List<Room> items) {
        this.context = context;
        this.items = items;
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
        ((Item) viewHolder).studySpaceTxt.setText(items.get(i).getName());

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


        }
    }

}
