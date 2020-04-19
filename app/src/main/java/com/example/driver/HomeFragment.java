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
        statusViewScroller.getStatusView().setMinStatusAdjacentMargin(0);
        Button next =view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusViewScroller.scrollBy(180,0);
//                Toast.makeText(getContext(), statusViewScroller.getStatusView().getMinStatusAdjacentMargin()+"", Toast.LENGTH_SHORT).show();
                statusViewScroller.getStatusView().setCurrentCount( statusViewScroller.getStatusView().getCurrentCount()+1);

            }
        });

        Button prev =view.findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusViewScroller.scrollBy(-180,0);
//                Toast.makeText(getContext(), statusViewScroller.getStatusView().getMinStatusAdjacentMargin()/4+"", Toast.LENGTH_SHORT).show();
                statusViewScroller.getStatusView().setCurrentCount( statusViewScroller.getStatusView().getCurrentCount()-1);
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
                    startMarker.setPosition(myLocalPoint);
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
                for(int i=0; i < stationArray.length(); i++){
                    JSONObject obj = (JSONObject) stationArray.get(i);
                    GeoPoint point= new GeoPoint(obj.getDouble("latitude"),
                            obj.getDouble("longitude"));
                    if(i==0){
                        startStation.setText(obj.getString("name"));
                    }
                    if (i==stationArray.length()-1){
                        targetStation.setText(obj.getString("name"));
                    }
                    String str,strOut;
                    str=obj.getString("name");
                    if(str.length() > 7)
                        strOut = str.substring(0,7) + "...";
                    else  strOut=str;

                    stationNamesStrings.add(strOut);
                    stationNamesStrings.add("");
                    stationNamesStrings.add("");
                    stationNamesStrings.add("");
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


}
