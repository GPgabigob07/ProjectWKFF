package org.gpginc.ntateam.projectwkff.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.CurrentPlayer;
import org.gpginc.ntateam.projectwkff.ui.widget.adapters.CurrentPlayerTabsAdapter;

public class CurrentPlayerFragment extends BaseFluxFrag
{
    private CurrentPlayer binder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_current_player, container, false);
        binder.setFrag(this);
        binder.setPlayer(RES.CP());

        setupWindow();
        return binder.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(getArguments()!=null)getArguments().putInt("CTAB", binder.pager.getCurrentItem() + 777);
        else
        {
            Bundle args = new Bundle();
            args.putInt("CTAB", binder.pager.getCurrentItem() + 777);
            this.setArguments(args);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getArguments()!=null) {
            if (getArguments().getInt("CTAB") != 0) {
                setupWindow();
                binder.pager.setCurrentItem(getArguments().getInt("CTAB") - 777);
            }
        }
    }

    protected void setupWindow()
    {

        CurrentPlayerTabsAdapter adapter = new CurrentPlayerTabsAdapter(getChildFragmentManager());
        adapter.add(PlayerInfosFragments.newInstance(0), RES.getResources().getString(R.string.label_status));
        adapter.add(PlayerInfosFragments.newInstance(1), RES.getResources().getString(R.string.label_battle));
        if(RES.allDebugEnabled || RES.CP().isDev) adapter.add(PlayerInfosFragments.newInstance(7097), RES.getResources().getString(R.string.label_debug));

        binder.pager.setAdapter(adapter);
        binder.tabs.setupWithViewPager(binder.pager);
    }
}
