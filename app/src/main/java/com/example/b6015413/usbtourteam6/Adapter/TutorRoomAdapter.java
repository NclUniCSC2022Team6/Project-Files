package com.example.b6015413.usbtourteam6.Adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b6015413.usbtourteam6.R;
import com.example.b6015413.usbtourteam6.Table_Models.Room;
import com.example.b6015413.usbtourteam6.Table_Models.Tutor;

import java.util.List;
import java.util.Locale;

public class TutorRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Tutor> items;
    private int maxItems;
    public static final int COLAPSED_MAX = 2;

    private OnItemClickListener mListener;

    public TutorRoomAdapter(Context context, List<Tutor> items, int maxItems) {
        this.context = context;
        this.items = items;
        this.maxItems = maxItems;
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
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
        TextView tutorInfoTxt;

        //adding font for recycler view to use
        AssetManager am = context.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));

        public Item(View itemView) {
            super(itemView);
            //findViewById for all objects defined above
            //Find A Room
            tutorInfoTxt = itemView.findViewById(R.id.infoTxt);

            //setting font for objects
            //Find A Room
            tutorInfoTxt.setTypeface(robotoLight);

            //OnClickListener for clicking on an item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        //make sure the item actually exists
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.OnItemClick(position);
                        }
                    }
                }
            });


        }
    }

}
