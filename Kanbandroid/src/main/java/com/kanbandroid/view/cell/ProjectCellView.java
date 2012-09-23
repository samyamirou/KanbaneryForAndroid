package com.kanbandroid.view.cell;

import android.content.Context;
import android.widget.TextView;
import com.kanbandroid.R;
import com.kanbandroid.model.Project;

public class ProjectCellView extends CustomCellView<Project> {
    private TextView tvCellProject;

    public ProjectCellView(Context context) {
        super(context);
        inflater.inflate(R.layout.project_cell_view, this);
        this.tvCellProject = (TextView) findViewById(R.id.tv_cell_project);
    }

    public void setData(Project project) {
        tvCellProject.setText(project.getName());
    }
}
