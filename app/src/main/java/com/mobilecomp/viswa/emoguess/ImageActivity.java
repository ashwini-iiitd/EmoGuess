package com.mobilecomp.viswa.emoguess;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

public class ImageActivity extends AppCompatActivity implements ImageFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ImageFragment imageFragment = new ImageFragment();
        fragmentTransaction.replace(R.id.image_layout, imageFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
