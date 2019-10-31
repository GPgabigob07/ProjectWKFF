package org.gpginc.ntateam.projectwkff.runtime.util.enums;

public enum EventHandler
{
    ALWAYS,
    DAMAGE_STEP,
    START_LOOP_EVENT,
    ON_DEATH,
    SKILL_EVENT,
    ON_GAME_END;

    public static final EventHandler[] HANDLERS = {ALWAYS, DAMAGE_STEP,START_LOOP_EVENT, ON_DEATH, SKILL_EVENT, ON_GAME_END};

    private Enum o;
    public static EventHandler withName(String name)
    {
        for(EventHandler evt : HANDLERS)
        {
            if(evt.name().equals(name)) return evt;
        }
        return ALWAYS;
    }

}
