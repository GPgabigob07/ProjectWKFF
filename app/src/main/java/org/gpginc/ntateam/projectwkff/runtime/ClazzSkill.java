package org.gpginc.ntateam.projectwkff.runtime;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.ViewDataBinding;

import org.gpginc.ntateam.projectwkff.R;

import java.io.Serializable;


public abstract class ClazzSkill implements Parcelable
{

    @Nullable protected final int layout;
    @StringRes protected final int name;
    @Nullable protected int maxCounterTimes, counteredTimes;
    @NonNull protected final Type type;
    @Nullable protected Effect antiSkill;

    /*--LAYOUT TYPERS--*/
    protected static int PLAYER_SELECTION_LAYOUT = 0;
    protected static int PLAYER_CHANGE_FIELD = 1;
    protected static int NONE = -666;


    public ClazzSkill(@StringRes int name, @LayoutRes int layout, @NonNull Type type) {
        this.layout = layout;
        this.name = name;
        this.type = type;
    }

    protected ClazzSkill(Parcel in) {
        layout = in.readInt();
        name = in.readInt();
        maxCounterTimes = in.readInt();
        counteredTimes = in.readInt();
        antiSkill = in.readParcelable(Effect.class.getClassLoader());
        type = (Type) in.readSerializable();
    }

    public int getName() {
        return name;
    }

    public boolean hasLayout()
    {
        return this.layout != 0;

    }

    public ClazzSkill setAntiSkill(@Nullable Effect antiSkill) {
        this.antiSkill = antiSkill;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(layout);
        dest.writeInt(name);
        dest.writeInt(maxCounterTimes);
        dest.writeInt(counteredTimes);
        dest.writeParcelable(antiSkill, flags);
        dest.writeSerializable(this.type);
    }

    public boolean getNeedIconTint()
    {
        return this.type.getIconTint();
    }
    public boolean isPassive(){return this.type == Type.PASSIVE;}
    public boolean isAttackTriggered(){return this.type == Type.ATTACK_TRIGGER;}
    public boolean isCounter(){return this.type == Type.COUNTER;}

    public abstract ClazzSkill newInstance();
    public abstract Creator getCreator();
    public abstract void runSkill(ViewDataBinding skill);


    public int getIcon()
    {
        return this.type.getIcon();
    }

    public int getLayout() {
        return layout;
    }

    public int getMaxCounterTimes() {
        return maxCounterTimes;
    }

    public int getCounteredTimes() {
        return counteredTimes;
    }

    public ClazzSkill base()
    {
        Main.SKILLS.add(this);
        Main.SKILL_MAP.put(this.name, this);
        return this;
    }

    public boolean isAttack() {
        return this.type == Type.ATTACK;
    }

    public enum Type implements Serializable
    {
        ATTACK(R.drawable.attack_icon, true),
        MAHOU(R.drawable.ic_mahou, true),
        MINDED(R.drawable.ic_config, false),
        COUNTER(R.drawable.shield_anim, false),
        PASSIVE(R.drawable.botao_adicionar, false),
        ATTACK_TRIGGER(R.drawable.counter_icon, true);


        @DrawableRes int icon;
        boolean iconTint;

        Type(int icon, boolean b) {
            this.icon = icon;
            this.iconTint=b;
        }

        public int getIcon() {
            return icon;
        }
        public boolean getIconTint()
        {
            return iconTint;
        }
    }


}
