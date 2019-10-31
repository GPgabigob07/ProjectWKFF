package org.gpginc.ntateam.projectwkff.ui.widget.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.ListItemEffectsBinding;
import org.gpginc.ntateam.projectwkff.runtime.Effect;
import org.gpginc.ntateam.projectwkff.runtime.Player;

import java.util.ArrayList;
import java.util.List;

public class EffectsAdapter extends RecyclerView.Adapter<EffectsAdapter.EffectItem>
{
    private final List<Effect> EFX = new ArrayList<>();

    public EffectsAdapter(Player p)
    {
       p.getEffects().stream().filter(e -> e.still(p.currentTurn)).forEach(EFX::add);
    }

    @NonNull
    @Override
    public EffectItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemEffectsBinding bind = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_effects, parent, false);
        return new EffectItem(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull EffectItem holder, int position) {
        holder.binder.setEffect(EFX.get(position));
    }

    @Override
    public int getItemCount() {
        return EFX != null ? EFX.size() : 0;
    }


    public class EffectItem extends RecyclerView.ViewHolder
    {
        private ListItemEffectsBinding binder;
        public EffectItem(ListItemEffectsBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }
}
