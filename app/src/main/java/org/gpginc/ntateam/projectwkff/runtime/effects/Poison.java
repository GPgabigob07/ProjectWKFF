package org.gpginc.ntateam.projectwkff.runtime.effects;

import android.os.Parcel;
import android.os.Parcelable;
import static org.gpginc.ntateam.projectwkff.GameFlux.LOG;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Effect;
import org.gpginc.ntateam.projectwkff.runtime.Player;

public class Poison extends Effect {

    public Poison() {
        super(R.string.efx_poison_name, R.string.efx_poison_description, 2, R.drawable.poison_icon);
    }

    public Poison(Parcel in)
    {
        super(in);
    }

    public void apply(Player p, int t) {
        if(this.still(t)) {
            applyTurn = t;
            p.damage(1, MAGIC);
            this.consume();
        }
        else p.antidote(this, t);
    }

    @Override
    public void consume() {
        this.currentUsages -= 1;
    }

    @Override
    public void antidote(Player p) {
        //No inspect antidote, note, poison dont change any player attribute, just does damage.
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

    public Poison newInstance() {
        return new Poison();
    }

    public static final Parcelable.Creator<Poison> CREATOR = new Parcelable.Creator<Poison>()
    {
        @Override
        public Poison createFromParcel(Parcel source) {
            return new Poison(source);
        }

        @Override
        public Poison[] newArray(int size) {
            return new Poison[size];
        }
    };
}
