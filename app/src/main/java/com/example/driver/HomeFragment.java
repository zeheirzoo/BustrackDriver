package com.example.driver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.driver.Adapter.LineAdapter;
import com.example.driver.controller.RetrofitRoutes;
import com.example.driver.model.InterStation;
import com.example.driver.model.IntermediaryPoint;
import com.example.driver.model.Line;
import com.example.driver.model.Position;
import com.example.driver.model.Ride;
import com.example.driver.model.Station;
import com.example.driver.model.Vehicle;
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
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import retrofit2.Response;
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
    LinearLayout correct_position;
    Button lineButton,mapButton,start_ride,next,prev;
    boolean b=false;
    MapView map = null;
    IMapController mapController;
    GeoPoint  myLocalPoint;
    Marker  startMarker;
    double latitude;
    double longitude;
    ImageButton zoomin,zoomout;
    String provider;
    List<String> stationNamesStrings,stationTitle;
    List<Position> stationsAndInerStationsPositionList;
    List<Position> pathPointList;
    List<Position> stationPositionList;
    TextView startStation,targetStation, timeTV,dateTV;
    int CurrentStation=1;
    SweetAlertDialog sweetAlertDialog;
    MyTextToSpeak myTextToSpeak;
    String lineColorType;

int driver_id;
int vehicle_id;
int line_id;
String direction;
int current_station_id;
SharedPreferences pref;
SharedPreferences.Editor editor;
SharedPreferences linePref;
SharedPreferences.Editor lineEditor;
Line myLine;
boolean online =false ;



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

//============
        pref =getActivity().getSharedPreferences("DriverPref", Context.MODE_PRIVATE);
        editor=pref.edit();
        linePref =getActivity().getSharedPreferences("linePref", Context.MODE_PRIVATE);
        lineEditor=linePref.edit();
        Log.i("driverPref", "pref :  "+pref.getAll());
        Log.i("driverPref2", "line pref :  "+linePref.getAll());

//============
//============

//============

        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        stationNamesStrings=new ArrayList<>();
        mapButton=view.findViewById(R.id.nav_map);
        lineButton=view.findViewById(R.id.nav_line);
        start_ride=view.findViewById(R.id.start_ride);
        mapLayout=view.findViewById(R.id.map_layout);
        main_layout=view.findViewById(R.id.main_layout);
        correct_position=view.findViewById(R.id.correct_position);
        getLine=view.findViewById(R.id.getLine);
        lineLayout=view.findViewById(R.id.line_layout);
        startStation=view.findViewById(R.id.start);
        targetStation=view.findViewById(R.id.target);
        statusViewScroller=view.findViewById(R.id.status_view);
        mapButton.setBackgroundColor(Color.GRAY);
        map = (MapView) view.findViewById(R.id.map);
        ImageButton goToMyLocalisation =  view.findViewById(R.id.goToMyLocalisation);



        startMarker = new Marker(map);
        startMarker.setIcon(getResources().getDrawable(R.drawable.ic_location_bus_24dp));


        mapController = map.getController();
        mapController.setZoom(15.0);
        map.setBuiltInZoomControls(false);
        map.getOverlays().add(startMarker);



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

        start_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                driver_id=pref.getInt("id",-1);
                vehicle_id=1;
                line_id=linePref.getInt("line_id",-1);
                current_station_id=linePref.getInt("current_station_id",-1);
                direction=linePref.getString("direction","a_b");
                createRide(new Ride(driver_id,vehicle_id,line_id,current_station_id,direction,new Date(),null,new Date()));
            }
        });


        //========================================== line ===============================
        statusViewScroller =view.findViewById(R.id.status_view);
         next =view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusViewScroller.getStatusView().getCurrentCount()<statusViewScroller.getStatusView().getStepCount())
                goToNextStation();
            }
        });

         prev =view.findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (statusViewScroller.getStatusView().getCurrentCount()>2)
                goToPreviousStation();
            }
        });


//                ------------currant time------------

                    currantTimeTimer(view);
//                ------------currant time------------


        //========================================== line ===============================


//        ++====================================maps =========================================









//        ==================

         zoomController(view);
//          ============
//
            map.setTileSource(TileSourceFactory.MAPNIK);
            map.setMultiTouchControls(true);
            map.setMinZoomLevel(5.0);

        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(getContext(), map);
        mRotationGestureOverlay.setEnabled(true);
        map.setMultiTouchControls(true);
        map.getOverlays().add(mRotationGestureOverlay);
