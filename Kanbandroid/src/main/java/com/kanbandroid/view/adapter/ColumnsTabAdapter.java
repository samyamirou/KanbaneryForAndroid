package com.kanbandroid.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.actionbarsherlock.app.ActionBar;
import com.kanbandroid.model.Column;
import com.kanbandroid.view.fragment.ColumnFragment;

import java.util.List;

public class ColumnsTabAdapter extends FragmentPagerAdapter {

    private final FragmentActivity activityFragment;
    private final ActionBar actionBar;
    private final List<Column> columns;
    private ViewPager pager;

    public ColumnsTabAdapter(FragmentActivity activityFragment, ActionBar actionBar, ViewPager pager, List<Column> columns) {
        super(activityFragment.getSupportFragmentManager());
        this.activityFragment = activityFragment;
        this.actionBar = actionBar;
        this.columns = columns;
        this.pager = pager;

        for (Column column : columns) {
            String tabName = column.getName();
            ActionBar.Tab tab = actionBar.newTab();
            //tab.setTabListener(tabListener);
            actionBar.addTab(tab);
        }

        pager.setAdapter(this);
    }

    @Override
    public Fragment getItem(int position) {
        return ColumnFragment.newInstance(columns.get(position));
    }

    @Override
    public int getCount() {
        return columns.size();
    }
}
