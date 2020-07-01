package com.mobilecomp.viswa.emoguess;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
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

public class VideoFragment extends Fragment {


    ImageButton leftNav, rightNav;
    View view;
    String[] emotions;
    static HorizontalViewPagerVideo horizontalViewPagerVideo;
    private Context mContext;
    private TypedArray videosArray;
    static int score;

    private TextView timertext;
    private Button timerbutton;
    private CountDownTimer timer;
    private long timeleft = 60000;
    private boolean timerrunning;
    static String timelefttext;
    private static View currentView;
    static String getName;
    static VideoView getVideo;

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

        //videosArray = getResources().obtainTypedArray(R.array.emo_videos);

        horizontalViewPagerVideo = view.findViewById(R.id.viewPagerVideo);
        return view;
    }

    public class Emo {
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

    Emo e1 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_angry1.mp4?alt=media&token=741fe80b-5144-4ef6-b830-30cd305f363e", "Angry");
    Emo e2 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_angry.mp4?alt=media&token=b6fe23b3-38d8-4feb-a993-8c9441aa1713", "Angry");
    Emo e3 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_fear1.mp4?alt=media&token=b8577913-ed62-45f8-a999-b5158cb557ed", "Fear");
    Emo e4 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_fear2.mp4?alt=media&token=d0a8e54c-7b83-45ce-9488-f3e7da21bb56", "Fear");
    Emo e5 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_happy1.mp4?alt=media&token=4d221cf2-cd16-43a9-876d-fb044825eacc", "Happy");
    Emo e6 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_happy2.mp4?alt=media&token=55a4bd73-a1f4-488a-a3ea-e5f98a670476", "Happy");
    Emo e7 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_neutral1.mp4?alt=media&token=59b190ed-c86b-463d-a019-6b42f322ebb5", "Neutral");
    Emo e8 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_neutral2.mp4?alt=media&token=dfbc7585-35dd-4b45-820b-b9070e47b93e", "Neutral");
    Emo e9 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_sad1.mp4?alt=media&token=caa6e3ea-4c78-4a4b-856e-34e325902bfd", "Sad");
    Emo e10 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_surprise1.mp4?alt=media&token=20210e4a-2597-4125-a462-21a3e81f9505", "Surprise");
    Emo e11 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fbheem_surprise2.mp4?alt=media&token=851350c1-49cd-4bdc-9048-9c5d63e05208", "Surprise");
    Emo e12 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fjerry_disgust1.mp4?alt=media&token=8ce63469-7f8a-4c3e-ad1a-51b17ec0aa8b", "Disgust");
    Emo e13 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fjerry_disgust2.mp4?alt=media&token=28e8aa34-04a0-4432-9b0a-570447ab24e5", "Disgust");
    Emo e14 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fjerry_sad2.mp4?alt=media&token=942b43ab-3dfc-4f0f-b9c6-6c7478223d63", "Sad");
    Emo e15 = new Emo("https://firebasestorage.googleapis.com/v0/b/emoguess-fe113.appspot.com/o/videos%2Fjerry_sad3.mp4?alt=media&token=d129eb50-ba83-481a-b465-0246137110ba", "Sad");

    Emo[] emos = new Emo[]{
            e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15,
    };


    public void startStop() {
        if (timerrunning) {
            stoptimer();
            //startActivity(new Intent(getContext(), RestartActivity.class));
        } else {
            starttimer();
            horizontalViewPagerVideo.setAdapter(new ViewPagerAdapterVideo(mContext, emos));
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
                System.out.println("Current emotion: "+getVideo);

                /**********************************************************************/




            }catch (Exception e){
                System.out.println(e);
                // Toaster.showShortMessage("Extra Page!");
            }
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

        public void setOnShakeListener(OnShakeListener listener) {
            mShakeListener = listener;
        }

        /**
         * Interface for shake gesture.
         */
        public interface OnShakeListener {

            /**
             * Called when shake gesture is detected.
             */
            void onShake();
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
                    horizontalViewPagerVideo.arrowScroll(View.FOCUS_RIGHT);
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
                        /**********************************************************************/




                    }catch (Exception e){
                        System.out.println(e);
                        // Toaster.showShortMessage("Extra Page!");
                    }

                } else if (z < -1 * FLIPCONSTANT && deltaZ > 0) {//got word
                    horizontalViewPagerVideo.arrowScroll(View.FOCUS_RIGHT);
                    score++;
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
                        /**********************************************************************/




                    }catch (Exception e){
                        System.out.println(e);
                        // Toaster.showShortMessage("Extra Page!");
                    }
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
        timerbutton.setText("RESUME");
        timerrunning = false;
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
            Toast toast = Toast.makeText(getActivity(), "Score: " + String.valueOf(ImageFragment.score), Toast.LENGTH_SHORT);
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
        horizontalViewPagerVideo.setOffscreenPageLimit(1);
        leftNav = view.findViewById(R.id.left_nav);
        rightNav = view.findViewById(R.id.right_nav);

        horizontalViewPagerVideo.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    leftNav.setVisibility(View.INVISIBLE);
                } else
                    leftNav.setVisibility(View.VISIBLE);

                if (position == emotions.length - 1) {
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
                horizontalViewPagerVideo.arrowScroll(View.FOCUS_LEFT);
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
                    /**********************************************************************/




                }catch (Exception e){
                    System.out.println(e);
                    // Toaster.showShortMessage("Extra Page!");
                }
            }
        });

        // Images right navigatin
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    horizontalViewPagerVideo.arrowScroll(View.FOCUS_RIGHT);




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
                    /**********************************************************************/




                }catch (Exception e){
                    System.out.println(e);
                    // Toaster.showShortMessage("Extra Page!");
                }
            }
        });
    }
}