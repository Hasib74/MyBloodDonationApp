package com.example.sbb;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Users extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference MuserDatabase;
    private SearchView searchView;
    private Animation animation;

    public Users() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        MuserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        MuserDatabase.keepSynced(true);

        recyclerView = view.findViewById(R.id.UserRLayoutID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchView = view.findViewById(R.id.BloodSearchID);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                SearchByBlood(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                SearchByBlood(newText);

                return false;
            }
        });

        return view;
    }


    public void SearchByBlood(String Blood){

        Query firebasequiry = MuserDatabase.orderByChild("blood").startAt(Blood).endAt(Blood+"\uf8ff");


        FirebaseRecyclerAdapter<UsersModal, userHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UsersModal, userHolder>(
                UsersModal.class,
                R.layout.contacts_layout,
                userHolder.class,
                firebasequiry
        ) {
            @Override
            protected void populateViewHolder(final userHolder userHolder, UsersModal usersModal, int i) {

               final String UID  =getRef(i).getKey();
                MuserDatabase.child(UID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            if(dataSnapshot.hasChild("address")){
                                String addressget = dataSnapshot.child("address").getValue().toString();
                                userHolder.setaddress(addressget);
                            }
                            if(dataSnapshot.hasChild("blood")){
                                String bloodget = dataSnapshot.child("blood").getValue().toString();
                                userHolder.setbloodgroup(bloodget);
                            }
                            if(dataSnapshot.hasChild("name")){
                                String nameget = dataSnapshot.child("name").getValue().toString();
                                userHolder.setanmese(nameget);
                            }
                            if(dataSnapshot.hasChild("profile_imageLink")){
                                String profile_imageLinkget = dataSnapshot.child("profile_imageLink").getValue().toString();
                                userHolder.setiamge(profile_imageLinkget);
                            }

                            userHolder.Mview.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(getContext(), BloodRequest.class);
                                    intent.putExtra("Key", UID);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });

                            animation = AnimationUtils.loadAnimation(userHolder.context, R.anim.from_button);
                            userHolder.Mview.setAnimation(animation);
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

        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    public void onStart() {

        FirebaseRecyclerAdapter<UsersModal, userHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UsersModal, userHolder>(
                UsersModal.class,
                R.layout.contacts_layout,
                userHolder.class,
                MuserDatabase
        ) {
            @Override
            protected void populateViewHolder(final userHolder userHolder, UsersModal usersModal, int i) {

               final String UID  =getRef(i).getKey();
                MuserDatabase.child(UID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            if(dataSnapshot.hasChild("address")){
                                String addressget = dataSnapshot.child("address").getValue().toString();
                                userHolder.setaddress(addressget);
                            }
                            if(dataSnapshot.hasChild("blood")){
                                String bloodget = dataSnapshot.child("blood").getValue().toString();
                                userHolder.setbloodgroup(bloodget);
                            }
                            if(dataSnapshot.hasChild("name")){
                                String nameget = dataSnapshot.child("name").getValue().toString();
                                userHolder.setanmese(nameget);
                            }
                            if(dataSnapshot.hasChild("profile_imageLink")){
                                String profile_imageLinkget = dataSnapshot.child("profile_imageLink").getValue().toString();
                                userHolder.setiamge(profile_imageLinkget);
                            }

                            userHolder.Mview.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(getContext(), BloodRequest.class);
                                    intent.putExtra("Key", UID);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });
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

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        super.onStart();
    }

    public static class userHolder extends RecyclerView.ViewHolder{

        private TextView name, blood, addrss;
        private CircleImageView profiliameg;
        private View Mview;
        private Context context;

        public userHolder(@NonNull View itemView) {
            super(itemView);

            Mview = itemView;
            name = Mview.findViewById(R.id.UserNameIDID);
            blood = Mview.findViewById(R.id.UserBloodID);
            addrss = Mview.findViewById(R.id.AddrIDID);
            profiliameg = Mview.findViewById(R.id.ProfileImageIDID);
            context = Mview.getContext();
        }

        public void setanmese(String nam){
            name.setText(nam);
        }
        public void setbloodgroup(String group){
            blood.setText(group);
        }
        public void setaddress(String add){
            addrss.setText(add);
        }
        public void setiamge(String img){
            Picasso.with(context).load(img).placeholder(R.drawable.defaltimage).into(profiliameg);
            Picasso.with(context).load(img).networkPolicy(NetworkPolicy.OFFLINE).into(profiliameg, new Callback() {
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
