package com.kanbandroid.view.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kanbandroid.R;
import com.kanbandroid.model.Project;

public class ProjectCellView extends LinearLayout {
    private TextView tvCellProject;

    public ProjectCellView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.project_cell_view, this);
        this.tvCellProject = (TextView) findViewById(R.id.tv_cell_project);
    }

    public void setData(Project project) {
        tvCellProject.setText(project.getName());
    }
}
