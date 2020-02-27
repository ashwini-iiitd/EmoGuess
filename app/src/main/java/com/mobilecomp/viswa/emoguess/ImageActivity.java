package com.mobilecomp.viswa.emoguess;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;

import com.mobilecomp.viswa.emoguess.ImageFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class ImageActivity extends AppCompatActivity implements ImageFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();



        Bundle bundle = new Bundle();
        ArrayList<String> emotions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.emotions)));
        bundle.putStringArrayList("emotions", emotions);

        ImageFragment imageFragment = new ImageFragment();
        imageFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.image_layout, imageFragment).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
