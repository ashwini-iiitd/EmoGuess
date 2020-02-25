package com.mobilecomp.viswa.emoguess;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class VideoActivity extends AppCompatActivity implements VideoFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        VideoFragment videoFragment = new VideoFragment();
        fragmentTransaction.replace(R.id.video_layout, videoFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
