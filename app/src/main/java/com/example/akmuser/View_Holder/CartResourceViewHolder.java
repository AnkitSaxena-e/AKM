package com.example.akmuser.View_Holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.akmuser.Interface.itermClickListner;
import com.example.akmuser.R;

public class CartResourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView Cart_Name, Cart_Quantity, Cart_Price, Cart_Color, Cart_SR;
    public ImageView Cart_Image;
    public Button EditCart, RemoveCart;
    public itermClickListner ClickListner;

    public CartResourceViewHolder(View itemView) {
        super(itemView);

        Cart_Image = itemView.findViewById(R.id.Image_CartSS);
        Cart_Name = itemView.findViewById(R.id.Name_CartSS);
        Cart_Price = itemView.findViewById(R.id.Price_CartSS);
        Cart_Color = itemView.findViewById(R.id.Color_CartSS);
        Cart_Quantity = itemView.findViewById(R.id.Quantity_CartSS);
        Cart_SR = itemView.findViewById(R.id.SR_CartSS);
        EditCart = itemView.findViewById(R.id.Edit_CartSS);
        RemoveCart = itemView.findViewById(R.id.Remove_CartSS);

    }

    @Override
    public void onClick(View v) {
        itermClickListner.onClick(v, getAdapterPosition(), false);
    }

    public void setItermClickListner(itermClickListner itermClickListner) {
        ClickListner = itermClickListner;
    }

}
