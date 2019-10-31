package org.gpginc.ntateam.projectwkff.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.PlayerBattleBinder;
import org.gpginc.ntateam.projectwkff.databinding.PlayerStatusBinder;
import org.gpginc.ntateam.projectwkff.runtime.util.Util;
import org.gpginc.ntateam.projectwkff.ui.widget.adapters.EffectsAdapter;
import org.gpginc.ntateam.projectwkff.ui.widget.adapters.EventsAdapter;
import org.gpginc.ntateam.projectwkff.ui.widget.adapters.SkillAdapter;

public class PlayerInfosFragments extends BaseFluxFrag
{
    public PlayerInfosFragments() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        switch (args.getInt("kind"))
        {
            case 0:
                PlayerStatusBinder status = DataBindingUtil.inflate(inflater, R.layout.fragment_cp_status, container, false);
                status.setPlayer(RES.CP());
                status.setRES(RES);

                LinearLayoutManager layoutManager = new LinearLayoutManager(RES, RecyclerView.VERTICAL, false);
                LinearLayoutManager layoutManager2 = new LinearLayoutManager(RES, RecyclerView.VERTICAL, false);
                EffectsAdapter efxAdapter = new EffectsAdapter(RES.CP());
                EventsAdapter evtAdapter = new EventsAdapter(Util.getEventsForPlayer(RES.EVENTS, RES.CP()), RES.CP(), RES);

                status.effects.setAdapter(efxAdapter);
                status.effects.setLayoutManager(layoutManager);
                status.events.setAdapter(evtAdapter);
                status.events.setLayoutManager(layoutManager2);

                RES.CP().getClazz().runPassive(status);
                if(RES.CP().getTurnAttacked(RES.currentTurn))RES.CP().getClazz().runAttackTrigger(status);

                return status.getRoot();
            case 1:
                PlayerBattleBinder battle = DataBindingUtil.inflate(inflater, R.layout.fragment_cp_battle, container, false);
                battle.setPlayer(RES.CP());
                SkillAdapter adapter = new SkillAdapter(RES);
                if(RES.CP().getClazz().getSkills().size() > 0) {
                    battle.skills.setAdapter(adapter);
                    battle.skills.setLayoutManager(new LinearLayoutManager(RES, RecyclerView.VERTICAL, false));
                }
                if(RES.CP().getClazz().getSkills().stream().filter(s -> s.isPassive() || s.isAttackTriggered()).count() > 0) {
                    SkillAdapter.Passives passives = adapter.new Passives(RES);
                    battle.skillsPassives.setAdapter(passives);
                    battle.skillsPassives.setLayoutManager(new LinearLayoutManager(RES, RecyclerView.VERTICAL, false));
                }

                return battle.getRoot();
            default : return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    public static PlayerInfosFragments newInstance(int kind) {

        Bundle args = new Bundle();
        args.putInt("kind", kind);
        PlayerInfosFragments fragment = new PlayerInfosFragments();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
