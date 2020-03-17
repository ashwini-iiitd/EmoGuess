package com.mobilecomp.viswa.emoguess;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class RestartActivity extends AppCompatActivity implements RestartFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        RestartFragment restartFragment = new RestartFragment();
        fragmentTransaction.replace(R.id.restart_layout, restartFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
