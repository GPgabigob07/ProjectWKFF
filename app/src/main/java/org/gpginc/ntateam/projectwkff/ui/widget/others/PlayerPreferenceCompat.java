package org.gpginc.ntateam.projectwkff.ui.widget.others;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;

public class PlayerPreferenceCompat extends Preference
{
    public PlayerPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public PlayerPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlayerPreferenceCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerPreferenceCompat(Context context) {
        super(context);
    }

    public class PPC_VH extends RecyclerView.ViewHolder
    {

        public PPC_VH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
