package org.gpginc.ntateam.projectwkff.runtime;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.annotation.StringRes;

import java.util.Objects;
import java.util.Random;

import static org.gpginc.ntateam.projectwkff.runtime.Main.EFX;

public abstract class Effect implements Parcelable {

    public static final int BENIGNE = 2032659;
    public static final int MALIGNE = 2032660;

    @StringRes protected final int name, description;
    @DrawableRes protected final int  icon;
    protected final int turnsDuration;
    protected int currentUsages, applyTurn = -63453;
    @EffectBehavior protected final int behavior;
    protected boolean instaEffect;

    public static final Player MAGIC = new Player("MAGIC").setClazz(Main.SPY.newInstance()).setKingdom(Kingdom.UNKNOWN).setField(666);

    public Effect(int name, int description, int turnsDuration, int icon) {
        this.name = name;
        this.description = description;
        this.turnsDuration = turnsDuration;
        this.currentUsages = turnsDuration;
        this.icon = icon;
        this.behavior = getBehavior();
    }

    protected Effect(Parcel in) {
        name = in.readInt();
        description = in.readInt();
        icon = in.readInt();
        turnsDuration = in.readInt();
        currentUsages = in.readInt();
        applyTurn = in.readInt();
        instaEffect = in.readByte() != 0;
        behavior = in.readInt();
    }

    public static Effect random()
    {
        return EFX.get(new Random().nextInt(EFX.size()));
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(name);
        dest.writeInt(description);
        dest.writeInt(icon);
        dest.writeInt(turnsDuration);
        dest.writeInt(currentUsages);
        dest.writeInt(applyTurn);
        dest.writeByte((byte) (instaEffect ? 1 : 0));
        dest.writeInt(behavior);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public abstract void apply(Player p, int turns);
    public abstract void consume();
    public abstract <T extends Effect> T newInstance();
    public abstract Parcelable.Creator getCreator();
    public abstract void antidote(Player p);
    @EffectBehavior protected abstract int getBehavior();
    public Effect base()
    {
        Main.EFX.add(this);
        Main.EFX_MAP.put(this.name, this);
        return this;
    }

    public boolean isInfinite()
    {
        return this.currentUsages < 0;
    }

    public int getName() {
        return name;
    }

    public int getDescription() {
        return description;
    }

    public int getTurnsDuration() {
        return turnsDuration;
    }

    public int getIcon() {
        return icon;
    }

    public int getCurrentUsages() {
        return isInstaEffect() ? currentUsages + 1: currentUsages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Effect effect = (Effect) o;
        return name == effect.name &&
                description == effect.description &&
                turnsDuration == effect.turnsDuration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, turnsDuration);
    }

    public boolean still(int turn) {
        return applyTurn <= turn && (isInfinite() || (isInstaEffect() && currentUsages <= 0 ? applyTurn == turn : currentUsages > 0));
    }

    public boolean isInstaEffect() {
        return instaEffect;
    }

    public String getNameAsString(Resources res)
    {
        return res.getString(this.name);
    }

    @IntDef({BENIGNE, MALIGNE})
    public @interface EffectBehavior{}

    public int getApplyTurn() {
        return applyTurn;
    }

    @Override
    public String toString() {
        return "Effect{" +
                this.getClass().getSimpleName() +
                ", currentUsages=" + currentUsages +
                ", applyTurn=" + applyTurn +
                ", behavior=" + behavior +
                ", instaEffect=" + instaEffect +
                '}';
    }
}
