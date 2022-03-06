package com.example.akmretail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.akmretail.Adapter.MySingleton;
import com.example.akmretail.Modal.OPro;
import com.example.akmretail.Prevalant.Prevalant;
import com.example.akmretail.View_Holder.catagorySearchVewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class SeeBuyingActivity extends AppCompatActivity {

    private TextView OD_Name, OD_Number, OD_Address, OD_City, OD_Pin, OD_TotalAmount, OD_Date, OD_Time, OD_DD;
    private Button ReturnBB;
    private String ProductPID, T;
    private ImageView back;
    private RelativeLayout llll;
    private RecyclerView RePro;
    private RecyclerView.LayoutManager layoutManager;

    private JsonObjectRequest jsonObjectRequest;
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAaG8jxA0:APA91bHr9CESgTqyuMCv0x5YQ5pEVgoW9C0pu3tdArtOKVBRKXMTV9YVPRbdXtUItQcfaI-q_5JT3kKzbD2QblnN1Dm17cWQFzLIoQbWzRz0jqxsoBnmh0MBIK1AuraD-5AH9qMq05Ll";

    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_buying);

        ProductPID = getIntent().getStringExtra("PPIdd");

        back = findViewById(R.id.back_sett_seeeeee);

        OD_Name = findViewById(R.id.see_order_name_BB);
        OD_Number = findViewById(R.id.see_order_PID_BB);
        OD_Address = findViewById(R.id.see_order_order_number_BB);
        OD_City = findViewById(R.id.see_order_login_number_BB);
        OD_Pin = findViewById(R.id.see_order_address_BB);
        OD_TotalAmount = findViewById(R.id.see_order_amount_BB);
        OD_Date = findViewById(R.id.see_order_type_BB);
        OD_Time = findViewById(R.id.see_order_orderNo_BB);
        OD_DD = findViewById(R.id.see_order_status_BB);

        RePro = findViewById(R.id.UserROSeeOrderUU);
        RePro.setHasFixedSize(true);
        RePro.setItemViewCacheSize(20);
        RePro.setDrawingCacheEnabled(true);
        RePro.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManager = new LinearLayoutManager(SeeBuyingActivity.this);
        RePro.setLayoutManager(layoutManager);

        ReturnBB = findViewById(R.id.Return_BB);

        Paper.book().write(Prevalant.CheckH, "A");
        Window window = SeeBuyingActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        getOrr(ProductPID);

        back.setOnClickListener(v -> {

            Intent i = new Intent(SeeBuyingActivity.this, HomeActivity.class);
            i.putExtra("eeee", "BA");
            startActivity(i);

        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        getOrr(ProductPID);

    }

    private void getOrr(String orderPID) {

        DatabaseReference reffreref = FirebaseDatabase.getInstance().getReference().child("ReOrder").child(orderPID);

        reffreref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String Name = dataSnapshot.child("Name").getValue().toString();
                    String NNO = dataSnapshot.child("PhoneNumber").getValue().toString();
                    String ONO = dataSnapshot.child("ONO").getValue().toString();
                    String Address = dataSnapshot.child("Address").getValue().toString();
                    String PID = dataSnapshot.child("PID").getValue().toString();
                    String Amount = dataSnapshot.child("TotalAmount").getValue().toString();
                    String City = dataSnapshot.child("City").getValue().toString();
                    String Pin = dataSnapshot.child("Pin").getValue().toString();
                    String Return = dataSnapshot.child("Return").getValue().toString();
                    String Date = dataSnapshot.child("Date").getValue().toString();
                    String Time = dataSnapshot.child("Time").getValue().toString();
                    String PNO = dataSnapshot.child("PNO").getValue().toString();

                    String Buy = dataSnapshot.child("Buy").getValue().toString();

                    OD_Name.setText("Name: " + Name);
                    OD_Number.setText("Number: " + NNO);
                    OD_Address.setText("Address: " + Address);
                    OD_City.setText("City:" + City);
                    OD_Pin.setText("Pin: " + Pin);
                    OD_TotalAmount.setText("Total Amount: ₹" + Amount);
                    OD_Date.setText("Date: " + Date);
                    OD_Time.setText("Time: " + Time);

                    getProd(Buy, PNO);

                    if(Return.equals("Complete")){
                        ReturnBB.setText("Return");
                    }

                    if(Return.equals("A")){
                        ReturnBB.setText("Cancel");
                    }

                    if(Return.equals("Return")){

                        ReturnBB.setText("Your Return is Accepted...Please Wait For Our Response");
                        ReturnBB.setClickable(false);

                    }

                    if(Return.equals("Done")){

                        ReturnBB.setText("You Return this Order....");
                        ReturnBB.setClickable(false);

                    }

                    if(Return.equals("Cancel")){

                        ReturnBB.setText("You Cancel this Order....");
                        ReturnBB.setClickable(false);

                    }

                    ReturnBB.setOnClickListener(v -> {

                        if(Return.equals("Complete")){
                            AlertDialog.Builder aD = new AlertDialog.Builder(SeeBuyingActivity.this);
                            aD.setMessage("Note : Return Will Not Applicable if The Packaging was Not Damaged." + "\n" + "Are You Sure, Your Wanna Return this Order..");
                            aD.setPositiveButton("Yes", (dialog, which) -> {

                                HashMap<String, Object> ll = new HashMap<>();

                                ll.put("Return", "Return");

                                reffreref.updateChildren(ll).addOnCompleteListener(task -> {

                                    GetToken(Return);

                                    Toast.makeText(SeeBuyingActivity.this, "Your Return is Accepted...Please Wait For Our Response", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                });

                            });
                            aD.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

                            AlertDialog alertDialog = aD.create();
                            alertDialog.show();
                        } else if(Return.equals("A")){

                            AlertDialog.Builder aD = new AlertDialog.Builder(SeeBuyingActivity.this);
                            aD.setMessage("Are You Sure, Your Wanna Cancel this Order..");
                            aD.setPositiveButton("Yes", (dialog, which) -> {

                                UpCo(Amount, Return, orderPID);

                                dialog.dismiss();

                            });
                            aD.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

                            AlertDialog alertDialog = aD.create();
                            alertDialog.show();

                        } else if(Return.equals("Cancel")) {

                            Toast.makeText(SeeBuyingActivity.this, "Canceled by the User", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(SeeBuyingActivity.this, "Please Wait For Our Response", Toast.LENGTH_SHORT).show();

                        }

                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void UpCo(String totalPrice, String aReturn, String orderPID) {

        DatabaseReference User = FirebaseDatabase.getInstance().getReference().child("ReUser").child(Paper.book().read(Prevalant.UserIdA));

        DatabaseReference Areffreref = FirebaseDatabase.getInstance().getReference().child("ReOrder").child(orderPID);

        User.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String hh = dataSnapshot.child("Reward").getValue().toString();

                    int tp = Integer.parseInt(totalPrice);
                    int kl = tp - 15;
                    int jk = kl/10;

                    String Op = String.valueOf(jk);

                    int pp = Integer.parseInt(hh) - Integer.parseInt(Op);

                    String oo = String.valueOf(pp);

                    DatabaseReference rty = FirebaseDatabase.getInstance().getReference().child("ReUser").child(Paper.book().read(Prevalant.UserIdA));

                    HashMap<String, Object> lo = new HashMap<>();

                    lo.put("Reward", oo);

                    rty.updateChildren(lo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            HashMap<String, Object> ll = new HashMap<>();

                            ll.put("Return", "Cancel");

                            Areffreref.updateChildren(ll).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    GetToken(aReturn);

                                    Toast.makeText(SeeBuyingActivity.this, "Cancellation Done", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void GetToken(String r) {

        DatabaseReference fh = FirebaseDatabase.getInstance().getReference().child("AdminTokens");

        fh.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    try {

                        AId((Map<String, Object>) snapshot.getValue(), r);

                    } catch (Exception e) {

                        Toast.makeText(SeeBuyingActivity.this, "gg" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void AId(Map<String, Object> value, String r) {

        ArrayList<String> Tomby = new ArrayList<>();

        for (Map.Entry<String, Object> en : value.entrySet()) {

            Map SPro = (Map) en.getValue();

            String oo = String.valueOf(SPro.get("Token"));

            Tomby.add(oo);

        }

        Hathi(Tomby, r);

    }

    private void Hathi(ArrayList<String> tomby, String r) {

        int Poppy = tomby.size();

        for(int peela = 0; peela < Poppy; peela++){

            String geela = tomby.get(peela);

            String To = geela;

            if(r.equals("A")){
                String Ti = "Cancellation Alert";
                String Me = "You Have A Cancel Order....Please Check It..";
                sendNotificationss(To, Ti, Me);
            }else {
                String Ti = "Return Alert";
                String Me = "You Have A Return Order....Please Check It..";
                sendNotificationss(To, Ti, Me);
            }

        }

    }

    private void sendNotificationss(String to, String ti, String me) {

        TOPIC = to; //topic must match with what the receiver subscribed to
        NOTIFICATION_TITLE = ti;
        NOTIFICATION_MESSAGE = me;

        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title", NOTIFICATION_TITLE);
            notifcationBody.put("message", NOTIFICATION_MESSAGE);

            notification.put("to", TOPIC);
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        sendNotification(notification);

    }

    private void sendNotification(JSONObject notification) {

        jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SeeBuyingActivity.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }



    private void getProd(String buy, String NT) {

        DatabaseReference OrREF = FirebaseDatabase.getInstance().getReference().child("ReOrderList").child(Paper.book().read(Prevalant.UserIdA))
                .child(buy).child(NT);

        FirebaseRecyclerOptions<OPro> OrOption =
                new FirebaseRecyclerOptions.Builder<OPro>()
                        .setQuery(OrREF, OPro.class)
                        .build();

        FirebaseRecyclerAdapter<OPro, catagorySearchVewHolder> OrAdapter =
                new FirebaseRecyclerAdapter<OPro, catagorySearchVewHolder>(OrOption) {
                    @Override
                    protected void onBindViewHolder(@NonNull catagorySearchVewHolder Holder, int i, @NonNull OPro Layout) {

                        String V = Layout.getPVar();
                        String N = Layout.getPNum();

                        if(!V.equals("AA")){
                            Holder.tltProductColor.setText(Layout.getPVar());
                        }else if(!N.equals("AA")){
                            Holder.tltProductColor.setText(Layout.getPNum());
                        }else{
                            Holder.tltProductColor.setVisibility(View.INVISIBLE);
                        }

                        Holder.tltProductName.setText(Layout.getPName());
                        Holder.tltProductPrice.setText(Layout.getPPri());
                        Holder.tltProductCompany.setText(Layout.getPCom());
                        Holder.tltProductQuantity.setText(Layout.getPQut());
                        Picasso.get().load(Layout.getPImage()).fit().centerCrop().into(Holder.tltProductImage);

                    }

                    @NonNull
                    @Override
                    public catagorySearchVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_resource_file, parent, false);
                        return new catagorySearchVewHolder(view);
                    }
                };

        RePro.setAdapter(OrAdapter);
        OrAdapter.startListening();

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SeeBuyingActivity.this, HomeActivity.class);
        i.putExtra("eeee", "BA");
        startActivity(i);
    }
}