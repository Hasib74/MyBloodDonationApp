package com.example.sbb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class MyRquestActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private DatabaseReference MuserDatabase;
    private String CurrentUserID;
    private FirebaseAuth Mauth;
    private DatabaseReference MFriendDatabase;
    private ImageView nolistimage;
    private TextView notextid;
    private Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rquest);

        nolistimage = findViewById(R.id.ListLogoIConID);
        notextid = findViewById(R.id.NoListTextID);




        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();
        MuserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        MuserDatabase.keepSynced(true);
        toolbar = findViewById(R.id.RequestToolID);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backicon);
        getSupportActionBar().setTitle("My List");

        recyclerView = findViewById(R.id.ListRecylearViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        MFriendDatabase = FirebaseDatabase.getInstance().getReference().child("Friend").child(CurrentUserID);
        MFriendDatabase.keepSynced(true);
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

        FirebaseRecyclerAdapter<RequestgetSet, FriendHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RequestgetSet, FriendHolder>(
                RequestgetSet.class,
                R.layout.request_layout,
                FriendHolder.class,
                MFriendDatabase
        ) {
            @Override
            protected void populateViewHolder(final FriendHolder friendHolder, RequestgetSet requestgetSet, int i) {

                String UID = getRef(i).getKey();

                MuserDatabase.child(UID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            notextid.setVisibility(View.GONE);
                            nolistimage.setVisibility(View.GONE);


                            if(dataSnapshot.hasChild("profile_imageLink")){
                                String profile_imageLinkget = dataSnapshot.child("profile_imageLink").getValue().toString();
                                friendHolder.setProfileimageSet(profile_imageLinkget);
                            }
                            if(dataSnapshot.hasChild("name")){
                                String nameget = dataSnapshot.child("name").getValue().toString();
                                friendHolder.setusernameset(nameget);
                            }
                            if(dataSnapshot.hasChild("blood")){
                                String bloodge = dataSnapshot.child("blood").getValue().toString();
                                friendHolder.setbloodgroupset(bloodge);
                            }

                            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_button);
                            friendHolder.Mview.setAnimation(animation);

                        }
                        else {

                            notextid.setVisibility(View.VISIBLE);
                            nolistimage.setVisibility(View.VISIBLE);
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

    public static class FriendHolder extends RecyclerView.ViewHolder{

        private CircleImageView profileimage;
        private TextView username;
        private TextView bloodgroup;
        private Context context;
        private View Mview;

        public FriendHolder(@NonNull View itemView) {
            super(itemView);

            Mview = itemView;
            profileimage = Mview.findViewById(R.id.RimageID);
            username = Mview.findViewById(R.id.RUserID);
            bloodgroup = Mview.findViewById(R.id.RBloodGroupID);
            context = Mview.getContext();
        }

        public void setProfileimageSet(String imag){
            Picasso.with(context).load(imag).placeholder(R.drawable.defaltimage).into(profileimage);
            Picasso.with(context).load(imag).placeholder(R.drawable.defaltimage).networkPolicy(NetworkPolicy.OFFLINE).into(profileimage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
        }

        public void setusernameset(String nam){
            username.setText(nam);
        }

        public void setbloodgroupset(String blood){
            bloodgroup.setText(blood);
        }
    }
}
