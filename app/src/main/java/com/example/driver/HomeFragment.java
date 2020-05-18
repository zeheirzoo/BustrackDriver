package com.example.driver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.driver.Adapter.LineAdapter;
import com.example.driver.controller.RetrofitRoutes;
import com.example.driver.model.Line;
import com.example.driver.model.Position;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shuhart.stepview.StepView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import params.com.stepview.StatusView;
import params.com.stepview.StatusViewScroller;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class HomeFragment extends Fragment {

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    Vibrator vibrator;
    RelativeLayout mapLayout,lineLayout,main_layout,getLine;
    Handler handler ;
    StatusViewScroller statusViewScroller;
    Button lineButton,mapButton;
    boolean b=false;
    MapView map = null;
    IMapController mapController;
    GeoPoint  myLocalPoint;
    Marker  startMarker;
    double latitude;
    double longitude;
    String provider;
    List<String> stationNamesStrings;
    List<Position> positionList;
    TextView startStation,targetStation;
    int CurrentStation=1;
    SweetAlertDialog sweetAlertDialog;
    MyTextToSpeak myTextToSpeak;

    GridView line_lv;
    LineAdapter lineAdapter;
    List<Line> lines;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        handler = new android.os.Handler();
        fragmentManager=getChildFragmentManager();

        lines=new ArrayList<>();
        getAllLine();

//        fragmentManager.beginTransaction().replace(R.id.frame_content_home,new mapsFragment()).commit();

        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        stationNamesStrings=new ArrayList<>();
        mapButton=view.findViewById(R.id.nav_map);
        lineButton=view.findViewById(R.id.nav_line);
        mapLayout=view.findViewById(R.id.map_layout);
        main_layout=view.findViewById(R.id.main_layout);
        getLine=view.findViewById(R.id.getLine);
        lineLayout=view.findViewById(R.id.line_layout);
        startStation=view.findViewById(R.id.start);
        targetStation=view.findViewById(R.id.target);
        mapButton.setBackgroundColor(Color.GRAY);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.color.colorPrimary);
                mapButton.setText("Maps");
                lineButton.setText("");

                lineButton.setBackgroundColor(Color.GRAY);
                mapLayout.setVisibility(View.VISIBLE);
                lineLayout.setVisibility(View.GONE);
//                onClick2(v);

            }
        });
        lineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.color.colorPrimary);
                mapButton.setBackgroundColor(Color.GRAY);
                mapLayout.setVisibility(View.GONE);
                mapButton.setText("");
                lineButton.setText("Line");
                lineLayout.setVisibility(View.VISIBLE);
//                onClick2(v);

            }
        });
//
        //========================================== line ===============================
        statusViewScroller =view.findViewById(R.id.status_view);
        Button next =view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusViewScroller.getStatusView().getCurrentCount()<statusViewScroller.getStatusView().getStepCount())
                goToNextStation();
            }
        });

        Button prev =view.findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (statusViewScroller.getStatusView().getCurrentCount()>2)
                goToPreviousStation();
            }
        });


//                ------------currant time------------


        final TextView timeTV=view.findViewById(R.id.current_time);
        final TextView dateTV=view.findViewById(R.id.current_date);

        CountDownTimer newtimer = new CountDownTimer(1000000000, 1000) {

            public void onTick(long millisUntilFinished) {
                Calendar c = Calendar.getInstance();
                timeTV.setText(c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND));
                dateTV.setText(c.get(Calendar.DAY_OF_MONTH)+"-"+ c.get(Calendar.MONTH)+"-"+c.get(Calendar.YEAR));
            }
            public void onFinish() {

            }
        };
        newtimer.start();
//                ------------currant time------------


        //========================================== line ===============================


//        ++====================================maps =========================================







            map = (MapView) view.findViewById(R.id.map);
            ImageButton goToMyLocalisation =  view.findViewById(R.id.goToMyLocalisation);



        line_lv =  view.findViewById(R.id.line_lv);
        line_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                stationNamesStrings=lines.get(position).getStationsNams();
                positionList=lines.get(position).getStationsAndInerStationsPosition();
                statusViewScroller.getStatusView().setStatusList(stationNamesStrings);
                statusViewScroller.getStatusView().setStepCount(stationNamesStrings.size());
                getLine.setVisibility(View.GONE);
                main_layout.setVisibility(View.VISIBLE);
                startStation.setText(stationNamesStrings.get(0));
                targetStation.setText(stationNamesStrings.get(stationNamesStrings.size()-1));
                GetRoute(positionList);

