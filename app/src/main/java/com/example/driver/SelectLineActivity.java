package com.example.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.driver.Adapter.LineAdapter;
import com.example.driver.controller.ConnexionController;
import com.example.driver.controller.RetrofitRoutes;
import com.example.driver.model.Affectation;
import com.example.driver.model.Driver;
import com.example.driver.model.Line;
import com.example.driver.model.Position;
import com.example.driver.model.User;
import com.example.driver.model.Vehicle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectLineActivity extends AppCompatActivity {
    String url = ConnexionController.getWebUrl();
    SharedPreferences driverPref,linePref,vehiclePref;
    SharedPreferences.Editor driverEditor,lineEditor,vehicleEditor;
    SweetAlertDialog sweetAlertDialog;
    Activity activity;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_line);
getSupportActionBar().hide();
activity=this;
        driverPref =getSharedPreferences("DriverPref", Context.MODE_PRIVATE);
        driverEditor=driverPref.edit();

        linePref =getSharedPreferences("linePref", Context.MODE_PRIVATE);
        lineEditor=linePref.edit();

        vehiclePref =getSharedPreferences("vehiclePref", Context.MODE_PRIVATE);
        vehicleEditor=vehiclePref.edit();


    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!linePref.getString("line_string","empty").equals("empty")){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else{
            int dr_id= driverPref.getInt("id",-1);
            getAffectation(dr_id);
        }
    }

    public void getAffectation(int id){
////
        final Gson gson=new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").create();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RetrofitRoutes retrofitRoutes=retrofit.create(RetrofitRoutes.class);

        sweetAlertDialog= new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog. setTitleText("please wait") .show();

        Call<Affectation> login=retrofitRoutes.getAffectation(id);

        login.enqueue(new Callback<Affectation>() {
            @Override
            public void onResponse(Call<Affectation> call, Response<Affectation> response) {
                if (response.isSuccessful()){

                    Affectation a=response.body();
//                  Driver driver=a.getDriver();
                    Line line=a.getLine();
                    String lineObject=gson.toJson(line);

                    lineEditor.putString("direction",a.getDirection());
                    lineEditor.putInt("line_id",a.getLine_id());
                    lineEditor.putString("line_string",lineObject);
                    lineEditor.commit();
//
                    vehicleEditor.putInt("vehicle_id",a.getVehicle_id());
                    vehicleEditor.commit();

                    sweetAlertDialog.dismiss();
//
                    Intent i=new Intent(getApplication(), MainActivity.class);
//                    i.putExtra("Line", line);

                    startActivity(i);




                }else {
                    sweetAlertDialog.dismiss();
                    new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Email or Password incorrect "+response.code()).show();

                }
            }

            @Override
            public void onFailure(Call<Affectation> call, Throwable t) {
                Toast.makeText(activity, "Oops !!  Somme things wrong  :", Toast.LENGTH_SHORT).show();
                Log.i("login", "onFailure : " +t.getMessage());
            }
        });
    }
}
