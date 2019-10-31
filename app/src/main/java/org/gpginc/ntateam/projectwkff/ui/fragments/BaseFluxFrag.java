package org.gpginc.ntateam.projectwkff.ui.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.runtime.Player;

public class BaseFluxFrag extends Fragment
{
    public GameFlux RES;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RES = (GameFlux) getActivity();
    }

    public Player CP()
    {
        return RES.CP();
    }

    public void goNext()
    {
        do
        {
            if(RES.currentPlayerIndex < RES.PLAYERS.size() -1)
            {
                RES.currentPlayerIndex++;
            } else RES.currentPlayerIndex = 0;
        }while(RES.DEAD_PLAYERS.containsKey(RES.CP()));

        RES.goNext();
    }

}
