package com.example.sbb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class GlobalChatActivity extends AppCompatActivity {

    private Toolbar gtoolbar;
    private EditText messgae;
    private ImageButton send;
    private FirebaseAuth Mauth;
    private String CurrentuserID;
    private DatabaseReference MdatabaseUsers;
    private String senderusername, senderprofileiamge;
    private DatabaseReference MMessagedatabase, MessageRead;
    private RecyclerView messageview;
    private ImageButton additems;
    private String cheacker;
    private CircleImageView gimage;
    private Uri sendimage = null;
    private StorageReference MimageStore;
    private ProgressBar progressBar;

    private ImageView onlineimage;
    private DatabaseReference MUserDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_chat);



        onlineimage = findViewById(R.id.OnlineStatasID);
        onlineimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonShide buttonShide = new ButtonShide();
                buttonShide.show(getSupportFragmentManager(), "open");
            }
        });

        MimageStore = FirebaseStorage.getInstance().getReference().child("Image");
        progressBar = findViewById(R.id.GlobalProgressbarID);
        progressBar.setVisibility(View.GONE);
        additems = findViewById(R.id.AddButtonID);
        gimage = findViewById(R.id.progchatimage);

        gimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonShide buttonShide = new ButtonShide();
                buttonShide.show(getSupportFragmentManager(), "open");
            }
        });

        additems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CharSequence charSequence[] = new CharSequence[]{

                        "Insert Image",
                        "Insert PDF",
                        "Insert Doc"
                };


                android.app.AlertDialog.Builder Mbuilder = new android.app.AlertDialog.Builder(GlobalChatActivity.this);
                Mbuilder.setTitle("Select Options");
                Mbuilder.setItems(charSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (i == 0) {
                            cheacker = "image";
                         //   Intent intent = new Intent();
                          //  intent.setAction(Intent.ACTION_GET_CONTENT);
                          //  intent.setType("image/*");
                          //  startActivityForResult(intent, 511);

                            CropImage.activity()
                                    .setGuidelines(CropImageView.Guidelines.ON)
                                    .start(GlobalChatActivity.this);
                        }
                        if (i == 1) {
                            cheacker = "pdf";
                            Intent intent = new Intent();
                            intent.setType("application/pdf");
                            startActivityForResult(intent, 501);
                        }
                        if (i == 2) {
                            cheacker = "msword";
                            Intent intent = new Intent();
                            intent.setType("application/msword");
                            startActivityForResult(intent, 512);
                        }
                    }

                });
                AlertDialog alertDialog = Mbuilder.create();
                alertDialog.show();

            }
        });

        messageview = findViewById(R.id.MessageListID);
        messageview.setHasFixedSize(true);
        messageview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        send = findViewById(R.id.SendButtonID);

        Mauth = FirebaseAuth.getInstance();
        CurrentuserID = Mauth.getCurrentUser().getUid().toString();
        MMessagedatabase = FirebaseDatabase.getInstance().getReference().child("Messages");
        MMessagedatabase.keepSynced(true);
        MdatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        MdatabaseUsers.keepSynced(true);

        MUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        MdatabaseUsers.child(CurrentuserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    if(dataSnapshot.hasChild("profile_imageLink")){

                        String profile_imageLinkget = dataSnapshot.child("profile_imageLink").getValue().toString();
                        Picasso.with(getApplicationContext()).load(profile_imageLinkget).placeholder(R.drawable.defaltimage).into(gimage);
                        Picasso.with(getApplicationContext()).load(profile_imageLinkget).networkPolicy(NetworkPolicy.OFFLINE).into(gimage, new Callback() {
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


        MdatabaseUsers.child(CurrentuserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    if(dataSnapshot.hasChild("name")){
                        senderusername = dataSnapshot.child("name").getValue().toString();
                    }
                    if(dataSnapshot.hasChild("profile_imageLink")){
                        senderprofileiamge = dataSnapshot.child("profile_imageLink").getValue().toString();
                    }

                }
                else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        gtoolbar = findViewById(R.id.GlobalChatToolbarID);
        setSupportActionBar(gtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backicon);
        getSupportActionBar().setTitle("Global Chat");

        messgae = findViewById(R.id.TextMessageID);
        send = findViewById(R.id.SendButtonID);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String messagetext = messgae.getText().toString();
                if(messagetext.isEmpty()){

                    Toast.makeText(getApplicationContext(), "type anythings ...", Toast.LENGTH_LONG).show();
                }
                else {

                    messagetext = messgae.getText().toString();

                    Calendar calendar_date = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                   final String date = simpleDateFormat.format(calendar_date.getTime());

                    ///time
                    Calendar calendar_time = Calendar.getInstance();
                    SimpleDateFormat currenttime = new SimpleDateFormat("hh:mm aa");
                    final String time = currenttime.format(calendar_date.getTime());


                    Map messagemap = new HashMap();
                    messagemap.put("message", messagetext);
                    messagemap.put("time", time);
                    messagemap.put("date", date);
                    messagemap.put("username", senderusername);
                    messagemap.put("senderprofileimage", senderprofileiamge);
                    messagemap.put("type", "text");

                    MMessagedatabase.push().updateChildren(messagemap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            messgae.setText("");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            String errormessage = e.getMessage().toString();
                            Toast.makeText(getApplicationContext(), errormessage, Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });

        MessageRead = FirebaseDatabase.getInstance().getReference().child("Messages");
        MessageRead.keepSynced(true);

        OnlineStatas("Online");
    }


    @Override
    protected void onStart() {

        FirebaseRecyclerAdapter<MessagegetSet, MessageHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<MessagegetSet, MessageHolder>(
                MessagegetSet.class,
                R.layout.message_templated,
                MessageHolder.class,
                MessageRead
        ) {
            @Override
            protected void populateViewHolder(final MessageHolder messageHolder, final MessagegetSet messagegetSet, int i) {

                String UID = getRef(i).getKey();

                MessageRead.child(UID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            if(dataSnapshot.hasChild("type")) {

                                String typeString = dataSnapshot.child("type").getValue().toString();
                                if(typeString.equals("text")){
                                    messageHolder.relativeLayoutimage.setVisibility(View.GONE);
                                    messageHolder.image.setVisibility(View.GONE);


                                    if(dataSnapshot.hasChild("message")){
                                        String messageget = dataSnapshot.child("message").getValue().toString();
                                        messageHolder.setmessageset(messageget);
                                    }
                                    if(dataSnapshot.hasChild("senderprofileimage")){
                                        String senderprofileimageget = dataSnapshot.child("senderprofileimage").getValue().toString();
                                        messageHolder.setimageset(senderprofileimageget);
                                    }
                                    if(dataSnapshot.hasChild("time")){
                                        String timeget = dataSnapshot.child("time").getValue().toString();
                                        messageHolder.settimeset(timeget);
                                    }
                                    if(dataSnapshot.hasChild("date")){
                                        String dateget = dataSnapshot.child("date").getValue().toString();
                                        messageHolder.setdateset(dateget);
                                    }
                                }
                                else if(typeString.equals("image")){
                                    messageHolder.imagetime.setVisibility(View.VISIBLE);
                                    messageHolder.imagedate.setVisibility(View.VISIBLE);
                                    messageHolder.date.setVisibility(View.GONE);
                                    messageHolder.time.setVisibility(View.GONE);
                                    messageHolder.message.setVisibility(View.GONE);
                                    messageHolder.image.setVisibility(View.VISIBLE);
                                    if(dataSnapshot.hasChild("send_imageUrl")){

                                         messageHolder.imageURL = dataSnapshot.child("send_imageUrl").getValue().toString();
                                        messageHolder.sendimage(messageHolder.imageURL);
                                        String time = dataSnapshot.child("time").getValue().toString();
                                        messageHolder.imagetime.setText(time);

                                        String date = dataSnapshot.child("date").getValue().toString();
                                        messageHolder.imagedate.setText(date);

                                        String senderprofileimageget = dataSnapshot.child("senderprofileimage").getValue().toString();
                                        messageHolder.setimageset(senderprofileimageget);

                                        messageHolder.Mview.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                String url = dataSnapshot.child("send_imageUrl").getValue().toString();

                                                android.app.AlertDialog.Builder Mbuilder = new android.app.AlertDialog.Builder(messageHolder.itemView.getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                                                View view = LayoutInflater.from(messageHolder.itemView.getContext()).inflate(R.layout.image_viewfullscren, null, false);
                                                final ImageView imageView = view.findViewById(R.id.ImageSampleFullScreenID);
                                                Picasso.with(getApplicationContext()).load(url).into(imageView);
                                                Picasso.with(getApplicationContext()).load(url).networkPolicy(NetworkPolicy.OFFLINE).into(imageView);
                                                Mbuilder.setView(view);
                                                AlertDialog alertDialog = Mbuilder.create();
                                                alertDialog.show();
                                            }
                                        });

                                    }

                                }

                            }


                        }
                        else {


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };

        messageview.setAdapter(firebaseRecyclerAdapter);
        super.onStart();
    }

    public static class MessageHolder extends RecyclerView.ViewHolder{

        private CircleImageView imageView;
        private TextView message;
        private TextView time, date;
        private View Mview;
        private Context context;
        private ImageView image;
        private RelativeLayout relativeLayoutimage;


        ///image time
        private TextView imagetime, imagedate;
        private String imageURL;
        ///image time



        public MessageHolder(@NonNull View itemView) {
            super(itemView);

            Mview = itemView;
            imageView = Mview.findViewById(R.id.SenderProfileImageID);
            message = Mview.findViewById(R.id.MessagID);
            time = Mview.findViewById(R.id.TimeIDID);
            date = Mview.findViewById(R.id.DateIDID);
            context = Mview.getContext();
            image = Mview.findViewById(R.id.ImageSenderID);

            imagetime = Mview.findViewById(R.id.imageTime);
            imagedate = Mview.findViewById(R.id.imagedate);

            relativeLayoutimage  = Mview.findViewById(R.id.RImageLayoutID);
        }

        public void setimageset(String img){
            Picasso.with(context).load(img).placeholder(R.drawable.defaltimage).into(imageView);
            Picasso.with(context).load(img).networkPolicy(NetworkPolicy.OFFLINE).into(image, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
        }
        public void setmessageset(String mess){
            message.setText(mess);
        }
        public void settimeset(String tim){
            time.setText(tim);
        }
        public void setdateset(String dat){
            date.setText(dat);
        }

        public void sendimage(String img){
            Picasso.with(context).load(img).into(image);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);  ///din't make me angry ok
    }





    @Override
    protected void onStop() {
        OnlineStatas("Offline");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        OnlineStatas("Offline");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        OnlineStatas("Online");
        super.onRestart();
    }

    public void OnlineStatas(String statas){



        Map onlinemap = new HashMap();
        onlinemap.put("statas", statas);

        MUserDatabase.child(CurrentuserID).updateChildren(onlinemap)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                        }
                        else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            progressBar.setVisibility(View.VISIBLE);
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                 sendimage = result.getUri();

                 StorageReference Mfilepath = MimageStore.child(sendimage.getLastPathSegment());
                 Mfilepath.putFile(sendimage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                         if(task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);


                             Calendar calendar_date = Calendar.getInstance();
                             SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                             final String date = simpleDateFormat.format(calendar_date.getTime());

                             ///time
                             Calendar calendar_time = Calendar.getInstance();
                             SimpleDateFormat currenttime = new SimpleDateFormat("hh:mm aa");
                             final String time = currenttime.format(calendar_date.getTime());
                             String imageurl = task.getResult().getDownloadUrl().toString();


                             Map messagemap = new HashMap();
                             messagemap.put("time", time);
                             messagemap.put("date", date);
                             messagemap.put("username", senderusername);
                             messagemap.put("senderprofileimage", senderprofileiamge);
                             messagemap.put("send_imageUrl", imageurl);
                             messagemap.put("type", "image");


                             MMessagedatabase.push().updateChildren(messagemap).addOnCompleteListener(new OnCompleteListener() {
                                 @Override
                                 public void onComplete(@NonNull Task task) {

                                     messgae.setText("");
                                 }
                             }).addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {

                                     String errormessage = e.getMessage().toString();
                                     Toast.makeText(getApplicationContext(), errormessage, Toast.LENGTH_LONG).show();
                                 }
                             });
                         }
                         else {
                             String errormesage = task.getException().getMessage().toString();
                             Toast.makeText(getApplicationContext(), errormesage, Toast.LENGTH_LONG).show();
                             progressBar.setVisibility(View.GONE);
                         }
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {

                         String error = e.getMessage().toString();
                         Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                     }
                 });


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


}
