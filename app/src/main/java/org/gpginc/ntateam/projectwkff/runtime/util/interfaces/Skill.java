package org.gpginc.ntateam.projectwkff.runtime.util.interfaces;


import android.os.Parcelable;

import androidx.annotation.Nullable;

import org.gpginc.ntateam.projectwkff.runtime.ClazzSkill;

public interface Skill extends Parcelable
{
    void runSkill(@Nullable Object o);

    Creator getCreator();

    boolean isPassive();
    boolean isAttackTriggered();
    boolean isPassiveRun();
    boolean isAttack();
    ClazzSkill setPassiveRun(boolean passiveRun);
    boolean hasLayout();
    String getName();
}
