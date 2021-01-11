package com.mobilecomp.viswa.emoguess;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    EditText editName, editEmail, editContact, editPassword, editAge, editEthnicity, editGender;
    Button buttonRegister;
    TextView txtLogin;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String TAG = "DB";
    String MobilePattern = "[0-9]{10}";




    private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        // Toast toast=Toast. makeText(getActivity(),"Hello Fragment",Toast. LENGTH_SHORT);
        // toast.show();

        editName = view.findViewById(R.id.editName);
        editEmail = view.findViewById(R.id.editEmail);
        editPassword = view.findViewById(R.id.editPassword);
        editContact = view.findViewById(R.id.editContact);
        editAge = view.findViewById(R.id.editAge);
        editEthnicity = view.findViewById(R.id.editEthnicity);
        editGender= view.findViewById(R.id.editGender);
        buttonRegister = view.findViewById(R.id.registerButton);
        txtLogin = view.findViewById(R.id.textLogin);
        progressBar = view.findViewById(R.id.progBar);
        fAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        buttonRegister.setEnabled(false);

        TextView textView = view.findViewById(R.id.consent);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setTypeface(ResourcesCompat.getFont(getContext(), R.font.atma));
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);

        CheckBox check = view.findViewById(R.id.consent);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                   if (isChecked) {
                       buttonRegister.setEnabled(true);
                   }
                   else {
                       buttonRegister.setEnabled(false);
                   }

               }
           }
        );

        if (fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getContext(),HomeActivity.class));
            getActivity().finish();
        }


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                final String fullname = editName.getText().toString();
                final String contact = editContact.getText().toString();
                final String age = editAge.getText().toString();
                final String ethnicity = editEthnicity.getText().toString();
                final String gender = editGender.getText().toString();

                if(TextUtils.isEmpty(fullname)){
                    editName.setError("Name is required");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    editEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    editPassword.setError("Password is required");
                    return;
                }

                if(TextUtils.isEmpty(contact)){
                    editContact.setError("Contact Number is required");
                    return;
                }

                if(TextUtils.isEmpty(age)){
                    editAge.setError("Age of child is required");
                    return;
                }

                if(TextUtils.isEmpty(ethnicity)){
                    editEthnicity.setError("Ethnicity of child is required");
                    return;
                }

                if(TextUtils.isEmpty(gender)){
                    editGender.setError("Gender of child is required");
                    return;
                }

                if (password.length()<6){
                    editPassword.setError("Password must be atleast 6 characters");
                    return;
                }
                if(contact.matches(MobilePattern)) {
                    Toast.makeText(getContext(), "Phone number is valid", Toast.LENGTH_SHORT).show();
                } else if(!contact.matches(MobilePattern)) {
                    editContact.setError("Please enter valid 10 digit phone number");
                    return;
                }
                progressBar.setVisibility(view.VISIBLE);

                //registration in Firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(),"User Successfully Registered ",Toast.LENGTH_SHORT).show();

                            //get userID from firebase authentication done
                            userID = fAuth.getCurrentUser().getUid();
                            //create a document reference to the collection
                            DocumentReference docReference = fStore.collection("users").document(userID);
                            // Create a hash map to store a new user with a fullname, email and contact number
                            Map<String, Object> user = new HashMap<>();
                            user.put("name", fullname);
                            user.put("email", email);
                            user.put("contact", contact);
                            user.put("age of child", age);
                            user.put("ethnicity of child", ethnicity);
                            user.put("gender of child", gender);

                            docReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"OnSuccess: User profile is created for"+fullname+"with id"+userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });

                            startActivity(new Intent(getContext(),HomeActivity.class));
                        }
                        else{
                            Toast.makeText(getContext(),"Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(view.GONE);
                        }
                    }
                });


            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (getContext(),MainActivity.class));
            }
        });

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI ev
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}