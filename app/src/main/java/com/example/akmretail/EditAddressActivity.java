package com.example.akmretail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akmretail.Prevalant.Prevalant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class EditAddressActivity extends AppCompatActivity {

    EditText change_Name, change_Address, change_Number, change_Pin, change_State, change_City;
    TextView Close, Update, Change_Photo;
    CircleImageView change_profile_Image;
    String Name, type, PID;
    Uri ImageUri;
    private String myUri = "";
    StorageTask uploadTask;
    private  int f = 0;
    StorageReference profilePictureRef;
    private String checker = "";
    private ImageView back;
    private Dialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        try{
            PID = getIntent().getStringExtra("PID");

            change_Name = findViewById(R.id.Change_Name);
            change_Address = findViewById(R.id.Change_Address);
            change_Number = findViewById(R.id.Change_Number);
            change_Pin = findViewById(R.id.Change_Pin);
            change_City = findViewById(R.id.Change_City);
            change_State = findViewById(R.id.Change_State);

            back = findViewById(R.id.back_sett_edit_add);

            Update = findViewById(R.id.update);

            LoadingBar = new Dialog(EditAddressActivity.this);
            LoadingBar.setContentView(R.layout.loading_dialog);
            LoadingBar.setCancelable(false);

            type = Paper.book().read(Prevalant.CheckAdmin);

            Window window = EditAddressActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

            Paper.book().write(Prevalant.CheckH, "A");
            Paper.init(EditAddressActivity.this);

            if(!PID.equals("AS")){

                UpdateAdddd(PID);

            }

            Update.setOnClickListener(v -> updateOnlyUserInfo(PID));

            back.setOnClickListener(v -> {

                Paper.book().write(Prevalant.CheckH, "SS");
                Intent i = new Intent(EditAddressActivity.this, HomeActivity.class);
                i.putExtra("eeee", "PA");
                startActivity(i);

            });

        }
        catch (Exception e){
            Toast.makeText(EditAddressActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void UpdateAdddd(String PID) {

        DatabaseReference ef = FirebaseDatabase.getInstance().getReference().child("ReUAddressI")
                .child(Paper.book().read(Prevalant.UserIdA)).child(PID);

        ef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!snapshot.exists()){

                    String Name = snapshot.child("Name").getValue().toString();
                    String Number = snapshot.child("Number").getValue().toString();
                    String Address = snapshot.child("Address").getValue().toString();
                    String State = snapshot.child("State").getValue().toString();
                    String Pin = snapshot.child("Pin").getValue().toString();
                    String City = snapshot.child("City").getValue().toString();

                    change_Name.setText(Name, TextView.BufferType.EDITABLE);
                    change_Number.setText(Number, TextView.BufferType.EDITABLE);
                    change_Address.setText(Address, TextView.BufferType.EDITABLE);
                    change_State.setText(State, TextView.BufferType.EDITABLE);
                    change_Pin.setText(Pin, TextView.BufferType.EDITABLE);
                    change_City.setText(City, TextView.BufferType.EDITABLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateOnlyUserInfo(String pid) {

        if (TextUtils.isEmpty(change_Name.getText().toString())) {
            change_Name.setError("Please Enter");
        } else if (TextUtils.isEmpty(change_Number.getText().toString())) {
            change_Number.setError("Please Enter");
        }else if (TextUtils.isEmpty(change_Address.getText().toString())) {
            change_Address.setError("Please Enter");
        } else if (TextUtils.isEmpty(change_Pin.getText().toString())) {
            change_Pin.setError("Please Enter");
        } else if (TextUtils.isEmpty(change_City.getText().toString())) {
            change_City.setError("Please Enter");
        } else if (TextUtils.isEmpty(change_State.getText().toString())) {
            change_State.setError("Please Enter");
        } else {
            userInfoSaved(pid);
        }
    }

    private void userInfoSaved(String PIDD) {

        DatabaseReference Userref = FirebaseDatabase.getInstance().getReference().child("ReUAddressI")
                .child(Paper.book().read(Prevalant.UserIdA));

        String SaveCurruntTime, SaveCurruntDate, Time;

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        SaveCurruntDate = currentDate.format(calendar.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat(" hh:mm:ss a");
        SaveCurruntTime = currentTime.format(calendar.getTime());

        if(PIDD.equals("AS")) {
            Time = SaveCurruntDate + SaveCurruntTime;
        }
        else {
            Time = PIDD;
        }

        HashMap<String, Object> map = new HashMap<>();

        map.put("Name", change_Name.getText().toString());
        map.put("Address", change_Address.getText().toString());
        map.put("Number", change_Number.getText().toString());
        map.put("Pin", change_Pin.getText().toString());
        map.put("PID", Time);
        map.put("City", change_City.getText().toString());
        map.put("State", change_State.getText().toString());

        Userref.child(Time).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(EditAddressActivity.this, "Profile Information Update Successfully", Toast.LENGTH_LONG).show();
                Paper.book().write(Prevalant.CheckH, "SS");
                Intent i = new Intent(EditAddressActivity.this, HomeActivity.class);
                i.putExtra("eeee", "PA");
                LoadingBar.dismiss();
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {

        Paper.book().write(Prevalant.CheckH, "SS");
        Intent i = new Intent(EditAddressActivity.this, HomeActivity.class);
        i.putExtra("eeee", "PA");
        startActivity(i);

    }
}