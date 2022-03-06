package com.example.akm_admin.View_Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akm_admin.Interface.itermClickListner;
import com.example.akm_admin.R;

public class catagorySearchVewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tltProductName, tltProductPrice, tltProductQuantity, tltProductCompany, tltProductColor;
    public ImageView tltProductImage;
    com.example.akm_admin.Interface.itermClickListner itermClickListner;

    public catagorySearchVewHolder(@NonNull View itemView) {
        super(itemView);

        tltProductName = itemView.findViewById(R.id.Name_CartSSAA);
        tltProductPrice = itemView.findViewById(R.id.Price_CartSSAA);
        tltProductQuantity = itemView.findViewById(R.id.Quantity_CartSSAA);
        tltProductColor = itemView.findViewById(R.id.Color_CartSSAA);
        tltProductCompany = itemView.findViewById(R.id.Company_CartSSAA);
        tltProductImage = itemView.findViewById(R.id.Image_CartSSAA);
    }
    @Override
    public void onClick(View v) {
        com.example.akm_admin.Interface.itermClickListner.onClick(v, getAdapterPosition(), false);
    }

    public void setItermClickListner(itermClickListner itermClickListner) {
        this.itermClickListner = itermClickListner;
    }
}