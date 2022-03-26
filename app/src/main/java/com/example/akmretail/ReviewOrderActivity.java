package com.example.akmretail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.example.akmretail.Prevalant.OrderPrevalent;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.paperdb.Paper;

public class ReviewOrderActivity extends AppCompatActivity {
    
    private RecyclerView SeeOrder;
    private RecyclerView.LayoutManager llll;
    private TextView RName, RAddress, TotalNoOfItem, RPin, RNumber, TTP, DDP, TTTPPP;
    private RelativeLayout COD, Online;
    private Button PlaceOr;
    private CheckBox CODCheck, OnlineCheck;
    private String PPPPPP = "", COnl = "COD", BuyFFFF, PPNNOO;;
    private final int UPI_PAYMENT = 0;

    private int Total_price_Number = 0, deliveryRate = 15, TotalPrice = 0;

    private Dialog LoadingBar;
    private String Keyword, CheckC = "No", TypeBuy = "A";
    private int check = 0;
    private int k = 0;
    private int j = 5;
    private int h = 0;
    private JsonObjectRequest jsonObjectRequest;
    private int m = 0;
    private ImageView back;
    private int n = 0;
    private int f = 0;
    private int d = 0;
    private int a = 0;
    private int b = 0;

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "-------------------------------------------------";

    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);
        
        PPPPPP = getIntent().getStringExtra("PPPP");
        BuyFFFF = getIntent().getStringExtra("BuyFFF");
        
        SeeOrder = findViewById(R.id.ROSeeOrderUU);
        SeeOrder.setHasFixedSize(true);
        SeeOrder.setItemViewCacheSize(20);
        SeeOrder.setDrawingCacheEnabled(true);
        SeeOrder.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        llll = new LinearLayoutManager(ReviewOrderActivity.this);
        SeeOrder.setLayoutManager(llll);

        back = findViewById(R.id.back_sett_rev_order);
        
        RName = findViewById(R.id.ROresourseName);
        RPin = findViewById(R.id.ROresoursePin);
        RAddress = findViewById(R.id.ROresourseAddress);
        RNumber = findViewById(R.id.ROresourseNumber);
        
        COD = findViewById(R.id.ROCODoooo);
        Online = findViewById(R.id.ROOnlinePoooo);
        
        PlaceOr = findViewById(R.id.ROPlaceOrder);

        TTP = findViewById(R.id.priceRateG);
        DDP = findViewById(R.id.deliveryPriceG);
        TTTPPP = findViewById(R.id.priceTotalRateG);

        CODCheck = findViewById(R.id.ROCODCheck);
        OnlineCheck = findViewById(R.id.ROOnlineCheck);
        TotalNoOfItem = findViewById(R.id.priceitrmG);

        Window window = ReviewOrderActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.parseColor("#093145"));

        Paper.book().write(Prevalant.CheckH, "A");
        LoadingBar = new Dialog(ReviewOrderActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);

        Review();

        COD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TypeBuy = "COD";

                if(a == 0){

                    COnl = "COD";
                    PlaceOr.setText("PlaceOrder");
                    PlaceOr.setEnabled(true);
                    PlaceOr.setBackgroundColor(Color.parseColor("#5a98ec"));
                    CODCheck.setChecked(true);
                    OnlineCheck.setChecked(false);

                    a = 1;

                }else{

                    COnl = "Online";
                    PlaceOr.setText("Pay Online");
                    PlaceOr.setEnabled(true);
                    PlaceOr.setBackgroundColor(Color.parseColor("#5a98ec"));
                    CODCheck.setChecked(false);
                    OnlineCheck.setChecked(true);

                    a = 0;

                }
            }
        });

        Online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TypeBuy = "Online Pay";

                if(b == 0){

                    COnl = "Online";
                    PlaceOr.setText("Pay Online");
                    PlaceOr.setEnabled(true);
                    PlaceOr.setBackgroundColor(Color.parseColor("#5a98ec"));
                    CODCheck.setChecked(false);
                    OnlineCheck.setChecked(true);

                    b = 1;

                }else{

                    COnl = "COD";
                    PlaceOr.setText("PlaceOrder");
                    PlaceOr.setEnabled(true);
                    PlaceOr.setBackgroundColor(Color.parseColor("#5a98ec"));
                    CODCheck.setChecked(true);
                    OnlineCheck.setChecked(false);

                    b = 0;
                }
            }
        });

        if(!CODCheck.isChecked() && !OnlineCheck.isChecked()){
            PlaceOr.setEnabled(false);
        }

        SetAddre();
        
        if(!CODCheck.isChecked() && !OnlineCheck.isChecked()){
            PlaceOr.setClickable(false);
        }
        
        PlaceOr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoadingBar.show();
                
                if(COnl.equals("COD")){
                    ConfermOrder(PPPPPP);
                }
                else{
                    OnlinePay();
                }
                
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ReviewOrderActivity.this, HomeActivity.class);
                i.putExtra("eeee", "PDA");
                startActivity(i);

            }
        });
        
    }

    private void Review() {

        if(BuyFFFF.equals("Buy")){

            DatabaseReference OrREF = FirebaseDatabase.getInstance().getReference().child("ReOrderList")
                    .child(Paper.book().read(Prevalant.UserIdA)).child("Buy").child(Paper.book().read(Prevalant.BNum));

            FirebaseRecyclerOptions<OPro> OrOption =
                    new FirebaseRecyclerOptions.Builder<OPro>()
                            .setQuery(OrREF, OPro.class)
                            .build();

            FirebaseRecyclerAdapter<OPro, catagorySearchVewHolder> OrAdapter =
                    new FirebaseRecyclerAdapter<OPro, catagorySearchVewHolder>(OrOption) {
                        @SuppressLint("SetTextI18n")
                        @Override
                        protected void onBindViewHolder(@NonNull catagorySearchVewHolder Holder, int i, @NonNull OPro Layout) {

                            String ll = Layout.getPVar();
                            String lll = Layout.getPNum();

                            if(!ll.equals("AA")){
                                Holder.tltProductColor.setText(Layout.getPVar());
                            }else if(!lll.equals("AA")){
                                Holder.tltProductColor.setText(Layout.getPNum());
                            }else {
                                Holder.tltProductColor.setVisibility(View.INVISIBLE);
                            }

                            Holder.tltProductName.setText(Layout.getPName());
                            Holder.tltProductPrice.setText(Layout.getPPri());
                            Holder.tltProductCompany.setText(Layout.getPCom());
                            Holder.tltProductQuantity.setText("Qut. " + Layout.getPQut());
                            Picasso.get().load(Layout.getPImage()).fit().centerCrop().into(Holder.tltProductImage);

                            TotalNoOfItem.setText("Price(1 item)");

                            String P = Layout.getPPri();
                            String Q = Layout.getPQut();

                            int Int = Integer.parseInt(P) * Integer.parseInt(Q);

                            TTP.setText(String.valueOf(Int));
                            DDP.setText("₹15");

                            int H = Int + 15;

                            TTTPPP.setText(String.valueOf(H));

                        }

                        @NonNull
                        @Override
                        public catagorySearchVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_resource_file, parent, false);
                            catagorySearchVewHolder holder = new catagorySearchVewHolder(view);
                            return holder;
                        }
                    };

            SeeOrder.setAdapter(OrAdapter);
            OrAdapter.startListening();

        }

        if(BuyFFFF.equals("Cart")){

            DatabaseReference OrREF = FirebaseDatabase.getInstance().getReference().child("OrderList")
                    .child(Paper.book().read(Prevalant.UserIdA)).child("Cart").child(Paper.book().read(Prevalant.CNum));

            FirebaseRecyclerOptions<OPro> OrOption =
                    new FirebaseRecyclerOptions.Builder<OPro>()
                            .setQuery(OrREF, OPro.class)
                            .build();

            FirebaseRecyclerAdapter<OPro, catagorySearchVewHolder> OrAdapter =
                    new FirebaseRecyclerAdapter<OPro, catagorySearchVewHolder>(OrOption) {
                        @Override
                        protected void onBindViewHolder(@NonNull catagorySearchVewHolder Holder, int i, @NonNull OPro Layout) {

                            check++;

                            Holder.tltProductName.setText(Layout.getPName());
                            Holder.tltProductPrice.setText(Layout.getPPri());
                            Holder.tltProductCompany.setText(Layout.getPCom());
                            Holder.tltProductQuantity.setText(Layout.getPQut());
                            Picasso.get().load(Layout.getPImage()).fit().centerCrop().into(Holder.tltProductImage);

                            if(!Layout.getPNum().equals("AA") && Layout.getPVar().equals("AA")){
                                Holder.tltProductColor.setText(Layout.getPNum());
                            }else if(Layout.getPNum().equals("AA") && !Layout.getPVar().equals("AA")){
                                Holder.tltProductColor.setText(Layout.getPVar());
                            }else if(!Layout.getPNum().equals("AA") && !Layout.getPVar().equals("AA")){
                                Holder.tltProductColor.setText(Layout.getPNum() + " / " + Layout.getPVar());
                            }else{
                                Holder.tltProductColor.setVisibility(View.INVISIBLE);
                            }

                            String P = Layout.getPPri();
                            String Q = Layout.getPQut();

                            int oneTypeProductPrice = Integer.parseInt(P) * Integer.parseInt(Q);
                            Total_price_Number = Total_price_Number + oneTypeProductPrice;

                            TotalNoOfItem.setText("Price(" + check +" item)");

                            TotalPrice = Total_price_Number + deliveryRate;

                            TTTPPP.setText("₹" + TotalPrice);

                            TTP.setText("₹" + String.valueOf(Total_price_Number));
                            DDP.setText("₹15");

                        }

                        @NonNull
                        @Override
                        public catagorySearchVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_resource_file, parent, false);
                            catagorySearchVewHolder holder = new catagorySearchVewHolder(view);
                            return holder;
                        }
                    };

            SeeOrder.setAdapter(OrAdapter);
            OrAdapter.startListening();

        }

    }

    private void OnlinePay() {

        String PayName = Paper.book().read(OrderPrevalent.NameP);
        String PayID = "7987777143@ybl";
        String PayAmount = Paper.book().read(OrderPrevalent.TotalPrice);
        String PayText = "Payment to AKM";

        if (TextUtils.isEmpty(PayAmount)) {
            Toast.makeText(ReviewOrderActivity.this, "Something Went Wrong Please Try Again", Toast.LENGTH_SHORT).show();
        } else {
            payUsingUpi(PayName, PayID, PayText, PayAmount);
        }

    }

    private void payUsingUpi(String payName, String payID, String payText, String payAmount) {

        Uri uri = Uri.parse("upi://pay").buildUpon()

                .appendQueryParameter("pa", payID)
                .appendQueryParameter("pn", payName)
                .appendQueryParameter("tn", payText)
                .appendQueryParameter("am", payAmount)
                .appendQueryParameter("cu", "INR")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW, uri);

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        if(chooser.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(ReviewOrderActivity.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }

    private void SetAddre() {
        
        RName.setText(Paper.book().read(OrderPrevalent.NameP));
        RPin.setText(Paper.book().read(OrderPrevalent.PinP));
        RAddress.setText(Paper.book().read(OrderPrevalent.AddressP));
        RNumber.setText(Paper.book().read(OrderPrevalent.NumberP));
        
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        LoadingBar.dismiss();
                        String trxt = data.getStringExtra("response");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(ReviewOrderActivity.this)) {
            String str = data.get(0);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(ReviewOrderActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();

                ConfermOrder(PPPPPP);

            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                LoadingBar.dismiss();
                Toast.makeText(ReviewOrderActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                LoadingBar.dismiss();
                Toast.makeText(ReviewOrderActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            LoadingBar.dismiss();
            Toast.makeText(ReviewOrderActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void ConfermOrder(String PP) {

        try {

            final String SaveCurruntTime, SaveCurruntDate, Time;

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
            SaveCurruntDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat(" hh:mm:ss a");
            SaveCurruntTime = currentTime.format(calendar.getTime());

            Time = SaveCurruntDate + SaveCurruntTime;

            if(BuyFFFF.equals("Buy")){
                PPNNOO = Paper.book().read(Prevalant.BNum);
            }
            if(BuyFFFF.equals("Cart")){
                PPNNOO = Paper.book().read(Prevalant.CNum);
            }

            Toast.makeText(this, "PNO " + PPNNOO, Toast.LENGTH_SHORT).show();

            String total_price = Paper.book().read(OrderPrevalent.TotalPrice);
            String name = Paper.book().read(OrderPrevalent.NameP);
            String number = Paper.book().read(OrderPrevalent.NumberP);
            String address = Paper.book().read(OrderPrevalent.AddressP);
            String city = Paper.book().read(OrderPrevalent.CityP);
            String pin = Paper.book().read(OrderPrevalent.PinP);
            String qqqqq = Paper.book().read(OrderPrevalent.Quantity);

            final HashMap<String, Object> ordersMap = new HashMap<>();
            ordersMap.put("TotalAmount", total_price);
            ordersMap.put("Name", name);
            ordersMap.put("PhoneNumber", number);
            ordersMap.put("Address", address);
            ordersMap.put("City", city);
            ordersMap.put("Pin", pin);
            ordersMap.put("Buy", BuyFFFF);
            ordersMap.put("Return", "A");
            ordersMap.put("OType", TypeBuy);
            ordersMap.put("ByUser", Paper.book().read(Prevalant.UserIdA));
            ordersMap.put("ONO", Paper.book().read(Prevalant.UserIdA));
            ordersMap.put("PID", Time);
            ordersMap.put("PNO", PPNNOO);
            ordersMap.put("Doc", "A");
            ordersMap.put("Date", SaveCurruntDate);
            ordersMap.put("Time", SaveCurruntTime);
            ordersMap.put("DeliveryDetail", "--/--/--");

            GetToken(Time, ordersMap);

            final DatabaseReference orderRefGG = FirebaseDatabase.getInstance().getReference().child("ReOrder").child(Time);

            orderRefGG.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("ReUser").child(Paper.book().read(Prevalant.UserIdA));

                    String ll = UUID.randomUUID().toString().substring(0, 18);

                    Toast.makeText(ReviewOrderActivity.this, "Buy " + ll, Toast.LENGTH_SHORT).show();
                    HashMap<String, Object> UM = new HashMap<>();

                    if(BuyFFFF.equals("Buy")) {
                        UM.put("Buy", ll);
                    }
                    if(BuyFFFF.equals("Cart")){
                        UM.put("Cart", ll);
                    }

                    userRef.updateChildren(UM).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ReviewOrderActivity.this, "Your Order Has Been Placed Successfully", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(ReviewOrderActivity.this, OrderPlacedActivity.class);
                            LoadingBar.dismiss();
                            startActivity(i);
                        }
                    });
                }
            });

        }catch (Exception e){
            Toast.makeText(ReviewOrderActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void GetToken(String time, HashMap<String, Object> ordersMap) {

        DatabaseReference fh = FirebaseDatabase.getInstance().getReference().child("AdminTokens");

        fh.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    try {

                        AId((Map<String, Object>) snapshot.getValue(), ordersMap, time);

                    } catch (Exception e) {

                        Toast.makeText(ReviewOrderActivity.this, "gg" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void AId(Map<String, Object> value, HashMap<String, Object> ordersMap, String time) {

        ArrayList<String> Tomby = new ArrayList<>();

        for (Map.Entry<String, Object> en : value.entrySet()) {

            Map SPro = (Map) en.getValue();

            String oo = String.valueOf(SPro.get("Token"));

            Tomby.add(oo);

        }

        Hathi(Tomby, ordersMap, time);

    }

    private void Hathi(ArrayList<String> tomby, HashMap<String, Object> ordersMap, String time) {

        int Poppy = tomby.size();

        for(int peela = 0; peela < Poppy; peela++){

            String geela = tomby.get(peela);

            String To = geela;
            String Ti = "Order Alert";
            String Me = "You Have An Order....Please Check It..";

            sendNotificationss(To, Ti, Me, time, ordersMap);

        }

    }

    private void sendNotificationss(String to, String ti, String me, String time, HashMap<String, Object> ordersMap) {

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
        sendNotification(notification, time, ordersMap);

    }

    private void sendNotification(JSONObject notification, String time, HashMap<String, Object> ordersMap) {

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
                        Toast.makeText(ReviewOrderActivity.this, "Request error", Toast.LENGTH_LONG).show();
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

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

}
