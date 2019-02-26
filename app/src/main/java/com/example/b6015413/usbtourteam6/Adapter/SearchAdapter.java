package com.example.b6015413.usbtourteam6.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b6015413.usbtourteam6.Table_Models.Tutor;
import com.example.b6015413.usbtourteam6.R;

import java.util.List;

class SearchViewHolder extends RecyclerView.ViewHolder {

    public TextView name,surname,room;

    public SearchViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        surname = (TextView) itemView.findViewById(R.id.surname);
        room = (TextView) itemView.findViewById(R.id.room);

    }

}
public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private Context context;
    private List<Tutor> tutors;

    public SearchAdapter(Context context, List<Tutor> tutors) {
        this.context = context;
        this.tutors = tutors;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item,parent,false);
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
}