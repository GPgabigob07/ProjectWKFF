package org.gpginc.ntateam.projectwkff.ui.widget.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.ListItemDeadPlayerBinding;
import org.gpginc.ntateam.projectwkff.runtime.Player;

import java.util.ArrayList;
import java.util.List;

public class DeadPlayerAdapter extends RecyclerView.Adapter<DeadPlayerAdapter.DeadPlayerListItem>
{

    private List<Player> playerList = new ArrayList<>();
    @NonNull
    @Override
    public DeadPlayerListItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemDeadPlayerBinding bind = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_dead_player, parent, false);
        return new DeadPlayerListItem(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull DeadPlayerListItem holder, int position) {
        holder.binder.setPlayer(playerList.get(position));
    }

    public DeadPlayerAdapter setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
        notifyDataSetChanged();
        return this;
    }

    @Override
    public int getItemCount() {
        return playerList != null ? playerList.size() : 0;
    }

    public class DeadPlayerListItem extends RecyclerView.ViewHolder
    {
        private ListItemDeadPlayerBinding binder;

        public DeadPlayerListItem(ListItemDeadPlayerBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }
}
