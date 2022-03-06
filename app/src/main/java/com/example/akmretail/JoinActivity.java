package com.example.akmretail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akmretail.Adapter.CounteryData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JoinActivity extends AppCompatActivity {

    Button Join;
    EditText Name, Shop;
    private Dialog LoadingBar;
    String No;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_join);

        No = getIntent().getStringExtra("NNOO");

        Join = findViewById(R.id.Join_button);
        Name = findViewById(R.id.Name_Join);
        Shop = findViewById(R.id.Passward_Join);

        LoadingBar = new Dialog(JoinActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);

        Join.setOnClickListener(v -> CreateAccount());
    }

    private void CreateAccount() {

        String name = Name.getText().toString();
        String shop = Shop.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Name.setError("Please Enter Your Name");
        } else if (TextUtils.isEmpty(shop)) {
            Shop.setError("Please Conform Your Password");
        } else {
            LoadingBar.dismiss();

            Intent o = new Intent(JoinActivity.this, ConfermOTP.class);
            o.putExtra("Number", No);
            o.putExtra("Name", name);
            o.putExtra("Passward", shop);
            startActivity(o);

        }
    }

}
