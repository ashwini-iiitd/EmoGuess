package com.mobilecomp.viswa.emoguess;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HowToActivity extends AppCompatActivity implements HowToFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);

        ActionBar actionBar =  getSupportActionBar();
        actionBar.setTitle("How to play");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        HowToFragment howtoFragment = new HowToFragment();
        fragmentTransaction.replace(R.id.howto_layout, howtoFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}