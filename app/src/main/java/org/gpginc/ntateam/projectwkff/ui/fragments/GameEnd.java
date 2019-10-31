package org.gpginc.ntateam.projectwkff.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.FragmentGameEndBinding;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.projectwkff.ui.widget.adapters.WinnerPlayersAdapter;

import java.util.ArrayList;
import java.util.List;

public class GameEnd extends BaseFluxFrag {

    protected FragmentGameEndBinding binder;
    protected final List<Player> WINNERS = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RES.setSupportActionBar(null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        RES.checkEvents(EventHandler.ON_GAME_END, RES.PLAYERS);
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_game_end, container, false);
        RES.setSupportActionBar(binder.toolbar);
        binder.setEndEvt(RES.ENDEVENT);
        genWin();

        WinnerPlayersAdapter adapter = new WinnerPlayersAdapter().setWinners(WINNERS);
        binder.content.winners.setAdapter(adapter);
        binder.content.winners.setLayoutManager(new LinearLayoutManager(RES));


        ActionBar actionBar = RES.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        RES.setTitle(R.string.label_game_over);
        binder.content.endevnt.setEvent(RES.ENDEVENT);
        binder.content.endevnt.oDescription.setText(RES.ENDEVENT.getEndDescription());
        if(RES.ENDEVENT.isAttacher || RES.ENDEVENT.needPlayers)binder.content.endevnt.setPlayer(RES.ENDEVENT.getOwner());

        return binder.getRoot();
    }

    private void genWin()
    {
        for (Player player : RES.PLAYERS) {
            if (player.isWinner) {
                WINNERS.add(player);
            }
        }
    }
}
