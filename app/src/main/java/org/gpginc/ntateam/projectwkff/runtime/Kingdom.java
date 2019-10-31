package org.gpginc.ntateam.projectwkff.runtime;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import org.gpginc.ntateam.projectwkff.R;

import java.io.Serializable;

public enum Kingdom implements Serializable
{
    OHXER(R.string.kingdom_ohxer, R.drawable.ohxer_emblem),
    CAMELOT(R.string.kingdom_camelot, R.drawable.camelot_emblem),
    UNKNOWN(R.string.kingdom_unkown, R.drawable.unkown_e);

    @StringRes private final int name;
    @DrawableRes private final int icon;

    Kingdom(int name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public int getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }
    public static Kingdom withName(String name)
    {
        for(Kingdom k : values())if(k.name().equals(name))return k;
        return null;
    }
}
