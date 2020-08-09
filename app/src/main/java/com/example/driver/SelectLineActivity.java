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

import com.example.driver.Adapter.LineAdapter;
import com.example.driver.controller.RetrofitRoutes;
import com.example.driver.model.Line;
import com.example.driver.model.Position;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectLineActivity extends AppCompatActivity {
GridView line_lv;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    SharedPreferences linePref;
    SharedPreferences.Editor lineEditor;
    List<Line>lines;
    List<String> stationNamesStrings;
    List<Position> stationsAndInerStationsPositionList;
    List<Position> pathPointList;
    List<Position> stationPositionList;
    SweetAlertDialog sweetAlertDialog;
    ImageButton refreche;
    LineAdapter lineAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_line);
getSupportActionBar().hide();
final Activity activity;
activity=this;
        lines=new ArrayList<>();
        linePref =getSharedPreferences("linePref", Context.MODE_PRIVATE);
        lineEditor=linePref.edit();
        getAllLine();
        line_lv =  findViewById(R.id.line_lv);
        refreche =  findViewById(R.id.refreche);
        refreche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllLine();
            }
        });
        line_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Line myLine=lines.get(position);
//                pref
//                driver_id,vehicle_id,line_id,current_station_id,direction
                lineEditor.clear();
                lineEditor.putInt("line_id",myLine.getId());
                lineEditor.putInt("current_station_id",myLine.getStation().get(0).getId());
                lineEditor.commit();


                ChoseDerectionDialog choseDerectionDialog=new ChoseDerectionDialog(SelectLineActivity.this,myLine,1);
                choseDerectionDialog.show();



            }

        });
    }






    private void getAllLine() {
        Gson gson=new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").create();

        String url ="http://transport.misc-lab.org/api/";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RetrofitRoutes retrofitRoutes=retrofit.create(RetrofitRoutes.class);
//
        sweetAlertDialog= new SweetAlertDialog(SelectLineActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog. setTitleText("please wait") .show();


        Call<List<Line>> call=retrofitRoutes.getLines();

        call.enqueue(new Callback<List<Line>>() {
            @Override
            public void onResponse(Call<List<Line>> call, retrofit2.Response<List<Line>> response) {
                sweetAlertDialog.dismiss();
                setLines(response.body());
                lineAdapter=new LineAdapter(getApplicationContext(),lines);
                line_lv.setAdapter(lineAdapter);
                if (lines.isEmpty()){
                    refreche.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<List<Line>> call, Throwable t) {
                Toasty.error(getApplicationContext(),"check you network");
                Log.i("getLine", "onFailure: "+t.getCause());
            }
        });

    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

}
