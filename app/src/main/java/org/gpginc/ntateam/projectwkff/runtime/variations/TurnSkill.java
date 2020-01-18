package org.gpginc.ntateam.projectwkff.runtime.variations;

import android.os.Parcel;
import static org.gpginc.ntateam.projectwkff.GameFlux.LOG;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.ClazzSkill;
import org.gpginc.ntateam.projectwkff.ui.widget.dialogs.MessageDialog;

public abstract class TurnSkill extends ClazzSkill
{
    protected boolean canUse = true, markToShowDialog = false;

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
        if(markToShowDialog)markToShowDialog=false;
        if(this.canUse)runSkill(o);
        else
        {
            this.canUse = true;
            this.markToShowDialog = true;
            runSkill(o);
            LOG.v("", "MUST BE USEFULL");
        }
    }
}
