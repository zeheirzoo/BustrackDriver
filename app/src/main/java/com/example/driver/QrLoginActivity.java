package com.example.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.driver.controller.ConnexionController;

import javax.xml.transform.Result;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class QrLoginActivity extends AppCompatActivity {
    CodeScanner mCodeScanner;
    Activity activity = this;
    Vibrator vibrator;
    String url = ConnexionController.getWebUrl();
    CodeScannerView scannerView;
            boolean b=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_login);



        getSupportActionBar().hide();
        if (!CheckPermissions())RequestPermissions();

        vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);


        final MediaPlayer scannerSound = MediaPlayer.create(this, R.raw.scanner_sound);

        final MediaPlayer errorSound = MediaPlayer.create(this, R.raw.error_sound);

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
                            b=true;
                            scannerSound.start();
                            scannerView.setFrameColor(Color.GREEN);
                            scannerView.setMaskColor(Color.argb(95,25,150,25));
                            Intent mainIntent = new Intent(QrLoginActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();

                        }else {
                            errorSound.start();
                            scannerView.setFrameColor(Color.RED);
                            scannerView.setMaskColor(Color.argb(95,150,25,25));

                            b=false;
                        }
                        timerScanner();
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
        ActivityCompat.requestPermissions(QrLoginActivity.this, new String[]{CAMERA,INTERNET,WRITE_EXTERNAL_STORAGE,ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION}, 1);
    }


    public void timerScanner(){
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override public void run() {
                //new intent here
                mCodeScanner.startPreview();
                scannerView.setFrameColor(Color.WHITE);
                scannerView.setMaskColor(Color.argb(50,250,200,255));

            }
        }, 2000);
    }


}
