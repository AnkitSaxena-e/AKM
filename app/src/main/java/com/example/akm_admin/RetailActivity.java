package com.example.akm_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.akm_admin.Prevalant.Prevalant;
import com.example.akm_admin.ReFragment.RetailHomeFragment;
import com.example.akm_admin.ReFragment.SeeOrderRetailFragment;
import com.example.akm_admin.ReFragment.SeeUserRetailFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import io.paperdb.Paper;

public class RetailActivity extends AppCompatActivity {

    private boolean doubleclickk = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retail);

        Paper.init(RetailActivity.this);

        BottomNavigationView navView = findViewById(R.id.nav_vieww_retail);

        navView.setOnNavigationItemSelectedListener(onNavi);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_retail, new RetailHomeFragment()).commit();

        Window window = RetailActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#303F9F"));

        updateToken();

    }

    BottomNavigationView.OnNavigationItemSelectedListener onNavi = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment fragment = null;

            if (menuItem.getItemId() == R.id.navigation_home_admin_re) {
                fragment = new RetailHomeFragment();
            } else if (menuItem.getItemId() == R.id.navigation_dashboard_admin_re) {
                fragment = new SeeOrderRetailFragment();
            } else if (menuItem.getItemId() == R.id.navigation_notifications_admin_re) {
                fragment = new SeeUserRetailFragment();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_retail, fragment).commit();

            return true;
        }
    };

    private void updateToken() {

        String refreshToken= FirebaseInstanceId.getInstance().getToken();

        DatabaseReference rtgv = FirebaseDatabase.getInstance().getReference().child("AdminTokens").child(Paper.book().read(Prevalant.UserIdA));

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Token", refreshToken);
        rtgv.updateChildren(hashMap);

    }


}