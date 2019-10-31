package org.gpginc.ntateam.projectwkff.runtime.variations;

import android.os.Parcel;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import org.gpginc.ntateam.projectwkff.runtime.ClazzSkill;

public abstract class TurnSkill extends ClazzSkill
{
    protected boolean canUse = true;

    public TurnSkill(int name, int layout, @NonNull Type type) {
        super(name, layout, type);
    }

    protected TurnSkill(Parcel in) {
        super(in);
        canUse = in.readByte() == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte)(canUse ? 1 : 0));
    }

    public void onTurnRun(ViewDataBinding o)
    {
        if(this.canUse)runSkill(o);
        else Log.v("", "MUST BE USEFULL");
    }
}
