/*
 * Copyright (c)
 *   * Created by Gwabstech on 1/14/22, 3:16 PM
 *   * Copyright (c) 2022 . All rights reserved.
 *   * Last modified 1/14/22, 3:16 PM
 */

package com.gwabs.GOLDEN_ODDS.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gwabs.GOLDEN_ODDS.Model.In_App_Products;
import com.gwabs.GOLDEN_ODDS.R;
import com.gwabs.GOLDEN_ODDS.clickListener;

import java.util.ArrayList;
import java.util.List;

public class VipProductAdapter extends RecyclerView.Adapter<VipProductAdapter.vipViewHolder>{
    public clickListener clickListener;
    final List<In_App_Products> ProductListVip;
    private Context context;
    protected ProgressDialog myProgressDialog;

    public VipProductAdapter(Context context, ArrayList<In_App_Products> productListVip, clickListener clickListener) {
        this.clickListener = clickListener;
        ProductListVip = productListVip;
        this.context = context;

    }

          //


    @NonNull
    @Override
    public vipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.one_off_items_view,parent,false);
        return new vipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vipViewHolder holder, int position) {
        holder.Name.setText(ProductListVip.get(position).getName());
        holder.Discription.setText(ProductListVip.get(position).getDiscription());
        holder.btnBuy.setText(ProductListVip.get(position).getPrice());
        clickListener.onItemClick(holder.itemView,holder.getAbsoluteAdapterPosition());

    }

    @Override
    public int getItemCount() {
        return ProductListVip.size();
    }

    static class vipViewHolder extends RecyclerView.ViewHolder{

        TextView Name,Discription;
        Button btnBuy;
        public vipViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.txtProductName);
            Discription = itemView.findViewById(R.id.txtProductDiscription);
            btnBuy = itemView.findViewById(R.id.btnBuy);
        }
    }
}

