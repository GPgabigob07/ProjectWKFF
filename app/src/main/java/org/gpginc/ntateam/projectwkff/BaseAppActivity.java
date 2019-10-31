package org.gpginc.ntateam.projectwkff;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class BaseAppActivity extends AppCompatActivity
{
    public boolean darkTheme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        darkTheme = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("DARK_THEME", false);

    }
}
