package org.gpginc.ntateam.projectwkff.runtime;

import android.os.Parcel;
import android.os.Parcelable;

import org.gpginc.ntateam.projectwkff.runtime.dragons.Dragon;

/**
 * BaseAttacker have one simple propose:
 * switch around {@link Player} and {@link Dragon} when writing a Player through a parcel.
 * Where a Player can be attacked from another player or / and some Dragon.
 */
public abstract class BaseAttacker implements Parcelable
{
    public static final int PLAYER = 0;
    public static final int DRAGON = 1;

    protected int typeApplied;

    public BaseAttacker(int typeApplied) {
        this.typeApplied = typeApplied;
    }

    protected BaseAttacker() {
    }

    public static Parcelable.Creator getCreator(int typeApplied)
    {
        switch(typeApplied){
            case PLAYER:return Player.CREATOR;
            case DRAGON:return Dragon.CREATOR;
            default: return null;
        }
    }

    protected void writeProperties(Parcel dest, int flags)
    {
        dest.writeInt(typeApplied);
    }
    protected void readProperties(Parcel in)
    {
        typeApplied = in.readInt();
    }

    public String getRelativeName()
    {
        return this.typeApplied == PLAYER
               ? ((Player)this).getName()
               : ((Dragon)this).getNameAsString();
    }
    public Kingdom getRelativeKingdom()
    {
        return this.typeApplied == PLAYER
               ? ((Player)this).getKingdom()
               : ((Dragon)this).getKingdom();
    }
}
