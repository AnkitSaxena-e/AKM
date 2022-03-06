package com.example.akm_admin.View_Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akm_admin.R;

public class BillViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

     com.example.akm_admin.Interface.itermClickListner itermClickListner;
     public TextView No, PName, Qut, MRP;

    public BillViewHolder(@NonNull View itemView) {
        super(itemView);

        No = itemView.findViewById(R.id.TCusNo);
        PName = itemView.findViewById(R.id.TCusPNa);
        Qut = itemView.findViewById(R.id.TCusPQu);
        MRP = itemView.findViewById(R.id.TCusPMRP);

    }

    @Override
    public void onClick(View v) {
        com.example.akm_admin.Interface.itermClickListner.onClick(v, getAdapterPosition(), false);
    }

    public void setItermClickListner(com.example.akm_admin.Interface.itermClickListner itermClickListner) {
        this.itermClickListner = itermClickListner;
    }

}
