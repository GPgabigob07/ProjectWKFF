package org.gpginc.ntateam.projectwkff.runtime.util;

import android.util.Log;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.ClazzSkill;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.dragons.Dragon;
import org.gpginc.ntateam.projectwkff.runtime.dragons.skills.BaseDragonSkill;
import org.gpginc.ntateam.projectwkff.ui.fragments.BaseFluxFrag;
import org.gpginc.ntateam.projectwkff.ui.widget.adapters.PlayerSelectionAdapter;
import org.gpginc.ntateam.projectwkff.ui.widget.dialogs.MessageDialog;

import java.util.ArrayList;
import java.util.List;

import static org.gpginc.ntateam.projectwkff.ui.widget.dialogs.MessageDialog.*;

public class SkillUtils
{
    public static PlayerSelectionAdapter getAdapterFor(Player provider, List<Player> players,int muchSelectable, boolean showNames, int... fields)
    {
        List<Player> player = new ArrayList<>();
        for(Integer i : fields)
        {
            for(Player p : players)
            {
                if(!p.equals(provider))if(p.getField() == i)
                {
                    player.add(p);
                    Log.w(SkillUtils.class.getName() + " using getAdapterFor()","DATA {provider="+provider.toString()+"; selectable="+muchSelectable+"; shoNames="+showNames+"; fields="+fields.toString()
                            +"\n FOUND: "+p.toString()+"\n-*-*-*-*-*-*-*-*-*-**-*-*-*--***-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
                }
            }
        }
        return new PlayerSelectionAdapter(muchSelectable, showNames).setPlayers(player);
    }

    public static ClazzSkill formUpControlForDragon(BaseDragonSkill skill, Dragon dragon)
    {
        BaseDragonSkill sk = skill.newInstance();
        sk.setDragon(dragon);
        return sk;
    }

    public static void manageDragonInteraction(Dragon d, @Dragon.DragonInteraction int flag, BaseFluxFrag res)
    {
        d.setRes(res.RES);
        Display.InheritedDialog dialog = new Display.InheritedDialog();
        switch(flag)
        {
            case Dragon.SUCCESS:
                dialog.setMsg(R.string.dragonattacksuccess);
                break;
            case Dragon.SLEEPINESS:
                dialog.setMsg(R.string.dragonsleeping);
                break;
            case Dragon.PROTECTING:
                dialog.setMsg(R.string.dragonprotecting);
                break;
            case Dragon.BOTH:
                dialog.setMsg(R.string.dragonoccupied);
                d.setBehaviour(Dragon.BEHAVIOUR_HOSTILE);
                d.IA.setMarkTotalHostile(true);
                break;
            case Dragon.ALREADY_ATTACKED:
                dialog.setMsg(R.string.dragonalreadyattack);
                break;
            case Dragon.FAILED:
                dialog.setMsg(R.string.dattackfailed);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + flag);
        }
        new Display(res.RES).from(dialog);

    }

}
