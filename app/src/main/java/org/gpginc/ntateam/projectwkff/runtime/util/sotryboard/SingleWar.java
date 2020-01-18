package org.gpginc.ntateam.projectwkff.runtime.util.sotryboard;

import org.gpginc.ntateam.projectwkff.navycore.Core;
import org.gpginc.ntateam.projectwkff.runtime.BaseAttacker;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.util.Util;
import org.gpginc.ntateam.projectwkff.runtime.variations.TurnSkill;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static org.gpginc.ntateam.projectwkff.GameFlux.LOG;

public class SingleWar extends Core.Storyboard<PlayerActions>
{

    public SingleWar(String name, int maxTimeStep) {
        super(name, maxTimeStep);
    }

    public SingleWar() {
    }

    public boolean containsCertainPlatyerBehavior(Player player)
    {
        return this.mItems.keySet().stream().filter(a -> a.getPlayer() == player).findFirst().isPresent();
    }
    @Override
    public void writeToJSON(File file) throws IOException {
        try {
            JSONObject STORY = new JSONObject();
            STORY.put("Name", this.name);
            STORY.put("Date", new Date());
            JSONObject time = new JSONObject();

            for (int i = 0; i < maxTimeStep; i++)
            {
                final int a = i;
                JSONObject unit = new JSONObject();
                storyFrom(i).forEach(pa -> {
                        pa.getActionsSet().forEach(act -> {;
                        try {
                            JSONObject cAction = new JSONObject();
                            JSONArray INTERACTIONS = new JSONArray();
                            act.getAllForResult(0).stream().forEach(INTERACTIONS::put);
                            JSONArray INFOS = new JSONArray();
                            act.getAllForResult(1).stream().forEach(INFOS::put);
                            cAction.put("Interactions", INTERACTIONS);
                            cAction.put("Infos", INFOS);
                            unit.put(act.getExecutor().getRelativeName(), cAction);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                });
                time.put(String.valueOf(i), unit);
            }
            STORY.put("Turns", time);
            LOG.wtf("JSON", STORY.toString());

            FileOutputStream fos = new FileOutputStream(file);
            LOG.wtf("JSON IDENTD", Util.identJSON(STORY));
            fos.write(Util.identJSON(STORY).getBytes());
            fos.close();
            LOG.wtf("WRITER", "File in: " + file.getPath());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public PlayerActions getActionsFor(BaseAttacker player)
    {
        return this.mItems.keySet().stream().filter(a -> a.actor.equals(player)).findFirst().get();
    }
}
