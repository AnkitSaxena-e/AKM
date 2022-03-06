package com.example.akm_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akm_admin.Adapter.BillAdapter;
import com.example.akm_admin.Modal.OPro;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreatePDFActivity extends AppCompatActivity {

    private TextView CustomerName, CustomerNumber, CustomerAddress, CustomerDate, CustomerOrderNo, CustomerTotalPri;
    private RecyclerView CustomerRecy;
    private RecyclerView.LayoutManager layoutManager;
    private Button CC;
    private android.app.Dialog Dialog;
    private String PPP;
    private ArrayList<OPro> PList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_p_d_f);

        PPP = getIntent().getStringExtra("PPP");

        CC = findViewById(R.id.Butt);
        CustomerName = findViewById(R.id.CusNameAA);
        CustomerNumber = findViewById(R.id.CusNumberAA);
        CustomerAddress = findViewById(R.id.CusAddressAA);
        CustomerDate = findViewById(R.id.CusDateAA);
        CustomerOrderNo = findViewById(R.id.CusOrderNoAA);
        CustomerTotalPri = findViewById(R.id.CusTotalPriAA);

        CustomerRecy = findViewById(R.id.CusRecyclerAA);
        CustomerRecy.setHasFixedSize(true);
        CustomerRecy.setItemViewCacheSize(20);
        CustomerRecy.setDrawingCacheEnabled(true);
        CustomerRecy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManager = new LinearLayoutManager(CreatePDFActivity.this);
        CustomerRecy.setLayoutManager(layoutManager);

        SetOrderDetail(PPP);

        CC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Generator();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        SetOrderDetail(PPP);

    }

    private void SetOrderDetail(String pid) {

        final DatabaseReference oAdminA = FirebaseDatabase.getInstance().getReference()
                .child("Order").child(pid);

        oAdminA.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    try {

                        String Name = dataSnapshot.child("Name").getValue().toString();
                        String NNO = dataSnapshot.child("PhoneNumber").getValue().toString();
                        String ONO = dataSnapshot.child("ONO").getValue().toString();
                        String Address = dataSnapshot.child("Address").getValue().toString();
                        String Amount = dataSnapshot.child("TotalAmount").getValue().toString();
                        String City = dataSnapshot.child("City").getValue().toString();
                        String Pin = dataSnapshot.child("Pin").getValue().toString();
                        String Date = dataSnapshot.child("Date").getValue().toString();
                        String Buy = dataSnapshot.child("Buy").getValue().toString();
                        String PPPPK = dataSnapshot.child("PNO").getValue().toString();

                        CustomerName.setText(Name);
                        CustomerNumber.setText(NNO);
                        CustomerAddress.setText(Address  + ", " + Pin + ", " + City);
                        CustomerDate.setText(Date);
                        CustomerOrderNo.setText("35623562456435643");
                        CustomerTotalPri.setText("Total Amount: ₹" + Amount);

                        ProductPPID(Buy, ONO, PPPPK);

                    }catch (Exception e){
                        Toast.makeText(CreatePDFActivity.this, "aaaaa " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ProductPPID(String buy, String ono, String ppppk) {

        DatabaseReference OrREF = FirebaseDatabase.getInstance().getReference().child("OrderList").child(ono).child(buy).child(ppppk);

        if(!OrREF.equals(null)){

            OrREF.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        PList = new ArrayList<>();

                        for (DataSnapshot ss : dataSnapshot.getChildren()) {
                            PList.add(ss.getValue(OPro.class));
                        }

                        BillAdapter adapter = new BillAdapter(CreatePDFActivity.this, PList);
                        CustomerRecy.setAdapter(adapter);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }

    private void Generator(){

        PdfGenerator.getBuilder().setContext(CreatePDFActivity.this)
                .fromViewIDSource().fromViewID(CreatePDFActivity.this, R.id.PDFPDF)
                .setPageSize(PdfGenerator.PageSize.A4).setFileName("YYYY")
                .setFolderName("APKParser/PDF").openPDFafterGeneration(true)
                .build(new PdfGeneratorListener() {
                    @Override
                    public void onStartPDFGeneration() {
                        Toast.makeText(CreatePDFActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinishPDFGeneration() {
                        Toast.makeText(CreatePDFActivity.this, "Finish", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(FailureResponse failureResponse) {
                        super.onFailure(failureResponse);
                        Toast.makeText(CreatePDFActivity.this, "Error " + failureResponse.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}