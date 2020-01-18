package org.gpginc.ntateam.projectwkff;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class BaseAppActivity extends AppCompatActivity
{
    public boolean darkTheme;
    public boolean allDebugEnabled;
    public boolean sessionLoadAdDone = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        darkTheme = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("DARK_THEME", false);
        allDebugEnabled = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("ALLDEBUG", false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if(outState ==null) outState = new Bundle();
        outState.putBoolean("session_ad", sessionLoadAdDone);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null)
        {
            sessionLoadAdDone = savedInstanceState.getBoolean("session_ad");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("session_ad", sessionLoadAdDone).apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sessionLoadAdDone = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("session_ad", false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("session_ad", false).apply();
    }
}
