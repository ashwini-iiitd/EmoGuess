package com.mobilecomp.viswa.emoguess;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class ImageActivity extends AppCompatActivity implements ImageFragment.OnFragmentInteractionListener{
    private SensorManager mSensorManager;
    private ImageFragment.ShakeEventListener mSensorListener;

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    public void onCreate(final Bundle savedInstanceState) {

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ImageFragment.ShakeEventListener();

        mSensorListener.setOnShakeListener(new ImageFragment.ShakeEventListener.OnShakeListener() {

            public void onShake() {
                ImageFragment.horizontalViewPager.arrowScroll(View.FOCUS_RIGHT);
                //Toast.makeText(ImageActivity.this, "Shake!", Toast.LENGTH_SHORT).show();
            }
        });

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


    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

}