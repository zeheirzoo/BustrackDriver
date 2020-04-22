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

import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import params.com.stepview.StatusView;
import params.com.stepview.StatusViewScroller;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class HomeFragment extends Fragment {

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    Vibrator vibrator;
    RelativeLayout mapLayout,lineLayout;
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
    ArrayList<String> stationNamesStrings;
    TextView startStation,targetStation;
    int CurrentStation=1;

    MyTextToSpeak myTextToSpeak;
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
//        fragmentManager.beginTransaction().replace(R.id.frame_content_home,new mapsFragment()).commit();

        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        stationNamesStrings=new ArrayList<>();
         mapButton=view.findViewById(R.id.nav_map);
         lineButton=view.findViewById(R.id.nav_line);
        mapLayout=view.findViewById(R.id.map_layout);
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
//        stationDetector();
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
        //========================================== line ===============================


//        ++====================================maps =========================================

            map = (MapView) view.findViewById(R.id.map);
            ImageButton goToMyLocalisation =  view.findViewById(R.id.goToMyLocalisation);


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
                    myLocalPoint = new GeoPoint(latitude, longitude);
                    mapController.setCenter(myLocalPoint);
                    startMarker.setPosition(myLocalPoint);
                    mapController.setZoom(17.0);
                }
            });
//

       GetRoute();
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
            locationManager.requestLocationUpdates(provider,1000,50,locationListener);

        }

        public List<GeoPoint>  getStationFromJson(String json){
            try{
                List<GeoPoint> path = new ArrayList<GeoPoint>();
                JSONObject jsonObj = new JSONObject(json);
                Log.i("jsonObj", ""+jsonObj);

                JSONArray stationArray = jsonObj.getJSONArray("station");
//                for(int i=0; i < stationArray.length(); i++){
                for(int i=0; i < 10; i++){
                    JSONObject obj = (JSONObject) stationArray.get(i);
                    GeoPoint point= new GeoPoint(obj.getDouble("latitude"),
                            obj.getDouble("longitude"));

                    String str,strOut;
                    str=obj.getString("name");
                    if(str.length() > 15)
                        strOut = str.substring(0,15) + "...";
                    else  strOut=str;
                    if (i==0)
                    startStation.setText(str);
                    if (i==9)
                    targetStation.setText(str);


                    stationNamesStrings.add(strOut);


                    if(i<9){
                        stationNamesStrings.add("");
                        stationNamesStrings.add("");
                        stationNamesStrings.add("");
                    }
                    statusViewScroller.getStatusView().setStatusList(stationNamesStrings);
                    statusViewScroller.getStatusView().setMinStatusAdjacentMargin(20);
                    statusViewScroller.getStatusView().setStepCount(stationNamesStrings.size());
                    path.add(point);

                }

                Log.i("stationNamesStrings",stationNamesStrings.toString());
                return  path;
            }
            catch (JSONException e){
                e.printStackTrace();
                return null;
            }
        }


    public void GetRoute(){

        RequestQueue queue= Volley.newRequestQueue(getContext());
        String url="https://api.jsonbin.io/b/5e980ef7435f5604bb4276e7";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String str=response;
                List<GeoPoint>pathPoint=new ArrayList<>();
                pathPoint=getStationFromJson(str);
                Polyline myPath=new Polyline(map);
                myPath.setPoints(pathPoint);
                myPath.setColor(Color.CYAN);
                map.getOverlayManager().add(myPath);
                map.invalidate();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String >headers=new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("secret-key", "$2b$10$Eo/niSb2cwA8v8ZpdUzv8.gIkLagFk4fzgN8d/imqAKwOHVZJ7Hxm");
                return headers;
            }};
        queue.add(stringRequest);


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
                ((MainActivity) getActivity()).openScanner(true);
            }
        }

    }
}
