package com.example.driver;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    private GoogleMap mMap;
    RelativeLayout menu_layout;
    ImageButton menuButton;
    Animation animation;
    int click=0;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
     RelativeLayout.LayoutParams menuParams;
    Vibrator vibrator;
    CodeScanner mCodeScanner;
    Activity activity=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_content,new mapsFragment()).commit();
        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);

        if (!CheckPermissions())RequestPermissions();

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        final CodeScannerView scannerView =findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.startPreview();

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull com.google.zxing.Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

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




    }




    public void onClickItem(View view) {
        int itemIndex=view.getId();
        fragmentTransaction =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        if (itemIndex==R.id.nav_map)fragmentTransaction.replace(R.id.frame_content,new mapsFragment()).addToBackStack( "pager" );
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
