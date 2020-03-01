package com.example.sbb;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ButtonShide extends BottomSheetDialogFragment {


    private DatabaseReference MuserDatabase;
    private int Counter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        MuserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        View view = inflater.inflate(R.layout.online_buttomshid, container, false);

        final TextView textView = view.findViewById(R.id.OnlineCounterID);
        recyclerView = view.findViewById(R.id.OnlineViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MuserDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String value = ds.getValue().toString();

                    if (value.equals("Online")) {
                        Counter++;
                        textView.setText("Total Online " + Counter);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

    @Override
    public void onStart() {

        FirebaseRecyclerAdapter<Onlinegetset, OnlineHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Onlinegetset, OnlineHolder>(
                Onlinegetset.class,
                R.layout.onlien_layout,
                OnlineHolder.class,
                MuserDatabase
        ) {
            @Override
            protected void populateViewHolder(final OnlineHolder onlineHolder, final Onlinegetset onlinegetset, int i) {


                MuserDatabase.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            String val = ds.getValue().toString();
                            if (val.equals("Online")) {

                                if (dataSnapshot.hasChild("name")) {
                                    String nameget = dataSnapshot.child("name").getValue().toString();
                                    onlineHolder.setnameset(nameget);
                                }
                                if (dataSnapshot.hasChild("profile_imageLink")) {
                                    String profile_imageLinkget = dataSnapshot.child("profile_imageLink").getValue().toString();
                                    onlineHolder.setimageset(profile_imageLinkget);
                                }

                            }
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

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

    public static class OnlineHolder extends RecyclerView.ViewHolder {

        private View Mview;
        private CircleImageView profileimage;
        private TextView username;
        private Context context;

        public OnlineHolder(@NonNull View itemView) {
            super(itemView);

            Mview = itemView;
            profileimage = Mview.findViewById(R.id.OnlineProfileImageID);
            username = Mview.findViewById(R.id.OnlineUserNameID);
            context = Mview.getContext();
        }

        public void setimageset(String img) {
            Picasso.with(context).load(img).placeholder(R.drawable.defaltimage).into(profileimage);
        }

        public void setnameset(String nam) {
            username.setText(nam);
        }
    }
}
