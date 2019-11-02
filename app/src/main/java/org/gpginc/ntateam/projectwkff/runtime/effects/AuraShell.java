package org.gpginc.ntateam.projectwkff.runtime.effects;

import android.os.Parcel;
import android.os.Parcelable;
import static org.gpginc.ntateam.projectwkff.GameFlux.LOG;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Effect;
import org.gpginc.ntateam.projectwkff.runtime.Player;

public class AuraShell extends Effect {

    public AuraShell() {
        super(R.string.efx_aurashell_name, R.string.efx_aurashell_description, -1, R.drawable.aurashell_icon);
        instaEffect = true;
    }

    public AuraShell(Parcel in)
    {
        super(in);
        instaEffect = true;
    }

    @Override
    public void apply(Player p, int t) {
        if(this.still(t)) {
            if (p.getTurnAttacked(t) || p.life() < 3) {
                applyTurn = t;
                consume();
                p.setProtected(true);
                p.antidote(this, t);
            }
        }
    }

    @Override
    public void consume() {
        this.currentUsages = 0;
    }

    @Override
    public void antidote(Player p) {
        //No inspect antidote, note, aurashell don't change any player attribute, just impeach damage
        LOG.wtf(this.getClass().getSimpleName(), "ANTIDOTE SET TO " + p.toString());
    }

    @Override
    protected int getBehavior() {
        return BENIGNE;
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    public AuraShell newInstance() {
        return new AuraShell();
    }

    public static final Parcelable.Creator<AuraShell> CREATOR = new Parcelable.Creator<AuraShell>()
    {
        @Override
        public AuraShell createFromParcel(Parcel source) {
            return new AuraShell(source);
        }

        @Override
        public AuraShell[] newArray(int size) {
            return new AuraShell[size];
        }
    };
}
