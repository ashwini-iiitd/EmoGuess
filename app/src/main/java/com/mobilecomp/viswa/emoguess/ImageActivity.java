package com.mobilecomp.viswa.emoguess;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.androidhiddencamera.HiddenCameraActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.androidhiddencamera.CameraConfig;
import com.androidhiddencamera.CameraError;
import com.androidhiddencamera.HiddenCameraUtils;
import com.androidhiddencamera.config.CameraFacing;
import com.androidhiddencamera.config.CameraFocus;
import com.androidhiddencamera.config.CameraImageFormat;
import com.androidhiddencamera.config.CameraResolution;
import com.androidhiddencamera.config.CameraRotation;

public class ImageActivity extends HiddenCameraActivity implements ImageFragment.OnFragmentInteractionListener {
    private static final int REQUEST_WRITE_STORAGE = 1254;
    private SensorManager mSensorManager;
    private ImageFragment.ShakeEventListener mSensorListener;
    private static final int REQ_CODE_CAMERA_PERMISSION = 1253;
    private CameraConfig mCameraConfig;


    @RequiresApi(api = Build.VERSION_CODES.M)
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
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setTitle("EmoGuess");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        ArrayList<String> emotions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.emotions)));
        bundle.putStringArrayList("emotions", emotions);

        ImageFragment imageFragment = new ImageFragment();
        imageFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.image_layout, imageFragment).commit();

        if (Build.VERSION.SDK_INT<=23) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }

        mCameraConfig = new CameraConfig()
                .getBuilder(this)
                .setCameraFacing(CameraFacing.FRONT_FACING_CAMERA)
                .setCameraResolution(CameraResolution.HIGH_RESOLUTION)
                .setImageFormat(CameraImageFormat.FORMAT_JPEG)
                .setImageRotation(CameraRotation.ROTATION_270)
                .setCameraFocus(CameraFocus.AUTO)
                .build();


        //Check for the camera permission for the runtime
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            //Start camera preview
            startCamera(mCameraConfig);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQ_CODE_CAMERA_PERMISSION);
        }

        //Take a picture

//        findViewById(R.id.capture_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Take picture using the camera without preview.
//                takePicture();
//            }
//        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
            takePicture();
        }
        return true;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQ_CODE_CAMERA_PERMISSION) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera(mCameraConfig);
            } else {
                Toast.makeText(this, R.string.error_camera_permission_denied, Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onImageCapture(@NonNull File imageFile) throws IOException {

        // Convert file to bitmap.
        // Do something.
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        //Display the image to the image view
     //   ((ImageView) findViewById(R.id.cam_prev)).setImageBitmap(bitmap);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }

        String file_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/EmoGuess";
        File dir = new File(file_path);
       // System.out.println(file_path);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dir, "emoguess" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".png");
      //  System.out.println(file);
        FileOutputStream fOut = new FileOutputStream(file);

        bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
        fOut.flush();
        fOut.close();
    }

    @Override
    public void onCameraError(@CameraError.CameraErrorCodes int errorCode) {
        switch (errorCode) {
            case CameraError.ERROR_CAMERA_OPEN_FAILED:
                //Camera open failed. Probably because another application
                //is using the camera
                Toast.makeText(this, R.string.error_cannot_open, Toast.LENGTH_LONG).show();
                break;
            case CameraError.ERROR_IMAGE_WRITE_FAILED:
                //Image write failed. Please check if you have provided WRITE_EXTERNAL_STORAGE permission
                Toast.makeText(this, R.string.error_cannot_write, Toast.LENGTH_LONG).show();
                break;
            case CameraError.ERROR_CAMERA_PERMISSION_NOT_AVAILABLE:
                //camera permission is not available
                //Ask for the camera permission before initializing it.
                Toast.makeText(this, R.string.error_cannot_get_permission, Toast.LENGTH_LONG).show();
                break;
            case CameraError.ERROR_DOES_NOT_HAVE_OVERDRAW_PERMISSION:
                //Display information dialog to the user with steps to grant "Draw over other app"
                //permission for the app.
                HiddenCameraUtils.openDrawOverPermissionSetting(this);
                break;
            case CameraError.ERROR_DOES_NOT_HAVE_FRONT_CAMERA:
                Toast.makeText(this, R.string.error_not_having_camera, Toast.LENGTH_LONG).show();
                break;
        }
    }

//    @Override
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
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
}