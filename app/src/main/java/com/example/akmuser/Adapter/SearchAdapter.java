package com.example.akmuser.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akmuser.HomeActivity;
import com.example.akmuser.Modal.Product;
import com.example.akmuser.Prevalant.Prevalant;
import com.example.akmuser.R;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;

import io.paperdb.Paper;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Viewholder> {

    private ArrayList<Product> Modals;
    private String CheckIt;
    private String orderNo;
    private Context context;

    public SearchAdapter(Context con, ArrayList<Product> categoryModals, String s) {
        this.Modals = categoryModals;
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

        try {

            String S1 = Modals.get(position).getS1();
            String S2 = Modals.get(position).getS2();
            String S3 = Modals.get(position).getS3();
            String S4 = Modals.get(position).getS4();
            String S5 = Modals.get(position).getS5();


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
            String ne = new DecimalFormat("#.#").format(Total_S);
            String Total_Star = String.valueOf(ne);

            holder.BtatProductName.setText(Modals.get(position).getProductName());
            holder.BtatProductPrice.setText(Modals.get(position).getPrice());
            holder.BtatProductSnR.setText(Modals.get(position).getCompany());
            holder.BtatProductColor.setVisibility(View.INVISIBLE);
            holder.BtatDRating.setText(Total_Star);
            Picasso.get().load(Modals.get(position).getImage1()).fit().centerCrop().into(holder.BimageaView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Paper.book().write(Prevalant.ProductId, Modals.get(position).getPid());
                    Intent i = new Intent(context, HomeActivity.class);
                    i.putExtra("eeee", "PDA");
                    context.startActivity(i);

                }
            });

        }catch (Exception e){
            Toast.makeText(context, "ghghghgh" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return Modals.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{

        public TextView BtatProductName, BtatProductColor, BtatProductSnR, BtatProductPrice, BtatDRating;
        public ImageView BimageaView;

        public Viewholder(View itemview){
            super(itemview);
            BimageaView = itemView.findViewById(R.id.Product_Image_II_PPPP);

            BtatProductPrice = itemview.findViewById(R.id.Product_Price_II_PPPP);
            BtatProductName = itemView.findViewById(R.id.Product_Name_II_PPPP);
            BtatDRating = itemView.findViewById(R.id.setDasiReading_PS_II_PPPP);
            BtatProductSnR = itemView.findViewById(R.id.Snr_PP_BBBB_II_PPPP);
            BtatProductColor = itemView.findViewById(R.id.Color_PP_BBBB_II_PPPP);
        }
    }

}