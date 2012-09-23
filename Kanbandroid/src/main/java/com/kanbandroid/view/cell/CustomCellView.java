package com.kanbandroid.view.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public abstract class CustomCellView<T> extends LinearLayout {

    protected final LayoutInflater inflater;
    protected ViewGroup parent;

    public CustomCellView(Context context) {
        super(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CustomCellView(Context context, ViewGroup parent) {
        this(context);
        this.parent = parent;
    }

    public abstract void setData(T modelObject);
}
