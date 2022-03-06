package com.example.akmretail;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akmretail.Fragments.CartFragment;
import com.example.akmretail.Fragments.CategoriesFragment;
import com.example.akmretail.Fragments.GalleryFragment;
import com.example.akmretail.Fragments.HomeFragment;
import com.example.akmretail.Fragments.OrderFragment;
import com.example.akmretail.Fragments.ProductDataFragment;
import com.example.akmretail.Fragments.ProfileFragment;
import com.example.akmretail.Fragments.SeeCategoryFragment;
import com.example.akmretail.Fragments.SlideshowFragment;
import com.example.akmretail.Prevalant.Prevalant;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private String ee;
    private TextView Dot;
    private NavigationView navigationView;
    private boolean doubleclick = false, yy = false;
    private Fragment fragment;
    private ImageView Cart;
    private Toolbar toolbar;
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ee = getIntent().getStringExtra("eeee");

        Paper.init(HomeActivity.this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Cart = findViewById(R.id.See_C);
        Dot = findViewById(R.id.dot);
        toolbar.setTitle("Home");

        Window window = HomeActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#22BFC1"));

        fragment = new HomeFragment();

        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);

        toggle = new ActionBarDrawerToggle(HomeActivity.this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        try {
            getSupportActionBar().setElevation(0);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try {

            View headerview = navigationView.getHeaderView(0);
            TextView usernameTextView = headerview.findViewById(R.id.textview_navnav);
            CircleImageView profileImageView = headerview.findViewById(R.id.imageView_navnav);
            String Name = Paper.book().read(Prevalant.UserNameA);
            Picasso.get().load(Paper.book().read(Prevalant.UserImageA).toString()).placeholder(R.drawable.profile).into(profileImageView);
            usernameTextView.setText(Name);

            profileImageView.setOnClickListener(v -> {

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new ProfileFragment()).commit();
                Paper.book().write(Prevalant.CheckH, "A");
                toolbar.setTitle("Profile");
                Paper.book().write(Prevalant.FAD, "ProfileA");
                drawer.closeDrawer(GravityCompat.START);

            });

            usernameTextView.setOnClickListener(v -> {

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new ProfileFragment()).commit();
                Paper.book().write(Prevalant.CheckH, "A");
                toolbar.setTitle("Profile");
                Paper.book().write(Prevalant.FAD, "ProfileA");
                drawer.closeDrawer(GravityCompat.START);

            });

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Cart.setOnClickListener(v -> {
            toolbar.setTitle("Cart");
            Paper.book().write(Prevalant.FAD, "CartA");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new CartFragment()).commit();
        });

        switch (ee) {
            case "SeA":
                toolbar.setTitle("Search");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new GalleryFragment()).commit();
                break;
            case "SA":
                toolbar.setTitle("Setting");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new SlideshowFragment()).commit();
                break;
            case "OA":
                toolbar.setTitle("Order");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new OrderFragment()).commit();
                break;
            case "CA":
                toolbar.setTitle("Category");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new CategoriesFragment()).commit();
                break;
            case "SCA":
                toolbar.setTitle("Product");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new SeeCategoryFragment()).commit();
                break;
            case "PDA":
                toolbar.setTitle("Product");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new ProductDataFragment()).commit();
                break;
            case "PA":
                toolbar.setTitle("Profile");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new ProfileFragment()).commit();
                break;
            case "CaA":
                toolbar.setTitle("Cart");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new CartFragment()).commit();
                break;
            default:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new HomeFragment()).commit();
                break;
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }

        updateToken();

    }

    private void updateToken() {

        String refreshToken= FirebaseInstanceId.getInstance().getToken();

        DatabaseReference rtgv = FirebaseDatabase.getInstance().getReference().child("ReUser").child(Paper.book().read(Prevalant.UserIdA));

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Token", refreshToken);
        rtgv.updateChildren(hashMap);

        TakeUser();

    }

    public void TakeUser() {

        DatabaseReference fh = FirebaseDatabase.getInstance().getReference().child("ReUser").child(Paper.book().read(Prevalant.UserIdA));

        fh.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String Cart = snapshot.child("Cart").getValue().toString();
                    String Buy = snapshot.child("Buy").getValue().toString();

                    Paper.book().write(Prevalant.BNum, Buy);
                    Paper.book().write(Prevalant.CNum, Cart);

                    CheckNotification();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void CheckNotification() {

        final DatabaseReference cartListRefABC = FirebaseDatabase.getInstance().getReference().child("ReOrderList").child(Paper.book().read(Prevalant.UserIdA))
                .child("Cart").child(Paper.book().read(Prevalant.CNum));

        cartListRefABC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Dot.setVisibility(View.VISIBLE);
                }else{
                    Dot.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        fragment = null;

        if (menuItem.getItemId() == R.id.nav_home) {
            fragment = new HomeFragment();
            Paper.book().write(Prevalant.FAD, "HomeA");
            toolbar.setTitle("Home");
            drawer.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        } else if (menuItem.getItemId() == R.id.nav_gallery) {
            fragment = new GalleryFragment();
            Paper.book().write(Prevalant.FAD, "SearchA");
            toolbar.setTitle("Search");
            drawer.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        } else if (menuItem.getItemId() == R.id.nav_order) {
            fragment = new OrderFragment();
            toolbar.setTitle("Order");
            Paper.book().write(Prevalant.FAD, "OrderA");
            drawer.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        } else if (menuItem.getItemId() == R.id.nav_categories) {
            fragment = new CategoriesFragment();
            toolbar.setTitle("Category");
            Paper.book().write(Prevalant.FAD, "CategoryA");
            drawer.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        } else if (menuItem.getItemId() == R.id.nav_cart) {
            fragment = new CartFragment();
            toolbar.setTitle("Cart");
            Paper.book().write(Prevalant.FAD, "CartA");
            drawer.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        } else if (menuItem.getItemId() == R.id.nav_slideshow) {
            fragment = new SlideshowFragment();
            toolbar.setTitle("Setting");
            Paper.book().write(Prevalant.FAD, "SettingA");
            drawer.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        } else if (menuItem.getItemId() == R.id.nav_logout) {
            Paper.book().destroy();
            yy = true;
            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(i);
        } else {
            fragment = new HomeFragment();
            toolbar.setTitle("Home");
            Paper.book().write(Prevalant.FAD, "HomeA");
            drawer.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            String FAA = Paper.book().read(Prevalant.FAD).toString();

            if (FAA.equals("HomeA")) {
                if (doubleclick) {
                    Intent hi = new Intent(Intent.ACTION_MAIN);
                    hi.addCategory(Intent.CATEGORY_HOME);
                    hi.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(hi);
                    return;
                }

                this.doubleclick = true;
                Toast.makeText(HomeActivity.this, "Press Again to Exit", Toast.LENGTH_LONG).show();

                new Handler().postDelayed(() -> doubleclick = false, 2000);
            } else {

                String C = Paper.book().read(Prevalant.CheckH);

                if(C.equals("C")){
                    Intent o = new Intent(HomeActivity.this, HomeActivity.class);
                    o.putExtra("eeee", "CaA");
                    startActivity(o);
                }else if(C.equals("S")){
                    Intent o = new Intent(HomeActivity.this, HomeActivity.class);
                    o.putExtra("eeee", "SeA");
                    startActivity(o);
                }else if(C.equals("SS")){
                    Intent o = new Intent(HomeActivity.this, HomeActivity.class);
                    o.putExtra("eeee", "SA");
                    startActivity(o);
                }else if(C.equals("CA")){
                    Intent o = new Intent(HomeActivity.this, HomeActivity.class);
                    o.putExtra("eeee", "CA");
                    startActivity(o);
                }else if(C.equals("SCA")){
                    Intent o = new Intent(HomeActivity.this, HomeActivity.class);
                    o.putExtra("eeee", "SCA");
                    startActivity(o);
                }else{
                    Intent o = new Intent(HomeActivity.this, HomeActivity.class);
                    o.putExtra("eeee", "HA");
                    startActivity(o);
                }

            }
        }
    }

    @Override
    protected void onPause() {
        if (drawer.isDrawerOpen(GravityCompat.START) && !yy) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onPause();
        }
    }
}