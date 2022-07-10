package com.example.mycommicreader;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUserName(String username) {
        prefs.edit().putString("username", username).commit();
    }

    public String getUserName() {
        String username = prefs.getString("username","");
        return username;
    }
}
