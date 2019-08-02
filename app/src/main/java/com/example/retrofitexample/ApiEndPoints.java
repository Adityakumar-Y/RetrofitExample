package com.example.retrofitexample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndPoints {

    @GET("users")
    Call<List<User>> getUsers();

    @GET("https://jsonplaceholder.typicode.com/posts")
    Call<List<Post>> getPosts(@Query("userId") int userID);

    @GET("users/{user}")
    Call<User> getUser(@Path("user") String name);

    @POST("https://jsonplaceholder.typicode.com/posts")
    Call<Post> createPost(@Body Post post);


}
