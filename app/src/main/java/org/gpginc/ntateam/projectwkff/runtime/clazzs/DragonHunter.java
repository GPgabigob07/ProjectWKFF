package org.gpginc.ntateam.projectwkff.runtime.clazzs;

import android.os.Parcel;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Clazz;
import org.gpginc.ntateam.projectwkff.runtime.Main;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;

public class DragonHunter extends Clazz
{

    public DragonHunter() {
        super(R.string.clazz_dragon_hunter, R.drawable.unkown_e, Rarity.MASTERRARE);
    }

    protected DragonHunter(Parcel in) {
        super(in);
    }

    @Override
    public Clazz newInstance() {
        return new DragonHunter()
                .bindSkill(Main.CHANGE_POSITION);
    }
    public static final Creator<DragonHunter> CREATOR = new Creator<DragonHunter>() {
        @Override
        public DragonHunter createFromParcel(Parcel in) {
            return new DragonHunter(in);
        }

        @Override
        public DragonHunter[] newArray(int size) {
            return new DragonHunter[size];
        }
    };
    @Override
    public Creator getCreator() {
        return CREATOR;
    }
}
