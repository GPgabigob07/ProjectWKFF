package org.gpginc.ntateam.projectwkff.runtime.clazzs;

import android.os.Parcel;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Clazz;
import org.gpginc.ntateam.projectwkff.runtime.Main;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;

public class Swordman extends Clazz
{

    public Swordman() {
        super(R.string.clazz_swordman, R.drawable.unkown_e, Rarity.COMMON);
    }

    protected Swordman(Parcel in) {
        super(in);
    }

    @Override
    public Clazz newInstance() {
        return new Swordman()
                .bindSkill(Main.ATTACK_SWORDMAN)
                .bindSkill(Main.CHANGE_POSITION);
    }
    public static final Creator<Swordman> CREATOR = new Creator<Swordman>() {
        @Override
        public Swordman createFromParcel(Parcel in) {
            return new Swordman(in);
        }

        @Override
        public Swordman[] newArray(int size) {
            return new Swordman[size];
        }
    };

    @Override
    public Creator getCreator() {
        return CREATOR;
    }
}