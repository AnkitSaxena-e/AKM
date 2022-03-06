package com.example.akmuser.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.akmuser.Adapter.FiRecyAdapter;
import com.example.akmuser.Adapter.SliderLayoutAdapter;
import com.example.akmuser.Modal.Product;
import com.example.akmuser.PhoneShowActivity;
import com.example.akmuser.Prevalant.Prevalant;
import com.example.akmuser.R;
import com.example.akmuser.ScanDocActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;

public class HomeFragment extends Fragment {


    private AppBarConfiguration mAppBarConfiguration;
    private String NName, IImage, type, Check;
    private String Name, Order;
    private static ViewPager mPager;
    private String[] urls;
    private ConstraintLayout layout;
    private CirclePageIndicator indicator;
    int a = 0;
    private ArrayList<Product> PList, QList, RList, FFList;
    private Boolean doubleclick;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private RelativeLayout ScannerClick;
    private TextView PhoneA, PhoneB, PhoneC, PhoneD, PhoneE, PhoneF, UI;
    private Dialog LoadingBar;
    private String Link1, Link2, Link3, Link4, Link5, PF1, PF2, PF3, PF4, PF5, PF6;
    private TextView ViewButton1, ViewButton2, ViewButton3, ViewButton4, ViewButton5, ViewButton6;
    private String PriceFi, PriceSe, PriceTh, PriceFo, PriceFv, PriceSi, In, SF;
    private ArrayList<Integer> FPriceFi, FPriceSe, FPriceTh, FPriceFo, FPriceFv, FPriceSi;
    private int A = 0, AA = 0, AAA = 0, AAAA = 0, AAAAA = 0, AAAAAA = 0;
    private RecyclerView FiPhone, SePhone, ThPhone, FoPhone, FvPhone, SiPhone;
    private RelativeLayout Search;
    private ViewFlipper ViewFlipperPrint;
    private RecyclerView.LayoutManager layoutManagerFiPhone, layoutManagerSePhone, layoutManagerThPhone, layoutManagerFoPhone, layoutManagerFvPhone, layoutManagerSiPhone;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Paper.init(getActivity());

        type = Paper.book().read(Prevalant.CheckAdmin);

        layout = root.findViewById(R.id.pqpq);

        ViewButton1 = root.findViewById(R.id.viewAll);
        ViewButton2 = root.findViewById(R.id.viewAllA);
        ViewButton3 = root.findViewById(R.id.viewAllA_earPhone);
        ViewButton4 = root.findViewById(R.id.viewAllA_headPhone);
        ViewButton5 = root.findViewById(R.id.viewAllA_stand);
        ViewButton6 = root.findViewById(R.id.viewAllA_vr);

        PhoneA = root.findViewById(R.id.dealoftheday);
        PhoneB = root.findViewById(R.id.bestOf);
        PhoneC = root.findViewById(R.id.bestOf_earPhone);
        PhoneD = root.findViewById(R.id.bestOf_headPhone);
        PhoneE = root.findViewById(R.id.bestOf_stand);
        PhoneF = root.findViewById(R.id.bestOf_vr);

        ScannerClick = root.findViewById(R.id.pppp);

        UI = root.findViewById(R.id.infoText);

        Search = root.findViewById(R.id.See_Se);

        TakeUser();

        mPager = root.findViewById(R.id.viewPagerShow_home);

        indicator = root.findViewById(R.id.indicator_home);

        LoadingBar = new Dialog(getContext());

        LoadingBar.setContentView(R.layout.loading_dialog);

        Prevalant.SuspendUser = new ArrayList<>();

        FPriceFi = new ArrayList<>();
        FPriceSe = new ArrayList<>();
        FPriceTh = new ArrayList<>();
        FPriceFo = new ArrayList<>();
        FPriceFv = new ArrayList<>();
        FPriceSi = new ArrayList<>();

        FiPhone = root.findViewById(R.id.sideRe);
        FiPhone.setHasFixedSize(true);
        FiPhone.setItemViewCacheSize(20);
        FiPhone.setDrawingCacheEnabled(true);
        FiPhone.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerFiPhone = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        FiPhone.setLayoutManager(layoutManagerFiPhone);

        SePhone = root.findViewById(R.id.grid_fragS);
        SePhone.setHasFixedSize(true);
        SePhone.setItemViewCacheSize(20);
        SePhone.setDrawingCacheEnabled(true);
        SePhone.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerSePhone = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        SePhone.setLayoutManager(layoutManagerSePhone);

