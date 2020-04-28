package com.mobilecomp.viswa.emoguess;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RestartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RestartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RestartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestartFragment newInstance(String param1, String param2) {
        RestartFragment fragment = new RestartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restart, container, false);
        //Toast toast=Toast. makeText(getActivity(),"restart fragment",Toast. LENGTH_SHORT);
        // toast.show();
        Button score = view.findViewById(R.id.Score);
        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ImageFragment.timelefttext.compareTo("0:00") == 0) {
                    Toast toast = Toast.makeText(getActivity(), "Score: " + String.valueOf(ImageFragment.score), Toast.LENGTH_LONG);
                    toast.show();
                } else if (VideoFragment.timelefttext.compareTo("0:00") == 0) {
                    Toast toast = Toast.makeText(getActivity(), "Score: " + String.valueOf(VideoFragment.score), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        ImageButton share = view.findViewById(R.id.share);

        share.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent1.setType("image/*");
                intent1.putExtra("crop", "true");
                intent1.putExtra("scale", true);
                intent1.putExtra("outputX", 256);
                intent1.putExtra("outputY", 256);
                intent1.putExtra("aspectX", 1);
                intent1.putExtra("aspectY", 1);
                intent1.putExtra("return-data", true);
                intent1.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent1, 1);
            }
        });


        Button restartiButton = view.findViewById(R.id.bRestartImages);
        restartiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ImageActivity.class));
            }
        });

        Button restartvButton = view.findViewById(R.id.bRestartVideos);
        restartvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), VideoActivity.class));
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 1 && null != data) {
                // Get the Image from data
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                ArrayList<String> imagesEncodedList = new ArrayList<>();
                ArrayList<Uri> mArrayUri = new ArrayList<>();
                String imageEncoded;
                if(data.getData()!=null){
                    Uri mImageUri=data.getData();
                    // Get the cursor
                    Cursor cursor = getActivity().getContentResolver().query(mImageUri, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();
                }
                else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();
                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
                  List<Intent> targetShareIntents=new ArrayList<Intent>();
                  Intent shareIntent=new Intent();
                  shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                  shareIntent.setType("image/*");
                  List<ResolveInfo> resInfos=getActivity().getPackageManager().queryIntentActivities(shareIntent, 0);
                  if(!resInfos.isEmpty()){
                    System.out.println("Have package");
                        for(ResolveInfo resInfo : resInfos){
                            String packageName=resInfo.activityInfo.packageName;
                           // Log.i("Package Name", packageName);
                             if(packageName.contains("com.google.android.apps.docs")){
                                Intent intent=new Intent();
                                intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                                intent.setType("image/*");
                                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, mArrayUri);
                                Uri u= Uri.parse("https://drive.google.com/drive/u/0/folders/1BvgUXNEPz8YJmqXXr5u81hdzlOdm6I_P");
                                intent.setDataAndType(u, intent.getType());
                                intent.setPackage(packageName);
                                targetShareIntents.add(intent);
                            }
                        }
                    if(!targetShareIntents.isEmpty()){
                        System.out.println("Have Intent");
                        Intent chooserIntent=Intent.createChooser(targetShareIntents.remove(0), "Choose app to share");
                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
                        startActivity(chooserIntent);
                    }
                    else {
                    System.out.println("Do not Have Intent");
                    }
              }
            } else {
                System.out.println("Image not picked");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
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
}