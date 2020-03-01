package com.example.sbb.mail;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Post {


    @POST("email")
    Call<Post> createPost(@Body Postt post);


}
