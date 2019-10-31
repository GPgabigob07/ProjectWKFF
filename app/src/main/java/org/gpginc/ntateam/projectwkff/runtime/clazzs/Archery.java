package org.gpginc.ntateam.projectwkff.runtime.clazzs;

import android.os.Parcel;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Clazz;
import org.gpginc.ntateam.projectwkff.runtime.Main;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;

public class Archery extends Clazz
{

    public Archery() {
        super(R.string.clazz_archery, R.drawable.ic_clazz_archery, Rarity.RARE);
    }

    protected Archery(Parcel in) {
        super(in);
    }

    @Override
    public Clazz newInstance() {
        return new Archery()
                .bindSkill(Main.ATTACK_ARCHERY)
                .bindSkill(Main.NULL_ATTACK);
    }
    public static final Creator<Archery> CREATOR = new Creator<Archery>() {
        @Override
        public Archery createFromParcel(Parcel in) {
            return new Archery(in);
        }

        @Override
        public Archery[] newArray(int size) {
            return new Archery[size];
        }
    };
    @Override
    public Creator getCreator() {
        return CREATOR;
    }
}
