package com.kanbandroid.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.kanbandroid.R;
import com.kanbandroid.model.Project;
import com.kanbandroid.model.Workspace;
import com.kanbandroid.util.RequestKey;
import com.kanbandroid.view.adapter.SeparatedListAdapter;

import java.util.List;

public class ProjectListActivity extends ContentActivity {
    private TextView tvWorkspacesHeader;
    private ListView lvWorkspaces;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projects);

        this.tvWorkspacesHeader = (TextView) findViewById(R.id.tv_workspaces_header);
        this.lvWorkspaces = (ListView) findViewById(R.id.lv_workspaces);

        requestForUser();
        requestForWorkspaces();

        getSherlock().setProgressBarIndeterminateVisibility(true);

        this.lvWorkspaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Project selectedProject = (Project) adapter.getItem(position);
                navigateToProjectActivity(selectedProject);
            }
        });
    }

    private void navigateToProjectActivity(Project selectedProject) {
        Intent intent = new Intent(this, ProjectActivity.class);
        intent.putExtra(ProjectActivity.PROJECT_INTENT_EXTRA, selectedProject);
        startActivity(intent);
        finish();
    }

    @Override
    protected void handleRequestSuccess(RequestKey requestKey, Object requestedData) {
        super.handleRequestSuccess(requestKey, requestedData);
        initializeLayout(requestKey);
    }

    private void initializeLayout(RequestKey requestKey) {
        switch (requestKey) {
            case USER:
                tvWorkspacesHeader.setText(user.getName());
                break;
            case WORKSPACES:
                adapter = initializeAdapter();
                lvWorkspaces.setAdapter(adapter);
                break;
        }
    }

    private ListAdapter initializeAdapter() {
        SeparatedListAdapter<Workspace> ret = new SeparatedListAdapter<Workspace>(this);
        if (workspaces != null) {
            for (Workspace workspace : workspaces) {
                List<Project> projects = workspace.getProjects();
                ret.addSection(workspace, new ArrayAdapter<Project>(this, R.layout.list_item, projects));
                for (Project project : projects) {
                    project.setWorkspace(workspace);
                }
            }
        }
        return ret;
    }
}
