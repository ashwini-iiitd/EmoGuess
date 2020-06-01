package com.mobilecomp.viswa.emoguess;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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
    Context mContext;
    FirebaseStorage storage;
    FirebaseAuth fAuth;
    StorageReference storageReference;
    static View view;
    String userID;

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
        storage = FirebaseStorage.getInstance();
        fAuth = FirebaseAuth.getInstance();
        storageReference = storage.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_restart, container, false);
        //Toast toast=Toast. makeText(getActivity(),"restart fragment",Toast. LENGTH_SHORT);
        // toast.show();
        Button score = view.findViewById(R.id.Score);

        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getActivity(), "Score: " + String.valueOf(ImageFragment.score), Toast.LENGTH_LONG);
                   toast.show();
//                if (ImageFragment.timelefttext.compareTo("0:00") == 0) {
//                    System.out.println("score");
//                    Toast toast = Toast.makeText(getActivity(), "Score: " + String.valueOf(ImageFragment.score), Toast.LENGTH_LONG);
//                    toast.show();
//                } else if (VideoFragment.timelefttext.compareTo("0:00") == 0) {
//                    Toast toast = Toast.makeText(getActivity(), "Score: " + String.valueOf(VideoFragment.score), Toast.LENGTH_LONG);
//                    toast.show();
//                }
//                else if (ImageFragment.timelefttext.compareTo("0:00") == 1) {
//                    System.out.println("no score");
//                    Toast toast = Toast.makeText(getActivity(), "Score yet: " + String.valueOf(VideoFragment.score), Toast.LENGTH_LONG);
//                    toast.show();
//                }
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

            userID = fAuth.getCurrentUser().getUid();
            // When an Image is picked
            if (requestCode == 1 && null != data) {
                // Get the Image from data
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                ArrayList<String> imagesEncodedList = new ArrayList<>();
                ArrayList<Uri> mArrayUri = new ArrayList<>();
                String imageEncoded;
                if (data.getData() != null) {
                    //System.out.println("nothing2 "+data.getData());
                    Uri mImageUri = data.getData();
                    mArrayUri.add(mImageUri);
                    // Get the cursor
                    Cursor cursor = getActivity().getContentResolver().query(mImageUri, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    cursor.close();
                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        //System.out.println("clip data " + mClipData);
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();
                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }

            for (int i =0 ; i < mArrayUri.size(); i++) {
                if (mArrayUri.get(i) != null) {
                    InputStream imageStream = null;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(mArrayUri.get(i));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 25, stream);
                    byte[] byteArray = stream.toByteArray();
                    try {
                        stream.close();
                        stream = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//                    final ProgressDialog progressDialog = new ProgressDialog(mContext);
//                    progressDialog.setTitle("Uploading...");
//                    progressDialog.show();

                    //Replace UUID.randomUUID().toString()  to image name
                    StorageReference ref = storageReference.child("images/"+userID+"/"+ UUID.randomUUID().toString());
                    ref.putBytes(byteArray)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //progressDialog.dismiss();
                                    System.out.println("uploaded");
                                    //Toast.makeText(mContext, "Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                  //  progressDialog.dismiss();
                                      //Toast.makeText(mContext, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                            .getTotalByteCount());
                                    //progressDialog.setMessage("Uploaded " + (int) progress + "%");
                                }
                            });
                }
            }

//                System.out.println("File "+filePathColumn);
//                System.out.println("encoded "+imagesEncodedList);
//                System.out.println("array "+mArrayUri);
//                System.out.println("array size "+mArrayUri.size());
//                  List<Intent> targetShareIntents=new ArrayList<Intent>();
//                  Intent shareIntent=new Intent();
//                  shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
//                  shareIntent.setType("image/*");
//                  List<ResolveInfo> resInfos=getActivity().getPackageManager().queryIntentActivities(shareIntent, 0);
//                  System.out.println(resInfos);
//                  if(!resInfos.isEmpty()){
//                    System.out.println("Have package");
//                        for(ResolveInfo resInfo : resInfos){
//                            String packageName=resInfo.activityInfo.packageName;
//                           // Log.i("Package Name", packageName);
//                             if(packageName.contains("com.google.android.apps.docs")){
//                                Intent intent=new Intent();
//                                intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
//                                System.out.println(packageName);
//                                System.out.println(resInfo.activityInfo.name);
//                                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
//                                intent.setType("image/*");
//                                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, mArrayUri);
//                                intent.setPackage(packageName);
//                                targetShareIntents.add(intent);
//                            }
//                        }
//                    if(!targetShareIntents.isEmpty()){
//                        System.out.println("Have Intent");
//                        Intent chooserIntent=Intent.createChooser(targetShareIntents.remove(0), "Choose app to share");
//                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
//                        System.out.println(Intent.EXTRA_INITIAL_INTENTS);
//                        startActivity(chooserIntent);
//                    }
//                    else {
//                    System.out.println("Do not Have Intent");
//                    }
//              }
            } else {
                System.out.println("Image not picked");
            }
        } catch (Exception e) {
            System.out.println(e);
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