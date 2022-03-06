package com.example.akmuser.View_Holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akmuser.Interface.itermClickListner;
import com.example.akmuser.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView ctName, ctNumber, ctAddress;
    public TextView ctSeeIt;
    com.example.akmuser.Interface.itermClickListner itermClickListner;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        ctName = itemView.findViewById(R.id.Product_Name_PP_SS);
        ctAddress = itemView.findViewById(R.id.Address_BB_SS);
        ctNumber = itemView.findViewById(R.id.setDasiReading_PS_PP_SS);

        ctSeeIt = itemView.findViewById(R.id.Edit_PP_SS);

    }

    @Override
    public void onClick(View v) {
        com.example.akmuser.Interface.itermClickListner.onClick(v, getAdapterPosition(), false);
    }

    public void setItermClickListner(com.example.akmuser.Interface.itermClickListner itermClickListner) {
        this.itermClickListner = itermClickListner;
    }
}
