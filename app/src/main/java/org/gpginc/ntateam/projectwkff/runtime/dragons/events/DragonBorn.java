package org.gpginc.ntateam.projectwkff.runtime.dragons.events;

import android.os.Parcel;

import androidx.annotation.Nullable;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.Main;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.dragons.Dragon;
import org.gpginc.ntateam.projectwkff.runtime.dragons.DragonHandler;
import org.gpginc.ntateam.projectwkff.runtime.util.SkillUtils;
import org.gpginc.ntateam.projectwkff.runtime.util.Util;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;
import static org.gpginc.ntateam.projectwkff.runtime.dragons.Dragon.*;
import java.util.Collection;
import java.util.Optional;

public class DragonBorn extends BaseDragonEvent {

    protected String dragonName;

    public DragonBorn() {
        super(R.string.evt_name_db, R.string.evt_dscr_db, R.string.evt_end_dscr_db, Rarity.ULTRARARE, 2, EventHandler.ALWAYS);
    }

    public DragonBorn(Parcel in) {
        super(in);
        dragonName = in.readString();
    }

    @Override
    public void setup_fromList(Collection<Player> players) {

    }

    @Override
    public void setup()
    {
        if(this.owner!=null)
        {
            ((Dragon) this.owner).randomBehavior();
        }
    }

    @Override
    public void setup_fromRuntime(GameFlux res) {

        if(res.EVENTS.stream().filter(evt -> evt instanceof  DragonBorn).count() <= 1) {
            if (res.PLAYERS.stream().anyMatch(player -> player.getClazz().equals(Main.DRAGON_HUNTER))) {
                if (res.PLAYERS.stream().filter(player -> player.getClazz().equals(Main.DRAGON_HUNTER)).count() == 1) {
                    Optional<Player> op = res.PLAYERS.stream().filter(player -> player.getClazz().equals(Main.DRAGON_HUNTER)).findAny();
                    if (op.isPresent() && !op.get().attachedToEvent) {
                        ((Dragon)this.owner).setDragonHunter(op.get());
                        setCTarget(op.get(), res.DRAGONS.get(res.DRAGONS.size() - 1));
                    }
                } else if (res.PLAYERS.stream().filter(player -> player.getClazz().equals(Main.DRAGON_HUNTER)).count() > 1) {
                    final DragonBorn secBorn = new DragonBorn();
                    int aux = 0;
                    for (Player player : res.PLAYERS) {
                        if (player.getClazz().equals(Main.DRAGON_HUNTER)) {
                            if (aux == 0) {
                                ((Dragon) owner).setDragonHunter(player);
                                player.setAttachedToEvent();
                                setCTarget(player, res.DRAGONS.get(res.DRAGONS.size() - 1));
                                aux++;
                            } else {
                                secBorn.newInstance(res, null);
                                secBorn.getDragon().setDragonHunter(player);
                                secBorn.setCTarget(player, res.DRAGONS.get(res.DRAGONS.size() - 1));
                                player.setAttachedToEvent();
                            }
                        }
                    }
                    res.EVENTS.add(secBorn);
                }
            } else setup();
        }
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }



    @Override
    public boolean check(Player p) {

        switch (((Dragon) this.owner).getBehavior())
        {
            case BEHAVIOUR_HONOR:
                return (!p.isEnemyFrom(this.owner) && (p.getDamageTaken() > 2 || p.life() <= 1)) || p.isEnemyFrom(this.owner) && ((Dragon) this.owner).IA.isMarkHonorPatienceZero();
            case BEHAVIOUR_AGGRESSIVE:
                return (!p.isEnemyFrom(this.owner) && (p.getDamageTaken() > 1 || p.life() < 3)) || p.isEnemyFrom(this.owner) && ((Dragon) this.owner).IA.isMarkAggressiveAttack();
            case BEHAVIOUR_PROTECTOR:
                return (!p.isEnemyFrom(this.owner) && (p.getDamageTaken() > 0 || p.life() < 3));
            case BEHAVIOUR_HOSTILE:
                return (p.isEnemyFrom(this.owner)) || !p.isEnemyFrom(this.owner) && ((Dragon) this.owner).IA.isMarkTotalHostile();
            default:
                throw new IllegalStateException("Unexpected value: " + ((Dragon) this.owner).getBehavior());
        }
    }

    @Override
    public void exe(Player p, GameFlux res)
    {
        switch (((Dragon) this.owner).getBehavior())
        {
            case BEHAVIOUR_HONOR:
                if(res.PLAYERS.stream().filter(pp -> pp.life() <= 2 && !pp.isEnemyFrom(this.owner)).count() >= (res.PLAYERS.size() / 2) -1)((Dragon) this.owner).IA.setMarkHonorPatienceZero(true);
                if (p.isEnemyFrom(this.owner)) {
                    ((Dragon) this.owner).lightAttack(res, Util.getMaxConcentrationOfEnemies(this.owner, res));
                    return;
                } else
                {
                    ((Dragon) this.owner).protect(p);
                }
                break;
            case BEHAVIOUR_AGGRESSIVE:
                if (p.isEnemyFrom(this.owner)) {
                    ((Dragon) this.owner).lightAttack(res, Util.getMaxConcentrationOfEnemies(this.owner, res));
                    return;
                } else p.clean();p.setProtected(true);
                break;
            case BEHAVIOUR_PROTECTOR:
                ((Dragon) this.owner).protect(p);
                return;
            case BEHAVIOUR_HOSTILE:
                ((Dragon) this.owner).setRes(res);
                ((Dragon) this.owner).attack(p);
                return;
            default:
                throw new IllegalStateException("Unexpected value: " + ((Dragon) this.owner).getBehavior());
        }
    }
    public static final Creator CREATOR = new Creator<DragonBorn>()
    {
        @Override
        public DragonBorn createFromParcel(Parcel source) {
            return new DragonBorn(source);
        }

        @Override
        public DragonBorn[] newArray(int size) {
            return new DragonBorn[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(dragonName);
    }

    @Override
    public DragonBorn newInstance(GameFlux res) {
        DragonBorn d = new DragonBorn();
        d.bornDragons(res);
        d.handler = new DragonHandler(res, res.DRAGONS.get(res.DRAGONS.size() - 1));
        d.handler.init();
        d.setup_fromRuntime(res);
        return d;
    }


    private DragonBorn newInstance(GameFlux res, Nullable nullabl)
    {
        this.bornDragons(res);
        this.handler = new DragonHandler(res, res.DRAGONS.get(res.DRAGONS.size() - 1));
        this.handler.init();
        return this;
    }


    void setCTarget(Player t, Dragon d)
    {
        t.getClazz().forceBindSkill(SkillUtils.formUpControlForDragon(Main.DRAGON_ATTACK, d));
        setTarget(t);
    }
}
