package com.kanbandroid.view.cell;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.kanbandroid.R;
import com.kanbandroid.model.Project;

public class ProjectCellView extends CustomCellView<Project> {
    private TextView tvCellProject;
    private TextView tvCellWorkspace;

    boolean printHeader = false;

    public ProjectCellView(Context context) {
        super(context);
        View view = inflater.inflate(R.layout.project_cell_view, this);
        this.tvCellWorkspace = (TextView) view.findViewById(R.id.tv_cell_workspace);
        this.tvCellProject = (TextView) view.findViewById(R.id.tv_cell_project);
    }

    @Override
    public void setData(Project project) {
        if(printHeader) {
            tvCellWorkspace.setText(project.getWorkspace().getName());
            tvCellWorkspace.setVisibility(VISIBLE);
        }
        tvCellProject.setText(project.getName());
    }
}
