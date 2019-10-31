package org.gpginc.ntateam.projectwkff.runtime.clazzs;

import android.os.Parcel;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Clazz;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;

public class Spy extends Clazz
{

    public Spy() {
        super(R.string.clazz_spy, R.drawable.unkown_e, Rarity.MASTERRARE);
    }

    protected Spy(Parcel in) {
        super(in);
    }

    @Override
    public Clazz newInstance() {
        return new Spy();
    }
    public static final Creator<Spy> CREATOR = new Creator<Spy>() {
        @Override
        public Spy createFromParcel(Parcel in) {
            return new Spy(in);
        }

        @Override
        public Spy[] newArray(int size) {
            return new Spy[size];
        }
    };
    @Override
    public Creator getCreator() {
        return CREATOR;
    }
}