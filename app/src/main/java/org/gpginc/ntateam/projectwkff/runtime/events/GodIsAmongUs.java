package org.gpginc.ntateam.projectwkff.runtime.events;

import android.os.Parcel;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.ClazzSkill;
import org.gpginc.ntateam.projectwkff.runtime.Event;
import org.gpginc.ntateam.projectwkff.runtime.Main;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.dragons.skills.BaseDragonSkill;
import org.gpginc.ntateam.projectwkff.runtime.util.Util;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;

import java.util.Random;

public class GodIsAmongUs extends Event  {

    
    public GodIsAmongUs() {
        super(R.string.evt_name_giau, R.string.evt_descr_giau, R.string.evt_end_descr_giau, Rarity.ULTRARARE, 1, EventHandler.ALWAYS);
        super.setAttacher();
    }

    public GodIsAmongUs(Parcel in) {
        super(in);
        super.setAttacher();
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    @Override
    public Event newInstance() {
        return null;
    }

    @Override
    public Event newInstance(Player target, Player owner) {
        return null;
    }

    @Override
    public Event newInstance(Player owner) {
        int l = new Random().nextInt(Main.SKILLS.size()-2);
        for(int i = 0; i < (l > 3 ? l : 3); i++)
        {
            ClazzSkill sk = null;
            do
            {
                 sk = Main.SKILLS.get(new Random().nextInt(Main.SKILLS.size()));
            }while(sk instanceof BaseDragonSkill | Util.findInList_instance(owner.getClazz().getSkills(), sk) );
            owner.getClazz().bindSkill(sk);
        }
        return new GodIsAmongUs().setOwner(owner);
    }

    @Override
    public boolean check(Player p)
    {
        return p.equals(this.owner);
    }

    @Override
    public void exe(Player p, GameFlux res)
    {
        if(p.equals(this.owner))
        if(p.getTurnAttacked(res.currentTurn))
        {
            p.setProtected(true);
        }
        else
        {
            p.clean();
        }
    }

    public static final Creator<GodIsAmongUs> CREATOR = new Creator<GodIsAmongUs>()
    {
        @Override
        public GodIsAmongUs createFromParcel(Parcel source) {
            return new GodIsAmongUs(source);
        }

        @Override
        public GodIsAmongUs[] newArray(int size) {
            return new GodIsAmongUs[size];
        }
    };
}
