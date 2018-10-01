package com.hello.om.texteditor;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyFragPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<MyFragment> fl = new ArrayList<>(1);

    public MyFragPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fl.get(position);
    }

    @Override
    public int getCount() {
        return fl.size();
    }

    void addTab() {
        fl.add(new MyFragment());
        notifyDataSetChanged();
    }

    void addTab(Uri fileUri) {
        MyFragment frag = new MyFragment();
        Bundle args = new Bundle();
        args.putParcelable("URI", fileUri);
        frag.setArguments(args);
        fl.add(frag);
        notifyDataSetChanged();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fl.get(position).getContentFileName(position);

    }
}


