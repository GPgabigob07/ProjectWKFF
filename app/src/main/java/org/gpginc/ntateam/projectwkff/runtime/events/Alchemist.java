package org.gpginc.ntateam.projectwkff.runtime.events;

import android.os.Parcel;
import static org.gpginc.ntateam.projectwkff.GameFlux.LOG;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Event;
import org.gpginc.ntateam.projectwkff.runtime.Main;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.util.EffectWalet;
import org.gpginc.ntateam.projectwkff.runtime.util.Util;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;

public class Alchemist extends Event {

    private EffectWalet walet = new EffectWalet();

    public Alchemist() {
        super(R.string.evt_name_al, R.string.evt_descr_al, R.string.evt_end_descr_al, Rarity.RARE, 1, EventHandler.ON_DEATH);
        setNeedPlayers();
    }

    protected Alchemist(Parcel in) {
        super(in);
        setNeedPlayers();
        walet = in.readTypedObject(EffectWalet.CREATOR);
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
    public Event newInstance(Player target, Player owner)
    {
        Alchemist a = new Alchemist();
        a.setOwner(owner).setTarget(target);
        owner.getClazz().bindSkill(Main.INFECTIOUS);
        a.walet = new EffectWalet().random();
        return a;
    }

    public EffectWalet getWalet() {
        return walet;
    }

    @Override
    public Alchemist base() {
        super.base();
        LOG.v("from event:"," BASE APLYIED TO ALCHEMIST");
        return this;
    }

    @Override
    public Event newInstance(Player owner) {
        return null;
    }

    @Override
    public boolean check(Player p) {
        return p.isDead && p.equals(this.target);
    }

    @Override
    public void exe(Player p, GameFlux res) {
        if(p.equals(target) && !res.getPlayer(target).isDead)res.getPlayer(this.owner).isWinner = true;

        Util.getGameEnd(this, res);
        res.gameover();
        LOG.v("EVENT RUNTIME", res.getPlayer(this.owner).getName() + " HAD WON BY " + res.getResources().getString(this.getName()));
    }
    public static Creator<Alchemist> CREATOR = new Creator<Alchemist>() {
        @Override
        public Alchemist createFromParcel(Parcel source) {
            return new Alchemist(source);
        }

        @Override
        public Alchemist[] newArray(int size) {
            return new Alchemist[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedObject(walet, flags);
    }
}