//                responceTx.setText("Statoin names size : "+ lines.get(position).getStationsNams().size()+"\n"+lines.get(position).getStationsNams().toString()+"\n\n"+
//                        "Statoin position size : "+lines.get(position).getStationsAndInerStationsPosition().size()+ "\n"+lines.get(position).getStationsAndInerStationsPosition().toString());
            }

        });



//        ==================
            final ImageButton zoomin=view.findViewById(R.id.zoomin);
            final ImageButton zoomout=view.findViewById(R.id.zoomout);
            zoomin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { if(map.getZoomLevelDouble()==map.getMaxZoomLevel()){
                    zoomin.setBackground(getResources().getDrawable(R.drawable.bg_circle_dark));
                } else {   mapController.setZoom(map.getZoomLevelDouble()+1);
                    zoomout.setBackground(getResources().getDrawable(R.drawable.bg_circle));}     }
            });
            zoomout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {if(map.getZoomLevelDouble()==map.getMinZoomLevel()){
                    zoomout.setBackground(getResources().getDrawable(R.drawable.bg_circle_dark));
                }else{ mapController.setZoom(map.getZoomLevelDouble()-1);
                    zoomin.setBackground(getResources().getDrawable(R.drawable.bg_circle));}    }
            });
//          ============
//
            map.setTileSource(TileSourceFactory.MAPNIK);
            map.setMultiTouchControls(true);
            map.setMinZoomLevel(5.0);


            locationMethode(getContext());
//
            goToMyLocalisation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Position p=positionList.get(statusViewScroller.getStatusView().getCurrentCount()-1);
                    myLocalPoint = new GeoPoint(p.getLatitude(), p.getLongitude());
                    mapController.setCenter(myLocalPoint);
                    startMarker.setPosition(myLocalPoint);
                    mapController.setZoom(17.0);
                }
            });
