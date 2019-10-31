package org.gpginc.ntateam.projectwkff.runtime.dragons.skills;

import android.os.Parcel;

import androidx.annotation.NonNull;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.runtime.ClazzSkill;
import org.gpginc.ntateam.projectwkff.runtime.Main;
import org.gpginc.ntateam.projectwkff.runtime.dragons.Dragon;

public abstract class BaseDragonSkill extends ClazzSkill
{
    protected final int energyNeed;
    protected Dragon dragon;

    public BaseDragonSkill(int name, int layout, @NonNull Type type, int energyNeed) {
        super(name, layout, type);
        this.energyNeed = energyNeed;
    }

    public BaseDragonSkill(Parcel in) {
        super(in);
        energyNeed = in.readInt();
    }

    public abstract boolean doDragonHaveEnergyEnough(Dragon d);

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(energyNeed);
    }

    public abstract void runFromBehaviour(GameFlux res);

    public void setDragon(Dragon dragon) {
        this.dragon = dragon;
    }

    public Dragon getDragon() {
        return dragon;
    }

    @Override
    public BaseDragonSkill base() {
        Main.SKILLS.add(this);
        Main.SKILL_MAP.put(this.name, this);
        return this;
    }

    @Override
    public abstract BaseDragonSkill newInstance();
}
