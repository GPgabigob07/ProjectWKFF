package org.gpginc.ntateam.projectwkff.runtime.util.enums;

import java.io.Serializable;

public enum Rarity implements Serializable
    {
    ALWAYS(-1),
    COMMON(100),
    RARE(35),
    ULTRARARE(15),
    MASTERRARE(1);


    public static final Rarity[] RARITIES = {COMMON, RARE, ULTRARARE, MASTERRARE};

    private final int p100t;

    Rarity(int  percent) {
        this.p100t = percent;
    }

    public int getPercent() {
        return p100t;
    }

    public static Rarity withName(String name)
    {
        for(Rarity r : RARITIES)
        {
            if(r.R().equals(name)) return r;
        }
        return Rarity.ALWAYS;
    }

    public String R()
    {
        return this.name();
    }
}
