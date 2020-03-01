package com.example.sbb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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

public class Request_Activity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FirebaseAuth Mauth;
    private DatabaseReference MuserDatabase;
    private DatabaseReference Requestdatabase;
    private String CurrentUserID;
    private ImageView nouserimage;
    private TextView nousertext;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_);

        nouserimage = findViewById(R.id.RequestIconID);
        nousertext = findViewById(R.id.NoRequesTextID);

        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();
        MuserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        MuserDatabase.keepSynced(true);
        Requestdatabase = FirebaseDatabase.getInstance().getReference().child("FriendRequest");
        Requestdatabase.keepSynced(true);

        toolbar = findViewById(R.id.RequestToolbarIDID);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backicon);
        getSupportActionBar().setTitle("My Request");

        recyclerView = findViewById(R.id.RequestListID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }



    @Override
    protected void onStart() {

        FirebaseRecyclerAdapter<RequestgetSet, RequestHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RequestgetSet, RequestHolder>(
                RequestgetSet.class,
                R.layout.request_layout,
                RequestHolder.class,
                Requestdatabase.child(CurrentUserID)
        ) {
            @Override
            protected void populateViewHolder(final RequestHolder requestHolder, RequestgetSet requestgetSet, int i) {
                final String UID = getRef(i).getKey();
                DatabaseReference gettype = getRef(i).child("request_type").getRef();

                gettype.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                            nousertext.setVisibility(View.INVISIBLE);
                            nouserimage.setVisibility(View.INVISIBLE);
                            requestHolder.Mview.setVisibility(View.VISIBLE);

                            String type = dataSnapshot.getValue().toString();

                            if(type.equals("recived")){
                                MuserDatabase.child(UID).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.exists()){

                                            if(dataSnapshot.hasChild("profile_imageLink")){
                                                String image_uriget = dataSnapshot.child("profile_imageLink").getValue().toString();
                                                requestHolder.setPimage(image_uriget);
                                            }
                                            if(dataSnapshot.hasChild("name")){
                                                String nameget = dataSnapshot.child("name").getValue().toString();
                                                requestHolder.setuserNameSet(nameget);
                                            }
                                            if(dataSnapshot.hasChild("blood"))
                                            {
                                                String bloodget = dataSnapshot.child("blood").getValue().toString();
                                                requestHolder.setBloodgroupSet(bloodget);
                                            }


                                            requestHolder.Mview.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    Intent intent = new Intent(requestHolder.context, BloodRequest.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    intent.putExtra("Key", UID);
                                                    startActivity(intent);
                                                }
                                            });

                                            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_button);
                                            requestHolder.Mview.setAnimation(animation);
                                        }
                                        else {
                                            nousertext.setVisibility(View.INVISIBLE);
                                            nouserimage.setVisibility(View.INVISIBLE);
                                            requestHolder.Mview.setVisibility(View.GONE);

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                        else {

                            nousertext.setVisibility(View.VISIBLE);
                            nouserimage.setVisibility(View.VISIBLE);
                            requestHolder.Mview.setVisibility(View.GONE);
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

    public static class RequestHolder extends RecyclerView.ViewHolder{

        private CircleImageView profileimage;
        private TextView username;
        private View Mview;
        private Context context;
        private TextView bloodgroup;

        public RequestHolder(@NonNull View itemView) {
            super(itemView);

            Mview = itemView;
            context = Mview.getContext();
            profileimage = Mview.findViewById(R.id.RimageID);
            username = Mview.findViewById(R.id.RUserID);
            bloodgroup = Mview.findViewById(R.id.RBloodGroupID);
        }
        public void setPimage(String img){
            Picasso.with(context).load(img).placeholder(R.drawable.defaltimage).into(profileimage);
            Picasso.with(context).load(img).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.defaltimage).into(profileimage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
        }
        public void setuserNameSet(String nam){
            username.setText(nam);
        }
        public void setBloodgroupSet(String group){
            bloodgroup.setText(group);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){

            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
