package com.example.akmuser.View_Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akmuser.Interface.itermClickListner;
import com.example.akmuser.R;

public class productGridLayout extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tatProductName, tatProductDescription, tatProductPrice, tatProductColor;
    public ImageView imageaView;
    public itermClickListner listner;

    public productGridLayout(@NonNull View itemView) {
        super(itemView);

        imageaView = itemView.findViewById(R.id.image_Product);
        tatProductName = itemView.findViewById(R.id.name_Product);
        tatProductPrice = itemView.findViewById(R.id.price_Product);
        tatProductColor = itemView.findViewById(R.id.colo_product);
    }

    @Override
    public void onClick(View v) {

    }

    public void setitemClickListner(itermClickListner listner) {
        this.listner = listner;
    }
}
