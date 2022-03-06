package com.example.akm_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class AdminFHShowActivity extends AppCompatActivity {

    private EditText FiFhone, SeFhone, ThFhone, FoFhone, FvFhone, SiFhone, II, Category_Name, Category_Value;
    private Button AddItt, IIU, CategoryAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_f_h_show);

        FiFhone = findViewById(R.id.FirstF);
        SeFhone = findViewById(R.id.SecondS);
        ThFhone = findViewById(R.id.ThirdT);
        FoFhone = findViewById(R.id.ForthF);
        FvFhone = findViewById(R.id.FiveF);
        SiFhone = findViewById(R.id.SixS);

        II = findViewById(R.id.InfiUP);
        IIU = findViewById(R.id.AddUp);

        AddItt = findViewById(R.id.AddFhoneF);

        Category_Name = findViewById(R.id.CategoryName);
        Category_Value = findViewById(R.id.CategoryValue);

        CategoryAdd = findViewById(R.id.AddCategory);

        UpdateAll();

        AddItt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckIt();

            }
        });

        IIU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String TT = II.getText().toString();

                if(TextUtils.isEmpty(TT)){
                    II.setError("Please Enter");
                }else{
                    UPPP(TT);
                }

            }
        });

        CategoryAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = Category_Name.getText().toString();
                String value = Category_Value.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Category_Name.setError("Please Enter");
                } else if(TextUtils.isEmpty(value)){
                    Category_Value.setError("Please Enter");
                } else {
                    AddCategory(name, value);
                }
            }
        });

    }

    private void AddCategory(String name, String value) {

        Calendar calendar = Calendar.getInstance();
        String storeCurruntDate, storeCurruntTime, productRandomKey;
        DatabaseReference ProductRef = FirebaseDatabase.getInstance().getReference().child("Category");

        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        storeCurruntDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat(" hh:mm:ss a");
        storeCurruntTime = currentTime.format(calendar.getTime());

        productRandomKey = storeCurruntDate + storeCurruntTime;

        String UID = UUID.randomUUID().toString().substring(0, 35);

        HashMap<String, Object> productMap = new HashMap<>();

        productMap.put("Pid", productRandomKey);
        productMap.put("CName", name);
        productMap.put("CValue", value);

        ProductRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(AdminFHShowActivity.this, AdminMainActivity.class);
                            startActivity(i);
                            Toast.makeText(AdminFHShowActivity.this, "Added successfully.......", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AdminFHShowActivity.this, "Error....", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void UPPP(String TT) {

        DatabaseReference reffff = FirebaseDatabase.getInstance().getReference().child("FFhones");

        HashMap<String, Object> pp = new HashMap<>();

        pp.put("Update", TT);

        reffff.updateChildren(pp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(AdminFHShowActivity.this, "Done", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void UpdateAll() {

        DatabaseReference reffff = FirebaseDatabase.getInstance().getReference().child("FFhones");

        reffff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    FiFhone.setText(snapshot.child("Fi").getValue().toString(), TextView.BufferType.EDITABLE);
                    SeFhone.setText(snapshot.child("Se").getValue().toString(), TextView.BufferType.EDITABLE);
                    ThFhone.setText(snapshot.child("Th").getValue().toString(), TextView.BufferType.EDITABLE);
                    FoFhone.setText(snapshot.child("Fo").getValue().toString(), TextView.BufferType.EDITABLE);
                    FvFhone.setText(snapshot.child("Fv").getValue().toString(), TextView.BufferType.EDITABLE);
                    SiFhone.setText(snapshot.child("Si").getValue().toString(), TextView.BufferType.EDITABLE);

                    II.setText(snapshot.child("Update").getValue().toString(), TextView.BufferType.EDITABLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void CheckIt() {

        String Fi = FiFhone.getText().toString();
        String Se = SeFhone.getText().toString();
        String Th = ThFhone.getText().toString();
        String Fo = FoFhone.getText().toString();
        String Fv = FvFhone.getText().toString();
        String Si = SiFhone.getText().toString();

        if(TextUtils.isEmpty(Fi)){
            FiFhone.setError("Please Enter");
        }else if(TextUtils.isEmpty(Se)){
            SeFhone.setError("Please Enter");
        }else if(TextUtils.isEmpty(Th)){
            ThFhone.setError("Please Enter");
        }else if(TextUtils.isEmpty(Fo)){
            FoFhone.setError("Please Enter");
        }else if(TextUtils.isEmpty(Fv)){
            FvFhone.setError("Please Enter");
        }else if(TextUtils.isEmpty(Si)){
            SiFhone.setError("Please Enter");
        }else {
            GoAdd(Fi, Se, Th, Fo, Fv, Si);
        }

    }

    private void GoAdd(String fi, String se, String th, String fo, String fv, String si) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("FFhones");

        HashMap<String, Object> ff = new HashMap<>();

        ff.put("Fi", fi);
        ff.put("Se", se);
        ff.put("Th", th);
        ff.put("Fo", fo);
        ff.put("Fv", fv);
        ff.put("Si", si);

        ref.updateChildren(ff).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(AdminFHShowActivity.this, "Done", Toast.LENGTH_SHORT).show();

            }
        });

    }

}




