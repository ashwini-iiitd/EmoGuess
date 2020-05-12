package com.mobilecomp.viswa.emoguess;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

    public void logout(View view) {
        SessionManagement sessionManagement=new SessionManagement(RestartActivity.this);
        sessionManagement.removeSession();
        movetohome();
    }

    private void movetohome() {
        Intent intent=new Intent(RestartActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
