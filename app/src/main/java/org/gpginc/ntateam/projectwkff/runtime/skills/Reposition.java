package org.gpginc.ntateam.projectwkff.runtime.skills;

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.SkillBinder;
import org.gpginc.ntateam.projectwkff.runtime.ClazzSkill;
import org.gpginc.ntateam.projectwkff.runtime.util.SkillUtils;
import org.gpginc.ntateam.projectwkff.runtime.variations.TurnSkill;
import org.gpginc.ntateam.projectwkff.ui.widget.adapters.PlayerSelectionAdapter;
import org.gpginc.ntateam.projectwkff.ui.widget.dialogs.MessageDialog;

public class Reposition extends TurnSkill {

    public Reposition() {
        super(R.string.skill_reposition, PLAYER_SELECTION_LAYOUT, Type.MINDED);
    }

    protected Reposition(Parcel in) {
        super(in);
    }

    @Override
    public ClazzSkill newInstance() {
        return new Reposition();
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    @Override
    public void runSkill(ViewDataBinding skill)
    {
        SkillBinder binder = ((SkillBinder) skill);
        PlayerSelectionAdapter adapter = SkillUtils.getAdapterFor(binder.getRES().CP(), binder.getRES().RES.PLAYERS, 1, true, 0,1,2,3).showFields();
        binder.currentLayout.setDisplayedChild(this.layout);

        binder.selectionLayout.playerSelectable.
                setAdapter(adapter);
        binder.selectionLayout.playerSelectable.
                setLayoutManager(new LinearLayoutManager(binder.getRES().RES, RecyclerView.VERTICAL, false));

        binder.selectionLayout.skillFunc.setOnClickListener(v ->{
            if(adapter.getSelectedCount() == 1)
            {
                ChangePosition.external(adapter.getSelected()[0]).runSkill(binder);
            }
            else
            {
                new MessageDialog.Display(binder.getRES().RES, R.string.mustselectsomeplayer).prompt();
            }
        });

    }

    public static final Creator <Reposition>  CREATOR = new Creator<Reposition>()
    {
        @Override
        public Reposition createFromParcel(Parcel source) {
            return new Reposition(source);
        }

        @Override
        public Reposition[] newArray(int size) {
            return new Reposition[size];
        }
    };
}
