package com.example.sbb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sbb.Common.Common;
import com.example.sbb.Model.Myresponce;
import com.example.sbb.Model.Notification;
import com.example.sbb.Model.Sender;
import com.example.sbb.Model.Token;
import com.example.sbb.Remote.APIservice;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

public class BloodRequest extends AppCompatActivity {

    private String ReciverID;
    private String SenderID;
    private DatabaseReference MuserDatabase;
    private FirebaseAuth Mauth;

    private DatabaseReference FriendsDatabase;
    private TextView phonenumber;
    private TextView blood;
    private TextView sextext;
    private TextView height;
    private TextView Age;
    private TextView friendsname;
    private TextView requestbuttontext;
    private String CurrentStates;
    private String Currentdate;

    /// blood card
    private Button requestbutton, unfriendbutton;
    /// blood card

    //////friends user details
    private String FriendsBloodGroup;
    private String FriendsUsername;
    private String FriendsGender;
    private String FriendsHeight;
    private String FriendsAge;
    private String FriendsPhoneNumber;
    private String FriendsPhotoUrl;
    //////friends user details
    private String Currentitme;

    private CircleImageView senderimage;
    private CircleImageView reciverimage;
    private String CURRENT_STATES;
    private DatabaseReference MFriendRequestDatabase;
    private DatabaseReference Friends;
    APIservice mService;
    private DatabaseReference MnotufactionDataase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request);

        mService= Common.getFCMClient();
        MnotufactionDataase = FirebaseDatabase.getInstance().getReference().child("Notifactions");


        CurrentStates = "not_friends";

        Friends = FirebaseDatabase.getInstance().getReference().child("Friend");
        MFriendRequestDatabase = FirebaseDatabase.getInstance().getReference().child("FriendRequest");
        requestbutton = findViewById(R.id.RequestButtonID);
        unfriendbutton = findViewById(R.id.RemoveButtonID);

        requestbuttontext = findViewById(R.id.RequestButtonID);
        friendsname = findViewById(R.id.FriendsNameID);
        blood = findViewById(R.id.BloodGroup);
        phonenumber = findViewById(R.id.PhoenNumberID);
        sextext = findViewById(R.id.Sex);
        height = findViewById(R.id.Height);
        Age = findViewById(R.id.AgeText);

        senderimage = findViewById(R.id.SenderImageID);
        reciverimage = findViewById(R.id.ReciverImageID);

        Mauth = FirebaseAuth.getInstance();
        SenderID = Mauth.getCurrentUser().getUid();
        MuserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        MuserDatabase.keepSynced(true);

        MuserDatabase.child(SenderID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    if (dataSnapshot.hasChild("profile_imageLink")) {
                        String Myimage = dataSnapshot.child("profile_imageLink").getValue().toString();
                        Picasso.with(getApplicationContext()).load(Myimage).placeholder(R.drawable.defaltimage).into(senderimage);
                        Picasso.with(getApplicationContext()).load(Myimage).networkPolicy(NetworkPolicy.OFFLINE).into(senderimage, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ReciverID = getIntent().getStringExtra("Key");


        FriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(ReciverID);
        FriendsDatabase.keepSynced(true);
        FriendsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    if (dataSnapshot.hasChild("phone")) {
                        FriendsPhoneNumber = dataSnapshot.child("phone").getValue().toString();
                        phonenumber.setText(FriendsPhoneNumber);
                    }
                    if (dataSnapshot.hasChild("blood")) {
                        FriendsBloodGroup = dataSnapshot.child("blood").getValue().toString();
                        blood.setText(FriendsBloodGroup);
                    }
                    if (dataSnapshot.hasChild("gender")) {
                        FriendsGender = dataSnapshot.child("gender").getValue().toString();
                        sextext.setText(FriendsGender);
                    }
                    if (dataSnapshot.hasChild("hight")) {
                        FriendsHeight = dataSnapshot.child("hight").getValue().toString();
                        height.setText(FriendsHeight);
                    }
                    if (dataSnapshot.hasChild("age")) {
                        FriendsAge = dataSnapshot.child("age").getValue().toString();
                        Age.setText(FriendsAge);
                    }
                    if (dataSnapshot.hasChild("profile_imageLink")) {
                        String profile_imageLinkget = dataSnapshot.child("profile_imageLink").getValue().toString();
                        Picasso.with(getApplicationContext()).load(profile_imageLinkget).placeholder(R.drawable.defaltimage).into(reciverimage);
                        Picasso.with(getApplicationContext()).load(profile_imageLinkget).networkPolicy(NetworkPolicy.OFFLINE).into(reciverimage, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
                    }
                    if (dataSnapshot.hasChild("name")) {
                        String nameget = dataSnapshot.child("name").getValue().toString();
                        friendsname.setText(nameget);
                    }
                } else {
                    String errormessage = "somethings error";
                    Toast.makeText(getApplicationContext(), errormessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        unfriendbutton.setEnabled(false);

        if (ReciverID.equals(SenderID)) {

            unfriendbutton.setEnabled(false);
            requestbutton.setEnabled(false);
            unfriendbutton.setVisibility(View.INVISIBLE);
            requestbutton.setVisibility(View.INVISIBLE);

        } else {

            requestbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestbutton.setEnabled(false);


                    if (CurrentStates.equals("not_friends")) {
                        SendFriend_Request();
                    }

                    if (CurrentStates.equals("request_send")) {
                        Cancel_Request();
                    }

                    if (CurrentStates.equals("Request_Recived")) {
                        AccepectRequest();
                    }

                    if (CurrentStates.equals("friends")) {

                        UnfriendExistingFriend();
                    }

                }
            });
        }

        matainted_button();

    }


    ///unfriend
    private void UnfriendExistingFriend() {
        Friends.child(SenderID).child(ReciverID)
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Friends.child(ReciverID).child(SenderID)
                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                CurrentStates = "not_friends";
                                requestbutton.setEnabled(true);
                                requestbutton.setText("Request");
                                requestbutton.setBackgroundResource(R.drawable.request_buttondesign);
                                unfriendbutton.setEnabled(false);
                            }
                        }
                    });
                }
            }
        });
    }
    ///unfriend


    ///Accepect Request
    private void AccepectRequest() {
        Calendar calendartime = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        Currentitme = simpleDateFormat.format(calendartime.getTime());

        Calendar calendardate = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MMMM-yyyy");
        Currentdate = simpleDateFormat1.format(calendardate.getTime());

        Friends.child(SenderID).child(ReciverID).child("date").setValue(Currentdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            Friends.child(ReciverID).child(SenderID).child("date").setValue(Currentdate)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                MFriendRequestDatabase.child(SenderID).child(ReciverID)
                                                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            MFriendRequestDatabase.child(ReciverID).child(SenderID)
                                                                    .removeValue()
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                CurrentStates = "friends";
                                                                                requestbutton.setEnabled(true);
                                                                                requestbutton.setText("Remove");
                                                                                requestbutton.setBackgroundResource(R.drawable.cancelrequest_design);
                                                                                unfriendbutton.setEnabled(false);
                                                                            }
                                                                        }
                                                                    });
                                                        }


                                                    }
                                                });
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
    ///Accepect Request


    ///send Friend Request
    private void SendFriend_Request() {

        MFriendRequestDatabase.child(SenderID).child(ReciverID).
                child("request_type").setValue("send").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    MFriendRequestDatabase.child(ReciverID).child(SenderID)
                            .child("request_type").setValue("recived")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {


                                        HashMap<String, String> notifactionmap = new HashMap<>();
                                        notifactionmap.put("from", SenderID);
                                        notifactionmap.put("type", "request");
                                        MnotufactionDataase.child(ReciverID).setValue(notifactionmap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){

                                                        }
                                                        else {

                                                        }
                                                    }
                                                });

                                        requestbutton.setEnabled(true);
                                        CurrentStates = "request_send";
                                        requestbutton.setText("Delate");

                                        requestbutton.setBackgroundResource(R.drawable.cancelrequest_design);

                                        unfriendbutton.setEnabled(false);

                                        sendNotification();

                                    }
                                }
                            });
                }
            }
        });
    }


    /// Cancel Request
    private void Cancel_Request() {
        MFriendRequestDatabase.child(SenderID).child(ReciverID)
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    MFriendRequestDatabase.child(ReciverID).child(SenderID)
                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            CurrentStates = "not_friends";
                            requestbutton.setEnabled(true);
                            requestbutton.setText("Request");
                            requestbutton.setBackgroundResource(R.drawable.request_buttondesign);

                            unfriendbutton.setEnabled(false);
                        }
                    });
                }
            }
        });
    }
    /// Cancel Request

    private void matainted_button() {

        MFriendRequestDatabase.child(SenderID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(ReciverID)) {
                            String request_type = dataSnapshot.child(ReciverID).child("request_type").getValue().toString();
                            if (request_type.equals("send")) {
                                CurrentStates = "request_send";
                                requestbutton.setText("Delate");


                                unfriendbutton.setEnabled(false);
                            } else if (request_type.equals("recived")) {

                                CurrentStates = "Request_Recived";
                                requestbutton.setText("Accept");
                                requestbutton.setBackgroundResource(R.drawable.request_buttondesign);

                                unfriendbutton.setEnabled(true);

                                unfriendbutton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Cancel_Request();
                                    }
                                });


                            }


                        } else {
                            Friends.child(SenderID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.hasChild(ReciverID)) {
                                        CurrentStates = "friends";
                                        requestbutton.setText("Remove");
                                        requestbutton.setBackgroundResource(R.drawable.cancelrequest_design);

                                        unfriendbutton.setEnabled(false);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }



    private void sendNotification() {



        FirebaseDatabase.getInstance().getReference("Token").child(ReciverID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                //Log.i("SnapShort "+dataSnapshot.)


                //Map<String,String> test= (Map<String, String>) dataSnapshot.getValue();




                String TOKEN = dataSnapshot.child("token").getValue().toString();



                Token token=dataSnapshot.getValue(Token.class);


                String message="New Blood Request Found!";
                String title="Blood Request";


              /*  HashMap<String,String> data=new HashMap<>();
                data.put("title",title);
                data.put("body",message);*/




                Notification notification=new Notification(message,title);
                Sender noti=new Sender(token.getToken(),notification);





                mService.sendNotification(noti).enqueue(new retrofit2.Callback<Myresponce>() {
                    @Override
                    public void onResponse(Call<Myresponce> call, Response<Myresponce> response) {

                        Log.i("STATUS", "onResponse: SUCCESS "  +  response.message());

                    }

                    @Override
                    public void onFailure(Call<Myresponce> call, Throwable t) {

                        Log.i("STATUS", "onResponse: FAILED ");


                    }
                });







            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
