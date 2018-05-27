package com2027.cw.group7.resteasy;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> descriptions = new ArrayList<>();
    private ArrayList<Boolean> suggestions = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(ArrayList<String> titles,
                               ArrayList<String> descriptions,
                               ArrayList<Boolean> suggestions, Context context) {
        this.titles = titles;
        this.descriptions = descriptions;
        this.suggestions = suggestions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d(TAG, "OnBindViewHolder: called.");

        holder.title.setText(titles.get(position));
        holder.description.setText(descriptions.get(position));
        Boolean suggestion = suggestions.get(position);

        if (suggestion == null) {
            holder.suggestion.setText("This treatment does not have a recommendation");
            holder.suggestion.setTextColor(Color.BLACK);
        }
        else if (suggestion) {
            holder.suggestion.setText("This treatment is recommended");
            holder.suggestion.setTextColor(Color.GREEN);
        } else {
            holder.suggestion.setText("This treatment is NOT recommended");
            holder.suggestion.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView suggestion;
        RelativeLayout itemLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.list_suggestion_title);
            description = itemView.findViewById(R.id.list_suggestion_description);
            suggestion = itemView.findViewById(R.id.list_suggestion_suggestion);
            itemLayout = itemView.findViewById(R.id.item_layout);
        }
    }
}
