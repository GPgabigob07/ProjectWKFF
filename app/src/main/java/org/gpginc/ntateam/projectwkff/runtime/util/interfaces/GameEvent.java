package org.gpginc.ntateam.projectwkff.runtime.util.interfaces;

import android.os.Parcelable;

import org.gpginc.ntateam.projectwkff.runtime.Player;

public interface GameEvent extends Parcelable
{

    boolean check(Player p);

    void exe(Player p);
}
