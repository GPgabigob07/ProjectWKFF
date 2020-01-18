package org.gpginc.ntateam.projectwkff.ui.widget.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.collections4.map.LinkedMap;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.DevListitemloggerBinding;
import org.gpginc.ntateam.projectwkff.databinding.DevloggerBinding;
import org.gpginc.ntateam.projectwkff.runtime.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _deloggerAdapter extends RecyclerView.Adapter<_deloggerAdapter._devloggerItem>
{
    private LinkedMap<String, Integer> mMap;
    List<String> aa = new ArrayList<>();
    List<Integer> ii = new ArrayList<>();

    public _deloggerAdapter setmMap(LinkedMap<String, Integer> mMap) {
        this.mMap = mMap;
        Util.reverseMap(mMap).forEach((S, I) ->
        {
            aa.add(S);
            ii.add(I);
        });
        Util.wrapMapToSize(mMap, 200);
        notifyDataSetChanged();
        return this;
    }

    @NonNull
    @Override
    public _devloggerItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DevListitemloggerBinding b = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout._dev_listitemlogger, parent, false);
        return new _devloggerItem(b);
    }

    @Override
    public void onBindViewHolder(@NonNull _devloggerItem holder, int position) {
        holder.binder.setOutput(aa.get(position));
        holder.binder.setTypo(ii.get(position));
    }

    @Override
    public int getItemCount() {
        return mMap!=null ? mMap.size() : 0;
    }

    public class _devloggerItem extends RecyclerView.ViewHolder
    {
        private DevListitemloggerBinding binder;

        public _devloggerItem(DevListitemloggerBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }
}
