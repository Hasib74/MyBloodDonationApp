package com.example.sbb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private DatabaseReference Mpostdatabase;
    private ImageView infoimage;
    private TextView infotext;
     boolean Likecheacker = false;
    private FirebaseAuth Mauth;
    private String CurrentUserID;
    private DatabaseReference MlikeDatabase;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        infoimage = findViewById(R.id.infoimageID);
        infotext = findViewById(R.id.infotextID);
        MlikeDatabase = FirebaseDatabase.getInstance().getReference().child("Likes");

        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.PostViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        toolbar = findViewById(R.id.RequestToolbarID);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backicon);

        Mpostdatabase = FirebaseDatabase.getInstance().getReference().child("Post");
        Mpostdatabase.keepSynced(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {


        FirebaseRecyclerAdapter<post_getset, MPostHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<post_getset, MPostHolder>(
                post_getset.class,
                R.layout.postbanner,
                MPostHolder.class,
                Mpostdatabase
        ) {
            @Override
            protected void populateViewHolder(final MPostHolder mPostHolder, post_getset post_getset, int i) {

                final String UID = getRef(i).getKey();
                Mpostdatabase.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            mPostHolder.likepostcheack(UID);

                            infoimage.setVisibility(View.INVISIBLE);
                            infotext.setVisibility(View.INVISIBLE);

                            if (dataSnapshot.hasChild("application")) {
                                String applicationget = dataSnapshot.child("application").getValue().toString();
                                mPostHolder.setapplicaton(applicationget);
                            }
                            if (dataSnapshot.hasChild("bloodgroup")) {
                                String bloodgroupget = dataSnapshot.child("bloodgroup").getValue().toString();
                                mPostHolder.setbloodgroup(bloodgroupget);
                            }
                            if (dataSnapshot.hasChild("date")) {
                                String dateget = dataSnapshot.child("date").getValue().toString();
                                mPostHolder.setpostdate(dateget);
                            }
                            if (dataSnapshot.hasChild("profileimage")) {
                                String profileimageget = dataSnapshot.child("profileimage").getValue().toString();
                                mPostHolder.setiamge(profileimageget);
                            }
                            if (dataSnapshot.hasChild("name")) {
                                String nameget = dataSnapshot.child("name").getValue().toString();
                                mPostHolder.setname(nameget);
                            }
                            if (dataSnapshot.hasChild("locationname")) {
                                String locationnameget = dataSnapshot.child("locationname").getValue().toString();
                                mPostHolder.setloction(locationnameget);
                            }
                            if (dataSnapshot.hasChild("hospitalname")) {
                                String hospitalnameget = dataSnapshot.child("hospitalname").getValue().toString();
                                mPostHolder.sethospitalset(hospitalnameget);
                            }

                            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_button);
                            mPostHolder.Mview.setAnimation(animation);

                            mPostHolder.likeicon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Likecheacker = true;


                                    MlikeDatabase.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            if(Likecheacker==true){
                                                if (dataSnapshot.child(UID).hasChild(CurrentUserID)) {
                                                    MlikeDatabase.child(UID).child(CurrentUserID).removeValue();
                                                    Likecheacker = false;
                                                } else {
                                                    MlikeDatabase.child(UID).child(CurrentUserID).setValue(true);
                                                    Likecheacker = false;
                                                }
                                            }


                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }
                            });
                        } else {
                            infoimage.setVisibility(View.VISIBLE);
                            infotext.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        super.onStart();
    }

    public static class MPostHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private View Mview;
        private TextView bloodgroup;
        private TextView postapplication;
        private TextView postdate;
        private CircleImageView postprofileimage;
        private Context context;
        private TextView hospital;
        private TextView location;

        private ImageView likeicon;
        private TextView likecount;

        private int countlike;
        private String CurrentUseID;
        private DatabaseReference MlikeDatabase;

        public MPostHolder(@NonNull View itemView) {
            super(itemView);

            Mview = itemView;
            username = Mview.findViewById(R.id.bannerusernameID);
            bloodgroup = Mview.findViewById(R.id.bannerblood);
            postapplication = Mview.findViewById(R.id.PostApplicationID);
            postdate = Mview.findViewById(R.id.CurrentDateID);
            postprofileimage = Mview.findViewById(R.id.cardprofileimageID);
            context = Mview.getContext();
            hospital = Mview.findViewById(R.id.HospitalNameID);
            location = Mview.findViewById(R.id.LocationNameID);

            likeicon = Mview.findViewById(R.id.LikeID);
            likecount = Mview.findViewById(R.id.LikeCountID);

            MlikeDatabase = FirebaseDatabase.getInstance().getReference().child("Likes");
            CurrentUseID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

        public void likepostcheack(final String UID) {

            MlikeDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {



                    if (dataSnapshot.child(UID).hasChild(CurrentUseID)) {
                        countlike = (int) dataSnapshot.child(UID).getChildrenCount();
                        likeicon.setBackgroundResource(R.drawable.redlike);
                        likecount.setText(Integer.toString(countlike)+" Likes");
                    }
                    else {
                        countlike = (int) dataSnapshot.child(UID).getChildrenCount();
                        likeicon.setBackgroundResource(R.drawable.like);
                        likecount.setText(Integer.toString(countlike)+" Likes");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        public void setname(String nam) {
            username.setText(nam);
        }

        public void setbloodgroup(String group) {
            bloodgroup.setText(group);
        }

        public void setapplicaton(String app) {
            postapplication.setText(app);
        }

        public void setiamge(String img) {
            Picasso.with(context).load(img).placeholder(R.drawable.defaltimage).into(postprofileimage);
            Picasso.with(context).load(img).networkPolicy(NetworkPolicy.OFFLINE).into(postprofileimage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
        }

        public void setpostdate(String date) {
            postdate.setText(date);
        }


        public void sethospitalset(String hos) {
            hospital.setText(hos);
        }

        public void setloction(String loc) {
            location.setText(loc);
        }
    }
}
