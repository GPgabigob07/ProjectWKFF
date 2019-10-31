package org.gpginc.ntateam.projectwkff.runtime.events;

import android.os.Parcel;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Effect;
import org.gpginc.ntateam.projectwkff.runtime.Event;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.effects.AuraShell;
import org.gpginc.ntateam.projectwkff.runtime.effects.Reincarnation;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;

import java.util.Random;

public class DarkWitch extends Event {
    public DarkWitch() {
        super(R.string.evt_name_dw, R.string.evt_descr_dw, R.string.evt_end_descr_dw, Rarity.RARE, 2, EventHandler.START_LOOP_EVENT);
    }

    public DarkWitch(Parcel in) {
        super(in);
    }


    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    @Override
    public Event newInstance() {
        return new DarkWitch();
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
        return true;
    }

    @Override
    public void exe(Player p, GameFlux res)
    {
        if(new Random().nextInt(10) < 4)
        {
            Effect e = Effect.random();
            int i = new Random().nextInt(res.PLAYERS.size());
            if((e instanceof Reincarnation || e instanceof AuraShell) && res.PLAYERS.get(i).isAffectedBy(e));
            else res.PLAYERS.get(i).affect(e);
        }
    }

    public static final Creator<DarkWitch> CREATOR = new Creator<DarkWitch>() {
        @Override
        public DarkWitch createFromParcel(Parcel in) {
            return new DarkWitch(in);
        }

        @Override
        public DarkWitch[] newArray(int size) {
            return new DarkWitch[size];
        }
    };
}
