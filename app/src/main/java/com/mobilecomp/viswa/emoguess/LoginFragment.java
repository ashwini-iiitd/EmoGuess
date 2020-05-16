package com.mobilecomp.viswa.emoguess;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(),"Logged in Successfully ",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(),HomeActivity.class));
                        }
                        else{
//                            status = 1;
                            Toast.makeText(getContext(),"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(view.GONE);

                        }
                    }
                });

            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),RegisterActivity.class));
            }
        });

        return view;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
