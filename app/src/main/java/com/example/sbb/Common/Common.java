package com.example.sbb.Common;

import com.example.sbb.Remote.APIservice;
import com.example.sbb.Remote.FCMretrofitClient;

public class Common {


    public static final String BaseUrl="https://fcm.googleapis.com/";




    public static APIservice getFCMClient(){
        return FCMretrofitClient.getClint(BaseUrl).create(APIservice.class);
    }

}