//
            goToMyLocalisation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Position p=stationPositionList.get(0);
                    myLocalPoint = new GeoPoint(p.getLatitude(), p.getLongitude());
                    mapController = map.getController();
                    mapController.animateTo(myLocalPoint);
                    mapController.setCenter(myLocalPoint);
                    startMarker.setPosition(myLocalPoint);
                    mapController.setZoom(17.0);
                }
            });

//        locationMethode(getContext());

            if (linePref.getBoolean("online",false)){
            }

//








// To retrieve object in second Activity
        myLine=(Line) getActivity().getIntent().getSerializableExtra("Line");
        if (myLine!=null){
            if (linePref.getString("direction","")=="a_b"){
                getStations(myLine.getStation());

            }else {
                getStationsB(myLine.getStation());
            }
            statusViewScroller.getStatusView().setStatusList(stationNamesStrings);
            statusViewScroller.getStatusView().setStepCount(stationNamesStrings.size()+1);


            statusViewScroller.getStatusView().setStatusList(stationNamesStrings);
            statusViewScroller.getStatusView().setStepCount(stationNamesStrings.size());

            startStation.setText(stationNamesStrings.get(0));
            targetStation.setText(stationNamesStrings.get(stationNamesStrings.size()-1));

            Position p=stationPositionList.get(0);
            myLocalPoint = new GeoPoint(p.getLatitude(), p.getLongitude());
            lineColorType=myLine.getColortype();
            mapController = map.getController();
            mapController.setCenter(myLocalPoint);
            startMarker.setPosition(myLocalPoint);
            mapController.setZoom(17.0);

            GetRoute(pathPointList);
            drawStationMarker(stationPositionList);
// stationNamesStrings=new ArrayList<>();
//    stationTitle=new ArrayList<>();
//    stationsAndInerStationsPositionList=new ArrayList<>();
//    stationPositionList=new ArrayList<>();
//    pathPointList=new ArrayList<>();
            Log.i("test", "stationNamesStrings : "+stationNamesStrings.size());
            Log.i("test", "stationsAndInerStationsPositionList: "+stationsAndInerStationsPositionList.size());
            Log.i("test", "stationTitle : "+stationTitle.size());
            Log.i("test", "stationPositionList: "+stationPositionList.size());

        }else {
            new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).setTitleText("my line  "+myLine).show();
        }

            return view;
        }

    private void currantTimeTimer(View view) {
         timeTV=view.findViewById(R.id.current_time);
         dateTV=view.findViewById(R.id.current_date);
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
    }

    private void zoomController(View view) {
        zoomin=view.findViewById(R.id.zoomin);
        zoomout=view.findViewById(R.id.zoomout);
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

                    Position position=stationsAndInerStationsPositionList.get(statusViewScroller.getStatusView().getCurrentCount());
                    Location nextStepLocation = new Location(provider);
                    nextStepLocation.setLatitude(position.getLatitude());
                    nextStepLocation.setLongitude(position.getLongitude());
                    float distanceInMeters = location.distanceTo(nextStepLocation);
                    if (distanceInMeters<20){
                        goToNextStation();
                    }

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    //            altitude = location.getAltitude();
                    myLocalPoint = new GeoPoint(latitude, longitude);
                    mapController.setCenter(myLocalPoint);
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

    public void drawStationMarker(List<Position> stationPosition){
       for (int i =0;i<stationPosition.size();i++){
           Position p=stationPosition.get(i);
           Marker stationMarker = new Marker(map);
           stationMarker.setIcon(getResources().getDrawable(R.mipmap.ic_bus_station_round));
           stationMarker.setTitle(stationTitle.get(i));
           stationMarker.setPosition(new GeoPoint(p.getLatitude(),p.getLongitude()));
           map.getOverlays().add(stationMarker);
       }
    }

    public void GetRoute(List<Position> positions){

        List<GeoPoint>pathPoint=new ArrayList<>();
        for (Position p : positions){
            pathPoint.add(new GeoPoint(p.getLatitude(),p.getLongitude()));
        }
        Polyline myPath=new Polyline(map);
        myPath.setPoints(pathPoint);
        myPath.setColor(Color.parseColor(lineColorType));
        myPath.setWidth((float) 8);
        map.getOverlayManager().add(myPath);
        map.invalidate();
    }

    public void goToNextStation(){
        CurrentStation=statusViewScroller.getStatusView().getCurrentCount();
        if (CurrentStation>2){
            statusViewScroller.scrollToStep(CurrentStation-1);
        }
        Position p =stationsAndInerStationsPositionList.get(statusViewScroller.getStatusView().getCurrentCount());
        myLocalPoint=new GeoPoint(p.getLatitude(),p.getLongitude());
        startMarker.setPosition(myLocalPoint);
        mapController.setCenter(myLocalPoint);
        mapController.animateTo(myLocalPoint);

        statusViewScroller.getStatusView().setCurrentCount( CurrentStation+1);
        map.invalidate();
        stationDetector();

        updatePosition(1,p);

    }

    public void goToPreviousStation(){
        CurrentStation=statusViewScroller.getStatusView().getCurrentCount();
        if (CurrentStation>3){
            statusViewScroller.scrollToStep(CurrentStation-3);
        }
        Position p =stationsAndInerStationsPositionList.get(CurrentStation-1);
        myLocalPoint=new GeoPoint(p.getLatitude(),p.getLongitude());
        startMarker.setPosition(myLocalPoint);

        mapController.setCenter(myLocalPoint);
        mapController.animateTo(myLocalPoint);

        statusViewScroller.getStatusView().setCurrentCount( CurrentStation-1);
        map.invalidate();

        stationDetector();

        updatePosition(1,p);
    }

    public void stationDetector() {

        myTextToSpeak=new MyTextToSpeak(getContext());
        final MediaPlayer bip = MediaPlayer.create(getContext(), R.raw.zxing_beep);

        if (statusViewScroller.getStatusView().getCurrentCount()==statusViewScroller.getStatusView().getStepCount()){

            myTextToSpeak.speakOut("Station " + stationNamesStrings.get(statusViewScroller.getStatusView().getCurrentCount()-1), bip);
            myTextToSpeak.speakOut("the end of our travel take care ", null);
//            Toasty.success(getContext(), "لمحطة" + "\n " + stationNamesStrings.get(statusViewScroller.getStatusView().getCurrentCount()-1)).show();
            ((MainActivity) getActivity()).openScanner(false);
            new SweetAlertDialog(getContext(),SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("the end of our travel ")
                    .setConfirmButton("getnew ride ", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
            sweetAlertDialog.dismiss();
//                            Collections.reverse((List) stationNamesStrings);
//                            Collections.reverse((List) stationsAndInerStationsPositionList);
//                            statusViewScroller.getStatusView().setStatusList(stationNamesStrings);
//                            statusViewScroller.getStatusView().setCurrentCount(1);
//                            statusViewScroller.scrollToStep(1);
//                            startStation.setText(stationNamesStrings.get(0)+"");
//                            targetStation.setText(stationNamesStrings.get(stationNamesStrings.size()-1));

                        }
                    }).show();

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

    private void createRide(final Ride ride) {
        Gson gson=new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").create();


        String url ="http://transport.misc-lab.org/api/";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RetrofitRoutes retrofitRoutes=retrofit.create(RetrofitRoutes.class);

        sweetAlertDialog= new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog. setTitleText("please wait") .show();



        Call<Ride> call=retrofitRoutes.StartRide(ride);

        call.enqueue(new Callback<Ride>() {
            @Override
            public void onResponse(Call<Ride> call, retrofit2.Response<Ride> response) {
             if(response.isSuccessful()){
                  sweetAlertDialog.dismiss();
                  new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("StART Ride SUCCESSFUL"+response.body().getId()) .show();

                  start_ride.setVisibility(View.GONE);
                  correct_position.setVisibility(View.VISIBLE);
                  lineEditor.putBoolean("online",true);
                  lineEditor.putInt("rideID",response.body().getId());
                  lineEditor.commit();

                 ((MainActivity) getActivity()).openScanner(true);
             }
              else {

                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("StART Ride failed") .show();


              }
            }

            @Override
            public void onFailure(Call<Ride> call, Throwable t) {
                sweetAlertDialog.dismiss();
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Error"+t.getCause()) .show();
                Log.i("driverPref6", "eroor :  "+t.getCause());


            }
        });
    }

    private void updatePosition(int id,Position position) {
        Gson gson=new GsonBuilder().serializeNulls().create();


        String url ="http://transport.misc-lab.org/api/";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RetrofitRoutes retrofitRoutes=retrofit.create(RetrofitRoutes.class);



        Call<Vehicle> call=retrofitRoutes.UpdatePosition(id,position);

        call.enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {

                if (response.isSuccessful()&&response.code()==200){
                    Log.i("successful", "onResponse: "+response.body());
                }else{
                    Toasty.warning(getContext(),response+"").show();


                }

            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
Toasty.error(getContext(),t.getCause()+"").show();
            }
        });
    }

    public void getStations(List<Station>stations) {
    stationNamesStrings=new ArrayList<>();
    stationTitle=new ArrayList<>();
    stationsAndInerStationsPositionList=new ArrayList<>();
    stationPositionList=new ArrayList<>();
    pathPointList=new ArrayList<>();
    for (Station s:stations){
        if (s.getA_b_latitude()!=0.0){
            List<InterStation>interStationList=new ArrayList<>();
            interStationList=s.getSrcinterstation();

                stationNamesStrings.add((s.getName()));
                stationTitle.add((s.getName()));
                if(!s.equals(stations.get(stations.size()-1))){
                    stationNamesStrings.add("");
                    stationNamesStrings.add("");
                    stationNamesStrings.add("");
                }

            Position sPosition=new Position(s.getA_b_address(),s.getA_b_latitude(),s.getA_b_longitude());
                stationPositionList.add(sPosition);
                stationsAndInerStationsPositionList.add(sPosition);

                if(interStationList.size()>0){
                    for(IntermediaryPoint point : PointInOrder(interStationList.get(0).getIntermediary_point()) ){
                        Position InterS_Position;
                        InterS_Position = new Position(point.getA_b_address(), point.getA_b_latitude(), point.getA_b_longitude());
                        stationsAndInerStationsPositionList.add(InterS_Position);
                    }

    //           Path point list
                    JSONArray jsonArray;
                    for (InterStation interStation:interStationList){
                        try {
                            jsonArray=new JSONArray( interStation.getA_b_path());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                double[] LatLongs= new Gson().fromJson(jsonArray.get(i).toString(),double[].class) ;
                                Position position=new Position(null,LatLongs[0],LatLongs[1]);
                                pathPointList.add(position);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }


        }
    }
    }

    public void getStationsB(List<Station>stations) {
    stationNamesStrings=new ArrayList<>();
    stationTitle=new ArrayList<>();
    stationsAndInerStationsPositionList=new ArrayList<>();
    stationPositionList=new ArrayList<>();
    pathPointList=new ArrayList<>();
    Collections.reverse(stations);
    for (Station s:stations){
        if (s.getB_a_latitude()!=0.0){

            List<InterStation>interStationList=new ArrayList<>();
                interStationList=s.getDestinterstation();
    //            stations names

                stationNamesStrings.add((s.getName()));
            stationTitle.add((s.getName()));
                if(!s.equals(stations.get(stations.size()-1))){
                    stationNamesStrings.add("");
                    stationNamesStrings.add("");
                    stationNamesStrings.add("");
                }
                Position sPosition=new Position(s.getB_a_address(),s.getB_a_latitude(),s.getB_a_longitude());
                stationPositionList.add(sPosition);
                stationsAndInerStationsPositionList.add(sPosition);


    //          stations positions


            if(interStationList.size()>0){

               List<IntermediaryPoint>points = new ArrayList<>();
                points=PointInOrder(interStationList.get(0).getIntermediary_point());
                Collections.reverse(points);
                for(IntermediaryPoint point :points ){
                    Position InterS_Position;
                        InterS_Position=new Position(point.getA_b_address(),point.getB_a_latitude(),point.getB_a_longitude());
                        stationsAndInerStationsPositionList.add(InterS_Position);
                }

    //           Path point list
                JSONArray jsonArray;

                    for (InterStation interStation:interStationList){
                        try {

                                jsonArray = new JSONArray(interStation.getB_a_path());

                            for (int i = 0; i < jsonArray.length(); i++) {
                                double[] LatLongs = new Gson().fromJson(jsonArray.get(i).toString(), double[].class);
                                    Position position = new Position(null, LatLongs[0], LatLongs[1]);
                                    pathPointList.add(position);
                                }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            Log.i("home", "getStations: "+stationsAndInerStationsPositionList);
        }
    }
    }

    public  List<IntermediaryPoint> PointInOrder(List<IntermediaryPoint> points){
    //
    Collections.sort(points, new Comparator<IntermediaryPoint>() {
        @Override
        public int compare(IntermediaryPoint o1, IntermediaryPoint o2) {
            return o1.getOrder().compareTo(o2.getOrder());
        }
    });


    return points;


    }


    @Override
    public void onDestroyView() {
        if(myTextToSpeak != null) {
            myTextToSpeak.stop();

        }
        super.onDestroyView();
    }



}
