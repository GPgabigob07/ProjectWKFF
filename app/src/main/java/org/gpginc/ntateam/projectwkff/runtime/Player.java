package org.gpginc.ntateam.projectwkff.runtime;

import android.os.Parcel;
import static org.gpginc.ntateam.projectwkff.GameFlux.LOG;

import androidx.annotation.Nullable;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.dragons.Dragon;
import org.gpginc.ntateam.projectwkff.ui.widget.dialogs.MessageDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Player extends BaseAttacker
{
    private String name;
    private Kingdom kingdom;
    private Clazz clazz;
    public boolean isStun;
    public boolean isDev;
    public boolean isBlind;
    public boolean isProtected;
    public boolean isDragonProtected;
    public boolean isDead = false;
    public boolean isWinner = false;
    public boolean attachedToEvent = false;
    private int field, lifePoints, damageTaken;
    private MultiValuedMap<BaseAttacker, Integer> attackers = new ArrayListValuedHashMap<>();
    private List<Effect> effects = new ArrayList<>();
    private List<Effect> removable = new ArrayList<>();

    public int currentTurn = 0;

    private boolean markRespawn, markDeath;

    public Player(String name) {
        super(PLAYER);
        this.name = name;
        this.lifePoints = 3;

    }

    public String getName() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isStun() {
        return isStun;
    }

    public Player setStun(boolean stun) {
        isStun = stun;
        return this;
    }

    public void affect(Effect e)
    {
        if(this.effects.contains(e))
        {
            this.effects.get(effects.indexOf(e)).currentUsages+=e.turnsDuration;
        } else this.effects.add(e.newInstance());
        LOG.wtf(this.getName() + "::AFFECTED BY: ", String.format("Effect {%s} added to player", e.getClass().getSimpleName()));

    }

    public boolean isBlind() {
        return isBlind;
    }

    public Player setBlind(boolean blind) {
        isBlind = blind;
        return this;
    }

    public int getLifeIcon()
    {
        if(markRespawn || markDeath) return R.drawable.reincarn_player_life_icon;
        else {
            switch (this.lifePoints) {
                case 1:
                    return this.isDragonProtected
                            ? R.drawable.player_1_dragon_protected_life
                            :   this.isProtected
                                ? R.drawable.player_1_protected_life
                                : R.drawable.player_1_life;
                case 2:
                    return this.isDragonProtected
                           ? R.drawable.player_2_dragon_protected_life
                           :   this.isProtected
                               ? R.drawable.player_2_protected_life
                               : R.drawable.player_2_life;
                case 3:
                    return this.isDragonProtected
                           ? R.drawable.player_full_dragon_protected_life
                           :   this.isProtected
                               ? R.drawable.player_full_protected_life
                               : R.drawable.player_full_life;
                default:
                    return R.drawable.dead_marker;
            }
        }
    }

    public int getFieldIcon()
    {
        switch (this.getField() + 1)
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
    public boolean isProtected() {
        return isProtected;
    }

    public Player setProtected(boolean aProtected) {
        isProtected = aProtected;
        return this;
    }

    public boolean isDragonProtected() {
        return isDragonProtected;
    }

    public Player setDragonProtected() {
        isDragonProtected = true;
        return this;
    }

    public int getField() {
        return field;
    }

    public Player setField(int field) {
        this.field = field;
        return this;
    }

    public int life()
    {
        return this.lifePoints;
    }

    protected Player(Parcel in) {
        super.readProperties(in);
        name = in.readString();
        lifePoints = in.readInt();
        field = in.readInt();
        damageTaken = in.readInt();
        isStun = in.readByte() != 0;
        isBlind = in.readByte() != 0;
        isProtected = in.readByte() != 0;
        isDragonProtected = in.readByte() != 0;
        attachedToEvent = in.readByte() != 0;
        markRespawn = in.readByte() != 0;
        kingdom = Kingdom.withName(in.readString());
        int name = in.readInt();
        this.clazz = in.readParcelable(Main.CLAZZ_MAP.get(name).getClass().getClassLoader());
        int efxsize = in.readInt();
        /*for(int i = 0; i <= efxsize - 1; ++i)
        {
            int efxname = in.readInt();
            LOG.w("PLAYER LOADER: " + this.name +": "," Loading effect: "+efxname+" :: ");
            System.out.print(Main.EFX_MAP.get(efxname).getClass().getSimpleName());
            this.effects.add(in.readParcelable(Main.EFX_MAP.get(efxname).getClass().getClassLoader()));
        }
        for(int a = 0; a <= in.readInt() - 1; ++a)
        {
            int type = in.readInt();
            BaseAttacker attacker = (BaseAttacker) in.readTypedObject(BaseAttacker.getCreator(type));
            if(attacker!=null)attackers.add(attacker);
        }*/
    }

    /*kingdom = (Kingdom) in.readSerializable();
    * dest.writeSerializable(kingdom);
    * */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeProperties(dest, flags);
        dest.writeString(name);
        dest.writeInt(life());
        dest.writeInt(field);
        dest.writeInt(damageTaken);
        dest.writeByte((byte) (isStun ? 1 : 0));
        dest.writeByte((byte) (isBlind ? 1 : 0));
        dest.writeByte((byte) (isProtected ? 1 : 0));
        dest.writeByte((byte) (isDragonProtected ? 1 : 0));
        dest.writeByte((byte) (attachedToEvent ? 1 : 0));
        dest.writeByte((byte) (markRespawn ? 1 : 0));
        dest.writeString(kingdom.name());
        dest.writeInt(clazz.getName());
        dest.writeParcelable(clazz, flags);
        dest.writeInt(effects.size() > 0 ? effects.size() : -1);
        /*for(int i = 0; i < effects.size(); ++i)
        {
            int efxname = effects.get(i).getName();
            LOG.w("PLAYER LOADER: " + this.name +": "," Writing effect: "+efxname+" :: "+effects.get(i).getClass().getSimpleName());
            dest.writeInt(efxname);
            dest.writeParcelable(effects.get(i), flags);
        }
        dest.writeInt(attackers.size() > 0 ? attackers.size() : -1);
        for (BaseAttacker baseAttacker : attackers) {
            dest.writeInt(baseAttacker.typeApplied);
            dest.writeTypedObject(baseAttacker, flags);
        }*/
    }

    public BaseAttacker clean()
    {
        this.effects.clear();
        this.attackers.clear();
        return this;
    }


    public Player setAttachedToEvent()
    {
        attachedToEvent = true;
        return this;
    }
    public boolean isAffected()
    {
        return this.effects.size() > 0;
    }

    public void damage(int i, @Nullable BaseAttacker attacker)
    {
        if(attacker.typeApplied==PLAYER)this.damageTaken+=i;
        else this.lifePoints-=i;
        if(attacker!=null)
        {
            BaseAttacker clone = null;
            if(attacker instanceof Player)
            {
                clone = new Player(((Player) attacker).getName()).setClazz(((Player) attacker).clazz).setField(((Player) attacker).field).setKingdom(((Player) attacker).kingdom);
                this.attackers.put(clone, currentTurn);
            } else this.attackers.put(attacker, currentTurn);

            LOG.v("ATTACKED", name + " was attacked by " + attacker.toString());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public Kingdom getKingdom() {
        return kingdom;
    }

    public Player setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
        return this;
    }

    public Player setClazz(Clazz c) {
        this.clazz = c;
        return this;
    }

    public List<BaseAttacker> getAttackers() {
        return Arrays.asList(attackers.keySet().toArray(new BaseAttacker[]{}));
    }

    @Nullable
    public BaseAttacker getLastAttacker()
    {
        return attackers.size() > 0 ? getAttackers().get(getAttackers().size() -1) : null;
    }


    public Player setEffects(List<Effect> effects) {
        this.effects = effects;
        return this;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public List<Effect> getEffects() {
        return this.effects;
    }

    public void antidote(Effect effect, int t)
    {
        //this.effects.remove(effect);
        if(!effect.still(t)) {
            removable.add(effect);
            LOG.wtf(this.name, effect.getClass().getSimpleName() + " marked to remove (" + effect.getCurrentUsages() + ")");
        }
        effect.antidote(this);
    }

    public void damageStep(GameFlux res) {
        swTurn();
        getEffects().stream().filter(e -> e.getBehavior() == Effect.MALIGNE).forEach(p -> p.apply(this, currentTurn));
        getEffects().stream().filter(e -> e.getBehavior() == Effect.BENIGNE).forEach(p -> p.apply(this, currentTurn));
        inspect(res);
        if(markDeath)
        {
            lifePoints = 0;
            markDeath = false;
            isDead = !isDragonProtected;
            res.DEAD_PLAYERS.put(this, res.currentTurn);
            markRespawn = false;
        }
        else {
            if (this.isDragonProtected) {
                res.DRAGONS.stream().filter(d -> d.isProtecting && d.getProtectedOne().equals(this)).forEach(d -> d.giveDamage(this.damageTaken));
            }
            this.setProtected(false);
            if (isDead) LOG.wtf("PLAYER INFO: ", this.name + " has died this turn!");
            if(markRespawn) LOG.wtf("PLAYER INFO: ", this.name + " has REINCARNATED!!");
        }
        effects.removeAll(removable);
        removable.clear();


    }
    public void inspect(GameFlux res)
    {
        if(!markRespawn) {
            this.lifePoints -= (isProtected || isDragonProtected) ? 0 : damageTaken;
            this.isDead = !isDragonProtected && life() <= 0;
            if (isDead) {
                res.DEAD_PLAYERS.put(this, res.currentTurn);
                LOG.wtf("PLAYER INFO: ", this.name + " has died this turn!");
            }
        } else
        {
            new MessageDialog.Display(res, R.string.reincarnated, getName()).prompt();
            res.DEAD_PLAYERS.remove(this);
        }
    }
    public boolean dragonAttack(Dragon d, boolean ignore)
    {
        if(!ignore && d.getKingdom() != this.kingdom) {
            this.lifePoints = isDragonProtected ? life() : 0;
            this.isDead = true;
            this.attackers.put(d, currentTurn);
            LOG.v("ATTACKED", name + " was attacked by " + d.toString());
            return true;
        } else if(ignore){
            if(!this.isEnemyFrom(d) && d.getBehavior()==Dragon.BEHAVIOUR_AGGRESSIVE)d.IA.setMarkAggressiveAttack(true);
            this.lifePoints = isDragonProtected ? life() : 0;
            this.isDead = true;
            this.attackers.put(d, currentTurn);
            LOG.v("ATTACKED", name + " was attacked by " + d.toString());
            return true;
        }
        else return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isEnemyFrom(BaseAttacker p)
    {
        return !p.equals(this) && !p.getRelativeKingdom().equals(this.kingdom);
    }

    public void masterAntidote()
    {
        this.effects.clear();
    }

    public int getDamageTaken() {
        return damageTaken;
    }

    public void undamage(int i) {
        damageTaken-=i;
    }

    public void increaseLifeIn(int i) {
        lifePoints+=i;
    }

    public void setLife(int i) {
        this.lifePoints = i;
    }

    public void respawn()
    {
        this.lifePoints = 1;
        this.damageTaken = 0;
        this.isDead = false;
        this.markRespawn = true;
    }

    public void markDeath() {
        markDeath = true;
        markRespawn = false;
    }

    public boolean isAffectedBy(Effect antiSkill)
    {
        return this.effects.contains(antiSkill) && antiSkill.still(currentTurn);
    }

    public boolean getAttacked()
    {
        return attackers.size() > 0;
    }

    public boolean getTurnAttacked(int turn)
    {
        return attackers.keySet().stream().anyMatch(atk -> attackers.get(atk).contains(turn));
    }

    public void swTurn(){this.currentTurn++;}

    public List<BaseAttacker> getTurnAttackers(Integer currentTurn)
    {
        List<BaseAttacker> tmpattackers = new ArrayList<>();
        this.attackers.entries().stream().filter(e -> e.getValue() == currentTurn).forEach(E -> tmpattackers.add(E.getKey()));
        return tmpattackers;
    }


    public Player setDev(boolean dev) {
        isDev = dev;
        return this;
    }
}

