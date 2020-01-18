package org.gpginc.ntateam.projectwkff.runtime.util.sotryboard;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.navycore.Core;
import org.gpginc.ntateam.projectwkff.runtime.BaseAttacker;
import org.gpginc.ntateam.projectwkff.runtime.Player;

public class PlayerActions extends Core.StoryItem<BaseAttacker>
{
    protected final Player actor;

    public PlayerActions(Player actor) {
        this.actor = actor;
    }

    @Override
    public PlayerActions addAction(BaseAttacker executor, String name, String act, int type) {
        super.addAction(executor, name, act, type);
        return this;
    }

    public Player getPlayer() {
        return actor;
    }

    public void newDeadResult() {
        if(actor.getLastAttacker()!=null)addAction(actor.getLastAttacker(), "DIED", "Was Killed by " + actor.getLastAttacker().getRelativeName(), 1);
        else addAction(actor, "DIED", "Was Killed by laziness", 1);
    }

    public void genEffectInfos(GameFlux res)
    {
        actor.getEffects().forEach(e ->
        {
            String name = res.getString(e.getName());
            if(existsAction(name))
            {
                addResultToAction(name, e.still(res.currentTurn) ? "AFFECTED BY " + name.toUpperCase() : "ANTIDOTE GIVEN TO " + name.toUpperCase(), 1);
            }
            else
            {
                addAction(actor, name, e.still(res.currentTurn) ? "AFFECTED BY " + name.toUpperCase() : "ANTIDOTE GIVEN TO " + name.toUpperCase(), 1);
            }
        });
    }

    @Override
    public String toString() {
        return "PlayerActions{" +
                "actor=" + actor +
                "} || " + super.toString();
    }
}
