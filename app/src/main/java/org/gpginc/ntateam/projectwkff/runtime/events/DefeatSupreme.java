package org.gpginc.ntateam.projectwkff.runtime.events;

import android.os.Parcel;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Event;
import org.gpginc.ntateam.projectwkff.runtime.Main;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.util.Util;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;

public class DefeatSupreme extends Event
{
    public DefeatSupreme() {
        super(R.string.event_defeat_supreme, R.string.evt_descr_eds ,R.string.evt_end_descr_eds, Rarity.ALWAYS, 1, EventHandler.ALWAYS);
    }

    public DefeatSupreme(Parcel in) {
        super(in);
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    @Override
    public Event newInstance() {
        return new DefeatSupreme();
    }

    @Override
    public Event newInstance(Player target, Player owner) {
        return null;
    }

    @Override
    public Event newInstance(Player owner) {
        return null;
    }

    @Override
    public boolean check(Player p) {
        if(p.getClazz().equals(Main.SUPREME))
        {
            return p.isDead && !p.isAffectedBy(Main.REINCARNATION);
        }
        return false;
    }

    @Override
    public void exe(Player p, GameFlux a)
    {
        for (Player player : a.PLAYERS) {
            if (player.isEnemyFrom(p) && !player.isWinner) {
                player.isWinner = true;
            }
        }
        Util.getGameEnd(this, a);
        a.gameover();
    }

    public static final Creator<DefeatSupreme> CREATOR = new Creator<DefeatSupreme>()
    {
        @Override
        public DefeatSupreme createFromParcel(Parcel source) {
            return new DefeatSupreme(source);
        }

        @Override
        public DefeatSupreme[] newArray(int size) {
            return new DefeatSupreme[size];
        }
    };
}

