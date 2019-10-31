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

public class AttackSwordsman extends ClazzSkill {

    public AttackSwordsman() {
        super(R.string.skill_attack_sword, PLAYER_SELECTION_LAYOUT, Type.ATTACK);
    }

    protected AttackSwordsman(Parcel in) {
        super(in);
    }

    @Override
    public AttackSwordsman newInstance() {
        return new AttackSwordsman();
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    @Override
    public void runSkill(final ViewDataBinding bind)
    {
        final SkillBinder binder = (SkillBinder) bind;
        if(!binder.getRES().CP().isBlind) {
            final PlayerSelectionAdapter adapter = SkillUtils.getAdapterFor(binder.getRES().RES.CP(), binder.getRES().RES.PLAYERS, 2, false, binder.getRES().RES.CP().getField());
            if(adapter.hasSomeTarget()) {
                binder.currentLayout.setDisplayedChild(this.layout);
                binder.selectionLayout.playerSelectable.
                        setAdapter(adapter);
                binder.selectionLayout.playerSelectable.
                        setLayoutManager(new LinearLayoutManager(binder.getRES().RES, RecyclerView.VERTICAL, false));

                binder.selectionLayout.skillFunc.setOnClickListener(v -> {
                    MessageDialog.Display.InheritedDialog d = new MessageDialog.Display.InheritedDialog();
                    boolean b = true;
                    if (adapter.getSelectedCount() > 1) {
                        adapter.getSelected()[0].damage(1, binder.getRES().RES.CP());
                        adapter.getSelected()[1].damage(1, binder.getRES().RES.CP());
                        binder.getRES().goNext();
                        b = false;
                    } else if (adapter.getSelectedCount() == 1) {
                        d.setMsg(R.string.youcanattack2player);
                        d.setOnAcceptListener(() -> {
                            adapter.getSelected()[0].damage(2, binder.getRES().RES.CP());
                            binder.getRES().goNext();
                        });
                    } else {
                        d.setMsg(R.string.mustselectsomeplayer);
                    }
                    if(b)new MessageDialog.Display(binder.getRES().RES).from(d);
                });
            }
            else
            {
                new MessageDialog.Display(binder.getRES().RES, R.string.theresnoonethatucanattack).withListener(() -> binder.getRES().RES.onBackPressed()).prompt();
            }
        } else
        {
            new MessageDialog.Display(binder.getRES().RES, R.string.effect_efx_youareblinded).withListener(() -> binder.getRES().RES.onBackPressed()).prompt();
        }
    }

    public static Creator<AttackSwordsman> CREATOR = new Creator<AttackSwordsman>() {
        @Override
        public AttackSwordsman createFromParcel(Parcel source) {
            return new AttackSwordsman(source);
        }

        @Override
        public AttackSwordsman[] newArray(int size) {
            return new AttackSwordsman[size];
        }
    };
}
