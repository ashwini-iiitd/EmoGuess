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
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.Arrays;

public class VideoActivity extends AppCompatActivity implements VideoFragment.OnFragmentInteractionListener{
    private SensorManager mSensorManager;
    private VideoFragment.ShakeEventListener mSensorListener;

    private static final int CAMERA_REQUEST = 1888;
    private VideoView videoView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new VideoFragment.ShakeEventListener();

        mSensorListener.setOnShakeListener(new VideoFragment.ShakeEventListener.OnShakeListener() {

            public void onShake() {
                VideoFragment.horizontalViewPagerVideo.arrowScroll(View.FOCUS_RIGHT);
                //Toast.makeText(VideoActivity.this, "Shake!", Toast.LENGTH_SHORT).show();
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        ArrayList<String> emotions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.emotions)));
        bundle.putStringArrayList("emotions", emotions);

        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.video_layout, videoFragment).commit();
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