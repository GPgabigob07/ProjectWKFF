package org.gpginc.ntateam.projectwkff.runtime.util.interfaces;

import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.dragons.Dragon;

public interface DragonEvent extends GameEvent
{
    String getKingdom();
    <T extends GameEvent> T setDragon(Dragon d);
    void obliterate(Player p);
}
