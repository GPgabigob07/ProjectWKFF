package org.gpginc.ntateam.projectwkff.ui.widget.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.ListItemSPlayer;
import org.gpginc.ntateam.projectwkff.runtime.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerSelectionAdapter extends RecyclerView.Adapter<PlayerSelectionAdapter.ItemPlayerSelectable>
{
    protected List<Player> players ;
    protected final Map<Player, Boolean> map = new HashMap<>();
    protected final int max;
    protected int selected;
    protected final boolean showNames;

    public PlayerSelectionAdapter(int max, boolean showNames) {
        this.max = max;
        this.showNames = showNames;
    }

    @NonNull
    @Override
    public ItemPlayerSelectable onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemSPlayer binder = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_selectable_player, parent, false);
        return new ItemPlayerSelectable(binder,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPlayerSelectable holder, int position)
    {
        holder.binder.setPlayer(players.get(position));
    }

    @Override
    public int getItemCount() {
        return players != null ? players.size() : 0;
    }

    public PlayerSelectionAdapter setPlayers(List<Player> players) {
        this.players = players;
        for (Player p : players)
        {
            map.put(p, false);
        }
        notifyDataSetChanged();
        return this;
    }


    public Player[] getSelected()
    {
        Player[] s = new Player[max];
        int aux = 0;
        for(Player  p : players)
        {
            if(map.get(p))
            {
                s[aux] = p;
                aux++;
            }
        }
        return s;
    }

    public int getSelectedCount()
    {
        return this.selected;
    }
    public boolean hasSomeTarget()
    {
        return this.players.size() > 0;
    }
    public class ItemPlayerSelectable extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public boolean isSelected = false;
        private PlayerSelectionAdapter adapter;
        protected final ListItemSPlayer binder;
        private final boolean showName;
        public ItemPlayerSelectable(ListItemSPlayer binder, PlayerSelectionAdapter adapter) {
            super(binder.getRoot());
            this.binder = binder;
            binder.setHolder(this);
            this.adapter = adapter;
            binder.playerItemName.setOnClickListener(this);
            showName = adapter.showNames;
        }


        public boolean getShowNames()
        {
           return this.showName;
        }
        @Override
        public void onClick(View v)
        {
            if(this.adapter.selected < this.adapter.max || isSelected) {
                this.isSelected = !isSelected;
                this.adapter.selected += isSelected ? 1 : -1;
                this.adapter.map.remove(binder.getPlayer());
                this.adapter.map.put(binder.getPlayer(), isSelected);
                binder.lay.setBackgroundColor(isSelected ? Color.RED : binder.getRoot().getResources().getColor(android.R.color.transparent, null));
            } else Toast.makeText(binder.getRoot().getContext(), R.string.select_limit, Toast.LENGTH_LONG).show();
        }
    }

}
