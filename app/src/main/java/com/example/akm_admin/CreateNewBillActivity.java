package com.example.akm_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akm_admin.Adapter.NewBillAdapter;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;

import java.util.ArrayList;

public class CreateNewBillActivity extends AppCompatActivity {

    private EditText UNa, UNo, UAd, UTP, BUPN, BUPQ, BUPP;
    private String SUNa, SUNo, SUAd, SUTP, SBUPN, SBUPQ, SBUPP;
    private TextView UBNa, UBNo, UBAd, UBTP;
    private RecyclerView reBUPr;
    private RecyclerView CustomerRecy;
    private RecyclerView.LayoutManager layoutManager;
    private Button CB, GB, RB;
    private ArrayList<ArrayList<String>> AppPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_bill);

        UNa = findViewById(R.id.BUserName);
        UNo = findViewById(R.id.BUserNo);
        UAd = findViewById(R.id.BUserAdd);
        UTP = findViewById(R.id.BUserTP);

        BUPN = findViewById(R.id.BUserProductN);
        BUPQ = findViewById(R.id.BUserProductQ);
        BUPP = findViewById(R.id.BUserProductP);

        UBNa = findViewById(R.id.CusNameAAM);
        UBNo = findViewById(R.id.CusNumberAAM);
        UBAd = findViewById(R.id.CusAddressAAM);
        UBTP = findViewById(R.id.CusTotalPriAAM);

        CB = findViewById(R.id.ButtonCC);
        GB = findViewById(R.id.ButtonGG);
        RB = findViewById(R.id.ButtonPP);

        CustomerRecy = findViewById(R.id.CusRecyclerAAM);
        CustomerRecy.setHasFixedSize(true);
        CustomerRecy.setItemViewCacheSize(20);
        CustomerRecy.setDrawingCacheEnabled(true);
        CustomerRecy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManager = new LinearLayoutManager(CreateNewBillActivity.this);
        CustomerRecy.setLayoutManager(layoutManager);

        AppPro = new ArrayList<>();

        GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Generator();

            }
        });

        RB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SBUPN = BUPN.getText().toString();
                SBUPQ = BUPQ.getText().toString();
                SBUPP = BUPP.getText().toString();

                if(TextUtils.isEmpty(SBUPN)){
                    BUPN.setError("Please Enter");
                }
                else if(TextUtils.isEmpty(SBUPQ)){
                    BUPQ.setError("Please Enter");
                }
                else if(TextUtils.isEmpty(SBUPP)){
                    BUPP.setError("Please Enter");
                }
                else{
                    ArrayList<String> LL = new ArrayList<>();

                    LL.add(0, SBUPN);
                    LL.add(1, SBUPQ);
                    LL.add(2, SBUPP);

                    AppPro.add(LL);

                    NewBillAdapter adapter = new NewBillAdapter(CreateNewBillActivity.this, AppPro);
                    CustomerRecy.setAdapter(adapter);

                    BUPN.setText("");
                    BUPQ.setText("");
                    BUPP.setText("");

                }
            }
        });

        CB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SUNa = UNa.getText().toString();
                SUNo = UNo.getText().toString();
                SUAd = UAd.getText().toString();
                SUTP = UTP.getText().toString();

                if(TextUtils.isEmpty(SUNa)){
                    UNa.setError("Please Enter");
                }
                else if(TextUtils.isEmpty(SUNo)){
                    UNo.setError("Please Enter");
                }
                else if(TextUtils.isEmpty(SUAd)){
                    UAd.setError("Please Enter");
                }
                else if(TextUtils.isEmpty(SUTP)){
                    UTP.setError("Please Enter");
                }
                else {
                    UBNa.setText(SUNa);
                    UBNo.setText(SUNo);
                    UBAd.setText(SUAd);
                    UBTP.setText("Total Price - " + SUTP);
                }
            }
        });
    }

    private void Generator(){

        PdfGenerator.getBuilder().setContext(CreateNewBillActivity.this)
                .fromViewIDSource().fromViewID(CreateNewBillActivity.this, R.id.PDFPDFM)
                .setPageSize(PdfGenerator.PageSize.A4).setFileName("YYYY")
                .setFolderName("APKParser/PDF").openPDFafterGeneration(true)
                .build(new PdfGeneratorListener() {
                    @Override
                    public void onStartPDFGeneration() {
                        Toast.makeText(CreateNewBillActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinishPDFGeneration() {
                        Toast.makeText(CreateNewBillActivity.this, "Finish", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(FailureResponse failureResponse) {
                        super.onFailure(failureResponse);
                        Toast.makeText(CreateNewBillActivity.this, "Error " + failureResponse.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}