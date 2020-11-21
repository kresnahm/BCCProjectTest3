package com.example.bccprojecttest3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class HomeFragment extends Fragment {
    ViewFlipper flipper;
    @Override

        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        int images[] = {R.drawable.keratif1,R.drawable.keratif2,R.drawable.keratif3};
        flipper = (ViewFlipper) v.findViewById(R.id.flipper);

        for(int image:images){
            flipperImage(image);
        }
        return v;

    }
    public void flipperImage(int image){
        ImageView imageview = new ImageView(this.getActivity());
        imageview.setBackgroundResource(image);

        flipper.addView(imageview);
        flipper.setFlipInterval(4000);
        flipper.setAutoStart(true);

        //animation
        flipper.setInAnimation(this.getActivity(),android.R.anim.slide_in_left);
        flipper.setOutAnimation(this.getActivity(),android.R.anim.slide_out_right);
    }
}
