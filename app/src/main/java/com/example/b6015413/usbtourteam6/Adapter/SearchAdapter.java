package com.example.b6015413.usbtourteam6.Adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.b6015413.usbtourteam6.Table_Models.Tutor;
import com.example.b6015413.usbtourteam6.R;

import java.util.List;
import java.util.Locale;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context context;
    private List<Tutor> tutors;

    public SearchAdapter(Context context, List<Tutor> tutors) {
        this.context = context;
        this.tutors = tutors;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item, parent, false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.name.setText(tutors.get(position).getFirstname());
        holder.surname.setText(tutors.get(position).getSurname());
        holder.room.setText(tutors.get(position).getRoom());

    }

    @Override
    public int getItemCount() {
        return tutors.size();
    }


    class SearchViewHolder extends RecyclerView.ViewHolder {

        public TextView name, surname, room, roomTxt;
        Button getDirections;

        //region importing fonts
        AssetManager am = context.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));
        Typeface robotoBlack = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Black.ttf"));
        //endregion

        public SearchViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            surname = itemView.findViewById(R.id.surname);
            room = itemView.findViewById(R.id.room);
            roomTxt= itemView.findViewById(R.id.roomTxt);
            getDirections = itemView.findViewById(R.id.getDirectionsBtn);

            name.setTypeface(robotoLight);
            surname.setTypeface(robotoLight);
            room.setTypeface(robotoLight);
            roomTxt.setTypeface(robotoLight);
            getDirections.setTypeface(robotoBlack);

        }

    }
}