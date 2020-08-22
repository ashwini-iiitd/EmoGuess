package com.mobilecomp.viswa.emoguess;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import static android.content.Context.POWER_SERVICE;

public class ImageFragment extends Fragment implements SensorEventListener {

    ImageButton leftNav, rightNav;
    View view;
    String[] emotions;
    static HorizontalViewPager horizontalViewPager;
    private static Context mContext;
    private TypedArray imagesArray;
    static int score;
    static int attempts;
    private TextView timertext;
    private Button timerbutton;
    private CountDownTimer timer;
    private long timeleft = 60000;
    private boolean timerrunning;
    static String timelefttext;
    private static View currentView;
    static String getName;
    MediaPlayer ring2;
    private static TextView scorekeep;
    static String scorek;
    private OnFragmentInteractionListener mListener;
    private Sensor accelerometer;
    private Sensor magnetometer;
    public static SensorManager mSensorManager;

    public ImageFragment() {
        // Required empty public constructor
    }

   // @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_image, container, false);
        timertext = view.findViewById(R.id.countdown_text);
        timerbutton = view.findViewById(R.id.countdown_button);
        scorekeep = view.findViewById(R.id.score_keep);
        timerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();
            }
        });
        mContext = getContext();
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);

        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        initListeners();

        //access data from the calling activity, i.e., question and answers
        Bundle bundle = this.getArguments();
        ArrayList<String> q = bundle.getStringArrayList("emotions");
        emotions = (q).toArray(new String[q.size()]);
