package org.gpginc.ntateam.projectwkff;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class BaseAppActivity extends AppCompatActivity
{
    public boolean darkTheme;
    public boolean allDebugEnabled;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        darkTheme = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("DARK_THEME", false);
        allDebugEnabled = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("ALLDEBUG", false);

    }
}
