package org.gpginc.ntateam.projectwkff.runtime.util;

import android.content.res.Resources;
import android.os.Bundle;
import static org.gpginc.ntateam.projectwkff.GameFlux.LOG;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.databinding.BindingAdapter;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.map.LinkedMap;
import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.BaseAttacker;
import org.gpginc.ntateam.projectwkff.runtime.Effect;
import org.gpginc.ntateam.projectwkff.runtime.Event;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.dragons.Dragon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util
{

    public static Bundle getGameEnd(Event ender, GameFlux res)
    {
       // res.PLAYERS.stream().filter(player -> player.attacked).forEach(player -> player.clean());
        Bundle output = new Bundle();
        res.ENDEVENT = ender;
        output.putParcelableArrayList("PLAYERS", res.PLAYERS);
        output.putParcelable("event", ender);
        return output;
    }
    public static int getFieldFor(Player p)
    {
        switch (p.getField())
        {
            case 1:
                return R.drawable.field_1;
            case 2:
                return R.drawable.field_2;
            case 3:
                return R.drawable.field_3;
            case 4:
                return R.drawable.field_4;
            default:
                return R.drawable.unkown_e;
        }
    }
    @DrawableRes
    public static int getKindomFor(Player p)
    {
        switch (p.getKingdom())
        {
            case CAMELOT:
                return R.drawable.camelot_emblem;
            case OHXER:
                return R.drawable.ohxer_emblem;
            default:
                return R.drawable.unkown_e;
        }
    }
    public static boolean findInList_instance(Collection<?> list , Object val)
    {
        for (Object o : list) {
            if(o.getClass().equals(val.getClass())) return true;
        }
        return false;
    }
    @StringRes
    public static int getDeadInfoFor(Player p)
    {
        if(p.isDead)
        {
            if (p.getLastAttacker() instanceof Dragon) return R.string.died_by_dragon;
            else if(p.getLastAttacker() == Effect.MAGIC) return R.string.died_by_magic;
            else if (p.life() == 0) {
                return R.string.dead_info_1;
            } else if (p.life() < 0) {
                return R.string.dead_info_2;
            }
        }
        return R.string.bug;
    }


    public static List<Event> getEventsForPlayer(List<Event> concrurrent, Player p)
    {
        List<Event> evts = new ArrayList<>();
        for(Event e : concrurrent)
        {
            if(!e.isAttacher && !e.needPlayers)evts.add(e);
            else if(e.isAttacher && e.getOwner().equals(p))evts.add(e);
            else if(e.needPlayers && e.getOwner().equals(p))evts.add(e);
            //else LOG.v("", "ERROR!! EVENT DISCREPANCY!!!!!!!!!!!!");
        }
        return concrurrent;
    }
    @DrawableRes
    public static int getPlayerLifeShowner(Player p)
    {
        switch(p.life())
        {
            case 1:
                return (p.isDragonProtected || p.isProtected) ? R.drawable.player_1_protected_life : R.drawable.player_1_life;
            case 2:
                return (p.isDragonProtected || p.isProtected) ? R.drawable.player_2_protected_life : R.drawable.player_2_life;
            case 3:
                return (p.isDragonProtected || p.isProtected) ? R.drawable.player_full_protected_life : R.drawable.player_full_life;
        }
        return R.drawable.unkown_e;
    }
    public static String getCrypto(String s)
    {
        String[] sp = s.split("");
        StringBuilder output = new StringBuilder();
        for(String crypted : sp)
        {
            output.append(crypt(crypted));
        }
        LOG.w("CRYPTING SERVICE:", "TYPO={"+s+"}; Param={3-3}; SupOut={"+output.toString()+"} serving out; Time 0ms;");
        return output.toString();
    }
    public static String getDecrypt(String s)
    {
        String[] sp = s.split("");
        StringBuilder output = new StringBuilder();
        for(String crypted : sp)
        {
            output.append(decrypt(crypted));
        }
        return output.toString();
    }


    static String crypt(String s)
    {
        List<String> alpha_minos = Arrays.asList("abcdefghijklmnopqrstuvwxyz".split(""));
        List<String> alpha_MAJOR = Arrays.asList("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split(""));
        Map<String, String> intRegex = new HashMap<>();
        /*map init*/
        intRegex.put("0", "§-i§");
        intRegex.put("1", "§i§");
        intRegex.put("2", "§ii§");
        intRegex.put("3", "§iii§");
        intRegex.put("4", "§iv§");
        intRegex.put("5", "§v§");
        intRegex.put("6", "§vi§");
        intRegex.put("7", "§vii§");
        intRegex.put("8", "§viii§");
        intRegex.put("9", "§ix§");
        /*map end*/
        if(alpha_minos.contains(s))
        {
            if(alpha_minos.indexOf(s) > alpha_minos.size() -3)
            {
                return alpha_minos.get(alpha_minos.indexOf(s) + 3 - 26);
            } else return alpha_minos.get(alpha_minos.indexOf(s) + 3);
        } else if (alpha_MAJOR.contains(s))
        {
            if(alpha_MAJOR.indexOf(s) > alpha_MAJOR.size() -3)
            {
                return alpha_MAJOR.get(alpha_MAJOR.indexOf(s) + 3 - 26);
            } else return alpha_MAJOR.get(alpha_MAJOR.indexOf(s) + 3);

        } else if (intRegex.containsKey(s))
        {
            return intRegex.get(s);
        }else return s;
    }
    static String decrypt(String s)
    {
        List<String> alpha_minos = Arrays.asList("abcdefghijklmnopqrstuvwxyz".split(""));
        List<String> alpha_MAJOR = Arrays.asList("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split(""));
        Map<String, String> intRegex = new HashMap<>();
        /*map init*/
        intRegex.put("0", "§-i§");
        intRegex.put("1", "§i§");
        intRegex.put("2", "§ii§");
        intRegex.put("3", "§iii§");
        intRegex.put("4", "§iv§");
        intRegex.put("5", "§v§");
        intRegex.put("6", "§vi§");
        intRegex.put("7", "§vii§");
        intRegex.put("8", "§viii§");
        intRegex.put("9", "§ix§");
        /*map end*/
        if(alpha_minos.contains(s))
        {
            if(alpha_minos.indexOf(s) < alpha_minos.size() +3)
            {
                return alpha_minos.get(alpha_minos.indexOf(s) - 3 + 26);
            } else return alpha_minos.get(alpha_minos.indexOf(s) - 3);
        } else if (alpha_MAJOR.contains(s))
        {
            if(alpha_MAJOR.indexOf(s) < alpha_MAJOR.size() +3)
            {
                return alpha_MAJOR.get(alpha_MAJOR.indexOf(s) - 3 + 26);
            } else return alpha_MAJOR.get(alpha_MAJOR.indexOf(s) - 3);

        } else if (intRegex.containsKey(s))
        {
            return intRegex.get(s);
        }else return s;
    }

    public static String[] settingsDefault()
    {
        return new String[]{
                getCrypto("//-SETTINGS-FILE--DO--NOT--MODIFY-//"),
                getCrypto("-|§BOOL§autoLoadSavedPlayers→0§----|"),
                getCrypto("-|§BOOL§checkServer→0§-------------|"),
                getCrypto("-|§CLASS§ARCHERY→1§----------------|"),
                getCrypto("-|§CLASS§SWORDMAN→1§---------------|"),
                getCrypto("-|§CLASS§LANCER→1§-----------------|"),
                getCrypto("-|§CLASS§DRAGONH→1§----------------|"),
                getCrypto("-|§CLASS§SUPPORT→1§----------------|"),
                getCrypto("-|§CLASS§SPY→1§--------------------|"),
                getCrypto("//--------------------------------//")

        };
    }

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView v, int resId)
    {
        v.setImageResource(resId);
    }
    @BindingAdapter("android:text")
    public static void setTextResource(TextView v, int resId)
    {
        v.setText(resId);
    }

    @BindingAdapter("android:text")
    public static void setTextResource(TextView v, String resId)
    {
        v.setText(resId);
    }

    public static List<String> getNames(List<Effect> effects, Resources res)
    {
        List<String> S = new ArrayList<>();
        for (Effect effect : effects) {
            String string = res.getString(effect.getName());
            S.add(string);
        }
        return S;
    }

    public static int getMaxConcentrationOfEnemies(BaseAttacker dragon, GameFlux res)
    {
        int[] count = {0,0,0,0};
        res.PLAYERS.stream().filter(p -> p.isEnemyFrom(dragon)).forEach(p -> count[p.getField()]++);
        int outval = 0;
        for (int value : count) {
            outval = outval < value ? value : outval;
        }
        return outval;
    }

    public static <K, V> LinkedMap<K, V> reverseMap(LinkedMap<K, V> map)
    {
        LinkedMap<K, V> out = new LinkedMap<K, V>();
        for (int i = map.size() -1; i >=0; i--) {
            out.put((K) map.keySet().toArray()[i], (V) map.values().toArray()[i]);
        }
        return out;
    }
}
