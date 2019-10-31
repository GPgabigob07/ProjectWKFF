package org.gpginc.ntateam.projectwkff.runtime.skills;

import android.os.Parcel;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.SkillBinder;
import org.gpginc.ntateam.projectwkff.runtime.ClazzSkill;
import org.gpginc.ntateam.projectwkff.runtime.Main;
import org.gpginc.ntateam.projectwkff.runtime.events.Alchemist;
import org.gpginc.ntateam.projectwkff.runtime.util.EffectWalet;
import org.gpginc.ntateam.projectwkff.runtime.util.SkillUtils;
import org.gpginc.ntateam.projectwkff.ui.widget.adapters.EffectWaletAdapter;
import org.gpginc.ntateam.projectwkff.ui.widget.adapters.PlayerSelectionAdapter;
import org.gpginc.ntateam.projectwkff.ui.widget.dialogs.MessageDialog;
import org.gpginc.ntateam.projectwkff.ui.widget.dialogs.RecyclerDialog;

public class Infectious extends ClazzSkill {
    
    public Infectious() {
        super(R.string.skill_effectation, PLAYER_SELECTION_LAYOUT, Type.MAHOU);
    }

    public Infectious(Parcel in) {
        super(in);
    }
    
    @Override
    public ClazzSkill newInstance() {
        return new Infectious();
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    @Override
    public void runSkill(final ViewDataBinding bind)
    {
        final SkillBinder binder = (SkillBinder) bind;
        int f1, f2;
        switch(binder.getRES().RES.CP().getField()) {
            case 1:
            case 2:
                f1=0;
                f2=3;
                break;
            default:
                f1 = 1;
                f2 = 2;
                break;
        }
        final PlayerSelectionAdapter adapter = SkillUtils.getAdapterFor(binder.getRES().RES.CP(), binder.getRES().RES.PLAYERS,1, true, f1, f2);
        binder.currentLayout.setDisplayedChild(this.layout);
        binder.selectionLayout.playerSelectable.
                setAdapter(adapter);
        binder.selectionLayout.playerSelectable.
                setLayoutManager(new LinearLayoutManager(binder.getRES().RES, RecyclerView.VERTICAL, false));

        binder.selectionLayout.skillFunc.setOnClickListener(v -> {
            if(adapter.getSelectedCount() > 0) {
                if (binder.getRES().RES.EVENTS.contains(Main.ALCHEMIST) && binder.getRES().RES.CP().equals(binder.getRES().RES.EVENTS.get(binder.getRES().RES.EVENTS.indexOf(Main.ALCHEMIST)).getOwner())) {
                    EffectWaletAdapter adapter2 = new EffectWaletAdapter();
                    adapter2.setWalet(((Alchemist) binder.getRES().RES.EVENTS.get(binder.getRES().RES.EVENTS.indexOf(Main.ALCHEMIST))).getWalet());
                    final RecyclerDialog<EffectWaletAdapter> dialog = new RecyclerDialog<>(adapter2);
                    adapter2.setOnItemSelected(pos -> {
                        if (((Alchemist) binder.getRES().RES.EVENTS.get(binder.getRES().RES.EVENTS.indexOf(Main.ALCHEMIST))).getWalet().items.get(pos).getCount() > 0) {
                            adapter.getSelected()[0].affect(((Alchemist) binder.getRES().RES.EVENTS.get(binder.getRES().RES.EVENTS.indexOf(Main.ALCHEMIST))).getWalet().items.get(pos).consume());
                            binder.getRES().goNext();
                            dialog.dismiss();
                        } else {
                            new MessageDialog.Display(binder.getRES().RES, R.string.wallet_insuf).prompt();
                        }
                    });
                    dialog.show(binder.getRES().RES.getSupportFragmentManager(), "");

            } else
            {
                final EffectWalet walet = new EffectWalet().random();
                EffectWaletAdapter adapter3 = new EffectWaletAdapter();
                adapter3.setWalet(walet);
                final RecyclerDialog<EffectWaletAdapter> dialog = new RecyclerDialog<>(adapter3);
                adapter3.setOnItemSelected(pos -> {
                    if (walet.items.get(pos).getCount() > 0) {
                        adapter.getSelected()[0].affect(walet.items.get(pos).consume());
                        binder.getRES().goNext();
                        dialog.dismiss();
                    } else {
                        new MessageDialog.Display(binder.getRES().RES, R.string.wallet_insuf).prompt();
                    }
                });
                dialog.show(binder.getRES().RES.getSupportFragmentManager(), "");
            }
        } else {
            new MessageDialog.Display(binder.getRES().RES, R.string.mustselectsomeplayer).prompt();
        }
    });
    }

    public static Creator<Infectious> CREATOR = new Creator<Infectious>() {
        @Override
        public Infectious createFromParcel(Parcel source) {
            return new Infectious(source);
        }

        @Override
        public Infectious[] newArray(int size) {
            return new Infectious[size];
        }
    };
}
