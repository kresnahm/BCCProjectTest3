package com.example.bccprojecttest3;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

public class CoursesFragment extends Fragment {
    @Nullable


    private RecyclerView coursesList;
    private DatabaseReference mDatabase;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_courses,container,false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Courses");
        mDatabase.keepSynced(true);

        coursesList = (RecyclerView) v.findViewById(R.id.recView);
        coursesList.setHasFixedSize(true);
        coursesList.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Courses,CourseViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Courses, CourseViewHolder>
                (Courses.class,R.layout.courses_row,CourseViewHolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(CourseViewHolder viewHolder, Courses model, int position) {
                viewHolder.setTitle(model.getCourseName());
                viewHolder.setAuthor(model.getCourseMaker());
                viewHolder.setType(model.getCourseType());
                viewHolder.setImage(getActivity(),model.getImageUrl());
            }
        };
    coursesList.setAdapter(firebaseRecyclerAdapter);
    }
    public static class CourseViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public CourseViewHolder(View itemView){
            super(itemView);
            mView=itemView;
        }
        public void setTitle(String title){
            TextView post_title = (TextView)mView.findViewById(R.id.postTitle);
            post_title.setText(title);
        }
        public void setType(String type){
            TextView post_type = (TextView)mView.findViewById(R.id.courseType);
            post_type.setText(type);
        }
        public void setAuthor(String author) {
            TextView post_author = (TextView) mView.findViewById(R.id.courseAuthor);
            post_author.setText(author);
        }
        public void setImage(Context ctx,String image){
            ImageView post_image = (ImageView)mView.findViewById(R.id.postImage);
            Picasso.get().load(image).into(post_image);
            Picasso.get().setLoggingEnabled(true);
        }
    }
}