        ThPhone = root.findViewById(R.id.grid_fragS_earPhone);
        ThPhone.setHasFixedSize(true);
        ThPhone.setItemViewCacheSize(20);
        ThPhone.setDrawingCacheEnabled(true);
        ThPhone.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerThPhone = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        ThPhone.setLayoutManager(layoutManagerThPhone);

        FoPhone = root.findViewById(R.id.grid_fragS_headPhone);
        FoPhone.setHasFixedSize(true);
        FoPhone.setItemViewCacheSize(20);
        FoPhone.setDrawingCacheEnabled(true);
        FoPhone.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerFoPhone = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        FoPhone.setLayoutManager(layoutManagerFoPhone);

        FvPhone = root.findViewById(R.id.grid_fragS_stand);
        FvPhone.setHasFixedSize(true);
        FvPhone.setItemViewCacheSize(20);
        FvPhone.setDrawingCacheEnabled(true);
        FvPhone.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerFvPhone = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        FvPhone.setLayoutManager(layoutManagerFvPhone);

        SiPhone = root.findViewById(R.id.grid_fragS_vr);
        SiPhone.setHasFixedSize(true);
        SiPhone.setItemViewCacheSize(20);
        SiPhone.setDrawingCacheEnabled(true);
        SiPhone.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerSiPhone = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        SiPhone.setLayoutManager(layoutManagerSiPhone);

