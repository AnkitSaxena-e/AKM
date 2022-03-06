package com.example.akmuser.View_Holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akmuser.Interface.itermClickListner;
import com.example.akmuser.R;

public class catagorySearchVewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tltProductName, tltProductPrice, tltProductQuantity, tltProductCompany, tltProductColor, tltProductSR;
    public ImageView tltProductImage;
    com.example.akmuser.Interface.itermClickListner itermClickListner;

    public catagorySearchVewHolder(@NonNull View itemView) {
        super(itemView);

        tltProductName = itemView.findViewById(R.id.Name_CartSSAA);
        tltProductPrice = itemView.findViewById(R.id.Price_CartSSAA);
        tltProductQuantity = itemView.findViewById(R.id.Quantity_CartSSAA);
        tltProductColor = itemView.findViewById(R.id.Color_CartSSAA);
        tltProductCompany = itemView.findViewById(R.id.Company_CartSSAA);
        tltProductSR = itemView.findViewById(R.id.SR_CartSSAA);
        tltProductImage = itemView.findViewById(R.id.Image_CartSSAA);
    }
    @Override
    public void onClick(View v) {
        com.example.akmuser.Interface.itermClickListner.onClick(v, getAdapterPosition(), false);
    }

    public void setItermClickListner(itermClickListner itermClickListner) {
        this.itermClickListner = itermClickListner;
    }
}