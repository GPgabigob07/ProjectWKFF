package org.gpginc.ntateam.projectwkff.runtime.skills;

import android.os.Parcel;

import androidx.databinding.ViewDataBinding;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.PlayerStatusBinder;
import org.gpginc.ntateam.projectwkff.runtime.ClazzSkill;
import org.gpginc.ntateam.projectwkff.runtime.util.Replay;
import org.gpginc.ntateam.projectwkff.runtime.variations.TurnSkill;
import org.gpginc.ntateam.projectwkff.ui.widget.dialogs.MessageDialog;

public class NullDirectAttack extends TurnSkill
{

    public NullDirectAttack() {
        super(R.string.skill_null_attack, NONE, Type.ATTACK_TRIGGER);

    }

    protected NullDirectAttack(Parcel in) {
        super(in);
    }

    @Override
    public ClazzSkill newInstance() {
        return new NullDirectAttack();
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    @Override
    public void runSkill(final ViewDataBinding bind)
    {
        final PlayerStatusBinder binder = (PlayerStatusBinder) bind;
        new MessageDialog.Display(binder.getRES(), R.string.youwereattackedbutwonttakedamage);
        binder.getRES().CP().setProtected(this.canUse);
        binder.getRES().replay.addAction(binder.getPlayer(), Replay.ReplayAction.Type.STRATEGY, binder.getRES().getString(R.string.replay_nulledattack), binder.getRES().currentTurn);
        this.canUse = false;
    }

    public static Creator<NullDirectAttack> CREATOR = new Creator<NullDirectAttack>() {
        @Override
        public NullDirectAttack createFromParcel(Parcel source) {
            return new NullDirectAttack(source);
        }

        @Override
        public NullDirectAttack[] newArray(int size) {
            return new NullDirectAttack[size];
        }
    };
}
