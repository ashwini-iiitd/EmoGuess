package com.mobilecomp.viswa.emoguess;

import java.io.File;

import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class ImageFragment extends Fragment {

    ImageButton leftNav, rightNav;
    View view;
    String[] emotions;
    static HorizontalViewPager horizontalViewPager;
    private Context mContext;
    private TypedArray imagesArray;
    static int score;
    private TextView timertext;
    private Button timerbutton;
    private CountDownTimer timer;
    private long timeleft = 60000;
    private boolean timerrunning;
    static String timelefttext;
    private OnFragmentInteractionListener mListener;

    public ImageFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_image, container, false);
        timertext = view.findViewById(R.id.countdown_text);
        timerbutton = view.findViewById(R.id.countdown_button);
        timerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();
            }
        });
        mContext = getContext();

        //access data from the calling activity, i.e., question and answers
        Bundle bundle = this.getArguments();
        ArrayList<String> q = bundle.getStringArrayList("emotions");
        emotions = (q).toArray(new String[q.size()]);
        imagesArray = getResources().obtainTypedArray(R.array.emo_images);
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

    Emo e61 = new Emo(R.drawable.sur1 , "Surprising");
    Emo e62 = new Emo(R.drawable.sur2 , "Surprising");
    Emo e63 = new Emo(R.drawable.sur3 , "Surprising");
    Emo e64 = new Emo(R.drawable.sur4 , "Surprising");
    Emo e65 = new Emo(R.drawable.sur5 , "Surprising");
    Emo e66 = new Emo(R.drawable.sur6 , "Surprising");
    Emo e67 = new Emo(R.drawable.sur7 , "Surprising");
    Emo e68 = new Emo(R.drawable.sur8 , "Surprising");
    Emo e69 = new Emo(R.drawable.sur9 , "Surprising");
    Emo e70 = new Emo(R.drawable.sur10 , "Surprising");


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
            startActivity(new Intent(getContext(), RestartActivity.class));
        } else {
            starttimer();
            horizontalViewPager.setAdapter(new ViewPagerAdapter(mContext, emos));
        }
    }

    /**
     * Listener that detects shake gesture.
     */
    public static class ShakeEventListener implements SensorEventListener {

        private final int FLIPCONSTANT = 8;
        private boolean mInitialized;

        /**
         * Minimum movement force to consider.
         */
        private static final int MIN_FORCE = 10;

        /**
         * Minimum times in a shake gesture that the direction of movement needs to
         * change.
         */
        private static final int MIN_DIRECTION_CHANGE = 3;

        /**
         * Maximum pause between movements.
         */
        private static final int MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE = 200;

        /**
         * Maximum allowed time for shake gesture.
         */
        private static final int MAX_TOTAL_DURATION_OF_SHAKE = 400;

        /**
         * Time when the gesture started.
         */
        private long mFirstDirectionChangeTime = 0;

        /**
         * Time when the last movement started.
         */
        private long mLastDirectionChangeTime;

        /**
         * How many movements are considered so far.
         */
        private int mDirectionChangeCount = 0;

        /**
         * The last x position.
         */
        private float lastX = 0;

        /**
         * The last y position.
         */
        private float lastY = 0;

        /**
         * The last z position.
         */
        private float lastZ = 0;
        private final float NOISE = (float) 1.0;

        /**
         * OnShakeListener that is called when shake is detected.
         */
        private OnShakeListener mShakeListener;

        /**
         * Interface for shake gesture.
         */
        public interface OnShakeListener {

            /**
             * Called when shake gesture is detected.
             */
            void onShake();
        }

        public void setOnShakeListener(OnShakeListener listener) {
            mShakeListener = listener;
        }

        @Override
        public void onSensorChanged(SensorEvent se) {
            // get sensor data
            float x = se.values[SensorManager.DATA_X];
            float y = se.values[SensorManager.DATA_Y];
            float z = se.values[SensorManager.DATA_Z];

            if (!mInitialized) {
                lastX = x;
                lastY = y;
                lastZ = z;

                mInitialized = true;
            } else {
                float deltaX = Math.abs(lastX - x);
                float deltaY = Math.abs(lastY - y);
                float deltaZ = Math.abs(lastZ - z);

//                System.out.println("deltaX ==" + deltaX);
//                System.out.println("deltaY ==" + deltaY);
//                System.out.println("deltaZ ==" + deltaZ);

                if (deltaX < NOISE) deltaX = (float) 0.0;
                if (deltaY < NOISE) deltaY = (float) 0.0;
                if (deltaZ < NOISE && deltaZ > -1 * NOISE) deltaZ = (float) 0.0;

                lastX = x;
                lastY = y;
                lastZ = z;

                if (z > FLIPCONSTANT && deltaZ > 0) { //pass
                    horizontalViewPager.arrowScroll(View.FOCUS_RIGHT);

                } else if (z < -1 * FLIPCONSTANT && deltaZ > 0) {//got word
                    horizontalViewPager.arrowScroll(View.FOCUS_RIGHT);
                    score++;
                }

            }

            // calculate movement
            float totalMovement = Math.abs(x + y + z - lastX - lastY - lastZ);

            if (totalMovement > MIN_FORCE) {

                // get time
                long now = System.currentTimeMillis();

                // store first movement time
                if (mFirstDirectionChangeTime == 0) {
                    mFirstDirectionChangeTime = now;
                    mLastDirectionChangeTime = now;
                }

                // check if the last movement was not long ago
                long lastChangeWasAgo = now - mLastDirectionChangeTime;
                if (lastChangeWasAgo < MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE) {

                    // store movement data
                    mLastDirectionChangeTime = now;
                    mDirectionChangeCount++;

                    // store last sensor data
                    lastX = x;
                    lastY = y;
                    lastZ = z;

                    // check how many movements are so far
                    if (mDirectionChangeCount >= MIN_DIRECTION_CHANGE) {

                        // check total duration
                        long totalDuration = now - mFirstDirectionChangeTime;
                        if (totalDuration < MAX_TOTAL_DURATION_OF_SHAKE) {
                            mShakeListener.onShake();
                            resetShakeParameters();
                        }
                    }

                } else {
                    resetShakeParameters();
                }
            }
        }

        /**
         * Resets the shake parameters to their default values.
         */
        private void resetShakeParameters() {
            mFirstDirectionChangeTime = 0;
            mDirectionChangeCount = 0;
            mLastDirectionChangeTime = 0;
            mInitialized = false;
            lastX = 0;
            lastY = 0;
            lastZ = 0;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

    }


    public void starttimer() {
        score=0;
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

    public void stoptimer() {
        timer.cancel();
        timerbutton.setText("START");
        timerrunning = false;
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
            Toast toast = Toast.makeText(getActivity(), "Score: " + String.valueOf(score), Toast.LENGTH_SHORT);
            toast.show();
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
        leftNav = view.findViewById(R.id.left_nav);
        rightNav = view.findViewById(R.id.right_nav);

        horizontalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    leftNav.setVisibility(View.INVISIBLE);
                } else
                    leftNav.setVisibility(View.VISIBLE);

                if (position==emos.length-1){
                    rightNav.setVisibility(View.INVISIBLE);
                } else {
                    rightNav.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Images left navigation
        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horizontalViewPager.arrowScroll(View.FOCUS_LEFT);
            }
        });

        // Images right navigatin
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    horizontalViewPager.arrowScroll(View.FOCUS_RIGHT);
                }catch (Exception e){
                    // Toaster.showShortMessage("Extra Page!");
                }
            }
        });
    }
}