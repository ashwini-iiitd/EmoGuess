package com.mobilecomp.viswa.emoguess;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mobilecomp.viswa.emoguess.PlayFragment.OnFragmentInteractionListener;

public class PlayActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        PlayFragment playFragment = new PlayFragment();
        fragmentTransaction.replace(R.id.play_layout, playFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}