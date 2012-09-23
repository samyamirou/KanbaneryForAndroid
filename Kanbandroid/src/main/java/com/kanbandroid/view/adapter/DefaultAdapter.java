package com.kanbandroid.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.kanbandroid.view.cell.CustomCellView;
import de.akquinet.android.androlog.Log;

import java.util.List;

@SuppressWarnings("unchecked")
public class DefaultAdapter<T> extends BaseAdapter {
    protected Context context;
    protected Class<? extends CustomCellView<T>> cellViewClass;
    protected List<T> objectList;
    private CustomCellView<T> cell;
    private ViewGroup parent;

    public DefaultAdapter(Context context, Class<? extends CustomCellView<T>> cellViewClass, List<T> objectList) {
        super();
        this.context = context;
        this.cellViewClass = cellViewClass;
        this.objectList = objectList;
    }

    public DefaultAdapter(Context context, Class<? extends CustomCellView<T>> cellViewClass, List<T> objectList, ViewGroup parent) {
        this(context, cellViewClass, objectList);
        this.parent = parent;
    }

    public void clear() {
        objectList.clear();
    }

    public void add(T item) {
        objectList.add(item);
    }

    @Override
    public int getCount() {
        return objectList.size();
    }

    @Override
    public Object getItem(int position) {
        return objectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        CustomCellView<T> cell = (CustomCellView<T>) view;

        if (cell == null) {
            cell = newView();
        }

        if (cell != null) {
            bindView(position, cell);
        }

        return cell;
    }

    protected CustomCellView<T> newView() {
        try {
            if (parent != null) {
                cell = cellViewClass.getConstructor(Context.class, ViewGroup.class).newInstance(context, parent);
            } else {
                cell = cellViewClass.getConstructor(Context.class).newInstance(context);
            }
        } catch (Exception e) {
            Log.e(this, "Error during constructor call", e);
        }
        return cell;
    }

    protected void bindView(int position, CustomCellView<T> cell) {
        T modelObject = (T) getItem(position);
        cell.setData(modelObject);
    }
}
