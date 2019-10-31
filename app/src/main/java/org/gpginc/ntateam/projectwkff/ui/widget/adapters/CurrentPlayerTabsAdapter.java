package org.gpginc.ntateam.projectwkff.ui.widget.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.gpginc.ntateam.projectwkff.ui.fragments.PlayerInfosFragments;

import java.util.ArrayList;
import java.util.List;

public class CurrentPlayerTabsAdapter extends FragmentStatePagerAdapter {

    public List<PlayerInfosFragments> FRAGMENTS = new ArrayList<>();
    public List<String> TITLES = new ArrayList<>();

    public CurrentPlayerTabsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    public void add(PlayerInfosFragments frag, String title)
    {
        this.FRAGMENTS.add(frag);
        this.TITLES.add(title);
    }

    @Override
    public Fragment getItem(int i) {
        return this.FRAGMENTS.get(i);
    }

    @Override
    public int getCount() {
        return this.FRAGMENTS.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.TITLES.get(position);
    }
}
