package com.example.sbb.Remote;



import com.example.sbb.Model.Myresponce;
import com.example.sbb.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;



public interface APIservice {
    @Headers(

            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAVgweDSw:APA91bGGiEsbjZ5VxXUyIPViqRfyvQaG-aDDlPvLNbjZDLWrk3XYjYZRn-zn_qGmK5topiFJKLRjLPPfPpShWqB65K11x7nBhHXvYzUUJF1AgUDNCDDz_6NvqB6LA38I-0_pHjfWryGU"
            }
    )
    @POST("fcm/send")
    Call<Myresponce> sendNotification(@Body Sender body);



    //Call<Myresponce>

}