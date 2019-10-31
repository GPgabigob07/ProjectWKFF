package org.gpginc.ntateam.projectwkff.ui.widget.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.ListItemEventsBinding;
import org.gpginc.ntateam.projectwkff.runtime.Event;
import org.gpginc.ntateam.projectwkff.runtime.Player;
import org.gpginc.ntateam.projectwkff.runtime.dragons.Dragon;
import org.gpginc.ntateam.projectwkff.runtime.dragons.events.BaseDragonEvent;
import org.gpginc.ntateam.projectwkff.runtime.dragons.events.DragonBorn;

import java.util.List;

import static org.gpginc.ntateam.projectwkff.runtime.dragons.Dragon.BEHAVIOUR_AGGRESSIVE;
import static org.gpginc.ntateam.projectwkff.runtime.dragons.Dragon.BEHAVIOUR_HONOR;
import static org.gpginc.ntateam.projectwkff.runtime.dragons.Dragon.BEHAVIOUR_HOSTILE;
import static org.gpginc.ntateam.projectwkff.runtime.dragons.Dragon.BEHAVIOUR_PROTECTOR;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventItem>
{
    private final List<Event> EVTS;
    private final Player p;
    private GameFlux res;
    public EventsAdapter(List<Event> evts, Player p, GameFlux res) {
        this.EVTS = evts;
        this.p = p;
        this.res= res;
    }

    @NonNull
    @Override
    public EventItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemEventsBinding bind = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_current_player_events, parent, false);
        return new EventItem(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull EventItem holder, int position) {
        holder.setEvent(EVTS.get(position));
        holder.binder.oDescription.setText(EVTS.get(position) instanceof BaseDragonEvent
                ? R.string.evt_supremacy_dragon
                : EVTS.get(position).getOwner()!=null && EVTS.get(position).getOwner().equals(p)
                ? EVTS.get(position).getDescription()
                : EVTS.get(position).isAttacher || EVTS.get(position).needPlayers
                ? EVTS.get(position).getMonsiaurDescriptionDefault()
                : EVTS.get(position).getDescription());
        holder.binder.oName.setTextColor(
                (holder.binder.getEvent().needPlayers || holder.binder.getEvent().isAttacher)
                        && holder.binder.getEvent().getOwner().equals(p)
                ? res.getResources().getColor(R.color.colorAccent, null)
                : res.darkTheme
                ? res.getResources().getColor(R.color.whiteBackgroundText,null)
                : res.getResources().getColor(R.color.blackBackgroundText, null));
        if(EVTS.get(position) instanceof BaseDragonEvent)
        {
            ((BaseDragonEvent) EVTS.get(position)).setBarToUpdateWithDragonCharge(holder.binder);
            if(EVTS.get(position) instanceof DragonBorn)
            {
                switch (((Dragon) ((DragonBorn) EVTS.get(position)).getDragon()).getBehavior())
                {
                    case BEHAVIOUR_HONOR:
                        holder.binder.oName.setTextColor(holder.binder.getRoot().getResources().getColor(android.R.color.holo_blue_light, null));
                        break;
                    case BEHAVIOUR_AGGRESSIVE:
                        holder.binder.oName.setTextColor(holder.binder.getRoot().getResources().getColor(android.R.color.holo_red_light, null));
                        break;
                    case BEHAVIOUR_PROTECTOR:
                        holder.binder.oName.setTextColor(holder.binder.getRoot().getResources().getColor(android.R.color.holo_green_dark, null));
                        break;
                    case BEHAVIOUR_HOSTILE:
                        holder.binder.oName.setTextColor(holder.binder.getRoot().getResources().getColor(android.R.color.holo_purple, null));
                        break;
                    default:
                        throw new IllegalStateException("null behavior");
                }
            }
            String lastText = holder.binder.oDescription.getText().toString();
            holder.binder.oDescription.setText(lastText +" :: "+EVTS.get(position).getDragon().toString());
        }
        holder.binder.setPlayer(p);
    }

    @Override
    public int getItemCount() {
        return EVTS != null ? EVTS.size() : 0;
    }


    public class EventItem extends RecyclerView.ViewHolder
    {
        private ListItemEventsBinding binder;

        public EventItem(ListItemEventsBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
        public void setEvent(Event e)
        {
            this.binder.setEvent(e);
        }
    }
}

