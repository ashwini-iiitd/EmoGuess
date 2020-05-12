package com.mobilecomp.viswa.emoguess;

import android.content.Context;
import android.content.SharedPreferences;

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
        int id=user.getId();
        editor.putInt(skey,id).commit();
    }

    public int getSession() {
        return sharedPreferences.getInt(skey, -1);
    }

    public void removeSession() {
        editor.putInt(skey,-1).commit();
    }
}
