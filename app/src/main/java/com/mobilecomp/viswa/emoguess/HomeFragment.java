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

public class HomeFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    FirebaseAuth fAuth;
    StorageReference storageReference;
    FirebaseStorage storage;
    static View view;
    String userID;
    Intent intent1;

    public HomeFragment() {
        // Required empty public constructor
    }
public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = FirebaseStorage.getInstance();
        fAuth = FirebaseAuth.getInstance();
        storageReference = storage.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.i("CONNECT","Connected");
        ImageView play = view.findViewById(R.id.pb);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PlayActivity.class));
            }
        });
        ImageView howto = view.findViewById(R.id.ib);
        howto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HowToActivity.class));
            }
        });
        Button playButton = view.findViewById(R.id.bPlay);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PlayActivity.class));
            }
        });
        Button howtoButton = view.findViewById(R.id.bHowto);
        howtoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HowToActivity.class));
            }
        });
        Button logoutButton = view.findViewById(R.id.bLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}