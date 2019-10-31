package org.gpginc.ntateam.projectwkff.runtime.events;

import android.os.Parcel;
import android.os.Parcelable;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Event;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;

public class Bodyguard extends Event
{
    public Bodyguard() {
        super(R.string.evt_name_bg, R.string.evt_descr_bg, R.string.evt_end_descr_bg, Rarity.ULTRARARE, 2, EventHandler.ON_GAME_END);
        super.setNeedPlayers();
    }

    public Bodyguard(Parcel in) {
        super(in);
        super.setNeedPlayers();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public Parcelable.Creator getCreator() {
        return CREATOR;
    }

    @Override
    public Event newInstance() {
        return null;
    }

    @Override
    public Event newInstance(Player target, Player owner) {
        return new Bodyguard().setOwner(owner).setTarget(target);
    }

    @Override
    public Event newInstance(Player owner) {
        return null;
    }

    @Override
    public boolean check(Player p)
    {
        if(p.equals(target) && !p.isDead)return true;
        else return p.equals(owner);
    }

    @Override
    public void exe(Player p, GameFlux res)
    {
        if(p.equals(target) && !p.isDead)res.getPlayer(this.owner).isWinner = true;
        else if(p.equals(target) && p.isDead)res.getPlayer(this.owner).isWinner = false;
    }

    public static final Creator<Bodyguard> CREATOR = new Creator<Bodyguard>()
    {
        @Override
        public Bodyguard createFromParcel(Parcel source) {
            return new Bodyguard(source);
        }

        @Override
        public Bodyguard[] newArray(int size) {
            return new Bodyguard[size];
        }
    };
}
