package com.example.retrofitexample;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;
    private static String baseUrl = "https://api.github.com/";

    public static Retrofit getRetrofitInstance(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
