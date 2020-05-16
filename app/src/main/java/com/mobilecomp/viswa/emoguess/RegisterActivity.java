package com.mobilecomp.viswa.emoguess;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements RegisterFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        RegisterFragment registerFragment = new RegisterFragment();
        fragmentTransaction.replace(R.id.register_layout, registerFragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkSession();
    }

    private void checkSession() {
        SessionManagement sessionManagement= new SessionManagement(RegisterActivity.this);
        int userID= sessionManagement.getSession();

        if (userID!=-1) {
            movetoplay();
        }
        else{

        }
    }

//    public void Register(View view) {
//
//    }

    private void movetoplay() {
        Intent intent=new Intent(RegisterActivity.this, PlayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void Register(View view) {
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String name = (editText.getText().toString()).replaceAll("\\s","");
//        EditText editText2 = (EditText) findViewById(R.id.editText2);
//        String email = (editText2.getText().toString()).replaceAll("\\s","");
//        EditText editText3 = (EditText) findViewById(R.id.editText3);
//        String temp = (editText3.getText().toString()).replaceAll("\\s","");
//        String phone = temp.substring(temp.length()-10,temp.length()-1);
//        User user=new User(name, email, phone);
//        SessionManagement sessionManagement=new SessionManagement(RegisterActivity.this);
//        sessionManagement.saveSession(user);
//        movetoplay();
        Log.d("REG","RegiterActivity");
    }
}