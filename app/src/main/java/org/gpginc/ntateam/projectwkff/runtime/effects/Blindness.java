package org.gpginc.ntateam.projectwkff.runtime.effects;

import android.os.Parcel;
import android.os.Parcelable;
import static org.gpginc.ntateam.projectwkff.GameFlux.LOG;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Effect;
import org.gpginc.ntateam.projectwkff.runtime.Player;

public class Blindness extends Effect {

    public Blindness() {
        super(R.string.efx_blindness_name, R.string.efx_blindness_description, 1, R.drawable.blindness_icon);
        instaEffect = true;
    }

    public Blindness(Parcel in)
    {
        super(in);instaEffect = true;
    }

    public void apply(Player p, int t) {
        if(this.still(t))
        {
            applyTurn = t;
            p.setBlind(true);
            consume();
        }
        else
        {
            p.antidote(this, t);
            p.setBlind(false);
        }
    }

    @Override
    public void consume() {
        this.currentUsages--;
    }

    @Override
    public void antidote(Player p) {
        p.setBlind(false);
        LOG.wtf(this.getClass().getSimpleName(), "ANTIDOTE SET TO " + p.toString());
    }

    @Override
    protected int getBehavior() {
        return MALIGNE;
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    public Blindness newInstance() {
        return new Blindness();
    }

    public static final Parcelable.Creator<Blindness> CREATOR = new Parcelable.Creator<Blindness>()
    {
        @Override
        public Blindness createFromParcel(Parcel source) {
            return new Blindness(source);
        }

        @Override
        public Blindness[] newArray(int size) {
            return new Blindness[size];
        }
    };
}
