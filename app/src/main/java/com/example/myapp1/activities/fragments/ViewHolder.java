package com.example.myapp1.activities.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapp1.R;
import com.example.myapp1.interfaces.OnItemClickListener;
import com.example.myapp1.logic.Player;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView player_info_TXT_name;
    private TextView player_info_TXT_score;
    private TextView player_info_TXT_distance;
    private LinearLayoutCompat linearLayoutCompat_info;
    private Player player;
    private OnItemClickListener mListener;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        findViews();
    }
    public void setmListener (OnItemClickListener mListener){
        this.mListener = mListener;
    }
    public TextView getPlayer_info_name() {
        return player_info_TXT_name;
    }
    public TextView getPlayer_info_score() {
        return player_info_TXT_score;
    }
    public TextView getPlayer_info_distance() {return player_info_TXT_distance;}

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void findViews() {
        player_info_TXT_name = itemView.findViewById(R.id.player_info_TXT_name);
        player_info_TXT_score = itemView.findViewById(R.id.player_info_TXT_score);
        player_info_TXT_distance = itemView.findViewById(R.id.player_info_TXT_distance);
        linearLayoutCompat_info = itemView.findViewById(R.id.linearLayoutCompat_info);
    }

    @Override
    public void onClick(View v) {
        mListener.onItemClick(player);
    }
}