package com.example.akmuser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akmuser.Fragments.ProductDataFragment;
import com.example.akmuser.Interface.itermClickListner;
import com.example.akmuser.Modal.Product;
import com.example.akmuser.Prevalant.Prevalant;
import com.example.akmuser.R;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;

import io.paperdb.Paper;

public class FiRecyAdapter extends RecyclerView.Adapter<FiRecyAdapter.PProductGridLayout> {

    private ArrayList<Product> modal;
    private String CheckIt;
    private String Price;
    private Context context;

    public FiRecyAdapter(Context con, ArrayList<Product> categoryModals, String s) {

        context = con;
        CheckIt = s;
        modal = categoryModals;

    }

    @NonNull
    @Override
    public PProductGridLayout onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_grid_layout, parent, false);
        PProductGridLayout holder = new PProductGridLayout(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PProductGridLayout holder, int position) {

        String S1 = modal.get(position).getS1();
        String S2 = modal.get(position).getS2();
        String S3 = modal.get(position).getS3();
        String S4 = modal.get(position).getS4();
        String S5 = modal.get(position).getS5();

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

        String P = modal.get(position).getPrice();
        String PN = modal.get(position).getPrice();
        String PV = modal.get(position).getPrice();

        if(!P.equals("A")){
            Price = P;
        }
        if(!PN.equals("A")){
            Price = PN;
        }
        if(!PV.equals("A")){
            Price = PV;
        }



        Picasso.get().load(modal.get(position).getImage1()).fit().centerCrop().into(holder.imageaView);

        holder.tatProductName.setText(modal.get(position).getProductName());
        holder.tatProductPrice.setText("₹" + Price);
        holder.tatProductColor.setText(modal.get(position).getCompany());
        holder.tatProductRating.setText(Total_Star);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Paper.book().write(Prevalant.ProductId, modal.get(position).getPid());
                    FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_fragment_Home, new ProductDataFragment());
                    ft.addToBackStack(null);
                    ft.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return modal.size();
    }


    class PProductGridLayout extends RecyclerView.ViewHolder {

        public TextView tatProductName, tatProductDes, tatProductPrice, tatProductColor, tatProductRating;
        public ImageView imageaView;
        public itermClickListner listner;

        public PProductGridLayout(@NonNull View itemView) {
            super(itemView);

            imageaView = itemView.findViewById(R.id.image_Product);
            tatProductName = itemView.findViewById(R.id.name_Product);
            tatProductRating = itemView.findViewById(R.id.setDasiReading_Proo);
            tatProductPrice = itemView.findViewById(R.id.price_Product);
            tatProductColor = itemView.findViewById(R.id.colo_product);
        }
    }

}
