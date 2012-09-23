package com.kanbandroid.view.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.kanbandroid.R;
import com.kanbandroid.model.Project;
import com.kanbandroid.view.adapter.ProjectListAdapter;

import java.util.Collections;
import java.util.List;

public class ProjectListActivity extends ContentActivity {
    private TextView tvProjectsHeader;
    private ListView lvProjects;
    private List<Project> projectList = Collections.emptyList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projects);

        this.tvProjectsHeader = (TextView) findViewById(R.id.tv_projects_header);
        this.lvProjects = (ListView) findViewById(R.id.lv_projects);

        loadUser();
        loadWorkspaces();

        if(!workspaces.isEmpty()) {
            projectList = workspaces.get(0).getProjects();
        }

        initializeLayout();
    }

    private void initializeLayout() {
        tvProjectsHeader.setText(user.getName());
        lvProjects.setAdapter(new ProjectListAdapter(this, projectList));
    }
}
