package org.gpginc.ntateam.projectwkff.ui.widget.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.EndPlayerAttackers;
import org.gpginc.ntateam.projectwkff.databinding.EndPlayerEfx;
import org.gpginc.ntateam.projectwkff.databinding.ListItemFinalPlayerStatusBinding;
import org.gpginc.ntateam.projectwkff.runtime.BaseAttacker;
import org.gpginc.ntateam.projectwkff.runtime.Effect;
import org.gpginc.ntateam.projectwkff.runtime.Player;

import java.util.List;

public class WinnerPlayersAdapter extends RecyclerView.Adapter<WinnerPlayersAdapter.WinnerPlayerItem>
{
    protected List<Player> Winners;
    @NonNull
    @Override
    public WinnerPlayerItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemFinalPlayerStatusBinding bind = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_final_player_status, parent, false);
        return new WinnerPlayerItem(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull WinnerPlayerItem holder, int position) {
        holder.binder.setPlayer(Winners.get(position));
        holder.binder.atckrslist.setAdapter(new EndPlayerAttackersAdapter(Winners.get(position).getAttackers()));
        holder.binder.atckrslist.setLayoutManager(new LinearLayoutManager(holder.binder.getRoot().getContext(), RecyclerView.HORIZONTAL, false));
        holder.binder.efxlist.setAdapter(new EndPlayerEfxAdapter(Winners.get(position).getEffects()));
        holder.binder.efxlist.setLayoutManager(new LinearLayoutManager(holder.binder.getRoot().getContext(), RecyclerView.HORIZONTAL, false));
    }

    @Override
    public int getItemCount() {
        return Winners != null ? Winners.size() : 0;
    }

    public WinnerPlayersAdapter setWinners(List<Player> winners) {
        Winners = winners;
        notifyDataSetChanged();
        return this;
    }

    public class WinnerPlayerItem extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final ListItemFinalPlayerStatusBinding binder;
        protected boolean isShowing = false;

        public WinnerPlayerItem(ListItemFinalPlayerStatusBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
            binder.plusInfo.setOnClickListener(this);
            this.binder.dropdownExtras.animate().translationY(-this.binder.dropdownExtras.getHeight()).alpha(0);
        }

        @Override
        public void onClick(final View v) {
            if(!isShowing)
            {
                v.setRotation(0f);
                binder.dropdownExtras.setTranslationY(-this.binder.dropdownExtras.getHeight());
                binder.dropdownExtras.setVisibility(View.VISIBLE);
                binder.dropdownExtras.setAlpha(0);
                v.animate().setDuration(200).rotation(45f).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                }).start();
                binder.dropdownExtras.animate().setDuration(200).alpha(1).translationY(0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                }).start();
            } else
            {
                v.setRotation(45f);
                binder.dropdownExtras.setTranslationY(0);
                binder.dropdownExtras.setVisibility(View.VISIBLE);
                binder.dropdownExtras.setAlpha(1);
                v.animate().setDuration(200).rotation(0f).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                }).start();
                binder.dropdownExtras.animate().setDuration(200).alpha(0).translationY(-binder.dropdownExtras.getHeight()).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        binder.dropdownExtras.setVisibility(View.GONE);
                    }
                }).start();
            }
            isShowing = !isShowing;
        }
    }

    public static class EndPlayerEfxAdapter extends RecyclerView.Adapter<EndPlayerEfxAdapter.EPEFXItem>
    {
        private List<Effect> effects;

        public EndPlayerEfxAdapter(List<Effect> effects) {
            this.effects = effects;
        }

        @NonNull
        @Override
        public EPEFXItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            EndPlayerEfx epe = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_end_player_effects, parent, false);
            return new EPEFXItem(epe);
        }

        @Override
        public void onBindViewHolder(@NonNull EPEFXItem holder, int position)
        {
            holder.binder.setEfx(effects.get(position));
        }

        @Override
        public int getItemCount() {
            return effects!=null ? effects.size() : 0;
        }

        public class EPEFXItem extends RecyclerView.ViewHolder
        {
            private final EndPlayerEfx binder;

            public EPEFXItem(EndPlayerEfx binder) {
                super(binder.getRoot());
                this.binder = binder;
            }
        }
    }
    public static class EndPlayerAttackersAdapter extends RecyclerView.Adapter<EndPlayerAttackersAdapter.EPAItem>
    {
        private List<BaseAttacker> attackers;

        public EndPlayerAttackersAdapter(List<BaseAttacker> attackers) {
            this.attackers = attackers;
        }

        @NonNull
        @Override
        public EPAItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            EndPlayerAttackers epe = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_end_player_attackers, parent, false);
            return new EPAItem(epe);
        }

        @Override
        public void onBindViewHolder(@NonNull EPAItem holder, int position)
        {
            holder.binder.setAttacker(attackers.get(position));
        }

        @Override
        public int getItemCount() {
            return attackers!=null ? attackers.size() : 0;
        }

        public class EPAItem extends RecyclerView.ViewHolder
        {
            private final EndPlayerAttackers binder;

            public EPAItem(EndPlayerAttackers binder) {
                super(binder.getRoot());
                this.binder = binder;
            }
        }
    }
}
