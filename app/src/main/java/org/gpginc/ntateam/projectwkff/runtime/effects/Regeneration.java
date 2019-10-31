package org.gpginc.ntateam.projectwkff.runtime.effects;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Effect;
import org.gpginc.ntateam.projectwkff.runtime.Player;

public class Regeneration extends Effect {

    public Regeneration() {
        super(R.string.efx_regen_name, R.string.efx_regen_description, 1, R.drawable.regen_icon);
    }

    public Regeneration(Parcel in)
    {
        super(in);
    }

    public void apply(Player p, int t) {
        if(this.still(t))
        {
            if(p.getDamageTaken() > 0)
            {
                applyTurn = t;
                p.undamage(1);
                consume();
            } else if(p.life() < 3)
            {
                applyTurn =t;
                p.increaseLifeIn(1);
                consume();
            }
        }
        else p.antidote(this, t);
    }

    @Override
    public void consume() {
        this.currentUsages--;
    }

    @Override
    public void antidote(Player p) {
        //No inspect antidote, note, regen don't change any player attribute, just impeach damage
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

    public Regeneration newInstance() {
        return new Regeneration();
    }

    public static final Parcelable.Creator<Regeneration> CREATOR = new Parcelable.Creator<Regeneration>()
    {
        @Override
        public Regeneration createFromParcel(Parcel source) {
            return new Regeneration(source);
        }

        @Override
        public Regeneration[] newArray(int size) {
            return new Regeneration[size];
        }
    };
}
