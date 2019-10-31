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

public class AttackArchery extends ClazzSkill {


    public AttackArchery() {
        super(R.string.skill_attack_bow, PLAYER_SELECTION_LAYOUT, Type.ATTACK);
    }

    protected AttackArchery(Parcel in) {
        super(in);
    }

    @Override
    public AttackArchery newInstance() {
        return new AttackArchery();
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    @Override
    public void runSkill(final ViewDataBinding bind) {
        final SkillBinder binder = (SkillBinder) bind;
        if(!binder.getRES().CP().isBlind)
        {
            final PlayerSelectionAdapter adapter = SkillUtils.getAdapterFor(binder.getRES().CP(), binder.getRES().RES.PLAYERS, 1, true, 0, 1, 2, 3);
            binder.currentLayout.setDisplayedChild(this.layout);
            binder.selectionLayout.playerSelectable.
                    setAdapter(adapter);
            binder.selectionLayout.playerSelectable.
                    setLayoutManager(new LinearLayoutManager(binder.getRES().RES, RecyclerView.VERTICAL, false));

            binder.selectionLayout.skillFunc.setOnClickListener(v -> {
                if (adapter.getSelectedCount() > 0) {
                    adapter.getSelected()[0].damage(1, binder.getRES().CP());
                    if (!binder.getRES().CP().isStun) binder.getRES().CP().setField(new Random().nextInt(4));
                    binder.getRES().goNext();
                } else {
                    new MessageDialog.Display(binder.getRES().RES, R.string.mustselectsomeplayer);
                }
            });
        } else
        {
            new MessageDialog.Display(binder.getRES().RES, R.string.effect_efx_youareblinded).withListener(() -> binder.getRES().RES.onBackPressed()).prompt();
        }

    }

    public static Creator<AttackArchery> CREATOR = new Creator<AttackArchery>() {
        @Override
        public AttackArchery createFromParcel(Parcel source) {
            return new AttackArchery(source);
        }

        @Override
        public AttackArchery[] newArray(int size) {
            return new AttackArchery[size];
        }
    };
}
