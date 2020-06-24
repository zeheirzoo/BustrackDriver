package com.example.driver.controller;

import com.example.driver.model.Driver;
import com.example.driver.model.Line;
import com.example.driver.model.Position;
import com.example.driver.model.Ride;
import com.example.driver.model.User;
import com.example.driver.model.Vehicle;

import org.osmdroid.util.GeoPoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitRoutes {

//// driver
    @POST("login")
    Call<Driver>Login(@Body User user);

    @PATCH("vehicle/{id}")
    Call<Vehicle>UpdatePosition(@Path("id") int id, @Body Position position);


//consum/3/3/30/10:10
    @GET("consum/{id}/{ride}/{price}/{time}")
    Call<String>consumTicket(@Path("id") int id, @Path("ride") int raid, @Path("price") int price, @Path("time")String time);//consum/3/3/30/10:10


//   ride
    @POST("ride")
    Call<Ride>StartRide(@Body Ride ride);



//   line
    @GET("line")
    Call<List<Line>>getLines();


}
