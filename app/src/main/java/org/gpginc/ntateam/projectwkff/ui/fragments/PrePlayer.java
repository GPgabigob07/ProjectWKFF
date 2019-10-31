package org.gpginc.ntateam.projectwkff.ui.fragments;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.FragmentPrePlayerBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrePlayer extends BaseFluxFrag {


    private FragmentPrePlayerBinding binder;
    private boolean SWITCH = false;

    public PrePlayer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if(RES.PLAYERS.size() <= RES.GONE.size() + RES.DEAD_PLAYERS.size())
        {
            RES.navController.navigate(R.id.action_prePlayer_to_damageStepFragment);
        }else {
            binder = DataBindingUtil.inflate(inflater, R.layout.fragment_pre_player, container, false);
            binder.showPlayer.setVisibility(View.GONE);
            binder.labelExtra.setTitle(R.string.title_waiting_player);
            binder.setFrag(this);
            binder.setActivity(RES);
            return binder.getRoot();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void switches()
    {
        AnimatorListenerAdapter mListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        };

        if(SWITCH)
        {
           // binder.switcher.animate().setListener(mListener).setDuration(100).alpha(0).start();
            binder.iconCamelot.animate().setListener(mListener).setDuration(200).translationXBy( - (float)(binder.iconCamelot.getWidth() * 1.5)).start();
            binder.iconOhxer.animate().setListener(mListener).setDuration(200).translationXBy((float)(binder.iconOhxer.getWidth() * 1.5)).start();
           // binder.switcher.setBackgroundResource(R.drawable.shields);
          //  binder.switcher.animate().setListener(mListener).setDuration(100).alpha(1).start();
        } else
        {
           // binder.switcher.animate().setListener(mListener).setDuration(100).alpha(0).start();
            binder.iconCamelot.animate().setListener(mListener).setDuration(200).translationXBy((float)(binder.iconCamelot.getWidth() * 1.5)).start();
            binder.iconOhxer.animate().setListener(mListener).setDuration(200).translationXBy( - (float)(binder.iconOhxer.getWidth() * 1.5)).start();
            //binder.switcher.setBackgroundResource(R.drawable.shields2);
           // binder.switcher.animate().setListener(mListener).setDuration(100).alpha(1).start();
        }
        SWITCH = !SWITCH;
        binder.showPlayer.setVisibility(SWITCH ? View.VISIBLE : View.GONE);
    }


}
