package org.gpginc.ntateam.projectwkff.runtime.dragons;

import android.content.Intent;
import android.os.Parcel;
import android.util.Log;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.annotation.StringRes;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.BaseAttacker;
import org.gpginc.ntateam.projectwkff.runtime.Kingdom;
import org.gpginc.ntateam.projectwkff.runtime.Player;

import java.util.List;
import java.util.Random;

public class Dragon extends BaseAttacker
{
    @StringRes
    private final int name;
    public int power = 0; //max = 10K

    public final DragonIA IA = new DragonIA();
    public static final String BEHAVIOUR_PROTECTOR ="Protector";
    public static final String BEHAVIOUR_AGGRESSIVE ="Aggressive";
    public static final String BEHAVIOUR_HOSTILE ="Hostile";
    public static final String BEHAVIOUR_HONOR ="Honor";

    public static final int SUCCESS = 31;
    public static final int SLEEPINESS = 32;
    public static final int PROTECTING = 33;
    public static final int BOTH = 34;
    public static final int ALREADY_ATTACKED = 35;
    public static final int FAILED = 36;
    /*FLAGS FOR ACTIONS*/
    private static int[] flags =
            {
                SLEEPINESS/*Is Sleepness*/,
                PROTECTING/*Is Protecting*/,
                BOTH/*both*/,
                ALREADY_ATTACKED/*Already Attacked this turn*/,
                FAILED/*When attack had failed*/
            };
    private final Kingdom kingdom;
    private int life, maxDamage, takenDamage;
    public boolean turnAttack, isSleepiness = false, isProtecting = false;
    protected String behaviour;
    private Player protectedOne, dragonHunter;
    /*Dragon hunter shared properties*/
    public boolean isOwned, isSideChanged, canLightAttack, canHeavyAttack, canMultiAttack, canObliterate, hasDragonHunter;

    private GameFlux res;

    protected Dragon(@StringRes int name, Kingdom kingdom)
    {
        super(DRAGON);
        this.name = name;
        this.kingdom = kingdom;
        this.life = 5;
        this.power = new Random().nextInt(7500);
    }

    protected Dragon(Parcel in) {
        super.readProperties(in);
        name = in.readInt();
        kingdom = Kingdom.withName(in.readString());
        life = in.readInt();
        behaviour = in.readString();
        maxDamage = in.readInt();
        takenDamage = in.readInt();
        turnAttack = in.readByte() != 0;
        isSleepiness = in.readByte() != 0;
        isOwned = in.readByte() != 0;
        isSideChanged = in.readByte() != 0;
        canLightAttack = in.readByte() != 0;
        canHeavyAttack = in.readByte() != 0;
        canMultiAttack = in.readByte() != 0;
        canObliterate = in.readByte() != 0;
        hasDragonHunter = in.readByte() != 0;
        if(isProtecting)protectedOne = in.readTypedObject(Player.CREATOR);
        else protectedOne = null;
        if(hasDragonHunter)dragonHunter = in.readTypedObject(Player.CREATOR);
        else dragonHunter = null;
    }

    public static Dragon bornToKingdom(Kingdom kingdom)
    {
        return kingdom.equals(Kingdom.OHXER) ?
                new Dragon(R.string.dragon_quasar_name, kingdom) : new Dragon(R.string.dragon_lancelot_name, kingdom);
    }

    public int getPower() {
        return power;
    }

    public int attack(Player player)
    {
        if(!turnAttack) {
            if (!isSleepiness && !isProtecting) {
                Log.v(getNameAsString(), "ATTACKING START");
                if(player.dragonAttack(this, false))
                {
                    player.inspect(res);
                    res=null;
                    this.turnAttack = true;
                    Log.v(getNameAsString(), "ATTACK DONE TO " + player.getName());
                    return SUCCESS;
                }
                else return flags[4];
            } else if (isProtecting && !isSleepiness) return flags[1];
            else if (!isProtecting && isSleepiness) return flags[0];
            else if (isProtecting && isSleepiness) return flags[2];

        } else return flags[3];
        return 0;
    }

    public int multiAttack(List<Player> player)
    {
        if(!turnAttack) {
            if (!isSleepiness && !isProtecting) {
                for (Player player1 : player) {
                    player1.dragonAttack(this, true);
                }
                this.turnAttack = true;
                return SUCCESS;
            } else if (isProtecting && !isSleepiness) return flags[1];
            else if (!isProtecting && isSleepiness) return flags[0];
            else if (isProtecting && isSleepiness) return flags[2];

        } else return flags[3];
        return 0;
    }

    public void lightAttack(GameFlux r, int field)
    {
        if(canLightAttack) {
            r.PLAYERS.forEach(p ->{
                if (p.getField() == field && !p.getKingdom().equals(this.kingdom))
                {
                    p.damage(1, this);
                    p.inspect(r);
                }
            });
        }
    }
    public void heavyAttack(GameFlux r, boolean ignore)
    {
        if(canHeavyAttack)
        {
            for (Player p : r.PLAYERS) {
                if (ignore) {
                    p.damage(1, this);
                    p.inspect(r);
                } else if (!p.getKingdom().equals(this.getKingdom())) {
                    p.damage(1, this);
                    p.inspect(r);
                }
            }
        }
    }

