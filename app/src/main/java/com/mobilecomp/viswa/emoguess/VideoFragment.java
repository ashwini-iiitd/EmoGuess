package com.mobilecomp.viswa.emoguess;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;

import static com.mobilecomp.viswa.emoguess.ViewPagerAdapterVideo.video;

public class VideoFragment extends Fragment {

    ImageButton leftNav, rightNav;
    View view;
    String[] emotions;
    static HorizontalViewPagerVideo horizontalViewPagerVideo;
    private static Context mContext;
    private TypedArray videosArray;
    //static int score;

    private TextView timertext;
    private Button timerbutton;
    private CountDownTimer timer;
    private long timeleft = 90000;
    private boolean timerrunning;
    static String timelefttext;
    private static View currentView;
    static String getName;
    static VideoView getVideo;
    static int stopPosition;
    private static TextView scorekeep;
    static String scorek;
    MediaPlayer ring2;
    private OnFragmentInteractionListener mListener;

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_video, container, false);
//        Toast toast=Toast. makeText(getActivity(),"video fragment",Toast. LENGTH_SHORT);
//        toast.show();
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

        //access data from the calling activity, i.e., question and answers
        Bundle bundle = this.getArguments();
        ArrayList<String> q = bundle.getStringArrayList("emotions");
        emotions = (q).toArray(new String[q.size()]);
        horizontalViewPagerVideo = view.findViewById(R.id.viewPagerVideo);
        return view;
    }

