package com.allison.leaderboard.CustomAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allison.leaderboard.Models.Hour;
import com.allison.leaderboard.R;

import java.util.List;


public class HourRecyclerAdapter extends RecyclerView.Adapter<HourRecyclerAdapter.ViewHolder> {
    private List<Hour> mHours;

    public HourRecyclerAdapter(List<Hour> hours) {
        mHours = hours;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hour hour = mHours.get(position);
        holder.username.setText(hour.getName());
        holder.hours.setText(new StringBuilder().append(hour.getHours()).append(" learning hours, ").append(hour.getCountry()).toString());
    }

    public void setHours(List<Hour> hours) {
        mHours = hours;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mHours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView username, hours;
        private ImageView mImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            hours = itemView.findViewById(R.id.values);
            mImageView = itemView.findViewById(R.id.image_view);
        }
    }
}
