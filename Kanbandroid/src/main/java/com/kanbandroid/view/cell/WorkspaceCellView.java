package com.kanbandroid.view.cell;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.kanbandroid.R;
import com.kanbandroid.model.Project;
import com.kanbandroid.model.Workspace;
import com.kanbandroid.view.adapter.DefaultAdapter;

public class WorkspaceCellView extends CustomCellView<Workspace> {

    private TextView tvProjectsHeader;
    private ListView lvProjects;

    public WorkspaceCellView(Context context) {
        super(context);
        inflater.inflate(R.layout.workspace_cell_view, this);
        this.tvProjectsHeader = (TextView) findViewById(R.id.tv_projects_header);
        this.lvProjects = (ListView) findViewById(R.id.lv_projects);
    }

    @Override
    public void setData(Workspace workspace) {
        tvProjectsHeader.setText(workspace.getName());
        lvProjects.setAdapter(new DefaultAdapter<Project>(getContext(), ProjectCellView.class, workspace
                .getProjects()));
    }
}