//        imagesArray = getResources().obtainTypedArray(R.array.emo_images);
        horizontalViewPager = view.findViewById(R.id.viewPager);

        return view;
    }

    public class Emo {
        private int image;
        private String text;

        public Emo(int image, String text) {
            this.image = image;
            this.text = text;
        }

        public int getImage() {
            return image;
        }

        public String getText() {
            return text;
        }
    }

    Emo e1 = new Emo(R.drawable.a1 , "Angry");
    Emo e2 = new Emo(R.drawable.a2 , "Angry");
    Emo e3 = new Emo(R.drawable.a3 , "Angry");
    Emo e4 = new Emo(R.drawable.a4 , "Angry");
    Emo e5 = new Emo(R.drawable.a5 , "Angry");
    Emo e6 = new Emo(R.drawable.a6 , "Angry");
    Emo e7 = new Emo(R.drawable.a7 , "Angry");
    Emo e8 = new Emo(R.drawable.a8 , "Angry");
    Emo e9 = new Emo(R.drawable.a9 , "Angry");
    Emo e10 = new Emo(R.drawable.a10 , "Angry");

    Emo e11 = new Emo(R.drawable.d1 , "Disgust");
    Emo e12 = new Emo(R.drawable.d2 , "Disgust");
    Emo e13 = new Emo(R.drawable.d3 , "Disgust");
    Emo e14 = new Emo(R.drawable.d4 , "Disgust");
    Emo e15 = new Emo(R.drawable.d5 , "Disgust");
    Emo e16 = new Emo(R.drawable.d6 , "Disgust");
    Emo e17 = new Emo(R.drawable.d7 , "Disgust");
    Emo e18 = new Emo(R.drawable.d8 , "Disgust");
    Emo e19 = new Emo(R.drawable.d9 , "Disgust");
    Emo e20 = new Emo(R.drawable.d10 , "Disgust");

    Emo e21 = new Emo(R.drawable.f1 , "Fearful");
    Emo e22 = new Emo(R.drawable.f2 , "Fearful");
    Emo e23 = new Emo(R.drawable.f3 , "Fearful");
    Emo e24 = new Emo(R.drawable.f4 , "Fearful");
    Emo e25 = new Emo(R.drawable.f5 , "Fearful");
    Emo e26 = new Emo(R.drawable.f6 , "Fearful");
    Emo e27 = new Emo(R.drawable.f7 , "Fearful");
    Emo e28 = new Emo(R.drawable.f8 , "Fearful");
    Emo e29 = new Emo(R.drawable.f9 , "Fearful");
    Emo e30 = new Emo(R.drawable.f10 , "Fearful");

    Emo e31 = new Emo(R.drawable.h1 , "Happy");
    Emo e32 = new Emo(R.drawable.h2 , "Happy");
    Emo e33 = new Emo(R.drawable.h3 , "Happy");
    Emo e34 = new Emo(R.drawable.h4 , "Happy");
    Emo e35 = new Emo(R.drawable.h5 , "Happy");
    Emo e36 = new Emo(R.drawable.h6 , "Happy");
    Emo e37 = new Emo(R.drawable.h7 , "Happy");
    Emo e38 = new Emo(R.drawable.h8 , "Happy");
    Emo e39 = new Emo(R.drawable.h9 , "Happy");
    Emo e40 = new Emo(R.drawable.h10 , "Happy");

    Emo e41 = new Emo(R.drawable.n1 , "Neutral");
    Emo e42 = new Emo(R.drawable.n2 , "Neutral");
    Emo e43 = new Emo(R.drawable.n3 , "Neutral");
    Emo e44 = new Emo(R.drawable.n4 , "Neutral");
    Emo e45 = new Emo(R.drawable.n5 , "Neutral");
    Emo e46 = new Emo(R.drawable.n6 , "Neutral");
    Emo e47 = new Emo(R.drawable.n7 , "Neutral");
    Emo e48 = new Emo(R.drawable.n8 , "Neutral");
    Emo e49 = new Emo(R.drawable.n9 , "Neutral");
    Emo e50 = new Emo(R.drawable.n10 , "Neutral");

    Emo e51 = new Emo(R.drawable.s1 , "Sad");
    Emo e52 = new Emo(R.drawable.s2 , "Sad");
    Emo e53 = new Emo(R.drawable.s3 , "Sad");
    Emo e54 = new Emo(R.drawable.s4 , "Sad");
    Emo e55 = new Emo(R.drawable.s5 , "Sad");
    Emo e56 = new Emo(R.drawable.s6 , "Sad");
    Emo e57 = new Emo(R.drawable.s7 , "Sad");
    Emo e58 = new Emo(R.drawable.s8 , "Sad");
    Emo e59 = new Emo(R.drawable.s9 , "Sad");
    Emo e60 = new Emo(R.drawable.s10 , "Sad");

    Emo e61 = new Emo(R.drawable.sur1 , "Surprise");
    Emo e62 = new Emo(R.drawable.sur2 , "Surprise");
    Emo e63 = new Emo(R.drawable.sur3 , "Surprise");
    Emo e64 = new Emo(R.drawable.sur4 , "Surprise");
    Emo e65 = new Emo(R.drawable.sur5 , "Surprise");
    Emo e66 = new Emo(R.drawable.sur6 , "Surprise");
    Emo e67 = new Emo(R.drawable.sur7 , "Surprise");
    Emo e68 = new Emo(R.drawable.sur8 , "Surprise");
    Emo e69 = new Emo(R.drawable.sur9 , "Surprise");
    Emo e70 = new Emo(R.drawable.sur10 , "Surprise");


    Emo[] emos = new Emo[]{
            e1, e2, e3, e4, e5, e6, e7, e8, e9, e10,
            e11, e12, e13, e14, e15, e16, e17, e18, e19, e20,
            e21, e22, e23, e24, e25, e26, e27, e28, e29, e30,
            e31, e32, e33, e34, e35, e36, e37, e38, e39, e40,
            e41, e42, e43, e44, e45, e46, e47, e48, e49, e50,
            e51, e52, e53, e54, e55, e56, e57, e58, e59, e60,
            e61, e62, e63, e64, e65, e66, e67, e68, e69, e70
    };

    public void startStop() {
        if (timerrunning) {
            stoptimer();
            //startActivity(new Intent(getContext(), RestartActivity.class));
        } else {
            if (timerbutton.getText().equals("START")) {
                horizontalViewPager.setAdapter(new ViewPagerAdapter(mContext, emos));
                starttimer();
                attempts++;
                try {
                    /********* To get current emotion displayed on the screen *********/
                    /*******Code in ViewPagerAdapter to set the current view***************/
                    currentView = ViewPagerAdapter.mCurrentView;

                    ViewGroup viewGroup = ((ViewGroup)currentView);
                    ScrollView scrollView = (ScrollView) viewGroup.getChildAt(0);
                    ViewGroup viewGroup1 = ((ViewGroup)scrollView);
                    LinearLayout linearLayout = (LinearLayout) viewGroup1.getChildAt(0);
                    ViewGroup viewGroup2 = ((ViewGroup)linearLayout);

                    getName = ((TextView)viewGroup2.getChildAt(1)).getText().toString();
                    System.out.println("Current emotion: "+getName);

                    /**********************************************************************/
                }catch (Exception e){
                    System.out.println(e);
                    // Toaster.showShortMessage("Extra Page!");
                }
            }
            else {
                starttimer();
            }
        }
    }

    public void initListeners()
    {
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    float[] inclineGravity = new float[3];
    float[] mGravity=new float[3];
    float[] mGeomagnetic=new float[3];
    float orientation[] = new float[3];
    float pitch;
    float roll;

    @Override
    public void onSensorChanged(SensorEvent event) {
        //If type is accelerometer only assign values to global property mGravity
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            mGravity = event.values;
        }
        else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
            mGeomagnetic = event.values;

            if (isTiltDownward())
            {
                final MediaPlayer ring= MediaPlayer.create(mContext, R.raw.correct);
                    ring.start();
                    horizontalViewPager.arrowScroll(View.FOCUS_RIGHT);
                    attempts++;
                    score++;
                    scorek=score+"";
                    scorekeep.setText(scorek);
                    try {
                        /********* To get current emotion displayed on the screen *********/
                        /*******Code in ViewPagerAdapter to set the current view***************/
                        currentView = ViewPagerAdapter.mCurrentView;

                        ViewGroup viewGroup = ((ViewGroup)currentView);
                        ScrollView scrollView = (ScrollView) viewGroup.getChildAt(0);
                        ViewGroup viewGroup1 = ((ViewGroup)scrollView);
                        LinearLayout linearLayout = (LinearLayout) viewGroup1.getChildAt(0);
                        ViewGroup viewGroup2 = ((ViewGroup)linearLayout);

                        getName = ((TextView)viewGroup2.getChildAt(1)).getText().toString();
                        System.out.println("Current emotion: "+getName);

                        /**********************************************************************/
                    }
                    catch (Exception e){
                        System.out.println(e);
                        // Toaster.showShortMessage("Extra Page!");
                    }
            }
            else if (isTiltUpward())
            {
                final MediaPlayer ring1= MediaPlayer.create(mContext, R.raw.wrong);
                ring1.start();
                horizontalViewPager.arrowScroll(View.FOCUS_RIGHT);
                attempts++;
                try {
                    /********* To get current emotion displayed on the screen *********/
                    /*******Code in ViewPagerAdapter to set the current view***************/
                    currentView = ViewPagerAdapter.mCurrentView;

                    ViewGroup viewGroup = ((ViewGroup)currentView);
                    ScrollView scrollView = (ScrollView) viewGroup.getChildAt(0);
                    ViewGroup viewGroup1 = ((ViewGroup)scrollView);
                    LinearLayout linearLayout = (LinearLayout) viewGroup1.getChildAt(0);
                    ViewGroup viewGroup2 = ((ViewGroup)linearLayout);

                    getName = ((TextView)viewGroup2.getChildAt(1)).getText().toString();
                    System.out.println("Current emotion: "+getName);

                    /**********************************************************************/
                }
                catch (Exception e){
                    System.out.println(e);
                    // Toaster.showShortMessage("Extra Page!");
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    public boolean isTiltUpward()
    {
        if (mGravity != null && mGeomagnetic != null)
        {
            float R[] = new float[9];
            float I[] = new float[9];

            SensorManager.remapCoordinateSystem(R,
                    SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X,
                    R);

            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);

            if (success)
            {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);

                /*
                 * If the roll is positive, you're in reverse landscape (landscape right), and if the roll is negative you're in landscape (landscape left)
                 *
                 * Similarly, you can use the pitch to differentiate between portrait and reverse portrait.
                 * If the pitch is positive, you're in reverse portrait, and if the pitch is negative you're in portrait.
                 *
                 * orientation -> azimut, pitch and roll
                 *
                 *
                 */

                pitch = orientation[1];
                roll = orientation[2];

                inclineGravity = mGravity.clone();

                double norm_Of_g = Math.sqrt(inclineGravity[0] * inclineGravity[0] + inclineGravity[1] * inclineGravity[1] + inclineGravity[2] * inclineGravity[2]);

                // Normalize the accelerometer vector
                inclineGravity[0] = (float) (inclineGravity[0] / norm_Of_g);
                inclineGravity[1] = (float) (inclineGravity[1] / norm_Of_g);
                inclineGravity[2] = (float) (inclineGravity[2] / norm_Of_g);

                //Checks if device is flat on ground or not
                int inclination = (int) Math.round(Math.toDegrees(Math.acos(inclineGravity[2])));

                /*
                 * Float obj1 = new Float("10.2");
                 * Float obj2 = new Float("10.20");
                 * int retval = obj1.compareTo(obj2);
                 *
                 * if(retval > 0) {
                 * System.out.println("obj1 is greater than obj2");
                 * }
                 * else if(retval < 0) {
                 * System.out.println("obj1 is less than obj2");
                 * }
                 * else {
                 * System.out.println("obj1 is equal to obj2");
                 * }
                 */
                Float objPitch = new Float(pitch);
                Float objZero = new Float(0.0);
                Float objZeroPointTwo = new Float(0.2);
                Float objZeroPointTwoNegative = new Float(-0.2);

                int objPitchZeroResult = objPitch.compareTo(objZero);
                int objPitchZeroPointTwoResult = objZeroPointTwo.compareTo(objPitch);
                int objPitchZeroPointTwoNegativeResult = objPitch.compareTo(objZeroPointTwoNegative);

                if (roll < 0 && ((objPitchZeroResult > 0 && objPitchZeroPointTwoResult > 0) || (objPitchZeroResult < 0 && objPitchZeroPointTwoNegativeResult > 0)) && (inclination > 30 && inclination < 40))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }

        return false;
    }

    public boolean isTiltDownward()
    {
        if (mGravity != null && mGeomagnetic != null)
        {
            float R[] = new float[9];
            float I[] = new float[9];

            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);

            if (success)
            {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);

                pitch = orientation[1];
                roll = orientation[2];

                inclineGravity = mGravity.clone();

                double norm_Of_g = Math.sqrt(inclineGravity[0] * inclineGravity[0] + inclineGravity[1] * inclineGravity[1] + inclineGravity[2] * inclineGravity[2]);

                // Normalize the accelerometer vector
                inclineGravity[0] = (float) (inclineGravity[0] / norm_Of_g);
                inclineGravity[1] = (float) (inclineGravity[1] / norm_Of_g);
                inclineGravity[2] = (float) (inclineGravity[2] / norm_Of_g);

                //Checks if device is flat on ground or not
                int inclination = (int) Math.round(Math.toDegrees(Math.acos(inclineGravity[2])));

                Float objPitch = new Float(pitch);
                Float objZero = new Float(0.0);
                Float objZeroPointTwo = new Float(0.2);
                Float objZeroPointTwoNegative = new Float(-0.2);

                int objPitchZeroResult = objPitch.compareTo(objZero);
                int objPitchZeroPointTwoResult = objZeroPointTwo.compareTo(objPitch);
                int objPitchZeroPointTwoNegativeResult = objPitch.compareTo(objZeroPointTwoNegative);

                if (roll < 0 && ((objPitchZeroResult > 0 && objPitchZeroPointTwoResult > 0) || (objPitchZeroResult < 0 && objPitchZeroPointTwoNegativeResult > 0)) && (inclination > 140 && inclination < 170))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }

        return false;
    }

//    /**
//     * Listener that detects shake gesture.
//     */
//    public static class ShakeEventListener implements SensorEventListener {
//
//        private final int FLIPCONSTANT = 2;
//        private boolean mInitialized;
//
//        /**
//         * Minimum movement force to consider.
//         */
//        private static final int MIN_FORCE = 30;
//
//        /**
//         * Minimum times in a shake gesture that the direction of movement needs to
//         * change.
//         */
//        private static final int MIN_DIRECTION_CHANGE = 70;
//
//        /**
//         * Maximum pause between movements.
//         */
//        private static final int MAX_PAUSE_BETWEEN_DIRECTION_CHANGE = 200;
//
//        /**
//         * Maximum allowed time for shake gesture.
//         */
//        private static final int MAX_TOTAL_DURATION_OF_SHAKE = 400;
//
//        /**
//         * Time when the gesture started.
//         */
//        private long mFirstDirectionChangeTime = 0;
//
//        /**
//         * Time when the last movement started.
//         */
//        private long mLastDirectionChangeTime;
//
//        /**
//         * How many movements are considered so far.
//         */
//        private int mDirectionChangeCount = 0;
//
//        /**
//         * The last x position.
//         */
//        private float lastX = 0;
//
//        /**
//         * The last y position.
//         */
//        private float lastY = 0;
//
//        /**
//         * The last z position.
//         */
//        private float lastZ = 0;
//        private final float NOISE = (float) 10.0;
//
//        /**
//         * OnShakeListener that is called when shake is detected.
//         */
//        private OnShakeListener mShakeListener;
//
//        /**
//         * Interface for shake gesture.
//         */
//        public interface OnShakeListener {
//
//            /**
//             * Called when shake gesture is detected.
//             */
//            void onShake();
//        }
//
//        public void setOnShakeListener(OnShakeListener listener) {
//            mShakeListener = listener;
//        }
//
//        @Override
//        public void onSensorChanged(SensorEvent se) {
//            // get sensor data
//            float x = se.values[SensorManager.DATA_X];
//            float y = se.values[SensorManager.DATA_Y];
//            float z = se.values[SensorManager.DATA_Z];
//
//            if (!mInitialized) {
//                lastX = x;
//                lastY = y;
//                lastZ = z;
//
//                mInitialized = true;
//            } else {
//                float deltaX = Math.abs(lastX - x);
//                float deltaY = Math.abs(lastY - y);
//                float deltaZ = Math.abs(lastZ - z);
//
////                System.out.println("deltaX ==" + deltaX);
////                System.out.println("deltaY ==" + deltaY);
////                System.out.println("deltaZ ==" + deltaZ);
//
//                if (deltaX < NOISE) deltaX = (float) 0.0;
//                if (deltaY < NOISE) deltaY = (float) 0.0;
//                if (deltaZ < NOISE && deltaZ > -1 * NOISE) deltaZ = (float) 0.0;
//
//                lastX = x;
//                lastY = y;
//                lastZ = z;
//
//                if (z > FLIPCONSTANT && deltaZ > 0) { //pass
//                    final MediaPlayer ring1= MediaPlayer.create(mContext, R.raw.wrong);
//                    ring1.start();
//                    horizontalViewPager.arrowScroll(View.FOCUS_RIGHT);
//                    attempts++;
//                    try {
//
//
//
//
//
//                        /********* To get current emotion displayed on the screen *********/
//                        /*******Code in ViewPagerAdapter to set the current view***************/
//                        currentView = ViewPagerAdapter.mCurrentView;
//
//                        ViewGroup viewGroup = ((ViewGroup)currentView);
//                        ScrollView scrollView = (ScrollView) viewGroup.getChildAt(0);
//                        ViewGroup viewGroup1 = ((ViewGroup)scrollView);
//                        LinearLayout linearLayout = (LinearLayout) viewGroup1.getChildAt(0);
//                        ViewGroup viewGroup2 = ((ViewGroup)linearLayout);
//
//                        getName = ((TextView)viewGroup2.getChildAt(1)).getText().toString();
//                        System.out.println("Current emotion: "+getName);
//
//                        /**********************************************************************/
//
//
//
//
//                    }catch (Exception e){
//                        System.out.println(e);
//                        // Toaster.showShortMessage("Extra Page!");
//                    }
//
//                } else if (z < -1 * FLIPCONSTANT && deltaZ > 0) {//got word
//                    final MediaPlayer ring= MediaPlayer.create(mContext, R.raw.correct);
//                    ring.start();
//                    horizontalViewPager.arrowScroll(View.FOCUS_RIGHT);
//                    attempts++;
//                    score++;
//                    scorek=score+"";
//                    scorekeep.setText(scorek);
//                    try {
//
//
//
//
//
//                        /********* To get current emotion displayed on the screen *********/
//                        /*******Code in ViewPagerAdapter to set the current view***************/
//                        currentView = ViewPagerAdapter.mCurrentView;
//
//                        ViewGroup viewGroup = ((ViewGroup)currentView);
//                        ScrollView scrollView = (ScrollView) viewGroup.getChildAt(0);
//                        ViewGroup viewGroup1 = ((ViewGroup)scrollView);
//                        LinearLayout linearLayout = (LinearLayout) viewGroup1.getChildAt(0);
//                        ViewGroup viewGroup2 = ((ViewGroup)linearLayout);
//
//                        getName = ((TextView)viewGroup2.getChildAt(1)).getText().toString();
//                        System.out.println("Current emotion: "+getName);
//
//                        /**********************************************************************/
//
//
//
//
//                    }catch (Exception e){
//                        System.out.println(e);
//                        // Toaster.showShortMessage("Extra Page!");
//                    }
//                }
//
//            }
//
//            // calculate movement
//            float totalMovement = Math.abs(x + y + z - lastX - lastY - lastZ);
//
//            if (totalMovement > MIN_FORCE) {
//
//                // get time
//                long now = System.currentTimeMillis();
//
//                // store first movement time
//                if (mFirstDirectionChangeTime == 0) {
//                    mFirstDirectionChangeTime = now;
//                    mLastDirectionChangeTime = now;
//                }
//
//                // check if the last movement was not long ago
//                long lastChangeWasAgo = now - mLastDirectionChangeTime;
//                if (lastChangeWasAgo < MAX_PAUSE_BETWEEN_DIRECTION_CHANGE) {
//
//                    // store movement data
//                    mLastDirectionChangeTime = now;
//                    mDirectionChangeCount++;
//
//                    // store last sensor data
//                    lastX = x;
//                    lastY = y;
//                    lastZ = z;
//
//                    // check how many movements are so far
//                    if (mDirectionChangeCount >= MIN_DIRECTION_CHANGE) {
//
//                        // check total duration
//                        long totalDuration = now - mFirstDirectionChangeTime;
//                        if (totalDuration < MAX_TOTAL_DURATION_OF_SHAKE) {
//                            mShakeListener.onShake();
//                            resetShakeParameters();
//                        }
//                    }
//
//                } else {
//                    resetShakeParameters();
//                }
//            }
//        }
//
//        /**
//         * Resets the shake parameters to their default values.
//         */
//        private void resetShakeParameters() {
//            mFirstDirectionChangeTime = 0;
//            mDirectionChangeCount = 0;
//            mLastDirectionChangeTime = 0;
//            mInitialized = false;
//            lastX = 0;
//            lastY = 0;
//            lastZ = 0;
//        }
//
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        }
//
//    }


    public void starttimer() {
        ring2= MediaPlayer.create(mContext, R.raw.timer);
        //horizontalViewPager.setOnTouchListener(null);
        initListeners();
//        ImageActivity.mSensorManager.registerListener(ImageActivity.mSensorListener,
//                ImageActivity.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
//                SensorManager.SENSOR_DELAY_UI);
        timer = new CountDownTimer(timeleft, 1000) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTick(long l) {
                timeleft = l;
                updatetimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
        timerbutton.setText("PAUSE");
        timerrunning = true;
    }

    int length;

    public void stoptimer() {
        timer.cancel();
        timerbutton.setText("RESUME");
        timerrunning = false;
        mSensorManager.unregisterListener(this);
//        ImageActivity.mSensorManager.unregisterListener(ImageActivity.mSensorListener);
        if (ring2.isPlaying()){
            ring2.pause();
            length = ring2.getCurrentPosition();
        }
//        horizontalViewPager.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View arg0, MotionEvent arg1) {
//                return true;
//            }
//        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void updatetimer() {
        int minutes = (int) timeleft / 60000;
        int seconds = (int) timeleft % 60000 / 1000;

        timelefttext = "" + minutes;
        timelefttext += ":";
        if (seconds < 10) timelefttext += "0";
        timelefttext += seconds;
        timertext.setText(timelefttext);
        if (timelefttext.compareTo("0:00") == 0) {
            startActivity(new Intent(getContext(), RestartActivity.class));
            Toast toast = Toast.makeText(getActivity(), "Score: " + String.valueOf(score) +" out of "+ String.valueOf(attempts), Toast.LENGTH_SHORT);
            toast.show();
        }
        if (timelefttext.compareTo("0:03") == 0 || (timelefttext.compareTo("0:02") == 0) || (timelefttext.compareTo("0:01") == 0)) {
            if (ring2.isPlaying()) {


            } else {
                ring2.seekTo(length);
                ring2.start();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy()
    {
        mSensorManager.unregisterListener(this);
        super.onDestroy();
    }

    @Override
    public void onPause() {
        if (timerrunning) {
            stoptimer();
        }
        super.onPause();
    }


    @Override
    public void onStop() {
        //getActivity().getIntent().putExtras();
        if (timerrunning) {
            stoptimer();
        }
        super.onStop();
    }

    @Override
    public void onResume() {
       // getActivity().getIntent().getExtras();
        System.out.println("r");
        if (!timerrunning) {
            mSensorManager.unregisterListener(this);
//            ImageActivity.mSensorManager.unregisterListener(ImageActivity.mSensorListener);
        }
        else {
            initListeners();
//            ImageActivity.mSensorManager.registerListener(ImageActivity.mSensorListener,
//                    ImageActivity.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
//                    SensorManager.SENSOR_DELAY_UI);
        }
        super.onResume();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        horizontalViewPager.setOffscreenPageLimit(1);
       // leftNav = view.findViewById(R.id.left_nav);
      //  rightNav = view.findViewById(R.id.right_nav);


        horizontalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                try {
//
//
//
//
//
//                    /********* To get current emotion displayed on the screen *********/
//                    /*******Code in ViewPagerAdapter to set the current view***************/
//                    currentView = ViewPagerAdapter.mCurrentView;
//
//                    ViewGroup viewGroup = ((ViewGroup)currentView);
//                    ScrollView scrollView = (ScrollView) viewGroup.getChildAt(0);
//                    ViewGroup viewGroup1 = ((ViewGroup)scrollView);
//                    LinearLayout linearLayout = (LinearLayout) viewGroup1.getChildAt(0);
//                    ViewGroup viewGroup2 = ((ViewGroup)linearLayout);
//
//                    getName = ((TextView)viewGroup2.getChildAt(1)).getText().toString();
//                    System.out.println("Current emotion: "+getName);
//
//                    /**********************************************************************/
//
//
//
//
//                }catch (Exception e){
//                    System.out.println(e);
//                    // Toaster.showShortMessage("Extra Page!");
//                }
            }

            @Override
            public void onPageSelected(int position) {
//                try {
//
//
//
//
//
//                    /********* To get current emotion displayed on the screen *********/
//                    /*******Code in ViewPagerAdapter to set the current view***************/
//                    currentView = ViewPagerAdapter.mCurrentView;
//
//                    ViewGroup viewGroup = ((ViewGroup)currentView);
//                    ScrollView scrollView = (ScrollView) viewGroup.getChildAt(0);
//                    ViewGroup viewGroup1 = ((ViewGroup)scrollView);
//                    LinearLayout linearLayout = (LinearLayout) viewGroup1.getChildAt(0);
//                    ViewGroup viewGroup2 = ((ViewGroup)linearLayout);
//
//                    getName = ((TextView)viewGroup2.getChildAt(1)).getText().toString();
//                    System.out.println("Current emotion: "+getName);
//
//                    /**********************************************************************/
//
//
//
//
//                }catch (Exception e){
//                    System.out.println(e);
//                    // Toaster.showShortMessage("Extra Page!");
//                }
                if (position == 0) {
                    //attempts++;
                   // leftNav.setVisibility(View.INVISIBLE);
                } else
                   // leftNav.setVisibility(View.VISIBLE);

                if (position==emos.length-1){
                   // rightNav.setVisibility(View.INVISIBLE);
                } else {
                   // attempts++;
                   // rightNav.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Images left navigation
//        leftNav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                horizontalViewPager.arrowScroll(View.FOCUS_LEFT);
//                try {
//
//
//
//
//
//                    /********* To get current emotion displayed on the screen *********/
//                    /*******Code in ViewPagerAdapter to set the current view***************/
//                    currentView = ViewPagerAdapter.mCurrentView;
//
//                    ViewGroup viewGroup = ((ViewGroup)currentView);
//                    ScrollView scrollView = (ScrollView) viewGroup.getChildAt(0);
//                    ViewGroup viewGroup1 = ((ViewGroup)scrollView);
//                    LinearLayout linearLayout = (LinearLayout) viewGroup1.getChildAt(0);
//                    ViewGroup viewGroup2 = ((ViewGroup)linearLayout);
//
//                    getName = ((TextView)viewGroup2.getChildAt(1)).getText().toString();
//                    System.out.println("Current emotion: "+getName);
//
//                    /**********************************************************************/
//
//
//
//
//                }catch (Exception e){
//                    System.out.println(e);
//                    // Toaster.showShortMessage("Extra Page!");
//                }
//
//            }
//        });
//
//        // Images right navigatin
//        rightNav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                attempts++;
//                try {
//                    horizontalViewPager.arrowScroll(View.FOCUS_RIGHT);
//
//
//
//
//                    /********* To get current emotion displayed on the screen *********/
//                    /*******Code in ViewPagerAdapter to set the current view***************/
//                    currentView = ViewPagerAdapter.mCurrentView;
//
//                    ViewGroup viewGroup = ((ViewGroup)currentView);
//                    ScrollView scrollView = (ScrollView) viewGroup.getChildAt(0);
//                    ViewGroup viewGroup1 = ((ViewGroup)scrollView);
//                    LinearLayout linearLayout = (LinearLayout) viewGroup1.getChildAt(0);
//                    ViewGroup viewGroup2 = ((ViewGroup)linearLayout);
//
//                    getName = ((TextView)viewGroup2.getChildAt(1)).getText().toString();
//                    System.out.println("Current emotion: "+getName);
//
//                    /**********************************************************************/
//
//
//
//
//                }catch (Exception e){
//                    System.out.println(e);
//                    // Toaster.showShortMessage("Extra Page!");
//                }
//            }
//        });
    }
}