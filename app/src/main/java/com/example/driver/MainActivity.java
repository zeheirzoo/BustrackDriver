package com.example.driver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    HorizontalScrollView scrollmenu;
    LinearLayout price_layout, error_layout, success_layout;
    CodeScanner mCodeScanner;
    CodeScannerView scannerView;
    Activity activity;
    TextView scanne_ticket_text;
    boolean b=false;
    int count=0;
    ImageButton next,prev,open_scanner;
     MediaPlayer scannerSound,errorSound;
    RelativeLayout scanner_Layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=this;
        getSupportActionBar().hide();
        if (!CheckPermissions())RequestPermissions();

        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_content,new HomeFragment()).commit();
         next=findViewById(R.id.next);
         prev=findViewById(R.id.prev);
        scrollmenu =findViewById(R.id.scroll_menu);
        price_layout =findViewById(R.id.price_layout);
        error_layout =findViewById(R.id.error_layout);
        success_layout =findViewById(R.id.success_layout);
        scanne_ticket_text =findViewById(R.id.scanne_ticket_text);
        open_scanner =findViewById(R.id.open_scanner);
        scanner_Layout =findViewById(R.id.scanner);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollmenu.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                v.setBackgroundColor(Color.GRAY);
                prev.setBackgroundResource(R.color.colorPrimaryDark);

            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollmenu.fullScroll(HorizontalScrollView.FOCUS_LEFT);
                v.setBackgroundColor(Color.GRAY);
                next.setBackgroundResource(R.color.colorPrimaryDark);

            }
        });


        scannerSound= MediaPlayer.create(this, R.raw.scanner_sound);

       errorSound = MediaPlayer.create(this, R.raw.error_sound);

        scannerView =findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.startPreview();

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @SuppressLint("WrongThread")
            @Override
            public void onDecoded(@NonNull final com.google.zxing.Result result) {

              activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result!=null && b==false){
                            price_layout.setVisibility(View.VISIBLE);
                            success_layout.setVisibility(View.GONE);
                            error_layout.setVisibility(View.GONE);
                            scanne_ticket_text.setVisibility(View.GONE);
//                            b=true;
                              scannerSound.start();
////                            scannerView.setFrameColor(Color.GREEN);
////                            scannerView.setMaskColor(Color.argb(95,25,150,25));

                        }else {
//                            errorSound.start();
//                            scannerView.setFrameColor(Color.RED);
//                            scannerView.setMaskColor(Color.argb(95,150,25,25));
//                            b=false;
                        }
//                        timerScanner();


                    }
                });

            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

        open_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count==0){
                    scanner_Layout.setVisibility(View.VISIBLE);
                    v.setBackgroundResource(R.drawable.ic_close);
                    count=1;

                }
                else {
                    scanner_Layout.setVisibility(View.GONE);
                    v.setBackgroundResource(R.drawable.ic_fullscreen_black_24dp);

                    count=0;

                }

            }
        });


    }




    public void onClickItem(View view) {
        int itemIndex=view.getId();
        fragmentTransaction =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        if (itemIndex==R.id.nav_home)fragmentTransaction.replace(R.id.frame_content,new HomeFragment()).addToBackStack( "pager" );
        if (itemIndex==R.id.nav_alert)fragmentTransaction.replace(R.id.frame_content,new AlertFragment()).addToBackStack( "pager" );
        if (itemIndex==R.id.nav_search)fragmentTransaction.replace(R.id.frame_content,new SearchFragment()).addToBackStack( "pager" );
        if (itemIndex==R.id.nav_settings)fragmentTransaction.replace(R.id.frame_content,new SettingsFragment()).addToBackStack( "pager" );
        if (itemIndex==R.id.nav_help)fragmentTransaction.replace(R.id.frame_content,new HelpFragment()).addToBackStack( "pager" );
        if (itemIndex==R.id.nav_profile)fragmentTransaction.replace(R.id.frame_content,new ProfileFragment()).addToBackStack( "pager" );
        fragmentTransaction.commit();

    }


    public boolean CheckPermissions() {
        int result1 = ActivityCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ActivityCompat.checkSelfPermission(getApplicationContext(), INTERNET);

        int result3 = ActivityCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        int result4 = ActivityCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result5 = ActivityCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        return result2 == PackageManager.PERMISSION_GRANTED
                &&result1 == PackageManager.PERMISSION_GRANTED
                &&result3 == PackageManager.PERMISSION_GRANTED
                &&result4 == PackageManager.PERMISSION_GRANTED
                &&result5 == PackageManager.PERMISSION_GRANTED;

    }
    private void RequestPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{CAMERA,INTERNET,WRITE_EXTERNAL_STORAGE,ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION}, 1);
    }


    public void timerScanner(){
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override public void run() {
                //new intent here
                mCodeScanner.startPreview();
                scannerView.setFrameColor(Color.WHITE);
                scannerView.setMaskColor(Color.argb(50,250,200,255));
                price_layout.setVisibility(View.GONE);
                success_layout.setVisibility(View.GONE);
                error_layout.setVisibility(View.GONE);
                scanne_ticket_text.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }


    public void choseStation(View view) {
        int itemIndex=view.getId();
        if (itemIndex==R.id.firstStation){
            price_layout.setVisibility(View.GONE);
            success_layout.setVisibility(View.VISIBLE);
            error_layout.setVisibility(View.GONE);
            scanne_ticket_text.setVisibility(View.GONE);
            scannerSound.start();
        }
        if (itemIndex==R.id.secondStation){
            price_layout.setVisibility(View.GONE);
            success_layout.setVisibility(View.VISIBLE);
            error_layout.setVisibility(View.VISIBLE);
            scanne_ticket_text.setVisibility(View.GONE);
            errorSound.start();

        }
        timerScanner();
    }
}
