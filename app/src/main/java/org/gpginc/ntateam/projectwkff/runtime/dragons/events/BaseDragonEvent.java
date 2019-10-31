package org.gpginc.ntateam.projectwkff.runtime.dragons.events;

import android.os.Parcel;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.databinding.ListItemEventsBinding;
import org.gpginc.ntateam.projectwkff.runtime.Event;
import org.gpginc.ntateam.projectwkff.runtime.Kingdom;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.dragons.Dragon;
import org.gpginc.ntateam.projectwkff.runtime.dragons.DragonHandler;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;

import java.util.Collection;
import java.util.Random;

public abstract class BaseDragonEvent extends Event
{

    protected DragonHandler handler;

    public BaseDragonEvent(int name, int description, int endDescription, Rarity rarity, int maxInGame, EventHandler handler) {
        super(name, description, endDescription, rarity, maxInGame, handler);
        setup();
    }

    public BaseDragonEvent(Parcel in) {
        super(in);

    }


    public BaseDragonEvent setDragon(Dragon d)
    {
        this.owner = d;
        return this;
    }
    public BaseDragonEvent newInstance(GameFlux res)
    {

        return this;
    }

    protected BaseDragonEvent bornDragons(GameFlux res)
    {
        if(res.DRAGONS.isEmpty())
        {
            this.setDragon(Dragon.bornToKingdom(Kingdom.values()[new Random().nextInt(2)]));
            res.DRAGONS.add(getDragon());
        }
        else
        {
            this.setDragon(Dragon.bornToKingdom(res.DRAGONS.stream().findAny().get().getKingdom().equals(Kingdom.OHXER) ? Kingdom.CAMELOT : Kingdom.OHXER));
            res.DRAGONS.add(getDragon());
        }
        return this;
    }

    /**
     * All defaults newInstance methods from comom events won't be aplicable to dragon events, once dragons are limited to 2, and they CAN'T repeat;
     */
    @Override
    public Event newInstance() {
        return null;
    }

    @Override
    public Event newInstance(Player target, Player owner) {
        return null;
    }

    @Override
    public Event newInstance(Player owner) {
        return null;
    }

    public abstract void setup_fromList(Collection<Player> players);
    public abstract void setup();
    public abstract void setup_fromRuntime(GameFlux res);

    public void setBarToUpdateWithDragonCharge(ListItemEventsBinding binder){
        this.handler.setBinder(binder);
    }



}
