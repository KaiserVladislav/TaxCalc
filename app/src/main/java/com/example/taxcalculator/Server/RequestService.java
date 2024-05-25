package com.example.taxcalculator.Server;

public class RequestService {
    RetrofitService retrofitService = new RetrofitService();
    UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
}
