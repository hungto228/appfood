package com.example.phantom_project;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManage {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context_;
    //sharedpref mode
    int PRIVATE_MODE = 0;
    //sharedpreferences file name
    private static final String PREF_NAME = "welcome";
    private static final String IS_FIST_TIME_LAUNCH = "IsFistTimeLaunch";

    public PreferenceManage(Context context) {
        this.context_ = context;
        preferences = context_.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void setFistTimelaunch(Boolean isFistTime) {
        editor.putBoolean(IS_FIST_TIME_LAUNCH, isFistTime);
        editor.commit();
    }

    public boolean isFistTimelauch() {
        return preferences.getBoolean(IS_FIST_TIME_LAUNCH, true);
    }
}
