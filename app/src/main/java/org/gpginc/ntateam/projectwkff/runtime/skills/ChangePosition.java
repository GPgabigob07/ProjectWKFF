package org.gpginc.ntateam.projectwkff.runtime.skills;

import android.graphics.PorterDuff;
import android.os.Parcel;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.SkillBinder;
import org.gpginc.ntateam.projectwkff.databinding.SkillLayoutChangeFieldBinding;
import org.gpginc.ntateam.projectwkff.runtime.ClazzSkill;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.util.Replay;
import org.gpginc.ntateam.projectwkff.ui.fragments.BaseFluxFrag;
import org.gpginc.ntateam.projectwkff.ui.widget.dialogs.MessageDialog;

public class ChangePosition extends ClazzSkill
{
    private int checked;
    private boolean external;
    private Player externalPlayer;

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
        if(!external) runToPlayer(binder.getRES().CP(), binder);
        else runToPlayer(externalPlayer, binder);
    }

    private void runToPlayer(Player p, SkillBinder binder)
    {
        if(!p.isStun) {
            binder.currentLayout.setDisplayedChild(this.layout);
            checked = p.getField();
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
            b1.moveFuncBtn.setOnClickListener(v -> checkFieldChange(binder.getRES(), checked, p));
        }
        else
        {
            new MessageDialog.Display().withListener(() -> binder.getRES().RES.onBackPressed()).directPrompt(binder.getRES().RES.getSupportFragmentManager(), externalPlayer!=null
                                                                                                                                                              ? R.string.playerisstun
                                                                                                                                                              : R.string.effect_efx_youarestunned);
        }
    }

    protected void unmarkAll(SkillBinder b, SkillLayoutChangeFieldBinding b1)
    {
        checked = -1;
        b1.fbtn1.clearColorFilter();
        b1.fbtn2.clearColorFilter();
        b1.fbtn3.clearColorFilter();
        b1.fbtn4.clearColorFilter();
    }
    protected void mark(View v, SkillBinder b, SkillLayoutChangeFieldBinding b1)
    {
        switch(v.getId())
        {
            case R.id.fbtn1: b1.fbtn1.setColorFilter(b.getRES().RES.getResources().getColor(android.R.color.holo_red_dark, null), PorterDuff.Mode.SRC_ATOP); checked = 0; break;
            case R.id.fbtn2: b1.fbtn2.setColorFilter(b.getRES().RES.getResources().getColor(android.R.color.holo_red_dark, null), PorterDuff.Mode.SRC_ATOP); checked = 1; break;
            case R.id.fbtn3: b1.fbtn3.setColorFilter(b.getRES().RES.getResources().getColor(android.R.color.holo_red_dark, null), PorterDuff.Mode.SRC_ATOP); checked = 2; break;
            case R.id.fbtn4: b1.fbtn4.setColorFilter(b.getRES().RES.getResources().getColor(android.R.color.holo_red_dark, null), PorterDuff.Mode.SRC_ATOP); checked = 3; break;
        }
    }
    public void checkFieldChange(final BaseFluxFrag frag, int field, Player p)
    {
        GameFlux res = frag.RES;
        if(p.getField() == field)
        {
            new MessageDialog.Display().directPrompt(res.getSupportFragmentManager(), externalPlayer!=null ? R.string.playerisinthisfield : R.string.uouareinthisfield);
        }
        else
        {
            new MessageDialog.Display(res, R.string.movedtofield, String.valueOf(field + 1)).withListener(() ->
            {
                String ac = String.format("%s: %s %s %s",
                        res.getString(R.string.replay_movedfiel1),
                        String.valueOf(p.getField()),
                        res.getString(R.string.replay_movedfiel2),
                        String.valueOf(field));
                res.replay.addAction(p, Replay.ReplayAction.Type.STRATEGY, ac, res.currentTurn);
                p.setField(field);
                frag.goNext();

            }).prompt();
        }
    }

    public static ChangePosition external(Player p)
    {
        ChangePosition SK = new ChangePosition();
        SK.externalPlayer = p;
        SK.external = true;
        return SK;
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
