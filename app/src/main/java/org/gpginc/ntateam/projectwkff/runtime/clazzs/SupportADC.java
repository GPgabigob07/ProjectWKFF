package org.gpginc.ntateam.projectwkff.runtime.clazzs;

import android.os.Parcel;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Clazz;
import org.gpginc.ntateam.projectwkff.runtime.Main;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;

public class SupportADC extends Clazz
{

    public SupportADC() {
        super(R.string.clazz_supportadc, R.drawable.unkown_e, Rarity.ULTRARARE);
    }

    protected SupportADC(Parcel in) {
        super(in);
    }

    @Override
    public Clazz newInstance() {
        return new SupportADC()
                .bindSkill(Main.GIVEN_PROTECTION);
    }
    public static final Creator<SupportADC> CREATOR = new Creator<SupportADC>() {
        @Override
        public SupportADC createFromParcel(Parcel in) {
            return new SupportADC(in);
        }

        @Override
        public SupportADC[] newArray(int size) {
            return new SupportADC[size];
        }
    };
    @Override
    public Creator getCreator() {
        return CREATOR;
    }
}   