    public void giveDamage(int dmg)
    {
        this.life -= dmg;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public int getLife() {
        return life;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public int getTakenDamage() {
        return takenDamage;
    }

    public Player getProtectedOne() {
        return protectedOne;
    }

    public Player getDragonHunter() {
        return dragonHunter;
    }


    public void setBehaviour(@DragonBehavior String behaviour) {
        this.behaviour = behaviour;
    }

    public Player protect(Player p)
    {
        if(protectedOne == null) {
            p.setDragonProtected();
            this.protectedOne = p;
        }
        return p;
    }

    public Dragon setDragonHunter(Player p)
    {
        this.dragonHunter = p;
        return this;
    }

    /*PARCELABLE IMPLEMENTATION*/
    public static final Creator<Dragon> CREATOR = new Creator<Dragon>() {
        @Override
        public Dragon createFromParcel(Parcel in) {
            return new Dragon(in);
        }

        @Override
        public Dragon[] newArray(int size) {
            return new Dragon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @DrawableRes
    public int getImage()
    {
        return this.name == R.string.dragon_lancelot_name ? R.drawable.lancelot_pre : R.drawable.quasar_pre;
    }

    public String getNameAsString()
    {
        return this.name == R.string.dragon_lancelot_name ? "Lancelot" : "Quasar";
    }

    @NonNull
    @Override
    public String toString() {
        return getNameAsString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeProperties(dest, flags);
        dest.writeInt(name);
        dest.writeString(kingdom.name());
        dest.writeInt(life);
        dest.writeString(behaviour);
        dest.writeInt(maxDamage);
        dest.writeInt(takenDamage);
        dest.writeByte((byte) (turnAttack ? 1 : 0));
        dest.writeByte((byte) (isSleepiness ? 1 : 0));
        dest.writeByte((byte) (isOwned ? 1 : 0));
        dest.writeByte((byte) (isSideChanged ? 1 : 0));
        dest.writeByte((byte) (canLightAttack ? 1 : 0));
        dest.writeByte((byte) (canHeavyAttack ? 1 : 0));
        dest.writeByte((byte) (canMultiAttack ? 1 : 0));
        dest.writeByte((byte) (canObliterate ? 1 : 0));
        dest.writeByte((byte) (hasDragonHunter ? 1 : 0));
        dest.writeTypedObject(protectedOne, flags);
        dest.writeTypedObject(dragonHunter, flags);
    }

    private void accesdasdapt(Player p) {
        p.dragonAttack(this, true);
    }

    public void setRes(GameFlux res) {
        this.res = res;
    }

    public GameFlux getRes() {
        return res;
    }

    public Dragon randomBehavior()
    {
        this.setBehaviour(behaviorChance(new Random().nextInt(100)));
        Log.wtf("DRAGON {"+this.getNameAsString()+"}", "BEHAVIOUR = {"+this.behaviour+"}");
        return this;
    }

    @DragonBehavior String behaviorChance(int chance)
    {
        return chance <= 40 ? BEHAVIOUR_HONOR
               : chance > 40 && chance < 70 ? BEHAVIOUR_PROTECTOR
                : chance >= 70 && chance <=85 ? BEHAVIOUR_AGGRESSIVE
                  : BEHAVIOUR_HOSTILE;
    }

    @DragonBehavior public String getBehavior() { return this.behaviour;
    }

    @StringDef({BEHAVIOUR_AGGRESSIVE, BEHAVIOUR_HONOR, BEHAVIOUR_HOSTILE, BEHAVIOUR_PROTECTOR})
    public @interface DragonBehavior {}

    @IntDef({SUCCESS, SLEEPINESS, PROTECTING, BOTH, ALREADY_ATTACKED, FAILED})
    public @interface DragonInteraction{}

    public class DragonIA
    {
        private boolean markHonorPatienceZero, markAggressiveAttack, markTotalHostile, markProtector;

        public boolean isMarkHonorPatienceZero() {
            return markHonorPatienceZero;
        }

        public DragonIA setMarkHonorPatienceZero(boolean markHonorPatienceZero) {
            this.markHonorPatienceZero = markHonorPatienceZero;
            return this;
        }

        public boolean isMarkAggressiveAttack() {
            return markAggressiveAttack;
        }

        public DragonIA setMarkAggressiveAttack(boolean markAggressiveAttack) {
            this.markAggressiveAttack = markAggressiveAttack;
            return this;
        }

        public boolean isMarkTotalHostile() {
            return markTotalHostile;
        }

        public DragonIA setMarkTotalHostile(boolean markTotalHostile) {
            this.markTotalHostile = markTotalHostile;
            return this;
        }

        public boolean isMarkProtector() {
            return markProtector;
        }

        public DragonIA setMarkProtector(boolean markProtector) {
            this.markProtector = markProtector;
            return this;
        }
    }
}
