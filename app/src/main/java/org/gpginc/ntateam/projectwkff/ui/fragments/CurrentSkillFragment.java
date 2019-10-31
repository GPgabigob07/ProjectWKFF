package org.gpginc.ntateam.projectwkff.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.SkillBinder;
import org.gpginc.ntateam.projectwkff.runtime.ClazzSkill;


public class CurrentSkillFragment extends BaseFluxFrag
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SkillBinder binder = DataBindingUtil.inflate(inflater,R.layout.fragment_current_skill, container, false);
        ClazzSkill sk = getArguments().getParcelable("skill");
        binder.setCurrentSkill(sk);
        binder.setRES(this);
        sk.runSkill(binder);
        return binder.getRoot();
    }
}
