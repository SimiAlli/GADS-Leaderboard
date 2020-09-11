package com.allison.leaderboard.CustomAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allison.leaderboard.Models.Skill;
import com.allison.leaderboard.R;

import java.util.List;

public class SkillRecyclerAdapter extends RecyclerView.Adapter<SkillRecyclerAdapter.ViewHolder> {

    private List<Skill> mSkills;
    private  LayoutInflater mLayoutInflater;

    public SkillRecyclerAdapter(List<Skill> skills) {
        mSkills = skills;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Skill skill = mSkills.get(position);
        holder.username.setText(skill.getName());
        holder.skill.setText(new StringBuilder().append(skill.getScore()).append(" skill IQ score, ").append(skill.getCountry()).toString());
    }

    public void setSkills(List<Skill> skills) {
        mSkills = skills;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mSkills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView username, skill;
        private ImageView mImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            skill = itemView.findViewById(R.id.values);
            mImageView = itemView.findViewById(R.id.image_view);
        }
    }
}
