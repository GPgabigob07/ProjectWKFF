package org.gpginc.ntateam.projectwkff.ui.widget.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.ListItemEffectWalletBinding;
import org.gpginc.ntateam.projectwkff.runtime.Effect;
import org.gpginc.ntateam.projectwkff.runtime.util.EffectWalet;

public class EffectWaletAdapter extends RecyclerView.Adapter<EffectWaletAdapter.ListItemEffectWaletItem>
{

    private EffectWalet walet = new EffectWalet();
    private OnItemSelected mListener;

    @NonNull
    @Override
    public ListItemEffectWaletItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemEffectWalletBinding bind = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_effect_wallet, parent, false);
        return new ListItemEffectWaletItem(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemEffectWaletItem holder, int position) {
        holder.binder.setItem(walet.items.get(position));
    }

    @Override
    public int getItemCount() {
        return walet != null ? walet.items.size() : 0;
    }

    public EffectWaletAdapter setWalet(EffectWalet walet) {
        this.walet = walet;
        return this;
    }

    public void addEffectToWallet(Effect e, int count)
    {
        this.walet.addItem(new EffectWalet.EffectWaletItem(e, count));
    }


    public void setOnItemSelected(OnItemSelected mListener) {
        this.mListener = mListener;
    }

    public interface OnItemSelected
    {
        void OnSelect(int pos);
    }

    public class ListItemEffectWaletItem extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public final ListItemEffectWalletBinding binder;

        public ListItemEffectWaletItem(ListItemEffectWalletBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
            this.binder.efxWalletName.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.OnSelect(getAdapterPosition());
        }
    }

}
