package org.gpginc.ntateam.projectwkff.runtime.clazzs;

import android.os.Parcel;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Clazz;
import org.gpginc.ntateam.projectwkff.runtime.Main;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;

public class Lancer extends Clazz
{

    public Lancer() {
        super(R.string.clazz_lancer, R.drawable.ic_clazz_lancer, Rarity.COMMON);
    }

    protected Lancer(Parcel in) {
        super(in);
    }

    @Override
    public Clazz newInstance() {
        return new Lancer()
                .bindSkill(Main.ATTACK_LANCER)
                .bindSkill(Main.CHANGE_POSITION);
    }
    public static final Creator<Lancer> CREATOR = new Creator<Lancer>() {
        @Override
        public Lancer createFromParcel(Parcel in) {
            return new Lancer(in);
        }

        @Override
        public Lancer[] newArray(int size) {
            return new Lancer[size];
        }
    };
    @Override
    public Creator getCreator() {
        return CREATOR;
    }
}