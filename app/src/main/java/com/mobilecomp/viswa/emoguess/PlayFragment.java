package com.mobilecomp.viswa.emoguess;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
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
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Intent intent1;
    FirebaseAuth fAuth;
    StorageReference storageReference;
    FirebaseStorage storage;
    String userID;


    private OnFragmentInteractionListener mListener;

    public PlayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayFragment newInstance(String param1, String param2) {
        PlayFragment fragment = new PlayFragment();
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
            storage = FirebaseStorage.getInstance();
            fAuth = FirebaseAuth.getInstance();
            storageReference = storage.getReference();
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_play, container, false);
        ImageView imView = view.findViewById(R.id.bImage);
        imView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageFragment.score=0;
                ImageFragment.attempts=0;
                startActivity(new Intent(getContext(),ImageActivity.class));
            }
        });

        Button immView = view.findViewById(R.id.bbImage);
        immView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageFragment.score=0;
                ImageFragment.attempts=0;
                startActivity(new Intent(getContext(),ImageActivity.class));
            }
        });

        ImageView videoView = view.findViewById(R.id.bVideo);
//        Uri s=Uri.parse("android.resource://com.mobilecomp.viswa.emoguess/"+R.raw.video);
//        videoView.setVideoURI(s);
//        videoView.start();
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isConnected(getContext());
                if (isConnected(getContext())==true) {
                ImageFragment.score=0;
                ImageFragment.attempts=0;
                    startActivity(new Intent(getContext(),VideoActivity.class));
                }
            }
        });

        Button videoView2 = view.findViewById(R.id.bbVideo);
        videoView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isConnected(getContext());
                if (isConnected(getContext())==true) {
                ImageFragment.score=0;
                ImageFragment.attempts=0;
                    startActivity(new Intent(getContext(),VideoActivity.class));
                }
            }
        });

        ImageButton share = view.findViewById(R.id.share);

        share.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View v){
                intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
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


        //   Toast toast=Toast. makeText(getActivity(),"play fragment",Toast. LENGTH_SHORT);
        //  toast.show();
        return view;
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
                final ArrayList<String> imagesNameList = new ArrayList<>();
                final ArrayList<Uri> mArrayUri = new ArrayList<>();
                String imageEncoded;
                String imageName;
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
                    imageName= imageEncoded.substring(47);
                    imagesEncodedList.add(imageEncoded);
                    imagesNameList.add(imageName);
                    System.out.println(imageName);
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
                            imageName= imageEncoded.substring(47);
                            imagesEncodedList.add(imageEncoded);
                            imagesNameList.add(imageName);
                            System.out.println(imageName);
                            cursor.close();
                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Confirm sharing images?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
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
                                        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bmp, imagesNameList.get(i), null);
                                        //Bitmap compressed = BitmapFactory.decodeStream(new ByteArrayInputStream(stream.toByteArray()));
                                        final Uri compressed= Uri.parse(path);
                                        byte[] byteArray = stream.toByteArray();
                                        try {
                                            stream.close();
                                            stream = null;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        final ProgressDialog progressDialog = new ProgressDialog(getContext());
                                        progressDialog.setTitle("Uploading...");
                                        progressDialog.show();

                                        //Replace UUID.randomUUID().toString()  to image name
                                        StorageReference ref = storageReference.child("images/"+userID+"/"+ imagesNameList.get(i));
                                        ref.putFile(compressed)
                                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                        getActivity().getContentResolver().delete(compressed, null, null);
                                                        progressDialog.dismiss();
                                                        System.out.println("uploaded");
                                                        Toast.makeText(getContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                                                .getTotalByteCount());
                                                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                                                    }
                                                });
                                    }
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            } else {
                System.out.println("Image not picked");
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Something went wrong");
        }
    }

    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())) {
            return true;
        } else {
            showDialog();
            return false;
        }
    }

        private void showDialog()
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Connect to wifi or quit")
                    .setCancelable(false)
                    .setPositiveButton("Connect to WIFI", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    })
                    .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            getActivity().finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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