//    public void loadData(){
//        // data for fragment when it visible here
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser)
//    {
//        System.out.println("visi1");
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser && isResumed()) {
//            System.out.println("visi");// fragment is visible and have created
//            loadData();
//        }
//    }

//    @Override
//    public void setMenuVisibility(final boolean visible) {
//        super.setMenuVisibility(visible);
//        if (visible) {
//            System.out.println("visi");
//        }
//        else {
//            System.out.println("invisi");
//        }
//    }

    public static class Emo {
        private String video;
        private String text;

        public Emo(String video, String text) {
            this.video = video;
            this.text = text;
        }

        public String getVideo() {
            return video;
        }

        public String getText() {
            return text;
        }
    }

    static Emo e1 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_angry1.mp4?alt=media&token=741fe80b-5144-4ef6-b830-30cd305f363e", "Angry");
    static Emo e2 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_angry.mp4?alt=media&token=b6fe23b3-38d8-4feb-a993-8c9441aa1713", "Angry");
    static Emo e3 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_fear1.mp4?alt=media&token=b8577913-ed62-45f8-a999-b5158cb557ed", "Fear");
    static Emo e4 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_fear2.mp4?alt=media&token=d0a8e54c-7b83-45ce-9488-f3e7da21bb56", "Fear");
    static Emo e5 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_happy1.mp4?alt=media&token=4d221cf2-cd16-43a9-876d-fb044825eacc", "Happy");
    static Emo e6 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_happy2.mp4?alt=media&token=55a4bd73-a1f4-488a-a3ea-e5f98a670476", "Happy");
    static Emo e7 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_neutral1.mp4?alt=media&token=59b190ed-c86b-463d-a019-6b42f322ebb5", "Neutral");
    static Emo e8 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_neutral2.mp4?alt=media&token=dfbc7585-35dd-4b45-820b-b9070e47b93e", "Neutral");
    static Emo e9 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_sad1.mp4?alt=media&token=caa6e3ea-4c78-4a4b-856e-34e325902bfd", "Sad");
    static Emo e10 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_surprise1.mp4?alt=media&token=20210e4a-2597-4125-a462-21a3e81f9505", "Surprise");
    static Emo e11 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_surprise2.mp4?alt=media&token=851350c1-49cd-4bdc-9048-9c5d63e05208", "Surprise");
    static Emo e12 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fjerry_disgust1.mp4?alt=media&token=8ce63469-7f8a-4c3e-ad1a-51b17ec0aa8b", "Disgust");
    static Emo e13 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fjerry_disgust2.mp4?alt=media&token=28e8aa34-04a0-4432-9b0a-570447ab24e5", "Disgust");
    static Emo e14 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fjerry_sad2.mp4?alt=media&token=942b43ab-3dfc-4f0f-b9c6-6c7478223d63", "Sad");
    static Emo e15 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fjerry_sad3.mp4?alt=media&token=d129eb50-ba83-481a-b465-0246137110ba", "Sad");

    static Emo[] emos = new Emo[]{
            e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15,
    };


    public void startStop() {
        if (timerrunning) {
            stoptimer();
            //startActivity(new Intent(getContext(), RestartActivity.class));
        } else {
            if (timerbutton.getText().equals("START")) {
                horizontalViewPagerVideo.setAdapter(new ViewPagerAdapterVideo(mContext, emos));
                starttimer();
                ImageFragment.attempts++;
                try {
                    /********* To get current emotion displayed on the screen *********/
                    /*******Code in ViewPagerAdapter to set the current view***************/
                    currentView = ViewPagerAdapterVideo.mCurrentView;

                    ViewGroup viewGroup = ((ViewGroup)currentView);
                    ScrollView scrollView = (ScrollView) viewGroup.getChildAt(0);
                    ViewGroup viewGroup1 = ((ViewGroup)scrollView);
                    LinearLayout linearLayout = (LinearLayout) viewGroup1.getChildAt(0);
                    ViewGroup viewGroup2 = ((ViewGroup)linearLayout);

                    getName = ((TextView)viewGroup2.getChildAt(1)).getText().toString();
                    System.out.println("Current emotion: "+getName);
                    getVideo= ((VideoView)viewGroup2.getChildAt(0));
                    System.out.println("Current video: "+getVideo);
                    getVideo.requestFocus();
                    getVideo.start();
                    getVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.setLooping(true);
                            getVideo.setBackgroundColor(Color.TRANSPARENT);
                            //mp.pause();
                        }
                    });

                    /**********************************************************************/




                }catch (Exception e){
                    System.out.println(e);
                    // Toaster.showShortMessage("Extra Page!");
                }

            }
            else {
                starttimer();
                if (getVideo != null){
                    getVideo.start();
                }
               // getVideo.resume();
            }
        }
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
//                    horizontalViewPagerVideo.setAdapter(new ViewPagerAdapterVideo(mContext, emos));
//                    ImageFragment.attempts++;
//                    try {
//
//
//
//
//                        /********* To get current emotion displayed on the screen *********/
//                        /*******Code in ViewPagerAdapter to set the current view***************/
//                        currentView = ViewPagerAdapterVideo.mCurrentView;
//
//                        ViewGroup viewGroup = ((ViewGroup)currentView);
//                        ScrollView scrollView = (ScrollView) viewGroup.getChildAt(0);
//                        ViewGroup viewGroup1 = ((ViewGroup)scrollView);
//                        LinearLayout linearLayout = (LinearLayout) viewGroup1.getChildAt(0);
//                        ViewGroup viewGroup2 = ((ViewGroup)linearLayout);
//
//                        getName = ((TextView)viewGroup2.getChildAt(1)).getText().toString();
//                        System.out.println("Current emotion: "+getName);
//                        getVideo= ((VideoView)viewGroup2.getChildAt(0));
//                        System.out.println("Current video: "+getVideo);
//                        getVideo.requestFocus();
//                        getVideo.start();
//                        getVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                            @Override
//                            public void onPrepared(MediaPlayer mp) {
//                                mp.setLooping(true);
//                                getVideo.setBackgroundColor(Color.TRANSPARENT);
//                                //mp.pause();
//                            }
//                        });
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
//                    horizontalViewPagerVideo.setAdapter(new ViewPagerAdapterVideo(mContext, emos));
//                    ImageFragment.attempts++;
//                    ImageFragment.score++;
//                    scorek=ImageFragment.score+"";
//                    scorekeep.setText(scorek);
//                    try {
//
//
//
//
//                        /********* To get current emotion displayed on the screen *********/
//                        /*******Code in ViewPagerAdapter to set the current view***************/
//                        currentView = ViewPagerAdapterVideo.mCurrentView;
//
//                        ViewGroup viewGroup = ((ViewGroup)currentView);
//                        ScrollView scrollView = (ScrollView) viewGroup.getChildAt(0);
//                        ViewGroup viewGroup1 = ((ViewGroup)scrollView);
//                        LinearLayout linearLayout = (LinearLayout) viewGroup1.getChildAt(0);
//                        ViewGroup viewGroup2 = ((ViewGroup)linearLayout);
//
//                        getName = ((TextView)viewGroup2.getChildAt(1)).getText().toString();
//                        System.out.println("Current emotion: "+getName);
//                        getVideo= ((VideoView)viewGroup2.getChildAt(0));
//                        System.out.println("Current video: "+getVideo);
//                        getVideo.requestFocus();
//                        getVideo.start();
//                        getVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                            @Override
//                            public void onPrepared(MediaPlayer mp) {
//                                mp.setLooping(true);
//                                getVideo.setBackgroundColor(Color.TRANSPARENT);
//                                //mp.pause();
//                            }
//                        });
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
        //horizontalViewPagerVideo.setOnTouchListener(null);
