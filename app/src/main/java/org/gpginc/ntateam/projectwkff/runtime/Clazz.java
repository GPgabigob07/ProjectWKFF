package org.gpginc.ntateam.projectwkff.runtime;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.databinding.ViewDataBinding;

import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;
import org.gpginc.ntateam.projectwkff.runtime.variations.TurnSkill;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Clazz implements Parcelable
{
    private final List<ClazzSkill> SKILLS = new ArrayList<>();
    @StringRes private final int name;
    @DrawableRes private final int icon;
    private final Rarity rarity;

    protected boolean isEnabled = true;

    public Clazz(int name, int icon, Rarity rarity) {
        this.name = name;
        this.icon = icon;
        this.rarity = rarity;
    }

    protected Clazz(Parcel in) {
        name = in.readInt();
        icon = in.readInt();
        isEnabled = in.readByte() != 0;
        rarity = Rarity.withName(in.readString());
        int l = in.readInt();
        for(int i = 0; i < l; ++i)
        {
            int name = in.readInt();
            ClazzSkill k = (ClazzSkill) in.readTypedObject(Main.SKILL_MAP.get(name).getCreator());
            this.SKILLS.add(k);
        }

    }

    public abstract Clazz newInstance();
    public abstract Creator getCreator();


    private Clazz setAllSkills(List<ClazzSkill> skills)
    {
        this.SKILLS.addAll(skills);
        return this;
    }

    public Clazz base()
    {
        Main.CLAZZ_LIST.add(this);
        Main.CLAZZ_MAP.put(this.name, this);
        return this;
    }

    public Clazz bindSkill(ClazzSkill s)
    {
        this.SKILLS.add(s.newInstance());
        return this;
    }

    public void runPassive(ViewDataBinding p)
    {
        for(ClazzSkill s : this.SKILLS)
        {
            if(s.isPassive())
            {
                if(s instanceof TurnSkill) ((TurnSkill) s).onTurnRun(p);
                else s.runSkill(p);
            }
        }
    }
    public void runAttackTrigger(ViewDataBinding p)
    {
        for(ClazzSkill s : this.SKILLS)
        {
            if(s.isAttackTriggered())
            {
                if(s instanceof TurnSkill) ((TurnSkill) s).onTurnRun(p);
                else s.runSkill(p);
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getName() {
        return name;
    }


    public boolean hasPassiveOrAttackTrigger()
    {
        return SKILLS.stream().filter(skill -> skill.isPassive() || skill.isAttackTriggered()).findAny().isPresent();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(name);
        dest.writeInt(icon);
        dest.writeByte((byte) (isEnabled ? 1 : 0));
        dest.writeString(rarity.R());
        dest.writeInt(this.SKILLS.size());
        for(ClazzSkill s : this.SKILLS) {
            dest.writeInt(s.getName());
            dest.writeTypedObject(s, flags);
        }
    }

    public Rarity getRarity() {
        return this.rarity;
    }

    public int getIcon() {
        return icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clazz clazz = (Clazz) o;
        return name == clazz.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(SKILLS, name, icon, rarity, isEnabled);
    }

    public List<ClazzSkill> getSkills() {
        return this.SKILLS;
    }

    public Clazz setEnabled(boolean en)
    {
        this.isEnabled = en;
        return this;
    }

    public void forceBindSkill(ClazzSkill formUpControlForDragon)
    {
        this.SKILLS.add(formUpControlForDragon);
    }
}
