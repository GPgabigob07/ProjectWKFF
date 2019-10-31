package org.gpginc.ntateam.projectwkff.runtime.dragons.skills;

import android.os.Parcel;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.SkillBinder;
import org.gpginc.ntateam.projectwkff.runtime.dragons.Dragon;
import org.gpginc.ntateam.projectwkff.runtime.util.SkillUtils;
import org.gpginc.ntateam.projectwkff.ui.widget.adapters.PlayerSelectionAdapter;
import org.gpginc.ntateam.projectwkff.ui.widget.dialogs.MessageDialog;

public class DragonAttack extends BaseDragonSkill
{

    public DragonAttack() {
        super(R.string.skill_attack_dragon, PLAYER_SELECTION_LAYOUT, Type.ATTACK, 2345);
    }

    public DragonAttack(Parcel in) {
        super(in);
    }

    @Override
    public boolean doDragonHaveEnergyEnough(Dragon d) {
        return d.power >= 2345;
    }

    @Override
    public void runFromBehaviour(GameFlux res) {

    }

    @Override
    public BaseDragonSkill newInstance() {
        return new DragonAttack();
    }

    @Override
    public Creator getCreator() {
        return null;
    }

    @Override
    public void runSkill(ViewDataBinding skill)
    {
        if(doDragonHaveEnergyEnough(this.dragon))
        {
            SkillBinder binder = (SkillBinder) skill;
            binder.currentLayout.setDisplayedChild(this.layout);
            final PlayerSelectionAdapter adapter = SkillUtils.getAdapterFor(binder.getRES().RES.CP(), binder.getRES().RES.PLAYERS, 1, true, 0,1,2,3);
            binder.selectionLayout.playerSelectable.setAdapter(adapter);
            binder.selectionLayout.playerSelectable.setLayoutManager(new LinearLayoutManager(binder.getRES().RES));

            binder.selectionLayout.skillFunc.setOnClickListener(v ->
            {
                if(adapter.getSelectedCount() > 0)
                {
                    SkillUtils.manageDragonInteraction(this.dragon, this.dragon.attack(adapter.getSelected()[0]), binder.getRES());
                }
                else
                {
                    new MessageDialog.Display(binder.getRES().RES, R.string.effect_efx_youareblinded).withListener(() -> binder.getRES().RES.onBackPressed()).prompt();
                }
            });
        }
        else
        {
            new MessageDialog.Display().withListener(() -> ((SkillBinder)skill).getRES().RES.onBackPressed()).directPrompt(((SkillBinder)skill).getRES().RES.getSupportFragmentManager(), R.string.dragonenoughenergy);
        }

    }

    public static Creator<DragonAttack> CREATOR = new Creator<DragonAttack>() {
        @Override
        public DragonAttack createFromParcel(Parcel source) {
            return new DragonAttack((source));
        }

        @Override
        public DragonAttack[] newArray(int size) {
            return new DragonAttack[size];
        }
    };


}
