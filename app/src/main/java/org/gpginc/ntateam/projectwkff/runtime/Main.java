package org.gpginc.ntateam.projectwkff.runtime;

import static org.gpginc.ntateam.projectwkff.GameFlux.LOG;

import androidx.annotation.NonNull;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.runtime.clazzs.Archery;
import org.gpginc.ntateam.projectwkff.runtime.clazzs.DragonHunter;
import org.gpginc.ntateam.projectwkff.runtime.clazzs.Lancer;
import org.gpginc.ntateam.projectwkff.runtime.clazzs.Spy;
import org.gpginc.ntateam.projectwkff.runtime.clazzs.SupportADC;
import org.gpginc.ntateam.projectwkff.runtime.clazzs.Supreme;
import org.gpginc.ntateam.projectwkff.runtime.clazzs.Swordman;
import org.gpginc.ntateam.projectwkff.runtime.dragons.events.BaseDragonEvent;
import org.gpginc.ntateam.projectwkff.runtime.dragons.events.DragonBorn;
import org.gpginc.ntateam.projectwkff.runtime.dragons.skills.BaseDragonSkill;
import org.gpginc.ntateam.projectwkff.runtime.dragons.skills.DragonAttack;
import org.gpginc.ntateam.projectwkff.runtime.effects.AuraShell;
import org.gpginc.ntateam.projectwkff.runtime.effects.Blindness;
import org.gpginc.ntateam.projectwkff.runtime.effects.Poison;
import org.gpginc.ntateam.projectwkff.runtime.effects.Regeneration;
import org.gpginc.ntateam.projectwkff.runtime.effects.Reincarnation;
import org.gpginc.ntateam.projectwkff.runtime.effects.Stun;
import org.gpginc.ntateam.projectwkff.runtime.events.Alchemist;
import org.gpginc.ntateam.projectwkff.runtime.events.Bodyguard;
import org.gpginc.ntateam.projectwkff.runtime.events.DarkWitch;
import org.gpginc.ntateam.projectwkff.runtime.events.DefeatSupreme;
import org.gpginc.ntateam.projectwkff.runtime.events.GodIsAmongUs;
import org.gpginc.ntateam.projectwkff.runtime.events.KillingSpree;
import org.gpginc.ntateam.projectwkff.runtime.skills.AttackArchery;
import org.gpginc.ntateam.projectwkff.runtime.skills.AttackLancer;
import org.gpginc.ntateam.projectwkff.runtime.skills.AttackSwordsman;
import org.gpginc.ntateam.projectwkff.runtime.skills.ChangePosition;
import org.gpginc.ntateam.projectwkff.runtime.skills.GivenProtection;
import org.gpginc.ntateam.projectwkff.runtime.skills.Infectious;
import org.gpginc.ntateam.projectwkff.runtime.skills.NullDirectAttack;
import org.gpginc.ntateam.projectwkff.runtime.skills.Reposition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main
{
    public static final List<Clazz> CLAZZ_LIST;
    public static final List<ClazzSkill> SKILLS;
    public static final Map<Integer, ClazzSkill> SKILL_MAP;
    public static final Map<Integer, Clazz> CLAZZ_MAP;
    public static final Map<Integer, Effect> EFX_MAP;

    public static final Random random = new Random();
    public static final String SETTINGS = "SHARED20%PREF_APPSETT_UNCOM_AS_DP_GPGINC20%PROVIDER";
    public static final List<Event> EVENT_LIST;
    public static final ArrayList<Effect> EFX;



    /**
     * Classes Declaration
     */
    public static  Clazz ARCHERY; //Arqueiro
    public static  Clazz SWORDMAN; //Espadachim
    public static  Clazz LANCER; //Lanceiro
    public static  Clazz SUPREME; //Mago supremo
    public static  Clazz SPY; // Espião
    public static  Clazz DRAGON_HUNTER; //Caçador de Dragões
    public static  Clazz ADC; // Feiticeiro

    /**
     * Skills declaration
     */
    public final static ClazzSkill ATTACK_ARCHERY;
    public static final ClazzSkill ATTACK_SWORDMAN;
    public static final ClazzSkill ATTACK_LANCER;
    public static final ClazzSkill GIVEN_PROTECTION;
    public static final ClazzSkill CHANGE_POSITION;
    public static final ClazzSkill NULL_ATTACK;
    public static final ClazzSkill REPOSITION;

    /**
     * Special Skills
     */
    public static final ClazzSkill INFECTIOUS;

    /**
     * Dragons Skills
     */
    public static final BaseDragonSkill DRAGON_ATTACK;
    /**
     * Events Declaration
     */
    public static final Event DEFEAT_SUPREME;
    public static final Event KILLING_SPREE;
    public static final Event BODYGUARD;
    public static final Event DRAGONBORN;
    public static final Event DARK_WITCH;
    public static final Event GOD_IS_AMONG_US;
    public static final Alchemist ALCHEMIST;

    /**
     * Effects Declaration
     */
    public static final Effect POISON;
    public static final Effect STUN;
    public static final Effect REGEN;
    public static final Effect AURA_SHELL;
    public static final Effect REINCARNATION;
    public static final Effect BLINDNESS;

    static {
        CLAZZ_LIST = new ArrayList<>();
        SKILLS = new ArrayList<>();
        SKILL_MAP = new HashMap<>();
        CLAZZ_MAP = new HashMap<>();
        EFX_MAP = new HashMap<>();
        EVENT_LIST = new ArrayList<>();
        EFX = new ArrayList<>();

        ARCHERY = new Archery().base();
        SWORDMAN = new Swordman().base();
        LANCER = new Lancer().base();
        SUPREME = new Supreme().base();
        SPY = new Spy().base();
        DRAGON_HUNTER = new DragonHunter().base();
        ADC = new SupportADC().base();

        ATTACK_ARCHERY = new AttackArchery().base();
        ATTACK_SWORDMAN = new AttackSwordsman().base();
        ATTACK_LANCER = new AttackLancer().base();
        GIVEN_PROTECTION = new GivenProtection().base();
        CHANGE_POSITION = new ChangePosition().base();
        NULL_ATTACK = new NullDirectAttack().base();
        REPOSITION = new Reposition().base();
        /*----------*/
        INFECTIOUS = new Infectious().base();
        /*----------*/
        DRAGON_ATTACK = new DragonAttack().base();
        /*----------*/

        DEFEAT_SUPREME = new DefeatSupreme().base();
        KILLING_SPREE = new KillingSpree().base();
        BODYGUARD = new Bodyguard().base();
        //DRAGONBORN = new Dragonborn().base();
        DARK_WITCH = new DarkWitch().base();
        GOD_IS_AMONG_US = new GodIsAmongUs().base();
        ALCHEMIST = new Alchemist().base();
        DRAGONBORN = new DragonBorn().base();

        POISON = new Poison().base();
        STUN = new Stun().base();
        REGEN = new Regeneration().base();
        BLINDNESS = new Blindness().base();
        AURA_SHELL = new AuraShell().base();
        REINCARNATION = new Reincarnation().base();
    }

    public static void prepare(@NonNull final List<Player> players, final List<Event> events)
    {

        List<Player> gone = new ArrayList<>();

        int k = random.nextInt(2);
        Kingdom K = Kingdom.values()[k];
        int i, cr;
        Clazz c;
        int msc = 0;
        if(players.size() % 2 !=0)
        {
            i = random.nextInt(players.size());
            setupPlayer(players.get(i), Kingdom.UNKNOWN, SPY, 5);
            gone.add(players.get(i));
        }
        do {
            int aux = 0;
            do {
                cr = random.nextInt(100);
                c = CLAZZ_LIST.get(random.nextInt(CLAZZ_LIST.size()));
                aux++;
                if(aux >= 20 && c instanceof Supreme) aux=10;
            }while(!isClazzAcceptable(c, cr) && aux <=20);
            do{
                i = random.nextInt(players.size());
            }while(gone.contains(players.get(i)));

            if(c instanceof DragonHunter && players.stream().filter(player -> player.getClazz() instanceof DragonHunter).count() < 2)
            {
                setupPlayer(players.get(i), K, msc < 2 ? SUPREME : c, random.nextInt(4));
                gone.add(players.get(i));
                K = K.equals(Kingdom.OHXER) ? Kingdom.CAMELOT : Kingdom.OHXER;
                msc++;
            }
            else if(c instanceof DragonHunter && players.stream().filter(player -> player.getClazz() instanceof DragonHunter).count() ==2);
            else
            {
                setupPlayer(players.get(i), K, msc < 2 ? SUPREME : c, random.nextInt(4));
                gone.add(players.get(i));
                K = K.equals(Kingdom.OHXER) ? Kingdom.CAMELOT : Kingdom.OHXER;
                msc++;
            }
        }while(gone.size()!= players.size());

    }

    private static void setupPlayer(Player p, Kingdom k, Clazz c, int field)
    {

        p.setField(field).setKingdom(k).setClazz(c.newInstance());
        LOG.wtf("MAIN BOOT", "PLAYER: " + p.getName() + " f: " + field + " K: " + k.name() + " c: " + c.getClass().getSimpleName());
    }

    private static boolean isClazzAcceptable(Clazz c, int rar)
    {
        return c.getRarity().getPercent() >= rar && c.isEnabled;
    }

    private static  boolean isEvtAcceptable(Event evt, int rar, List<Event> concurrent)
    {
        int c = 0;
        for (Event e : concurrent)
        {
            if(e.getName()==evt.getName())++c;
        }
        return (evt.getRarity().getPercent() >= rar && evt.getMax() > c) && evt.isEnabled;
    }

    public static void bootEvents(GameFlux res){
        int evtCOunt = 0;
        res.EVENTS.add(DEFEAT_SUPREME);
        int maxEvtCount = ((res.PLAYERS.size() > 4)? res.PLAYERS.size()-3 > EVENT_LIST.size() ? EVENT_LIST.size() - 1 : res.PLAYERS.size()-3 :1 );
        if(EVENT_LIST.stream().filter(evt -> evt.isEnabled).count() < 3)
        {
            maxEvtCount = 2;
        }
        while(evtCOunt < maxEvtCount)
        {
            int rar = random.nextInt(100);
            Event evt = EVENT_LIST.get(random.nextInt(EVENT_LIST.size()));
            if(isEvtAcceptable(evt, rar, res.EVENTS)) {
                if(evt.needPlayers)
                {
                    Player p1;
                    Player p2;
                    int flag;
                    do
                    {
                        flag = 0;
                        p1 = res.PLAYERS.get(random.nextInt(res.PLAYERS.size()));
                        p2 = res.PLAYERS.get(random.nextInt(res.PLAYERS.size()));
                        if(evt instanceof KillingSpree)
                        {
                            if(p2.getClazz().equals(SPY) || p2.getClazz().equals(ADC) || p2.getClazz().equals(SUPREME))flag=1;
                        }
                    } while ((p1==p2 || p1.attachedToEvent || p2.attachedToEvent || p2.getClazz().equals(SUPREME)) && (flag ==0));
                    evt = evt.newInstance(p2, p1);
                }
                else if(evt.isAttacher)
                {
                    Player p1;
                    do
                    {
                        p1 = res.PLAYERS.get(random.nextInt(res.PLAYERS.size()));
                    } while (p1.attachedToEvent);
                    evt = evt.newInstance(p1);
                }
                else if(evt instanceof BaseDragonEvent)
                {
                    evt = ((BaseDragonEvent)evt).newInstance(res);
                }
                res.EVENTS.add(evt);
                ++evtCOunt;
            }
        }
    }
}
