package com.example.bccprojecttest3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class EventsFragment extends Fragment {
    @Nullable

    private RecyclerView eventsList;
    private DatabaseReference mDatabase;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event,container,false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
        mDatabase.keepSynced(true);

        eventsList = (RecyclerView) v.findViewById(R.id.eventRecView);
//        eventsList.setHasFixedSize(true);
        eventsList.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Events,EventViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Events, EventViewHolder>
                (Events.class,R.layout.event_row,EventViewHolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(EventViewHolder viewHolder, Events model, int position) {
                viewHolder.setHari(model.getHari());
                viewHolder.setNamaEvent(model.getNama());
                viewHolder.setTanggal(model.getTanggal());
                viewHolder.setTempat(model.getTempat());
            }
        };
        eventsList.setAdapter(firebaseRecyclerAdapter);
    }
    public static class EventViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public EventViewHolder(View itemView){
            super(itemView);
            mView=itemView;
        }
        public void setHari(String title){
            TextView post_hari = (TextView)mView.findViewById(R.id.hariEvent);
            post_hari.setText(title);
        }
        public void setNamaEvent(String type){
            TextView post_nama = (TextView)mView.findViewById(R.id.namaEvent);
            post_nama.setText(type);
        }
        public void setTanggal(String author) {
            TextView post_tanggal = (TextView) mView.findViewById(R.id.tglEvent);
            post_tanggal.setText(author);
        }
        public void setTempat(String tempat){
            TextView post_tempat = (TextView) mView.findViewById(R.id.tmptEvent);
            post_tempat.setText(tempat);
        }
    }
}
