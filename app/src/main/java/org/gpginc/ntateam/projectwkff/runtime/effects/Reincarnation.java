package org.gpginc.ntateam.projectwkff.runtime.effects;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Effect;
import org.gpginc.ntateam.projectwkff.runtime.Player;

public class Reincarnation extends Effect {

    public Reincarnation() {
        super(R.string.efx_reincarnation_name, R.string.efx_reincarnation_description, 1, R.drawable.reincarnation_icon);
    }

    public Reincarnation(Parcel in)
    {
        super(in);
    }

    public void apply(Player p,int t) {
        if(this.still(t))
        {
            if(p.life() <=0) {
                applyTurn=t;
                p.respawn();
                consume();
            }
        }
        else p.antidote(this, t);
    }

    @Override
    public void consume() {
        this.currentUsages -= 1;
    }

    @Override
    public void antidote(Player p) {
        p.markDeath();
        Log.wtf(this.getClass().getSimpleName(), "ANTIDOTE SET TO " + p.toString());
    }

    @Override
    protected int getBehavior() {
        return BENIGNE;
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    public Reincarnation newInstance() {
        return new Reincarnation();
    }

    public static final Parcelable.Creator<Reincarnation> CREATOR = new Parcelable.Creator<Reincarnation>()
    {
        @Override
        public Reincarnation createFromParcel(Parcel source) {
            return new Reincarnation(source);
        }

        @Override
        public Reincarnation[] newArray(int size) {
            return new Reincarnation[size];
        }
    };
}
