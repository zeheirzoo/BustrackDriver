package com.example.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.driver.controller.RetrofitRoutes;
import com.example.driver.model.Driver;
import com.example.driver.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class LoginActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor prefsEditor;

    SweetAlertDialog sweetAlertDialog;
    EditText emailET,passwordET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_land);

        getSupportActionBar().hide();
        if (!CheckPermissions())RequestPermissions();


        pref =getSharedPreferences("DriverPref", Context.MODE_PRIVATE);
        if (pref.getInt("id",-1)!=-1){

            startActivity(new Intent(LoginActivity.this, SelectLineActivity.class));
            finish();
        }


        emailET =findViewById(R.id .email);
        passwordET =findViewById(R.id .password);


        Button loginButton=findViewById(R.id.login_button);
        Button QR_button=findViewById(R.id.qr_code);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailET.getText().toString();
                String pass=passwordET.getText().toString();
                if (!email.equals("")&&!pass.equals("")){
                    login(new User(email,pass));
                }else{
                    sweetAlertDialog= new SweetAlertDialog(getApplication(), SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog. setTitleText("Empty inputs !!") .show();

                }

            }
        });
        QR_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(LoginActivity.this, QrLoginActivity.class);
                startActivity(mainIntent);
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
        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{CAMERA,INTERNET,WRITE_EXTERNAL_STORAGE,ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION}, 1);
    }


    public void login(User c){
////
        Gson gson=new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").create();

        String url ="http://transport.misc-lab.org/api/";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RetrofitRoutes retrofitRoutes=retrofit.create(RetrofitRoutes.class);

         sweetAlertDialog= new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog. setTitleText("please wait") .show();


        pref =getSharedPreferences("DriverPref", Context.MODE_PRIVATE);
        prefsEditor=pref.edit();
        Call<Driver> login=retrofitRoutes.Login(c);

        login.enqueue(new Callback<Driver>() {
            @Override
            public void onResponse(Call<Driver> call, Response<Driver> response) {
                if (response.isSuccessful()){

                    Driver driver=response.body();

                    if (driver.getUser().getRole().equals("driver")){
                        prefsEditor.putInt("id",driver.getId());
                        prefsEditor.putInt("userId",driver.getUser().getId());
                        prefsEditor.putString("username",driver.getUser().getUsername());
                        prefsEditor.putString("firstname",driver.getUser().getFirstname());
                        prefsEditor.putString("lastname",driver.getUser().getLastname());
                        prefsEditor.commit();

                        sweetAlertDialog.dismiss();
                       startActivity(new Intent(LoginActivity.this, SelectLineActivity.class));
                       finish();

                    }else {
                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("r "+response.body()) .show();
                        sweetAlertDialog.dismiss();
                    }

                }else {
                    sweetAlertDialog.dismiss();
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Email or Password incorrect "+response.code()) .show();

                }
            }

            @Override
            public void onFailure(Call<Driver> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Oops !!  Somme things wrong  :", Toast.LENGTH_SHORT).show();
                Log.i("login", "onFailure: " +t.getCause());
            }
        });
    }
//





}
