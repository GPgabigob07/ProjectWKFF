package org.gpginc.ntateam.projectwkff.runtime.skills;

import android.os.Parcel;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.SkillBinder;
import org.gpginc.ntateam.projectwkff.runtime.ClazzSkill;
import org.gpginc.ntateam.projectwkff.runtime.Main;
import org.gpginc.ntateam.projectwkff.runtime.util.SkillUtils;
import org.gpginc.ntateam.projectwkff.ui.widget.adapters.PlayerSelectionAdapter;
import org.gpginc.ntateam.projectwkff.ui.widget.dialogs.MessageDialog;

import java.util.Random;

public class AttackLancer extends ClazzSkill
{
    public AttackLancer() {
        super(R.string.skill_attack_lance, PLAYER_SELECTION_LAYOUT, Type.ATTACK);
    }

    protected AttackLancer(Parcel in) {
        super(in);
    }

    @Override
    public AttackLancer newInstance() {
        return new AttackLancer();
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    @Override
    public void runSkill(final ViewDataBinding bind)
    {
        final SkillBinder binder = (SkillBinder) bind;
        if(!binder.getRES().CP().isBlind)
        {
            int attackField = binder.getRES().RES.CP().getField() == 0 ? 3 : binder.getRES().RES.CP().getField() == 1 ? 2 : binder.getRES().RES.CP().getField() == 2 ? 1 : 0;
            final PlayerSelectionAdapter adapter = SkillUtils.getAdapterFor(binder.getRES().RES.CP(), binder.getRES().RES.PLAYERS, 1, false, attackField, binder.getRES().RES.CP().getField());
            if(adapter.hasSomeTarget()) {
                binder.currentLayout.setDisplayedChild(this.layout);
                binder.selectionLayout.playerSelectable.
                        setAdapter(adapter);
                binder.selectionLayout.playerSelectable.
                        setLayoutManager(new LinearLayoutManager(binder.getRES().RES, RecyclerView.VERTICAL, false));

                binder.selectionLayout.skillFunc.setOnClickListener(v -> {
                    if (adapter.getSelectedCount() > 0) {
                        adapter.getSelected()[0].damage(1, binder.getRES().RES.CP());
                        binder.getRES().RES.CP().setField(new Random().nextInt(4));
                        binder.getRES().goNext();
                    } else {
                       new MessageDialog.Display(binder.getRES().RES, R.string.mustselectsomeplayer).prompt();
                    }
                });
            } else
            {
                new MessageDialog.Display(binder.getRES().RES, R.string.theresnoonethatucanattack).withListener(() -> binder.getRES().RES.onBackPressed()).prompt();
            }
        } else
        {
            new MessageDialog.Display(binder.getRES().RES, R.string.effect_efx_youareblinded).withListener(() -> binder.getRES().RES.onBackPressed()).prompt();
        }
    }

    public static Creator<AttackLancer> CREATOR = new Creator<AttackLancer>() {
        @Override
        public AttackLancer createFromParcel(Parcel source) {
            return new AttackLancer(source);
        }

        @Override
        public AttackLancer[] newArray(int size) {
            return new AttackLancer[size];
        }
    };
}
