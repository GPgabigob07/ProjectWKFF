package org.gpginc.ntateam.projectwkff.ui.widget.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.ListItemSkillBinding;
import org.gpginc.ntateam.projectwkff.runtime.ClazzSkill;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.dragons.skills.BaseDragonSkill;

import java.util.ArrayList;
import java.util.List;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.ItemSkill>
{
    protected final Player p;
    protected final GameFlux res;
    protected final List<ClazzSkill> aplicableSkills = new ArrayList<>();

    public SkillAdapter(GameFlux res) {
        this.p = res.CP();
        for(ClazzSkill sk : p.getClazz().getSkills())
        {
            if(!sk.isAttackTriggered() && !sk.isPassive() && !sk.isCounter() /*&& /*!p.isAffected()*/) aplicableSkills.add(sk);
            else if(sk.isCounter() && p.getTurnAttacked(res.currentTurn))aplicableSkills.add(sk);
            /*else if(p.isAffected())
            {
                if(p.getEffects().contains(Main.STUN) && sk.isAttack());
            }*/
        }
        this.res = res;
    }

    @NonNull
    @Override
    public ItemSkill onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemSkillBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_skill, parent, false);
        return new ItemSkill(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSkill holder, int position) {
        if(aplicableSkills.size()>0) {
            holder.binder.setSkill(aplicableSkills.get(position));
            holder.binder.setRES(res);
            if(aplicableSkills.get(position) instanceof BaseDragonSkill)
            {
                String localename = holder.binder.skillName.getText().toString();
                holder.binder.skillName.setText(((BaseDragonSkill)holder.binder.getSkill()).getDragon().getNameAsString() + ": " + localename);
            }
        }
    }

    @Override
    public int getItemCount() {
        return aplicableSkills !=null ? aplicableSkills.size() : 0;
    }

    public class ItemSkill extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        protected final ListItemSkillBinding binder;

        public ItemSkill(ListItemSkillBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
            binder.skillName.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(binder.getSkill().isPassive() || binder.getSkill().isAttackTriggered());
            else binder.getRES().openSkill(binder.getSkill());
        }
    }

    public class Passives extends SkillAdapter
    {

        public Passives(GameFlux res) {
            super(res);
            this.aplicableSkills.clear();
            res.CP().getClazz().getSkills().stream().filter(skill -> skill.isPassive() || skill.isAttackTriggered()).forEach(aplicableSkills::add);
        }
    }
}
