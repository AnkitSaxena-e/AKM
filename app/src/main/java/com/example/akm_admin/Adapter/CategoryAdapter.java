package com.example.akm_admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akm_admin.AdminMaintainProductActivity;
import com.example.akm_admin.Modal.Product;
import com.example.akm_admin.R;
import com.example.akm_admin.RetailMaintainProductActivity;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder> {

    private ArrayList<Product> categoryModals;
    private String CheckIt;
    private Context context;

    public CategoryAdapter(Context con, ArrayList<Product> categoryModals, String s) {
        this.categoryModals = categoryModals;
        CheckIt = s;
        context = con;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_iterm_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        String S1 = categoryModals.get(position).getS1();
        String S2 = categoryModals.get(position).getS2();
        String S3 = categoryModals.get(position).getS3();
        String S4 = categoryModals.get(position).getS4();
        String S5 = categoryModals.get(position).getS5();

        int s1 = Integer.parseInt(S1);
        int s2 = Integer.parseInt(S2);
        int s3 = Integer.parseInt(S3);
        int s4 = Integer.parseInt(S4);
        int s5 = Integer.parseInt(S5);

        int Upper = (s1) + (s2 * 2) + (s3 * 3) + (s4 * 4) + (s5 * 5);

        int Lower = s1 + s2 + s3 + s4 + s5;

        BigInteger U = new BigInteger(String.valueOf(Upper));
        BigInteger L = new BigInteger(String.valueOf(Lower));

        float Ul = U.floatValue();
        float Ll = L.floatValue();

        float Total_S = Ul / Ll;
        String Total_Star = new DecimalFormat("#.#").format(Total_S);

        Picasso.get().load(categoryModals.get(position).getImage1()).fit().centerCrop().into(holder.imageView);
        holder.txtPRating.setText(Total_Star);
        holder.txtProductName.setText(categoryModals.get(position).getProductName());
        holder.txtSnR.setVisibility(View.INVISIBLE);
        holder.txtColor.setVisibility(View.INVISIBLE);
        holder.txtPricee.setVisibility(View.INVISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckIt.equals("Re")){
                    Intent i = new Intent(context, RetailMaintainProductActivity.class);
                    i.putExtra("pid", categoryModals.get(position).getPid());
                    context.startActivity(i);
                }else {
                    Intent i = new Intent(context, AdminMaintainProductActivity.class);
                    i.putExtra("pid", categoryModals.get(position).getPid());
                    context.startActivity(i);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryModals.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        public TextView txtProductName, txtPRating, txtSnR, txtColor, txtPricee;
        public ImageView imageView;

        public Viewholder(View itemview) {
            super(itemview);
            imageView = itemview.findViewById(R.id.Product_Image_II_PPPP);
            txtPricee = itemview.findViewById(R.id.Product_Price_II_PPPP);
            txtProductName = itemview.findViewById(R.id.Product_Name_II_PPPP);
            txtColor = itemview.findViewById(R.id.Color_PP_BBBB_II_PPPP);
            txtSnR = itemview.findViewById(R.id.Snr_PP_BBBB_II_PPPP);
            txtPRating = itemview.findViewById(R.id.setDasiReading_PS_II_PPPP);
        }
    }

}
