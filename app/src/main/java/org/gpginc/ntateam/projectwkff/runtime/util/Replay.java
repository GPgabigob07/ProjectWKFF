package org.gpginc.ntateam.projectwkff.runtime.util;

import android.content.res.Resources;
import android.util.JsonWriter;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.runtime.BaseAttacker;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.dragons.Dragon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.gpginc.ntateam.projectwkff.GameFlux.LOG;

@SuppressWarnings("uncheked")
public class Replay
{
    private final GameFlux game;
    private final List<ReplayAction<BaseAttacker>> allactions = new LinkedList<>();
    private String name;

    public Replay(GameFlux game) {
        this.game = game;
    }

    public void addAction(BaseAttacker actor, ReplayAction.Type name, String action, int turn)
    {
        ReplayAction<BaseAttacker> na = new ReplayAction<>(actor, action, name, turn);
        allactions.add(new ReplayAction<>(actor, action, name, turn));
        LOG.d("ACTION ADDED", na.toString());
    }

    public Replay setName(String name) {
        this.name = name;
        return this;
    }

    public void saveToFile(File file)
    {
        if(name==null || name=="")
        {
            throw new NumberFormatException("Name cannot be empty " + name);
        }
        else 
        {
            JSONObject parent = new JSONObject();
            JSONObject resume = new JSONObject();
            JSONObject iplayer = new JSONObject();
            try {
                resume.put("Name", name);
                resume.put("Date", new Date().toLocaleString());
                
                JSONArray players = new JSONArray();
                game.PLAYERS.forEach(players::put);
                
                JSONArray evts = new JSONArray();
                game.EVENTS.forEach(e -> evts.put(e.getNameLikeStr(game.getResources())));
                
                JSONArray dg = new JSONArray();
                game.DRAGONS.forEach(dg::put);
                resume.put("Players", players);
                resume.put("Events", evts);
                resume.put("Dragons", dg);

                parent.put("Resume", resume);
                game.PLAYERS.forEach(p ->
                {
                    JSONObject o = new JSONObject();
                    try {
                        o.put("Final Life", p.life());
                        JSONObject actions = new JSONObject();
                        for(int j = 0; j <=game.currentTurn -1; ++j)
                        {
                            JSONObject turn = new JSONObject();
                            final int i = j;
                            for (ReplayAction.Type value : ReplayAction.Type.values()) {
                                JSONArray array = new JSONArray();
                                allactions.stream().filter(a -> a.getActor().equals(p) && a.getTurn() == i && a.getName().equals(value)).forEach(a -> array.put(a.getAction()));
                                turn.put("Actions of " + value.name().toLowerCase(), array);
                            }
                            actions.put("Turn " + i, turn);
                        }
                        o.put("Turns Actions", actions);
                        iplayer.put(p.getName(), o);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                parent.put("Players Actions", iplayer);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String output = Util.identJSON(parent);
            try {
                FileOutputStream stream = new FileOutputStream(file);
                stream.write(output.getBytes());
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void newDeadAction(Player p, Resources resources)
    {
        if (p.getLastAttacker() != null) {
            addAction(p, ReplayAction.Type.INTERACTION, resources.getString(R.string.replay_killedby) + " " + p.getLastAttacker().getRelativeName(), p.currentTurn);
        }
        else addAction(p, ReplayAction.Type.INTERACTION, resources.getString(R.string.replay_diedbylaziness), p.currentTurn);
    }
    public void genEffectInfos(Player actor, Resources res){
        actor.getEffects().forEach(e ->
        {
            String name = res.getString(e.getName());
            if(e.getApplyTurn() == (e.isInstaEffect() ? actor.currentTurn  : actor.currentTurn))
            {
                addAction(actor, ReplayAction.Type.EFFECTS_INTERACTIONS, res.getString(R.string.replay_effectadd) + " " + e.getNameAsString(res), actor.currentTurn );
            }else if(e.still(actor.currentTurn - 1))
            {
                addAction(actor, ReplayAction.Type.EFFECTS_INTERACTIONS, res.getString(R.string.replay_effectstill) + " " + e.getNameAsString(res), actor.currentTurn);
            }else if(!e.still(actor.currentTurn - 1))
            {
                addAction(actor, ReplayAction.Type.EFFECTS_INTERACTIONS, res.getString(R.string.replay_effectantidote) + " " + e.getNameAsString(res), actor.currentTurn);
            }
        });
    }
    public void genDragonInteraction(Player p, Dragon d)
    {

    }

    @Override
    public String toString() {
        return "Replay{" +
                "game=" + game +
                ", allactions=" + allactions +
                ", name='" + name + '\'' +
                '}';
    }

    public static class ReplayAction<T>
    {
        private T actor;
        private String action;
        private Type name;
        private int turn;

        public ReplayAction(T actor, String action, Type name, int turn) {
            this.actor = actor;
            this.action = action;
            this.name = name;
            this.turn = turn;

        }

        public T getActor() {
            return actor;
        }

        public String getAction() {
            return action;
        }

        public int getTurn() {
            return turn;
        }
        public JSONObject toJson() throws JSONException
        {
            JSONObject object = new JSONObject(name.name());
            object.put("Action", action);
            return object;
        }

        public Type getName() {
            return name;
        }

        @Override
        public String toString() {
            return "ReplayAction{" +
                    "actor=" + actor +
                    ", action='" + action + '\'' +
                    ", name=" + name +
                    ", turn=" + turn +
                    '}';
        }

        public enum Type
        {
            INTERACTION,
            ATTACK,
            STRATEGY,
            EFFECTS_INTERACTIONS
        }
    }
}
