package com.example.driver;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

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

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    HorizontalScrollView scrollmenu;
    ImageButton next,prev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        if (!CheckPermissions())RequestPermissions();

        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_content,new HomeFragment()).commit();
         next=findViewById(R.id.next);
         prev=findViewById(R.id.prev);
        scrollmenu =findViewById(R.id.scroll_menu);

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
        return result2 == PackageManager.PERMISSION_GRANTED&&result1 == PackageManager.PERMISSION_GRANTED&&result3 == PackageManager.PERMISSION_GRANTED;
    }
    private void RequestPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{CAMERA,INTERNET,WRITE_EXTERNAL_STORAGE}, 1);
    }

}
