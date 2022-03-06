package com.example.akmretail.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akmretail.ConfermOrderActivity;
import com.example.akmretail.Modal.OPro;
import com.example.akmretail.Prevalant.Prevalant;
import com.example.akmretail.R;
import com.example.akmretail.View_Holder.CartResourceViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout1, relativeLayout2, relativeLayout3;
    private Button Next;
    private RecyclerView.LayoutManager layoutManager;
    private int Total_price_Number = 0, deliveryRate = 15, TotalPrice = 0;
    private TextView TotalNoOfItem, TotalNoOfItemPrice, DeliveryRate, TotalRete, R1, R2, R3;
    private ImageView empty_cart;
    private TextView empty_cart_text;
    private String Name, Order, type;
    private int check = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        Paper.init(getActivity());

        recyclerView = root.findViewById(R.id.recycler_cart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        empty_cart = root.findViewById(R.id.lkjhgfds);
        empty_cart_text = root.findViewById(R.id.poiuytr);

        R1 = root.findViewById(R.id.pricedetail);
        R2 = root.findViewById(R.id.lop);
        R3 = root.findViewById(R.id.priceitrmr);
        TotalNoOfItem = root.findViewById(R.id.priceitrm);
        TotalNoOfItemPrice = root.findViewById(R.id.priceRate);
        DeliveryRate = root.findViewById(R.id.deliveryPrice);
        TotalRete = root.findViewById(R.id.priceTotalRate);
        relativeLayout1 = root.findViewById(R.id.setRecycler);
        relativeLayout2 = root.findViewById(R.id.qwerty);
        relativeLayout3 = root.findViewById(R.id.aqwer);

        type = Paper.book().read(Prevalant.CheckAdmin).toString();
        Paper.book().write(Prevalant.CheckH, "A");

        CheckNotification();

        Cart();

        Next = root.findViewById(R.id.Next_cart);

        Next.setOnClickListener(v -> CheckAddNo());

        return root;
    }

    private void Cart() {

        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("ReOrderList").child(Paper.book().read(Prevalant.UserIdA))
                .child("Cart").child(Paper.book().read(Prevalant.CNum));

        FirebaseRecyclerOptions<OPro> options =
                new  FirebaseRecyclerOptions.Builder<OPro>()
                        .setQuery(cartListRef, OPro.class)
                        .build();

        FirebaseRecyclerAdapter<OPro, CartResourceViewHolder> adapter =
                new FirebaseRecyclerAdapter<OPro, CartResourceViewHolder>(options) {
                    @SuppressLint("SetTextI18n")
                    @Override
                    protected void onBindViewHolder(@NonNull CartResourceViewHolder holderC, int i, @NonNull final OPro cart_resource) {

                        check++;

                        holderC.Cart_Name.setText(cart_resource.getPName());
                        holderC.Cart_Price.setText("₹" + cart_resource.getPPri());
                        holderC.Cart_Quantity.setText("Qty: " + cart_resource.getPQut());
                        holderC.Cart_Color.setText(cart_resource.getPCom());

                        if(!cart_resource.getPNum().equals("AA") && cart_resource.getPVar().equals("AA")){
                            holderC.Cart_SR.setText(cart_resource.getPNum());
                        }else if(cart_resource.getPNum().equals("AA") && !cart_resource.getPVar().equals("AA")){
                            holderC.Cart_SR.setText(cart_resource.getPVar());
                        }else if(!cart_resource.getPNum().equals("AA") && !cart_resource.getPVar().equals("AA")){
                            holderC.Cart_SR.setText(cart_resource.getPNum() + " / " + cart_resource.getPVar());
                        }else{
                            holderC.Cart_SR.setVisibility(View.INVISIBLE);
                        }

                        Picasso.get().load(cart_resource.getPImage()).fit().centerCrop().into(holderC.Cart_Image);

                        String Pricee = cart_resource.getPPri();
                        String Quantityy = cart_resource.getPQut();

                        String P = Pricee.replace(",", "");
                        String Q = Quantityy.replace(",", "");

                        int oneTypeProductPrice = Integer.parseInt(P) * Integer.parseInt(Q);
                        Total_price_Number = Total_price_Number + oneTypeProductPrice;

                        TotalNoOfItem.setText("Price(" + check +" item)");

                        TotalNoOfItemPrice.setText("₹" + String.valueOf(Total_price_Number));

                        DeliveryRate.setText("₹" + String.valueOf(deliveryRate));

                        TotalPrice = Total_price_Number + deliveryRate;

                        TotalRete.setText("₹" + TotalPrice);

                        holderC.EditCart.setOnClickListener(v -> {

                            Paper.book().write(Prevalant.ProductId, cart_resource.getPPid());
                            Paper.book().write(Prevalant.CheckH, "C");
                            Paper.book().write(Prevalant.FAD, "CartA");
                            Paper.book().write(Prevalant.CheckFromCart, "Cart");
                            FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.frame_fragment_Home, new ProductDataFragment());
                            ft.addToBackStack(null);
                            ft.commit();


                        });

                        holderC.RemoveCart.setOnClickListener(v -> cartListRef.child(cart_resource.getPPid()).removeValue().addOnCompleteListener(task -> cartListRef.child(cart_resource.getPPid())
                                .removeValue().addOnCompleteListener(task1 -> Toast.makeText(getActivity(), "Item Removed Successfully",Toast.LENGTH_LONG).show())));
                    }

                    @NonNull
                    @Override
                    public CartResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_rr, parent, false);
                        return new CartResourceViewHolder(view);
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void CheckAddNo() {

        Intent i = new Intent(getActivity(), ConfermOrderActivity.class);
        i.putExtra("PPID", "ProductId");
        i.putExtra("TotalPrice", String.valueOf(TotalPrice));
        i.putExtra("BuyF", "Cart");
        i.putExtra("From", "A");
        i.putExtra("IM", "A");
        startActivity(i);

    }

    private void CheckNotification() {

        final DatabaseReference cartListRefABC = FirebaseDatabase.getInstance().getReference().child("ReOrderList").child(Paper.book().read(Prevalant.UserIdA))
                .child("Cart").child(Paper.book().read(Prevalant.CNum));

        cartListRefABC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    Next.setVisibility(View.INVISIBLE);
                    empty_cart.setVisibility(View.VISIBLE);
                    empty_cart_text.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    R1.setVisibility(View.INVISIBLE);
                    R2.setVisibility(View.INVISIBLE);
                    R3.setVisibility(View.INVISIBLE);
                    TotalNoOfItem.setVisibility(View.INVISIBLE);
                    TotalNoOfItemPrice.setVisibility(View.INVISIBLE);
                    DeliveryRate.setVisibility(View.INVISIBLE);
                    TotalRete.setVisibility(View.INVISIBLE);
                    relativeLayout1.setVisibility(View.INVISIBLE);
                    relativeLayout2.setVisibility(View.INVISIBLE);
                    relativeLayout3.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