//




            return view;
        }

        public void locationMethode(Context ctx) {

            LocationManager locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
            if (provider != null) {
                Criteria cr = new Criteria();
                cr.setAccuracy(Criteria.ACCURACY_FINE); // précision
                cr.setAltitudeRequired(true); // altitude
                cr.setBearingRequired(true); // direction
                cr.setCostAllowed(false); // payant/gratuit
                cr.setPowerRequirement(Criteria.POWER_HIGH);  // consommation
                cr.setSpeedRequired(true);  // vitesse
                provider = locationManager.getBestProvider(cr, false);

            } else provider = LocationManager.GPS_PROVIDER;


            mapController = map.getController();
            mapController.setZoom(15.0);
            map.setBuiltInZoomControls(false);
            startMarker = new Marker(map);
            startMarker.setIcon(getResources().getDrawable(R.drawable.ic_location));
            map.getOverlays().add(startMarker);



            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }

            Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
            if (lastKnownLocation!=null) {
                latitude = lastKnownLocation.getLatitude();
                longitude = lastKnownLocation.getLongitude();
                //        altitude = lastKnownLocation.getAltitude();
                GeoPoint lastGeoPoint=new GeoPoint(latitude,longitude);
                mapController.setCenter(lastGeoPoint);
                startMarker.setPosition(lastGeoPoint);
            }



            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    //            altitude = location.getAltitude();
                    myLocalPoint = new GeoPoint(latitude, longitude);
                    mapController.setCenter(myLocalPoint);
                    startMarker.setIcon(getResources().getDrawable(R.drawable.ic_location));
                    startMarker.setPosition(myLocalPoint);

                    map.getOverlays().add(startMarker);
                }
                @Override public void onStatusChanged(String provider, int status, Bundle extras) {

                }
                @Override public void onProviderEnabled(String provider) {

                }
                @Override public void onProviderDisabled(String provider) { }

            };
            locationManager.requestLocationUpdates(provider,1000,5,locationListener);

        }




    public void GetRoute(List<Position> positions){

        List<GeoPoint>pathPoint=new ArrayList<>();
        for (Position p : positions){
            pathPoint.add(new GeoPoint(p.getLatitude(),p.getLongitude()));
        }
        Polyline myPath=new Polyline(map);
        myPath.setPoints(pathPoint);
        myPath.setColor(Color.CYAN);
        map.getOverlayManager().add(myPath);
        map.invalidate();
    }



    public void goToNextStation(){
        CurrentStation=statusViewScroller.getStatusView().getCurrentCount();
        if (CurrentStation>2){
            statusViewScroller.scrollToStep(CurrentStation-1);
        }
        statusViewScroller.getStatusView().setCurrentCount( CurrentStation+1);
        stationDetector();
    }

    public void goToPreviousStation(){
        CurrentStation=statusViewScroller.getStatusView().getCurrentCount();
        if (CurrentStation>3){
            statusViewScroller.scrollToStep(CurrentStation-3);
        }
        statusViewScroller.getStatusView().setCurrentCount( CurrentStation-1);
        stationDetector();
    }

    public void stationDetector() {

        myTextToSpeak=new MyTextToSpeak(getContext());
        final MediaPlayer bip = MediaPlayer.create(getContext(), R.raw.zxing_beep);

        if (statusViewScroller.getStatusView().getCurrentCount()==statusViewScroller.getStatusView().getStepCount()){

            myTextToSpeak.speakOut("Station " + stationNamesStrings.get(statusViewScroller.getStatusView().getCurrentCount()-1), bip);
            myTextToSpeak.speakOut("the end of our travel take care ", null);
            Toasty.success(getContext(), "لمحطة" + "\n " + stationNamesStrings.get(statusViewScroller.getStatusView().getCurrentCount()-1)).show();
            ((MainActivity) getActivity()).openScanner(false);



            Collections.reverse((List) stationNamesStrings);
            statusViewScroller.getStatusView().setStatusList(stationNamesStrings);
            statusViewScroller.getStatusView().setCurrentCount(1);
            statusViewScroller.scrollToStep(1);
            startStation.setText(stationNamesStrings.get(0)+"");
            targetStation.setText(stationNamesStrings.get(stationNamesStrings.size()-1));




        }else {
            if (!stationNamesStrings.get(statusViewScroller.getStatusView().getCurrentCount() - 2).isEmpty() && stationNamesStrings.get(statusViewScroller.getStatusView().getCurrentCount() - 1).isEmpty()) {
                //            Toasty.success(getContext(), "الانطلاق محطة " +"\n "+stationNamesStrings.get(statusViewScroller.getStatusView().getCurrentCount())).show();
                ((MainActivity) getActivity()).openScanner(false);

            }
            if (!stationNamesStrings.get(statusViewScroller.getStatusView().getCurrentCount()).isEmpty()) {
                myTextToSpeak.speakOut(" next station " + stationNamesStrings.get(statusViewScroller.getStatusView().getCurrentCount()), bip);
                Toasty.success(getContext(), "المحطة القادمة" + "\n " + stationNamesStrings.get(statusViewScroller.getStatusView().getCurrentCount())).show();
                ((MainActivity) getActivity()).openScanner(true);

            }
            if (!stationNamesStrings.get(statusViewScroller.getStatusView().getCurrentCount() - 1).isEmpty()) {
                myTextToSpeak.speakOut("Station " + stationNamesStrings.get(statusViewScroller.getStatusView().getCurrentCount() - 1), bip);
                Toasty.success(getContext(), "محطة" + "\n " + stationNamesStrings.get(statusViewScroller.getStatusView().getCurrentCount() - 1)).show();
                sweetAlertDialog. setTitleText( "Station "   + stationNamesStrings.get(statusViewScroller.getStatusView().getCurrentCount() - 1)) .show();
                timer();
                ((MainActivity) getActivity()).openScanner(true);
            }
        }

    }



    public void timer(){
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override public void run() {
                //new intent here
                sweetAlertDialog.dismiss();
            }
        }, 10000);
    }

    private void getAllLine() {
        Gson gson=new GsonBuilder().serializeNulls().create();

        String url ="http://transport.misc-lab.org/api/";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RetrofitRoutes retrofitRoutes=retrofit.create(RetrofitRoutes.class);

        sweetAlertDialog= new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog. setTitleText("please wait") .show();


        Call<List<Line>> call=retrofitRoutes.getLines();

        call.enqueue(new Callback<List<Line>>() {
            @Override
            public void onResponse(Call<List<Line>> call, retrofit2.Response<List<Line>> response) {
                sweetAlertDialog.dismiss();
                setLines(response.body());
                lineAdapter=new LineAdapter(getContext(),lines);
                line_lv.setAdapter(lineAdapter);

            }

            @Override
            public void onFailure(Call<List<Line>> call, Throwable t) {
                sweetAlertDialog.dismiss();
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Error"+t.getCause()) .show();
                Log.i("getLine", "onFailure: "+t.getCause());
            }
        });

    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }


    @Override
    public void onDestroyView() {
        if(myTextToSpeak != null) {
            myTextToSpeak.stop();

        }
        super.onDestroyView();
    }


}
