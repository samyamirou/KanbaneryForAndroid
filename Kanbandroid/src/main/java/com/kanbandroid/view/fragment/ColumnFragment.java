package com.kanbandroid.view.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kanbandroid.model.Column;

public class ColumnFragment extends ListFragment {

    public static final String COLUMN_KEY = "column";
    private Column column;

    public static ColumnFragment newInstance(Column column) {
        ColumnFragment fragment = new ColumnFragment();

        Bundle args = new Bundle();
        args.putSerializable(COLUMN_KEY, column);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.column = (Column) getArguments().getSerializable("column");
    }
}
