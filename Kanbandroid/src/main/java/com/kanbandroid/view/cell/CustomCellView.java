package com.kanbandroid.view.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public abstract class CustomCellView<T> extends LinearLayout {

    protected final LayoutInflater inflater;

    public CustomCellView(Context context) {
        super(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public abstract void setData(T modelObject);
}
