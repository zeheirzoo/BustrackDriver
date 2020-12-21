package com.example.driver;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.driver.Adapter.LineAdapter;
import com.example.driver.controller.RetrofitRoutes;
import com.example.driver.model.Driver;
import com.example.driver.model.Line;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class test extends Activity{
    /** Called when the activity is first created. */

    private Button btnSpeak;
    private EditText txtText;
    private TextView responceTx;
    SweetAlertDialog sweetAlertDialog;
    GridView line_lv;
    LineAdapter lineAdapter;
    List<Line> lines;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        btnSpeak = findViewById(R.id.btnSpeak);
        responceTx = findViewById(R.id.responceTx);
        txtText =  findViewById(R.id.txtText);


        lines=new ArrayList<>();
//

//        btnSpeak.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getAllLine();
//            }
//        });
        line_lv =  findViewById(R.id.line_lv);
//        line_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//           @Override
//           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               responceTx.setText("Statoin names size : "+ lines.get(position).getStationsNams().size()+"\n"+lines.get(position).getStationsNams().toString()+"\n\n"+
//                       "Statoin position size : "+lines.get(position).getStationsAndInerStationsPosition().size()+ "\n"+lines.get(position).getStationsAndInerStationsPosition().toString());
//           }
//
//        });
    }

//    private void getAllLine() {
//        Gson gson=new GsonBuilder().serializeNulls().create();
//
//        String url ="http://transport.misc-lab.org/api/";
//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//        RetrofitRoutes retrofitRoutes=retrofit.create(RetrofitRoutes.class);
//
//        sweetAlertDialog= new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
//        sweetAlertDialog. setTitleText("please wait") .show();
//
//
//        Call<List<Line>> call=retrofitRoutes.getLines();
//
//        call.enqueue(new Callback<List<Line>>() {
//            @Override
//            public void onResponse(Call<List<Line>> call, Response<List<Line>> response) {
//                sweetAlertDialog.dismiss();
//                setLines(response.body());
//                lineAdapter=new LineAdapter(getApplicationContext(),lines);
//                line_lv.setAdapter(lineAdapter);
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Line>> call, Throwable t) {
//                sweetAlertDialog.dismiss();
//                new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Error"+t.getCause()) .show();
//                Log.i("getLine", "onFailure: "+t.getCause());
//            }
//        });
//
//    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }
}
