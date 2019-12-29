package com.example.tcallercharacters.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCall {

    @GET("/2018/01/22/life-as-an-android-engineer/")
    Call<String> doNetworkRequest();

}
