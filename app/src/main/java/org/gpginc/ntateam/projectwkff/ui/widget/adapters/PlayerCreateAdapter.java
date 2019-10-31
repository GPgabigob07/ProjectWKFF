package org.gpginc.ntateam.projectwkff.ui.widget.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.ItemListPlayer;
import org.gpginc.ntateam.projectwkff.runtime.Player;

import java.util.List;

public class PlayerCreateAdapter extends RecyclerView.Adapter<PlayerCreateAdapter.ItemPlayerVH>
{
    private List<Player> player;

    @NonNull
    @Override
    public ItemPlayerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListPlayer bind = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_players, parent, false);
        return new ItemPlayerVH(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPlayerVH holder, int i) {
        holder.binder.setPlayer(player.get(i));
    }

    @Override
    public int getItemCount() {
        return player!=null ? player.size() : 0;
    }


    public void setPlayer(List<Player> player) {
        this.player = player;
        notifyDataSetChanged();
    }

    public class ItemPlayerVH extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final ItemListPlayer binder;
        private boolean isOpen = false;

        public ItemPlayerVH(@NonNull ItemListPlayer binder) {
            super(binder.getRoot());
            this.binder = binder;
            this.binder.pdrop.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {

            v.animate().setDuration(200).rotation(isOpen ? 0 : 135f).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            }).start();

            if(!isOpen)
            {
                binder.dropdown.setVisibility(View.VISIBLE);
                binder.dropdown.setAlpha(0f);
                //binder.dropdown.setTranslationY(0);
                binder.dropdown.animate()
                        .setDuration(200)
                        .translationYBy(binder.dropdown.getHeight())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                            }
                        }).alpha(1f)
                        .start();
            }
            else
            {
                binder.dropdown.setVisibility(View.VISIBLE);
                binder.dropdown.setAlpha(1f);
                //binder.dropdown.setTranslationY(binder.dropdown.getHeight());
                binder.dropdown.animate()
                        .setDuration(200)
                        .translationYBy(-binder.dropdown.getHeight())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                binder.dropdown.setVisibility(View.GONE);
                                super.onAnimationEnd(animation);
                            }
                        }).alpha(0f)
                        .start();
            }
            isOpen = !isOpen;
        }
    }
}
