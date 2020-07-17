package com.mobilecomp.viswa.emoguess;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    EditText editLogEmail, editLogPassword;
    Button buttonLogin;
    TextView txtRegister;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    private static final int REQ_CODE_CAMERA_PERMISSION = 1253;
//    static int status = 0;

    public LoginFragment() {
    }
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        editLogEmail = view.findViewById(R.id.editLogEmail);
        editLogPassword = view.findViewById(R.id.editLogPassword);
        buttonLogin = view.findViewById(R.id.loginButton);
        txtRegister = view.findViewById(R.id.textRegister);
        progressBar = view.findViewById(R.id.progLoginBar);
        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getContext(),HomeActivity.class));
            getActivity().finish();
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String email = editLogEmail.getText().toString().trim();
                String password = editLogPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    editLogEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    editLogPassword.setError("Password is required");
                    return;
                }

                if (password.length()<6){
                    editLogPassword.setError("Password must be atleast 6 characters");
                    return;
                }
                progressBar.setVisibility(view.VISIBLE);

                //registration in Firebase

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        isConnected(getContext());
                        if(task.isSuccessful() && isConnected(getContext())==true){
                            Toast.makeText(getContext(),"Logged in Successfully ",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(),HomeActivity.class));
                        }
                        else{
//                            status = 1;
                            Toast.makeText(getContext(),"Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(view.GONE);

                        }
                    }
                });

            }
        });

//        if (Build.VERSION.SDK_INT<=23) {
//            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
//        }

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET},
                REQ_CODE_CAMERA_PERMISSION);

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isConnected(getContext());
                if (isConnected(getContext())==true) {
                    startActivity(new Intent(getContext(),RegisterActivity.class));
                }
            }
        });

        return view;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
