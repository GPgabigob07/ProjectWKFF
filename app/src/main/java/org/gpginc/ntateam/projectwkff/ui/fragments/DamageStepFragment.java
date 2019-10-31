package org.gpginc.ntateam.projectwkff.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.FragmentDamageStepBinding;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.projectwkff.ui.widget.adapters.DeadPlayerAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DamageStepFragment extends BaseFluxFrag
{
    private FragmentDamageStepBinding binder;
    private List<Player> playerList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_damage_step, container, false);
        binder.setRes(RES);
        RES.currentPlayerIndex = 0;
        Collections.shuffle(RES.PLAYERS);
        RES.setTitle(R.string.label_damage_step);
        RES.currentTurn++;
        DAMAGESTEP(RES.PLAYERS, RES);
        if(RES.DEAD_PLAYERS.size()>0)RES.checkEvents(EventHandler.ON_DEATH,RES.DEAD_PLAYERS.keySet());
        RES.PLAYERS.stream().filter(RES.DEAD_PLAYERS::containsKey).filter(p -> RES.DEAD_PLAYERS.get(p) == RES.currentTurn).forEach(playerList::add);
        if(playerList.size()>0)binder.maybeInfo.setVisibility(View.GONE);
        DeadPlayerAdapter a = new DeadPlayerAdapter().setPlayerList(playerList);
        binder.deadPlayersList.setLayoutManager(new LinearLayoutManager(RES));
        binder.deadPlayersList.setAdapter(a);
        RES.GONE.clear();

        return binder.getRoot();
    }

    public static void DAMAGESTEP(Collection<Player> players, GameFlux res)
    {
        players.forEach(p ->{
            p.damageStep(res);
        });
    }
}
