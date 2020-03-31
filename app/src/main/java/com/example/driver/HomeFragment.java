package com.example.driver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class HomeFragment extends Fragment {

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    Vibrator vibrator;
    CodeScanner mCodeScanner;
    Activity activity=getActivity();
    Button lineButton,mapButton;
    CodeScannerView scannerView;
    boolean b=false;
    public HomeFragment() {
        // Required empty public constructor
    }
    Handler handler ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_home, container, false);
        handler = new android.os.Handler();
        fragmentManager=getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_content_home,new mapsFragment()).commit();

        final MediaPlayer scannerSound = MediaPlayer.create(getActivity(), R.raw.scanner_sound);
        final MediaPlayer errorSound = MediaPlayer.create(getActivity(), R.raw.error_sound);




        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

         mapButton=root.findViewById(R.id.nav_map);
         lineButton=root.findViewById(R.id.nav_line);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.color.colorPrimary);
                lineButton.setBackgroundColor(Color.GRAY);

            }
        });
        lineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.color.colorPrimary);
                mapButton.setBackgroundColor(Color.GRAY);
            }
        });
//


      scannerView =root.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(getContext(), scannerView);
        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.startPreview();

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @SuppressLint("WrongThread")
            @Override
            public void onDecoded(@NonNull final com.google.zxing.Result result) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result!=null && b==false){
                                b=true;
                                scannerSound.start();
                                scannerView.setFrameColor(Color.GREEN);

                            }else {
                                errorSound.start();
                                scannerView.setFrameColor(Color.RED);
                                b=false;
                            }
                            timerRescanner();
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


        return  root;
    }








    public void onClick(View view) {
        int itemIndex=view.getId();
        fragmentTransaction =getChildFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        if (itemIndex==R.id.nav_map)fragmentTransaction.replace(R.id.frame_content_home,new mapsFragment());
        if (itemIndex==R.id.nav_line)fragmentTransaction.replace(R.id.frame_content_home,new LinePlanFragment());
        fragmentTransaction.commit();

    }
    public void timerRescanner(){
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override public void run() {
                //new intent here
                mCodeScanner.startPreview();
               scannerView.setFrameColor(Color.WHITE);

            }
        }, 1000);
    }

}
