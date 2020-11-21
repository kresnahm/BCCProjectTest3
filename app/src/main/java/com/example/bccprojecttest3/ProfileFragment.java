package com.example.bccprojecttest3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private TextView namaTextView,dpTextView,tglTextView,kotaTextView,userID;
//    private CircleImageView profileImage;
    private Firebase myRef;
    private FirebaseAuth mAuth;
    @Override

    @Nullable



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        View v = inflater.inflate(R.layout.fragment_profile,container,false);

        Firebase.setAndroidContext(this.getActivity());

        namaTextView = v.findViewById(R.id.namaTextView);
        dpTextView = v.findViewById(R.id.dpTextView);
        tglTextView = v.findViewById(R.id.tglTextView);
        kotaTextView = v.findViewById(R.id.kotaTextView);
        userID = v.findViewById(R.id.userID);
//        profileImage = v.findViewById(R.id.profile_image);

        String user_id = mAuth.getCurrentUser().getUid();
        myRef = new Firebase("https://bcc-project-3.firebaseio.com/Users/"+user_id);

        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            dpTextView.setText(user.getDisplayName());
//            Uri photoUrl = user.getPhotoUrl();
//            Picasso.get().load(photoUrl).into(profileImage);
            userID.setText(mAuth.getCurrentUser().getUid());
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String,String> map = dataSnapshot.getValue(Map.class);

                    String name = map.get("nama");
                    String thnLahir = map.get("thnLahir");
                    String kotaAsal = map.get("kotaAsal");

                    namaTextView.setText(name);
                    tglTextView.setText(thnLahir);
                    kotaTextView.setText(kotaAsal);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            }
        }
    }