//        VideoActivity.mSensorManager.registerListener(VideoActivity.mSensorListener,
//                 VideoActivity.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
//                SensorManager.SENSOR_DELAY_UI);
        timer = new CountDownTimer(timeleft, 1000) {
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
        if (getVideo != null){
            getVideo.pause();
        }
        timer.cancel();
        timerbutton.setText("RESUME");
        timerrunning = false;
//        VideoActivity.mSensorManager.unregisterListener(VideoActivity.mSensorListener);
        if (ring2.isPlaying()){
            ring2.pause();
            length = ring2.getCurrentPosition();
        }
//        horizontalViewPagerVideo.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View arg0, MotionEvent arg1) {
//                return true;
//            }
//        });
    }



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
            Toast toast = Toast.makeText(getActivity(), "Score: " + String.valueOf(ImageFragment.score) + " out of " + String.valueOf(ImageFragment.attempts), Toast.LENGTH_SHORT);
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
    public void onPause() {
        if (timerrunning) {
            stoptimer();
        }
        System.out.println("p");
        if (getVideo != null){
            getVideo.pause();
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if (timerrunning) {
            stoptimer();
        }
        System.out.println("s");
        if (getVideo != null){
            getVideo.pause();
        }
        super.onStop();
    }

    @Override
    public void onResume() {
        if (!timerrunning) {
//            VideoActivity.mSensorManager.unregisterListener(VideoActivity.mSensorListener);
        }
        else {
            if (getVideo != null){
                getVideo.start();
            }
//            VideoActivity.mSensorManager.registerListener(VideoActivity.mSensorListener,
//                    VideoActivity.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
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
        horizontalViewPagerVideo.setOffscreenPageLimit(1);
        leftNav = view.findViewById(R.id.left_nav);
        rightNav = view.findViewById(R.id.right_nav);

        horizontalViewPagerVideo.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

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
                    //ImageFragment.attempts++;
                   // leftNav.setVisibility(View.INVISIBLE);
                } else
                   // leftNav.setVisibility(View.VISIBLE);

                if (position == emos.length - 1) {
                   // rightNav.setVisibility(View.INVISIBLE);
                } else {
                    //ImageFragment.attempts++;
                   // rightNav.setVisibility(View.VISIBLE);
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
                final MediaPlayer ring1= MediaPlayer.create(mContext, R.raw.wrong);
                ring1.start();
                horizontalViewPagerVideo.setAdapter(new ViewPagerAdapterVideo(mContext, emos));
                ImageFragment.attempts++;
                try {




                    /********* To get current emotion displayed on the screen *********/
                    /*******Code in ViewPagerAdapter to set the current view***************/
                    currentView = ViewPagerAdapterVideo.mCurrentView;

                    ViewGroup viewGroup = ((ViewGroup)currentView);
                    ScrollView scrollView = (ScrollView) viewGroup.getChildAt(0);
                    ViewGroup viewGroup1 = ((ViewGroup)scrollView);
                    LinearLayout linearLayout = (LinearLayout) viewGroup1.getChildAt(0);
                    ViewGroup viewGroup2 = ((ViewGroup)linearLayout);

                    getName = ((TextView)viewGroup2.getChildAt(1)).getText().toString();
                    System.out.println("Current emotion: "+getName);
                    getVideo= ((VideoView)viewGroup2.getChildAt(0));
                    System.out.println("Current video: "+getVideo);
                    getVideo.requestFocus();
                    getVideo.start();
                    getVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.setLooping(true);
                            getVideo.setBackgroundColor(Color.TRANSPARENT);
                            //mp.pause();
                        }
                    });
                    /**********************************************************************/




                }catch (Exception e){
                    System.out.println(e);
                    // Toaster.showShortMessage("Extra Page!");
                }
            }
        });
//
//        // Images right navigatin
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer ring= MediaPlayer.create(mContext, R.raw.correct);
                ring.start();
                horizontalViewPagerVideo.setAdapter(new ViewPagerAdapterVideo(mContext, emos));
                ImageFragment.attempts++;
                ImageFragment.score++;
                scorek=ImageFragment.score+"";
                scorekeep.setText(scorek);
                try {




                    /********* To get current emotion displayed on the screen *********/
                    /*******Code in ViewPagerAdapter to set the current view***************/
                    currentView = ViewPagerAdapterVideo.mCurrentView;

                    ViewGroup viewGroup = ((ViewGroup)currentView);
                    ScrollView scrollView = (ScrollView) viewGroup.getChildAt(0);
                    ViewGroup viewGroup1 = ((ViewGroup)scrollView);
                    LinearLayout linearLayout = (LinearLayout) viewGroup1.getChildAt(0);
                    ViewGroup viewGroup2 = ((ViewGroup)linearLayout);

                    getName = ((TextView)viewGroup2.getChildAt(1)).getText().toString();
                    System.out.println("Current emotion: "+getName);
                    getVideo= ((VideoView)viewGroup2.getChildAt(0));
                    System.out.println("Current video: "+getVideo);
                    getVideo.requestFocus();
                    getVideo.start();
                    getVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.setLooping(true);
                            getVideo.setBackgroundColor(Color.TRANSPARENT);
                            //mp.pause();
                        }
                    });
                    /**********************************************************************/




                }catch (Exception e){
                    System.out.println(e);
                    // Toaster.showShortMessage("Extra Page!");
                }
            }
        });
    }
}