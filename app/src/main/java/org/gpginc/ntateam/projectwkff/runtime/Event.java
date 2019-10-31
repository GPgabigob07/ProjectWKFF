package org.gpginc.ntateam.projectwkff.runtime;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.dragons.Dragon;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;

import java.util.Objects;

public abstract class Event implements Parcelable
{
    @StringRes
    private final int name, description, endDescription;
    private final int maxInGame;
    private final Rarity rarity;
    private Rarity useRarity;
    private final EventHandler handler;
    public boolean needPlayers = false;
    public boolean isAttacher = false;
    public boolean needConfiguration = false;
    public boolean isEnabled = true;

    public static final int monsiaurDescriptionDefault = R.string.label_beware;


    @Nullable
    protected BaseAttacker target, owner;

/**
 * Used to create some events that might set who wins in the end;
 * @param name Resource name
 * @param endDescription Resource endDescription
 * @param rarity {@link Rarity} rarity
 * @param maxInGame How many equals events can be in one game;
 * @param handler Basically, the runtime will call this event depending on it handler.
 */
    public Event(int name, int description, int endDescription, Rarity rarity, int maxInGame, EventHandler handler) {
        this.name = name;
        this.description = description;
        this.endDescription = endDescription;
        this.maxInGame = maxInGame;
        this.rarity = rarity;
        this.useRarity = rarity;
        this.handler = handler;

    }

    protected Event(Parcel in) {
        name = in.readInt();
        description = in.readInt();
        endDescription = in.readInt();
        maxInGame = in.readInt();
        needPlayers = in.readByte() != 0;
        isAttacher = in.readByte() != 0;
        needConfiguration = in.readByte() != 0;
        target = in.readParcelable(Player.class.getClassLoader());
        owner = in.readParcelable(Player.class.getClassLoader());
        rarity = Rarity.withName(in.readString());
        useRarity = Rarity.withName(in.readString());
        handler = EventHandler.withName(in.readString());
    }

    /*
     rarity = Rarity.withName(in.readString());
        handler = EventHandler.withName(in.readString())

    dest.writeString(rarity.R());
        dest.writeString(handler.name());
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(name);
        dest.writeInt(description);
        dest.writeInt(endDescription);
        dest.writeInt(maxInGame);
        dest.writeByte((byte) (needPlayers ? 1 : 0));
        dest.writeByte((byte) (isAttacher ? 1 : 0));
        dest.writeByte((byte) (needConfiguration ? 1 : 0));
        dest.writeParcelable(target, flags);
        dest.writeParcelable(owner, flags);
        dest.writeString(rarity.R());
        dest.writeString(useRarity.R());
        dest.writeString(handler.name());

    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected void setNeedPlayers()
    {
        this.needPlayers =true;
    }
    protected void setAttacher()
    {
        this.isAttacher = true;
    }
    public Rarity getRarity() {
        return this.useRarity;
    }

    public int getEndDescription() {
        return endDescription;
    }

    @StringRes
    public int getName() {
        return this.name;
    }

    public EventHandler getHandler() {
        return handler;
    }

    public int getMax() {
        return maxInGame;
    }

    public int getDescription() {
        return description;
    }

    public int getMaxInGame() {
        return maxInGame;
    }

    @Nullable
    public Player getTarget() {
        return (Player) target;
    }

    public Event setTarget(@Nullable Player target) {
        this.target = target;
        return this;
    }

    @Nullable
    public Player getOwner() {
        return (Player) owner;
    }

    public BaseAttacker getBaseOwner()
    {
        return this.owner;
    }

    public Event setOwner(@Nullable Player owner) {
        this.owner = owner;
        owner.setAttachedToEvent();
        return this;
    }

    public Event base()
    {
        Main.EVENT_LIST.add(this);
        return this;
    }

    public abstract Creator getCreator();

    /**
     * Used for simple events, that not interact directly with players
     * @return
     */
    public abstract Event newInstance();

    /**
     * Userd for events that needs a target and an owner.
     * @param target
     * @param owner
     * @return
     */
    public abstract Event newInstance(Player target, Player owner);

    /**
     * Used for single attached event, that change just one player behaviour
     * @param owner
     * @return
     */
    public abstract Event newInstance(Player owner);
    public abstract  boolean check(Player p);
    public abstract void exe(Player p, GameFlux res);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return name == event.name &&
                description == event.description &&
                endDescription == event.endDescription &&
                maxInGame == event.maxInGame &&
                rarity == event.rarity &&
                handler == event.handler;
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, description, endDescription, maxInGame, rarity, handler);
    }

    public int getMonsiaurDescriptionDefault() {
        return monsiaurDescriptionDefault;
    }

    public String getNameLikeStr(Resources res)
    {
        return res.getString(this.name);
    }

    public void setEnabled(boolean b)
    {
        this.isEnabled =b;
    }

    public Rarity defaultRarity()
    {
        return this.rarity;
    }

    public void setRarity(Rarity defaultRarity) {
        this.useRarity = defaultRarity;
    }

    public String getDefaultRarity() {
        return this.rarity.R();
    }
    /**
     * Stuff to be used with Dragon Events, but, databind cannot cast directly into xml from Event do BaseDragonEvent
     */
    public Dragon getDragon()
    {
        return (Dragon) this.owner;
    }
}
