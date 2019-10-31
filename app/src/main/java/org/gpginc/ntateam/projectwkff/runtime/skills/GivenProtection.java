package org.gpginc.ntateam.projectwkff.runtime.skills;

import android.os.Parcel;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.SkillBinder;
import org.gpginc.ntateam.projectwkff.runtime.ClazzSkill;
import org.gpginc.ntateam.projectwkff.runtime.util.SkillUtils;
import org.gpginc.ntateam.projectwkff.ui.widget.adapters.PlayerSelectionAdapter;
import org.gpginc.ntateam.projectwkff.ui.widget.dialogs.MessageDialog;

public class GivenProtection extends ClazzSkill 
{
    public GivenProtection() {
        super(R.string.skill_given_protection, PLAYER_SELECTION_LAYOUT, Type.MAHOU);
    }

    protected GivenProtection(Parcel in) {
        super(in);
    }

    @Override
    public GivenProtection newInstance() {
        return new GivenProtection();
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    @Override
    public void runSkill(final ViewDataBinding bind)
    {
        final SkillBinder binder = (SkillBinder) bind;
        final PlayerSelectionAdapter adapter = SkillUtils.getAdapterFor(binder.getRES().RES.CP(), binder.getRES().RES.PLAYERS,1, true, 0,1,2,3);
        binder.currentLayout.setDisplayedChild(this.layout);
        binder.selectionLayout.playerSelectable.
                setAdapter(adapter);
        binder.selectionLayout.playerSelectable.
                setLayoutManager(new LinearLayoutManager(binder.getRES().RES, RecyclerView.VERTICAL, false));

        binder.selectionLayout.skillFunc.setOnClickListener(v -> {
            if(adapter.getSelectedCount() > 0) {
                adapter.getSelected()[0].setProtected(true);
                binder.getRES().goNext();
            } else {
                new MessageDialog.Display(binder.getRES().RES, R.string.mustselectsomeplayer).prompt();
            }
        });

    }

    public static Creator<GivenProtection> CREATOR = new Creator<GivenProtection>() {
        @Override
        public GivenProtection createFromParcel(Parcel source) {
            return new GivenProtection(source);
        }

        @Override
        public GivenProtection[] newArray(int size) {
            return new GivenProtection[size];
        }
    };
}
