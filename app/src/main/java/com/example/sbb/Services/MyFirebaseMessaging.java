package com.example.sbb.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;


import com.example.sbb.Helper.NotificationHelper;
import com.example.sbb.HomeActivity;
import com.example.sbb.R;
import com.example.sbb.Request_Activity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

/**
 * Created by HASIB on 6/29/2018.
 */

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        Log.i("OMK", "onMessageReceived: "+remoteMessage.getNotification().getTitle());
      //  Toast.makeText(this, "Message    "+remoteMessage, Toast.LENGTH_SHORT).show();


        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

            sendNotificatonAPI26(remoteMessage);
        }else {
            sendNotification(remoteMessage);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotificatonAPI26(RemoteMessage remoteMessage) {


       // Map<String,String> notification=remoteMessage.getData();

        String title=remoteMessage.getNotification().getTitle();
        String content=remoteMessage.getNotification().getBody();

        Intent in=new Intent(this, Request_Activity.class);


        PendingIntent pendingIntent;
        NotificationHelper helper;
        Notification.Builder builder ;


     //   if (Common.currentUser!=null){


            //  in.putExtra(Common.PHONE_TEXT,Common.currentShipper.getNumber());
            in.setFlags(in.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent= PendingIntent.getActivity(this,0,in, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            helper=new NotificationHelper(this);


            builder= helper.getFoodServerlicationNotification(title,content,pendingIntent,defaultSound);

            helper.getManager().notify(new Random().nextInt(),builder.build());

      //  }
        /*else {
            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            helper=new NotificationHelper(this);
            builder=helper.getFoodServerlicationNotification(title,content,defaultSoundUri);

            helper.getManager().notify(new Random().nextInt(),builder.build());
        }*/





    }

    private void sendNotification(RemoteMessage remoteMessage) {

  /*      Map<String,String> notification=remoteMessage.getData();

        String title=notification.get("title");
        String content=notification.get("message");*/


        String title=remoteMessage.getNotification().getTitle();
        String content=remoteMessage.getNotification().getBody();


      //  remoteMessage.getData().get("")


       // if (Common.currentUser!=null){


            Intent in=new Intent(this, Request_Activity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent= PendingIntent.getActivity(this,0,in, PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.request)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setAutoCancel(true)
                    .setSound(defaultSound);

            NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0,notificationBuilder.build());
      //  }
      /*  else {


            Uri defaultSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_local_shipping_black_24dp)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setAutoCancel(true)
                    .setSound(defaultSound);

            NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0,notificationBuilder.build());
        }*/


    }
}