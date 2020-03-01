package com.example.sbb.Services;

import android.media.session.MediaSession;

import com.example.sbb.Model.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseIdService extends FirebaseInstanceIdService {

    private  String TAG ="MyFirebaseIdService";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken= FirebaseInstanceId.getInstance().getToken();


     //   Log.d(TAG, "Refreshed token: " + refreshedToken);


        if(refreshedToken!=null){
            sendNotification(refreshedToken);



        }


    }

    private void sendNotification(String refreshedToken) {

        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference referance=db.getReference("Token");
        Token token=new Token(refreshedToken,true);
        referance.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);

    }
}