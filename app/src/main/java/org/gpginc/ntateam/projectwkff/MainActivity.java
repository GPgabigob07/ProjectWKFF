package org.gpginc.ntateam.projectwkff;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import static org.gpginc.ntateam.projectwkff.GameFlux.LOG;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.gpginc.ntateam.projectwkff.databinding.ActivityMainBinding;
import org.gpginc.ntateam.projectwkff.runtime.Event;
import org.gpginc.ntateam.projectwkff.runtime.Main;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;
import org.gpginc.ntateam.projectwkff.ui.widget.adapters.PlayerCreateAdapter;
import org.gpginc.ntateam.projectwkff.ui.widget.dialogs.AddNewPlayer;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends BaseAppActivity {


    /*TEST STUFF*/
/*    private ClipDrawable drawable;
    private int mLevel = 0;
    private int ei = 1;
    private int nextLevel = 50;
    private Handler mHandler = new Handler();
    private Runnable growth = () -> {
        grow(nextLevel);
        LOG.w("MAIN", mLevel + " to " +nextLevel);
    };

    void grow(int to)
    {
        mLevel+=50;
        nextLevel+=50;
        drawable.setLevel(mLevel);
        ei++;
        binder.content.playersList.setTranslationY(ei);
        if(mLevel <= to && mLevel < 10000)
        {
            mHandler.postDelayed(growth, 30);
        }
        else mHandler.removeCallbacks(growth);
    }*/
    /*----------*/
    private ActivityMainBinding binder;
    private boolean isOpen = false;
    private final ArrayList<Player> PLAYERS = new ArrayList<>();
    private final PlayerCreateAdapter creator = new PlayerCreateAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /* if(!sessionLoadAdDone)
        {
            sessionLoadAdDone = true;
            startActivity(new Intent(this, AdActivity.class));
            finish();
        }*/

        setTheme(darkTheme ? R.style.AppTheme_Dark_NoActionBar : R.style.AppTheme_NoActionBar);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binder.toolbar);

        getSupportActionBar().hide();
        initFab(binder.fabAddExternalPlayer, 0);
        initFab(binder.fabDowloadCloudPlayers, 0);
        initFab(binder.settingsFab, 0);
        initFab(binder.fabAddLocalPlayer, 1);
        binder.content.playersList.setAdapter(creator);
        binder.content.playersList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        binder.setActivity(this);


        binder.content.addPlayer.setOnClickListener((v) -> inputNewPlayer(false));

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,android.R.color.transparent));

        //mHandler.post(growth);
    }

    public void openSettings(View v)
    {
        Intent i = new Intent(this, SettingsActivity.class);
        i.putExtra("dark", darkTheme);
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setupClazzsByMainstream();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadClazzsByMainstream();
    }

    public void setupClazzsByMainstream() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        /*for(Clazz c : Clazzs.CLAZZS)
        {
            editor.putBoolean(c.getNameLikeStr(getResources()), c.enabled);
            Main.p(c.getNameLikeStr(getResources())+ c.enabled);
        }*/
        editor.putInt("players_size", PLAYERS.size());
        for(int i  = 0; i < PLAYERS.size(); ++i)
        {
            if(PLAYERS.get(i).getName()!="")editor.putString("PLAYER " + i , PLAYERS.get(i).getName());
        }
        editor.apply();
    }

    public void loadClazzsByMainstream()
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        if(!pref.getBoolean("default_clazz", false))
        {
            Main.ARCHERY.setEnabled(true);
            Main.SWORDMAN.setEnabled(true);
            Main.LANCER.setEnabled(true);
            Main.DRAGON_HUNTER.setEnabled(true);
            Main.ADC.setEnabled(true);
        } else
        {
            Main.ARCHERY.setEnabled(pref.getBoolean("archery", true));
            Main.SWORDMAN.setEnabled(pref.getBoolean("swordman", true));
            Main.LANCER.setEnabled(pref.getBoolean("lancer", true));
            Main.DRAGON_HUNTER.setEnabled(pref.getBoolean("dragon_hunter", true));
            Main.ADC.setEnabled(pref.getBoolean("adc", true));
        }
        if(!pref.getBoolean("default_events",  false))
        {
            Main.EVENT_LIST.forEach(event ->
            {
                event.setEnabled(true);
                event.setRarity(event.defaultRarity());
            });
        } else
        {
            Main.EVENT_LIST.forEach(event -> {
                String evtKey = "EVTLOADER_" + event.getNameLikeStr(getResources());
                event.setEnabled(pref.getBoolean(evtKey, true));
                event.setRarity(Rarity.withName(pref.getString(evtKey+"_Rarity=", event.getDefaultRarity())));
            });
        }

        if(PLAYERS.size() <= 0) {
            for(int i = 0; i < pref.getInt("players_size", 4); ++i)
            {
                String s = pref.getString("PLAYER " + i, "");
                boolean b = pref.getBoolean("PLAYER " + i + "is_dev", false);
                if(s!="")PLAYERS.add(new Player(s).setDev(b));
            }
            updatePlayesList();
        }
    }
    private void initFab(View v, int t)
    {
        switch(t)
        {
            case 0:
                v.animate().alpha(0).translationYBy(v.getHeight());
                v.setVisibility(View.INVISIBLE);
                break;
            case 1:
                v.animate().alpha(0).translationXBy(v.getWidth());
                v.setVisibility(View.INVISIBLE);

        }
    }
    private void unshowFab(final View v, int t)
    {
        switch(t)
        {
            case 0:
                v.setVisibility(View.VISIBLE);
                v.setAlpha(1f);
                v.setTranslationY(0);
                v.animate()
                        .setDuration(200)
                        .translationY(v.getHeight())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                v.setVisibility(View.INVISIBLE);
                                super.onAnimationEnd(animation);
                            }
                        }).alpha(0f)
                        .start();
                break;
            case 1:
                v.setVisibility(View.VISIBLE);
                v.setAlpha(1f);
                v.setTranslationX(0);
                v.animate()
                        .setDuration(200)
                        .translationX(v.getWidth())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                v.setVisibility(View.INVISIBLE);
                                super.onAnimationEnd(animation);
                            }
                        }).alpha(0f)
                        .start();
                break;
        }
    }

    private void showFab(final View v, int t)
    {
        switch(t)
        {
            case 0:
                v.setVisibility(View.VISIBLE);
                v.setAlpha(0f);
                v.setTranslationY(v.getHeight());
                v.animate()
                        .setDuration(200)
                        .translationY(0)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                            }
                        })
                        .alpha(1f)
                        .start();
                break;
            case 1:
                v.setVisibility(View.VISIBLE);
                v.setAlpha(0f);
                v.setTranslationX(v.getWidth());
                v.animate()
                        .setDuration(200)
                        .translationX(0)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                            }
                        })
                        .alpha(1f)
                        .start();
                break;
        }
    }
    public void openAndShow(View v)
    {
        if(isOpen)
        {
            v.animate().setDuration(300).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    unshowFab(binder.fabDowloadCloudPlayers, 0);
                    unshowFab(binder.fabAddExternalPlayer, 0);
                    unshowFab(binder.settingsFab, 0);
                    unshowFab(binder.fabAddLocalPlayer, 1);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isOpen = false;
                }
            }).rotation(0f);
        }
        else
        {
            v.animate().setDuration(300).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    showFab(binder.fabDowloadCloudPlayers, 0);
                    showFab(binder.fabAddExternalPlayer, 0);
                    showFab(binder.settingsFab, 0);
                    showFab(binder.fabAddLocalPlayer, 1);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isOpen = true;
                }
            }).rotation(135f);
        }
    }

    private void updatePlayesList()
    {
        creator.setPlayer(PLAYERS);
    }
    public void inputNewPlayer(boolean from)
    {
        final AddNewPlayer player = new AddNewPlayer();
        player.setStyle(DialogFragment.STYLE_NORMAL, darkTheme ? R.style.AppTheme_Dark : R.style.AppTheme);
        player.setOnCompleteListener(() -> {
            PLAYERS.add(player.binder.getNewPlayer());
            updatePlayesList();
            if(from)binder.fab.performClick();
        });
        player.setOnFailListener(() -> {
            Toast.makeText(MainActivity.this, R.string.mustaddplayername, Toast.LENGTH_SHORT).show();
            if(from)binder.fab.performClick();
        });
        player.show(this.getSupportFragmentManager(), "");
    }

    public void removePlayer(int index)
    {
        this.PLAYERS.remove(index);
        updatePlayesList();
    }
    public void startGame(View v)
    {
        if(PLAYERS.size()>=4)
        {
            final ArrayList<Event> EVTS = new ArrayList<>();
            Main.prepare(PLAYERS, EVTS);
            Bundle b = new Bundle();
            b.putParcelableArrayList("PLAYERS", PLAYERS);
            Intent i = new Intent(this, GameFlux.class);
            i.putExtras(b);
            startActivity(i);
            finish();
        } else Toast.makeText(this, R.string.player_limit, Toast.LENGTH_SHORT).show();
    }

}
