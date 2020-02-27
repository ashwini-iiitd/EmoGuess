package com.mobilecomp.viswa.emoguess;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import android.support.v4.view.ViewPager;
import com.mobilecomp.viswa.emoguess.ViewPagerAdapter;
import com.mobilecomp.viswa.emoguess.HorizontalViewPager;

import java.util.ArrayList;

public class ImageFragment extends Fragment {


    ImageButton leftNav, rightNav;
    View view;
    String[] emotions;
    private HorizontalViewPager horizontalViewPager;
    private Context mContext;
    private TypedArray imagesArray;




    private OnFragmentInteractionListener mListener;

    public ImageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_image, container, false);
//        Toast toast=Toast. makeText(getActivity(),"image fragment",Toast. LENGTH_SHORT);
//        toast.show();

        mContext = getContext();

        //access data from the calling activity, i.e., question and answers
        Bundle bundle = this.getArguments();
        ArrayList<String> q = bundle.getStringArrayList("emotions");
        emotions = (q).toArray(new String[q.size()]);

        imagesArray = getResources().obtainTypedArray(R.array.emo_images);

        horizontalViewPager = view.findViewById(R.id.viewPager);
        return view;
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
        horizontalViewPager.setAdapter(new ViewPagerAdapter(mContext, emotions,imagesArray));
        leftNav = view.findViewById(R.id.left_nav);
        rightNav = view.findViewById(R.id.right_nav);

        horizontalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    leftNav.setVisibility(View.INVISIBLE);
                }
                else
                    leftNav.setVisibility(View.VISIBLE);

                if(position==emotions.length-1)
                    rightNav.setVisibility(View.INVISIBLE);
                else
                    rightNav.setVisibility(View.VISIBLE);
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
