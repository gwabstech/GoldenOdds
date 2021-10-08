/*
 * Copyright (c)
 *   * Created by Gwabstech on 10/8/21, 3:24 PM
 *   * Copyright (c) 2021 . All rights reserved.
 *   * Last modified 10/8/21, 3:24 PM
 */

package com.gwabs.GOLDEN_ODDS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {

    public Context mContext;
    // --Commented out by Inspection (10/15/2020 3:20 AM):private static final String Tag= "RecylerView";
    // --Commented out by Inspection (10/15/2020 3:20 AM):final Context mContext;
    final ArrayList<Massage> MassageList;
    public myAdapter(Context mContext, ArrayList<Massage> massageList) {
        this.mContext = mContext;
        MassageList = massageList;
    }

    @NonNull
    @Override
    public myAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.itemsviews,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // binding text
        holder.Date.setText(MassageList.get(position).getDate());
        holder.time.setText(MassageList.get(position).getTime());
        holder.game.setText(MassageList.get(position).getGame());
        holder.tips.setText(MassageList.get(position).getTips());
        holder.Odds.setText(MassageList.get(position).getOdds());
        holder.status.setText(MassageList.get(position).getStatus());
        holder.country.setText(MassageList.get(position).getCountry());





    }

    @Override
    public int getItemCount() {
        return MassageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final TextView Date;
        final TextView time;
        final TextView game;
        final TextView tips;
        final TextView Odds;
        final TextView status;
        final TextView country;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Date=itemView.findViewById(R.id.Date);
            time=itemView.findViewById(R.id.time);
            game=itemView.findViewById(R.id.gameTextView);
            tips=itemView.findViewById(R.id.tips);
            Odds=itemView.findViewById(R.id.ODD);
            status=itemView.findViewById(R.id.status);
            country=itemView.findViewById(R.id.country1);


        }
    }
}
