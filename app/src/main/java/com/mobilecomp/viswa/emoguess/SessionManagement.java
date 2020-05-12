package com.mobilecomp.viswa.emoguess;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.view.View;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SPname= "session";
    String skey= "session_user";

    public SessionManagement(Context context) {
        sharedPreferences=context.getSharedPreferences(SPname, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public void saveSession(User user) {
        String name=user.getName();
        String email=user.getEmail();
        String phone=user.getPhone();
        System.out.println(user);
        editor.putInt(skey, Integer.parseInt(phone)).commit();
    }

    public int getSession() {
        return sharedPreferences.getInt(skey, -1);
    }

    public void removeSession() {
        editor.putInt(skey,-1).commit();
    }
}
