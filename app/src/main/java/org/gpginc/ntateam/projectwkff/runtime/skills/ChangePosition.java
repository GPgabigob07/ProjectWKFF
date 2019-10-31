package org.gpginc.ntateam.projectwkff.runtime.skills;

import android.os.Parcel;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.SkillBinder;
import org.gpginc.ntateam.projectwkff.databinding.SkillLayoutChangeFieldBinding;
import org.gpginc.ntateam.projectwkff.runtime.ClazzSkill;
import org.gpginc.ntateam.projectwkff.runtime.Main;
import org.gpginc.ntateam.projectwkff.ui.fragments.BaseFluxFrag;
import org.gpginc.ntateam.projectwkff.ui.widget.dialogs.MessageDialog;

public class ChangePosition extends ClazzSkill 
{
    private int checked;
    public ChangePosition() {
        super(R.string.skill_change_position, PLAYER_CHANGE_FIELD, Type.MINDED);
    }

    protected ChangePosition(Parcel in) {
        super(in);
    }

    @Override
    public ChangePosition newInstance() {
        return new ChangePosition();
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    @Override
    public void runSkill(final ViewDataBinding bind)
    {
        final SkillBinder binder = (SkillBinder) bind;
        if(!binder.getRES().CP().isStun) {
            binder.currentLayout.setDisplayedChild(this.layout);
            checked = binder.getRES().RES.CP().getField();
            final SkillLayoutChangeFieldBinding b1 = DataBindingUtil.bind(binder.currentLayout.getChildAt(1));
            View.OnClickListener listener = v -> {
                unmarkAll(binder, b1);
                mark(v, binder, b1);
            };

            b1.fbtn1.setOnClickListener(listener);
            b1.fbtn2.setOnClickListener(listener);
            b1.fbtn3.setOnClickListener(listener);
            b1.fbtn4.setOnClickListener(listener);
            b1.setPlayer(binder.getRES().RES.CP());
            b1.moveFuncBtn.setOnClickListener(v -> checkFieldChange(binder.getRES(), checked));
        }
        else
        {
            new MessageDialog.Display().withListener(() -> binder.getRES().RES.onBackPressed()).directPrompt(binder.getRES().RES.getSupportFragmentManager(), R.string.effect_efx_youarestunned);
        }
    }

    protected void unmarkAll(SkillBinder b, SkillLayoutChangeFieldBinding b1)
    {
        checked = -1;
        b1.fbtn1.setColorFilter(b.getRES().RES.getResources().getColor(android.R.color.transparent, null));
        b1.fbtn2.setColorFilter(b.getRES().RES.getResources().getColor(android.R.color.transparent, null));
        b1.fbtn3.setColorFilter(b.getRES().RES.getResources().getColor(android.R.color.transparent, null));
        b1.fbtn4.setColorFilter(b.getRES().RES.getResources().getColor(android.R.color.transparent, null));
    }
    protected void mark(View v, SkillBinder b, SkillLayoutChangeFieldBinding b1)
    {
        switch(v.getId())
        {
            case R.id.fbtn1: b1.fbtn1.setColorFilter(b.getRES().RES.getResources().getColor(android.R.color.holo_red_dark, null)); checked = 0; break;
            case R.id.fbtn2: b1.fbtn2.setColorFilter(b.getRES().RES.getResources().getColor(android.R.color.holo_red_dark, null)); checked = 1; break;
            case R.id.fbtn3: b1.fbtn3.setColorFilter(b.getRES().RES.getResources().getColor(android.R.color.holo_red_dark, null)); checked = 2; break;
            case R.id.fbtn4: b1.fbtn4.setColorFilter(b.getRES().RES.getResources().getColor(android.R.color.holo_red_dark, null)); checked = 3; break;
        }
    }
    public void checkFieldChange(final BaseFluxFrag frag, int field)
    {
        GameFlux res = frag.RES;
        if(res.CP().getField() == field)
        {
            new MessageDialog.Display().directPrompt(res.getSupportFragmentManager(), R.string.uouareinthisfield);
        }
        else
        {
            new MessageDialog.Display(res, R.string.movedtofield, String.valueOf(field + 1)).withListener(() ->
            {
                res.CP().setField(field);
                frag.goNext();

            }).prompt();
        }
    }

    public static Creator<ChangePosition> CREATOR = new Creator<ChangePosition>() {
        @Override
        public ChangePosition createFromParcel(Parcel source) {
            return new ChangePosition(source);
        }

        @Override
        public ChangePosition[] newArray(int size) {
            return new ChangePosition[size];
        }
    };
}
