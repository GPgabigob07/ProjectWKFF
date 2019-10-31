package org.gpginc.ntateam.projectwkff.runtime.events;

import android.os.Parcel;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Event;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;

public class KillingSpree extends Event {


    public KillingSpree() {
        super(R.string.event_killing_spree, R.string.evt_descr_ks, R.string.evt_end_descr_ks, Rarity.RARE, 2, EventHandler.ON_DEATH);
        super.setNeedPlayers();
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    @Override
    public Event newInstance() {
        return null;
    }

    @Override
    public Event newInstance(Player target, Player owner) {
        return new KillingSpree().setTarget(target).setOwner(owner);
    }

    @Override
    public Event newInstance(Player owner) {
        return null;
    }

    protected KillingSpree(Parcel in) {
        super(in);
        super.setNeedPlayers();
    }

    @Override
    public boolean check(Player p) {
        if (p.getLastAttacker() instanceof Player)
            return p.isDead && (p.getLastAttacker().equals(owner));
        else return false;
    }

    @Override
    public void exe(Player p, GameFlux res) {
        res.getPlayer(owner).isWinner = true;
    }

    public static final Creator<KillingSpree> CREATOR = new Creator<KillingSpree>() {
        @Override
        public KillingSpree createFromParcel(Parcel source) {
            return new KillingSpree(source);
        }

        @Override
        public KillingSpree[] newArray(int size) {
            return new KillingSpree[size];
        }
    };

    @Override
    public KillingSpree setTarget(Player p) {
        p.setAttachedToEvent();
        this.target = p;
        return this;
    }
}
