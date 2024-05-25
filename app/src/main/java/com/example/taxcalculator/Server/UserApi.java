package com.example.taxcalculator.Server;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    @GET("/users/get-all")
    Call<List<User>> getAllUsers();

    @GET("/users/get-by-email/{email}")
    Call<User> getUserByEmail(@Path("email") String email);

    @GET("/users/get-by-username/{username}")
    Call<User> getUserByUsername(@Path("username") String username);

    @GET("/users/count-by-email/{email}")
    Call<Integer> countUsersByEmail(@Path("email") String email);

    @GET("/users/count-by-username/{username}")
    Call<Integer> countUsersByUsername(@Path("username") String email);

    @POST("/users/add")
    Call<User> save(@Body User user);
}
