package org.gpginc.ntateam.projectwkff.runtime.clazzs;

import android.os.Parcel;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Clazz;
import org.gpginc.ntateam.projectwkff.runtime.Main;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;

public class Supreme extends Clazz
{
    public Supreme() {
        super(R.string.clazz_supreme, R.drawable.unkown_e, Rarity.ALWAYS);
    }

    protected Supreme(Parcel in) {
        super(in);
    }

    @Override
    public Clazz newInstance() {
        return new Supreme()
                .bindSkill(Main.CHANGE_POSITION)
                .bindSkill(Main.REPOSITION);
    }
    public static final Creator<Supreme> CREATOR = new Creator<Supreme>() {
        @Override
        public Supreme createFromParcel(Parcel in) {
            return new Supreme(in);
        }

        @Override
        public Supreme[] newArray(int size) {
            return new Supreme[size];
        }
    };
    @Override
    public Creator getCreator() {
        return CREATOR;
    }
}