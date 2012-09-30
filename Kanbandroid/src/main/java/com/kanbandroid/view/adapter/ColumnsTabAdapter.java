package com.kanbandroid.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import com.actionbarsherlock.app.ActionBar;
import com.kanbandroid.model.Column;
import com.kanbandroid.model.Workspace;
import com.kanbandroid.view.fragment.ColumnFragment;

import java.util.ArrayList;
import java.util.List;

public class ColumnsTabAdapter extends FragmentPagerAdapter {

    private final FragmentActivity activityFragment;
    private final ActionBar actionBar;
    private String apiKey;
    private final List<Column> columns;
    private ViewPager pager;
    private Workspace workspace;
    List<ColumnFragment> columnFragments = new ArrayList<ColumnFragment>();

    public ColumnsTabAdapter(FragmentActivity activityFragment, final ActionBar actionBar, final ViewPager pager, Workspace workspace, String apiKey, List<Column> columns) {
        super(activityFragment.getSupportFragmentManager());
        this.activityFragment = activityFragment;
        this.actionBar = actionBar;
        this.workspace = workspace;
        this.apiKey = apiKey;
        this.columns = columns;
        this.pager = pager;

        ActionBar.TabListener tabListener = createTabListener(pager);

        initializeTabs(actionBar, columns, tabListener);

        initializePager(actionBar, pager);
    }

    private void initializePager(final ActionBar actionBar, ViewPager pager) {
        pager.setAdapter(this);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initializeTabs(ActionBar actionBar, List<Column> columns, ActionBar.TabListener tabListener) {
        for (Column column : columns) {
            String tabName = column.getName();
            ActionBar.Tab tab = actionBar.newTab();
            tab.setText(tabName);
            tab.setTabListener(tabListener);
            actionBar.addTab(tab);
            this.columnFragments.add(ColumnFragment.newInstance(column, workspace, apiKey));
        }
    }

    private ActionBar.TabListener createTabListener(final ViewPager pager) {
        return new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                if (pager != null) {
                    pager.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            }
        };
    }

    @Override
    public Fragment getItem(int position) {
        return columnFragments.get(position);
    }

    @Override
    public int getCount() {
        return columns.size();
    }
}
