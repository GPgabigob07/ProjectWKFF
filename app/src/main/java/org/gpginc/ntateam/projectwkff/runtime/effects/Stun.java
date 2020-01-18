package org.gpginc.ntateam.projectwkff.runtime.effects;

import android.os.Parcel;
import static org.gpginc.ntateam.projectwkff.GameFlux.LOG;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Effect;
import org.gpginc.ntateam.projectwkff.runtime.Player;

public class Stun extends Effect
{
    public Stun() {
        super(R.string.efx_stun_name, R.string.efx_stun_descr, 1, R.drawable.stun_icon);
        instaEffect = true;
    }

    protected Stun(Parcel in) {
        super(in);instaEffect = true;
    }

    public void apply(Player p, int t) {
        if(this.still(t)) {
            applyTurn = t;
            p.setStun(true);
            consume();
        }
        else
        {
            p.antidote(this, t);
            p.setStun(false);
        }
    }

    @Override
    public void consume() {
        this.currentUsages -= 1;
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    public Effect newInstance() {
        return new Stun();
    }

    @Override
    public void antidote(Player p) {
        p.setStun(false);
        LOG.wtf(this.getClass().getSimpleName(), "ANTIDOTE SET TO " + p.toString());
    }

    @Override
    protected int getBehavior() {
        return MALIGNE;
    }

    public static final Creator<Stun> CREATOR = new Creator<Stun>()
    {
        @Override
        public Stun createFromParcel(Parcel source) {
            return new Stun(source);
        }

        @Override
        public Stun[] newArray(int size) {
            return new Stun[size];
        }
    };
}
