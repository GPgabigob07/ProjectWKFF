package org.gpginc.ntateam.projectwkff;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.collections4.map.LinkedMap;
import org.gpginc.ntateam.projectwkff.databinding.ActivityGameFluxBinding;
import org.gpginc.ntateam.projectwkff.runtime.BaseAttacker;
import org.gpginc.ntateam.projectwkff.runtime.ClazzSkill;
import org.gpginc.ntateam.projectwkff.runtime.Effect;
import org.gpginc.ntateam.projectwkff.runtime.Event;
import org.gpginc.ntateam.projectwkff.runtime.Main;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.dragons.Dragon;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.projectwkff.ui.fragments.PlayerInfosFragments;
import org.gpginc.ntateam.projectwkff.ui.widget.adapters._deloggerAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameFlux extends BaseAppActivity {

    public static final FluxLog LOG = new FluxLog();

    public final ArrayList<Player> PLAYERS = new ArrayList<>();
    public final ArrayList<Dragon> DRAGONS = new ArrayList<>();
    public final Map<Player, Integer> DEAD_PLAYERS = new HashMap<>();
    public final ArrayList<Player> GONE = new ArrayList<>();
    public final List<Event> EVENTS = new ArrayList<>();
    public Integer currentPlayerIndex;
    public Integer currentTurn;
    public PlayerInfosFragments LOGGERFRAG = PlayerInfosFragments.newInstance(7097);
   // protected final DragonHandler dragonHandler = new DragonHandler(this);

    public NavController navController;
    public Event ENDEVENT;
    private AppBarConfiguration mAppBarConfiguration;


    public ActivityGameFluxBinding BINDER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(darkTheme ? R.style.AppTheme_Dark_NoActionBar : R.style.AppTheme_NoActionBar);
        BINDER = DataBindingUtil.setContentView(this, R.layout.activity_game_flux);
        setSupportActionBar(BINDER.toolbar);
        currentTurn = 0;

        load();
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.prePlayer,
                R.id.currentPlayerFragment, R.id.currentSkillFragment, R.id.damageStepFragment)
                .build();
        navController = Navigation.findNavController(this, R.id.flux);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);


        setTitle(CP().getName());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

       /* if(EVENTS.stream().anyMatch(evt -> evt instanceof BaseDragonEvent))
        {
            dragonHandler.init();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ingame_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.force_finish)
        {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
        return super.onSupportNavigateUp();
    }

    public void goNext()
    {

        checkEvents(EventHandler.ALWAYS, PLAYERS);
        Random r = new Random();
        if(GONE.size() == 0)
        {
            checkEvents(EventHandler.START_LOOP_EVENT, PLAYERS);
        }

        if(currentPlayerIndex < PLAYERS.size())
        {
           // currentPlayerIndex++;
            LOG.d("LOG DEBUG:", GONE.size() + " / " +DEAD_PLAYERS.size() + "  -  " + PLAYERS.size());
            switch(navController.getCurrentDestination().getId())
            {
                case R.id.prePlayer:
                    navController.navigate(R.id.action_prePlayer_to_currentPlayerFragment);
                    break;
                case R.id.currentPlayerFragment:
                    GONE.add(CP());
                    navController.navigate(R.id.action_currentPlayerFragment_to_prePlayer);
                    //currentPlayerIndex++;
                    break;
                case R.id.currentSkillFragment:
                    GONE.add(CP());
                    //do { currentPlayerIndex = r.nextInt(PLAYERS.size());}while(GONE.contains(CP()) || DEAD_PLAYERS.keySet().contains(CP()));
                    navController.navigate(R.id.action_currentSkillFragment_to_prePlayer);
                    break;
                case R.id.damageStepFragment:
                    navController.navigate(R.id.action_damageStepFragment_to_prePlayer);
                    break;
            }
            setTitle(CP().getName());
            LOG.wtf("GAME FLUX CALLER: CURRENT PLAYER: -*-*-*-*-*-*-*- ",PLAYERS.get(currentPlayerIndex).getName());
        }
        else
        {
            switch(navController.getCurrentDestination().getId())
            {
                case R.id.prePlayer:
                    navController.navigate(R.id.action_prePlayer_to_damageStepFragment);
                    break;
                case R.id.currentPlayerFragment:
                    GONE.add(CP());
                    navController.navigate(R.id.action_currentPlayerFragment_to_damageStepFragment);
                    break;
                case R.id.currentSkillFragment:
                    GONE.add(CP());
                    navController.navigate(R.id.action_currentSkillFragment_to_damageStepFragment);
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
      if(navController.getCurrentDestination().getId() == R.id.currentSkillFragment)
      {
          Bundle save = navController.saveState();
          navController.popBackStack();
          navController.restoreState(save);
      }
    }
    @Nullable
    public Dragon byName(String name)
    {
        for (Dragon dragon : DRAGONS) {
            if(dragon.getNameAsString().equals(name))return dragon;
        }
        return null;
    }

    public void openSkill(ClazzSkill skill)
    {
        Bundle b = new Bundle();
        b.putParcelable("skill", skill);
        navController.navigate(R.id.action_currentPlayerFragment_to_currentSkillFragment, b);
    }
    protected final void load()
    {
        Bundle ins = getIntent().getExtras();
        for(Parcelable p : ins.getParcelableArrayList("PLAYERS")) {
            PLAYERS.add((Player) p);
        }
        this.currentPlayerIndex = 0;
        Main.bootEvents(this);
        for(Event e : EVENTS)
        {
            if(((Event) e).isAttacher)
            {
                LOG.d("EVENT SUBSEC LOADER ", "EVENT "+getResources().getString(((Event) e).getName())+" OWNER = " + ((Event) e).getOwner().getName());
            } else if(((Event) e).needPlayers) {
                LOG.d("EVENT SUBSEC LOADER ", "EVENT " + getResources().getString(((Event) e).getName()) + " OWNER = " + ((Event) e).getOwner().getName());
                LOG.d("EVENT SUBSEC LOADER ", "EVENT " + getResources().getString(((Event) e).getName()) + " TARGET = " + ((Event) e).getTarget().getName());


            }
            LOG.v("EVENT LOADER {"+e.getClass().getSimpleName()+"}", "LOADS WITH SUCCESS!!");
            if (e.getHandler().equals(EventHandler.START_LOOP_EVENT)) {
                for (Player p : PLAYERS) {
                    if (e.check(p)) e.exe(p, this);
                }
            }
        }
        PLAYERS.forEach(p -> p.getEffects().stream().filter(Effect::isInstaEffect).forEach(e2 -> e2.apply(p, currentTurn)));
    }
    public Player CP()
    {
        return PLAYERS.get(currentPlayerIndex);
    }

    public void checkEvents(EventHandler handler, Collection<Player> players)
    {
        for(Event e : EVENTS)
        {
            if(e.getHandler().equals(handler))
            {
                for(Player p : players)
                {
                    if(e.check(p))e.exe(p, this);
                }
            }
        }
    }


    public void gameover()
    {
        getSupportActionBar().hide();
        switch(navController.getCurrentDestination().getId())
        {
            case R.id.prePlayer:
                navController.navigate(R.id.action_prePlayer_to_gameEnd2);
                break;
            case R.id.currentPlayerFragment:
                navController.navigate(R.id.action_currentPlayerFragment_to_gameEnd);
                break;
            case R.id.currentSkillFragment:
                navController.navigate(R.id.action_currentSkillFragment_to_gameEnd);
                break;
            case R.id.damageStepFragment:
                navController.navigate(R.id.action_damageStepFragment_to_gameEnd);
                break;
        }
    }

    public Player getPlayer(BaseAttacker target) {
        for (Player player : PLAYERS) {
            if(player.equals(target))return player;
        }
        return null;
    }

    public Dragon dragon(Dragon dragon)
    {
        return DRAGONS.stream().filter(d -> d.equals(dragon)).findFirst().get();
    }

    public static class FluxLog
    {
        public final LinkedMap<String, Integer> loggerout = new LinkedMap<>();

        public static final int V = 0;
        public static final int D = 1;
        public static final int W = 2;

        private _deloggerAdapter adapter;
        private RecyclerView view;

        public RecyclerView getView() {
            return view;
        }

        public FluxLog setView(RecyclerView view) {
            this.view = view;
            return this;
        }

        public _deloggerAdapter getAdapter() {
            return adapter;
        }

        public FluxLog setAdapter(_deloggerAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        String ar(String... a)
        {
            StringBuilder b = new StringBuilder();
            for(String s : a) b.append(s).append("::");
            return b.toString();
        }
        public void v(String tag, String msg)
        {
            Log.v("LOGG{}:: "+tag, msg);
            this.loggerout.put(ar(tag, msg), V);
            wrapMap();
        }
        public void d(String tag, String msg)
        {
            Log.d(tag, msg);
            this.loggerout.put(ar(tag, msg), D);
            wrapMap();

        }
        public void wtf(String tag, String msg)
        {
            Log.wtf(tag, msg);
            this.loggerout.put(ar(tag, msg), W);
            wrapMap();
        }
        public void w(String tag, String msg)
        {
            Log.wtf(tag, msg);
            this.loggerout.put(ar(tag, msg), W);
            wrapMap();
        }

        void wrapMap()
        {
            if(this.adapter!=null)
            {
                this.adapter = new _deloggerAdapter().setmMap(loggerout);
                this.view.setAdapter(this.adapter);
            }
        }

        @IntDef({V,D,W})
        private @interface LogType{}
    }
}

