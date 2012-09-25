package com.kanbandroid.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.kanbandroid.R;
import com.kanbandroid.model.Project;
import com.kanbandroid.model.Workspace;
import com.kanbandroid.model.Workspaces;
import com.kanbandroid.util.RequestKey;
import com.kanbandroid.view.adapter.SeparatedListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProjectListActivity extends ContentActivity {
    private TextView tvWorkspacesHeader;
    private ListView lvWorkspaces;
    private List<Project> projectList;

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
                Project selectedProject = projectList.get(position);
                 // TODO des trucs
            }
        });
    }

    @Override
    protected void handleRequestSuccess(RequestKey requestKey) {
        super.handleRequestSuccess(requestKey);
        getSherlock().setProgressBarIndeterminateVisibility(false);
        initializeLayout(requestKey);
    }

    private void initializeLayout(RequestKey requestKey) {
        switch (requestKey) {
            case USER:
                tvWorkspacesHeader.setText(user.getName());
                break;
            case WORKSPACES:
                projectList = getProjectList(workspaces);
                lvWorkspaces.setAdapter(initializeAdapter());
                break;
        }
    }

    private ListAdapter initializeAdapter() {
        SeparatedListAdapter<Workspace> ret = new SeparatedListAdapter<Workspace>(this);
        if (workspaces != null) {
            for (Workspace workspace : workspaces) {
                ret.addSection(workspace, new ArrayAdapter<Project>(this, R.layout.list_item, workspace.getProjects()));
            }
        }
        return ret;
    }

    private List<Project> getProjectList(Workspaces workspaces) {
        List<Project> ret = new ArrayList<Project>();
        for(Workspace workspace : workspaces) {
            for (Project project : workspace.getProjects()) {
                project.setWorkspace(workspace);
                ret.add(project);
            }
        }
        return ret;
    }
}