        Paper.book().write(Prevalant.FAD, "HomeA");

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new GalleryFragment());
                ft.addToBackStack(null);
                ft.commit();

            }
        });


        ScannerClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent p = new Intent(getActivity(), ScanDocActivity.class);
                startActivity(p);

            }
        });

        try {

            Link1 = Paper.book().read(Prevalant.AD1);
            Link2 = Paper.book().read(Prevalant.AD2);
            Link3 = Paper.book().read(Prevalant.AD3);
            Link4 = Paper.book().read(Prevalant.AD4);
            Link5 = Paper.book().read(Prevalant.AD5);

            urls = new String[]{
                    Link1,
                    Link2,
                    Link3,
                    Link4,
                    Link5
            };
            Flip();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "kk" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return root;
    }

    private void GetFFFF() {

        DatabaseReference refrefrefref = FirebaseDatabase.getInstance().getReference().child("FFhones");

        refrefrefref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    PF1 = snapshot.child("Fi").getValue().toString();
                    PF2 = snapshot.child("Se").getValue().toString();
                    PF3 = snapshot.child("Th").getValue().toString();
                    PF4 = snapshot.child("Fo").getValue().toString();
                    PF5 = snapshot.child("Fv").getValue().toString();
                    PF6 = snapshot.child("Si").getValue().toString();

                    String l =  snapshot.child("Update").getValue().toString();

                    PhoneA.setText(PF1);
                    PhoneB.setText(PF2);
                    PhoneC.setText(PF3);
                    PhoneD.setText(PF4);
                    PhoneE.setText(PF5);
                    PhoneF.setText(PF6);

                    UI.setText(l);

                    GetListfi(PF1);
                    GetListfi(PF2);
                    GetListfi(PF3);
                    GetListfi(PF4);
                    GetListfi(PF5);
                    GetListfi(PF6);

                    ViewButton1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!type.equals("Admin")) {
                                Intent i = new Intent(getActivity(), PhoneShowActivity.class);
                                i.putExtra("Admin", "AS");
                                i.putExtra("category", PF1);
                                startActivity(i);
                            } else {
                                Intent i = new Intent(getActivity(), PhoneShowActivity.class);
                                i.putExtra("Admin", "Admin");
                                i.putExtra("category", PF1);
                                startActivity(i);
                            }
                        }
                    });

                    ViewButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!type.equals("Admin")) {
                                Intent i = new Intent(getActivity(), PhoneShowActivity.class);
                                i.putExtra("Admin", "AS");
                                i.putExtra("category", PF2);
                                startActivity(i);
                            } else {
                                Intent i = new Intent(getActivity(), PhoneShowActivity.class);
                                i.putExtra("Admin", "Admin");
                                i.putExtra("category", PF2);
                                startActivity(i);
                            }
                        }
                    });

                    ViewButton3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!type.equals("Admin")) {
                                Intent i = new Intent(getActivity(), PhoneShowActivity.class);
                                i.putExtra("Admin", "AS");
                                i.putExtra("category", PF3);
                                startActivity(i);
                            } else {
                                Intent i = new Intent(getActivity(), PhoneShowActivity.class);
                                i.putExtra("Admin", "Admin");
                                i.putExtra("category", PF3);
                                startActivity(i);
                            }
                        }
                    });

                    ViewButton4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!type.equals("Admin")) {
                                Intent i = new Intent(getActivity(), PhoneShowActivity.class);
                                i.putExtra("Admin", "AS");
                                i.putExtra("category", PF4);
                                startActivity(i);
                            } else {
                                Intent i = new Intent(getActivity(), PhoneShowActivity.class);
                                i.putExtra("Admin", "Admin");
                                i.putExtra("category", PF4);
                                startActivity(i);
                            }
                        }
                    });

                    ViewButton5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!type.equals("Admin")) {
                                Intent i = new Intent(getActivity(), PhoneShowActivity.class);
                                i.putExtra("Admin", "AS");
                                i.putExtra("category", PF5);
                                startActivity(i);
                            } else {
                                Intent i = new Intent(getActivity(), PhoneShowActivity.class);
                                i.putExtra("Admin", "Admin");
                                i.putExtra("category", PF5);
                                startActivity(i);
                            }
                        }
                    });

                    ViewButton6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!type.equals("Admin")) {
                                Intent i = new Intent(getActivity(), PhoneShowActivity.class);
                                i.putExtra("Admin", "AS");
                                i.putExtra("category", PF6);
                                startActivity(i);
                            } else {
                                Intent i = new Intent(getActivity(), PhoneShowActivity.class);
                                i.putExtra("Admin", "Admin");
                                i.putExtra("category", PF6);
                                startActivity(i);
                            }
                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void TakeUser() {

        DatabaseReference fh = FirebaseDatabase.getInstance().getReference().child("User").child(Paper.book().read(Prevalant.UserIdA));

        fh.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String Cart = snapshot.child("Cart").getValue().toString();
                    String Buy = snapshot.child("Buy").getValue().toString();

                    Paper.book().write(Prevalant.BNum, Buy);
                    Paper.book().write(Prevalant.CNum, Cart);

                    GetFFFF();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        TakeUser();

    }

    private void GetListfi(String j) {

        DatabaseReference ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");
        DatabaseReference rty = FirebaseDatabase.getInstance().getReference().child("User");

        if (!ProductRef.equals(null))

            ProductRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        PList = new ArrayList<>();
                        QList = new ArrayList<>();
                        RList = new ArrayList<>();
                        FFList = new ArrayList<>();

                        for (DataSnapshot ss : dataSnapshot.getChildren()) {
                            PList.add(ss.getValue(Product.class));
                        }

                        for (int g = 0; g < PList.size(); g++) {

                            String h = PList.get(g).getCategory().toString();

                            if (h.equals(j)) {
                                QList.add(PList.get(g));
                            }

                        }

                        FiRecyAdapter adapter = new FiRecyAdapter(getActivity(), QList, type);

                        if (j.equals(PF1)) {
                            FiPhone.setAdapter(adapter);
                        }

                        if (j.equals(PF2)) {
                            SePhone.setAdapter(adapter);
                        }

                        if (j.equals(PF3)) {
                            ThPhone.setAdapter(adapter);
                        }

                        if (j.equals(PF4)) {
                            FoPhone.setAdapter(adapter);
                        }

                        if (j.equals(PF5)) {
                            FvPhone.setAdapter(adapter);
                        }

                        if (j.equals(PF6)) {
                            SiPhone.setAdapter(adapter);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }

    private void Flip() {

        try {
            mPager.setAdapter(new SliderLayoutAdapter(getActivity(), urls));

            indicator.setViewPager(mPager);

            final float density = getResources().getDisplayMetrics().density;

            indicator.setRadius(5 * density);

            NUM_PAGES = urls.length;

            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == NUM_PAGES) {
                        currentPage = 0;
                    }
                    mPager.setCurrentItem(currentPage++, true);
                }
            };
            Timer swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 3000, 3000);

            indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;
                }

                @Override
                public void onPageScrolled(int pos, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int pos) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}