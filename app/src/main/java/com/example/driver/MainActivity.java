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
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.driver.controller.RetrofitRoutes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    boolean isOpen=true;
    ImageButton next,prev,open_scanner;
     MediaPlayer scannerSound,successSound, errorSound;
    RelativeLayout scanner_Layout;
    MyTextToSpeak myTextToSpeak;
    MediaPlayer bipSmok ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=this;
        getSupportActionBar().hide();
        if (!CheckPermissions())RequestPermissions();

        myTextToSpeak=new MyTextToSpeak(this);
        bipSmok = MediaPlayer.create(this, R.raw.zxing_beep);


        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_content,new HomeFragment()).commit();

        price_layout =findViewById(R.id.price_layout);
        error_layout =findViewById(R.id.error_layout);
        success_layout =findViewById(R.id.success_layout);
        scanne_ticket_text =findViewById(R.id.scanne_ticket_text);
        open_scanner =findViewById(R.id.open_scanner);
        scanner_Layout =findViewById(R.id.scanner);

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

        successSound= MediaPlayer.create(this, R.raw.zxing_beep);
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
                    if (result!=null ){
                        try {
                            scannerSound.start();
                            checkScannerResult(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    }
                });

            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
                mCodeScanner.startPreview();
                scannerView.setFrameColor(Color.WHITE);
                scannerView.setMaskColor(Color.argb(50,250,200,255));
                price_layout.setVisibility(View.GONE);
                success_layout.setVisibility(View.GONE);
                error_layout.setVisibility(View.GONE);
                scanne_ticket_text.setVisibility(View.VISIBLE);
            }
        });
        openScanner(isOpen);
        open_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScanner(isOpen);
            }
        });


    }
    JsonObject resultObject;
    String time;
    private void checkScannerResult(Result result) throws JSONException {
        Calendar c = Calendar.getInstance();
         time=(c.get(Calendar.HOUR_OF_DAY)-1+":"+c.get(Calendar.MINUTE));
         resultObject=new Gson().fromJson(String.valueOf(result),JsonObject.class);
        if (resultObject.get("destination").getAsInt()==0||resultObject.get("price").getAsInt()==0){
            price_layout.setVisibility(View.VISIBLE);
            scanne_ticket_text.setVisibility(View.GONE);


        }else{
            ConsumTicket(resultObject.get("id").getAsInt(),3,resultObject.get("price").getAsInt(),time);
        }



        Log.i("ticket", "Result: "+result);
        Log.i("ticket", "id : "+resultObject.get("id").getAsInt());
        Log.i("ticket", "time : "+time);

    }

    public void openScanner(boolean b){

    if (b){
        scanner_Layout.setVisibility(View.VISIBLE);
        open_scanner.setBackgroundResource(R.drawable.ic_close);
        mCodeScanner.startPreview();

    }
    else {
        scanner_Layout.setVisibility(View.GONE);
        open_scanner.setBackgroundResource(R.drawable.ic_fullscreen_black_24dp);
        mCodeScanner.stopPreview();
    }
    isOpen=!isOpen;
}

    public void onClickItem(View view) {

        int itemIndex=view.getId();
        fragmentTransaction =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        if (itemIndex==R.id.nav_home)fragmentTransaction.replace(R.id.frame_content,new HomeFragment()).addToBackStack( "pager" );
        if (itemIndex==R.id.nav_alert)fragmentTransaction.replace(R.id.frame_content,new AlertFragment()).addToBackStack( "pager" );
        if (itemIndex==R.id.nav_help)fragmentTransaction.replace(R.id.frame_content,new HelpFragment()).addToBackStack( "pager" );
        if (itemIndex==R.id.nav_profile)fragmentTransaction.replace(R.id.frame_content,new ProfileFragment()).addToBackStack( "pager" );
        fragmentTransaction.commit();


        if (itemIndex==R.id.nav_place){
            myTextToSpeak.speakOut("Avancer vers le fond du bus",bipSmok);
            Toast.makeText(this, "Avancer vers le fond du bus", Toast.LENGTH_SHORT).show();
        }
        if (itemIndex==R.id.nav_smoke){
            myTextToSpeak.speakOut("le tabac est interdit",bipSmok);
            Toast.makeText(this, "le tabac est interdit", Toast.LENGTH_SHORT).show();

        }
        if (itemIndex==R.id.nav_silence){
            myTextToSpeak.speakOut("Veuillez SVP respecter le silence",bipSmok);
            Toast.makeText(this, "Veuillez SVP respecter le silence", Toast.LENGTH_SHORT).show();
        }

        if (itemIndex==R.id.nav_logout)fragmentTransaction.replace(R.id.frame_content,new SettingsFragment()).addToBackStack( "pager" );

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
        }, 3000);
    }

    public void SuccessRequest(int price) {
        price_layout.setVisibility(View.GONE);
        success_layout.setVisibility(View.VISIBLE);
        error_layout.setVisibility(View.GONE);
        scanne_ticket_text.setVisibility(View.GONE);
        successSound.start();
        TextView pricetv =findViewById(R.id.priceTv);
        pricetv.setText(""+price +"  DZA");

    }

    public void WrongRequest(String error) {
            price_layout.setVisibility(View.GONE);
            success_layout.setVisibility(View.VISIBLE);
            error_layout.setVisibility(View.VISIBLE);
            scanne_ticket_text.setVisibility(View.GONE);
            TextView errorTv=findViewById(R.id.errorTV);
        errorTv.setText("Error "+error);
            errorSound.start();
    }

    public void ConsumTicket(int id, int ride, final int price, String time){
        Retrofit retrofit;
        RetrofitRoutes retrofitRoutes;
        String url ="http://transport.misc-lab.org/api/";
        Gson gson=new GsonBuilder().serializeNulls().setLenient().create();

        retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofitRoutes=retrofit.create(RetrofitRoutes.class);


        Call<String> call=retrofitRoutes.consumTicket(id,ride,price,time);

        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("ticket", "responce : "+response.body());
                if(response.body().equals("OK")){

                    SuccessRequest(price);
                }else {
                    WrongRequest("is paid ");
                }
                timerScanner();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                WrongRequest("Recharger voter compte ");
                timerScanner();
            }
        });
    }

    public void choseStation(View view) {
        TextView price =(TextView) ((LinearLayout)view).getChildAt(1);
        ConsumTicket(resultObject.get("id").getAsInt(),3,(int)Integer.valueOf(price.getText().toString()),time);

    }
}
