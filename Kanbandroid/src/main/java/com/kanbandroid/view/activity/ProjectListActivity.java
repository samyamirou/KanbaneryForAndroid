package com.kanbandroid.view.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.kanbandroid.R;
import com.kanbandroid.model.Project;
import com.kanbandroid.model.Workspace;
import com.kanbandroid.model.Workspaces;
import com.kanbandroid.util.RequestKey;
import com.kanbandroid.view.adapter.ProjectCellAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProjectListActivity extends ContentActivity {
    private TextView tvWorkspacesHeader;
    private ListView lvWorkspaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projects);

        this.tvWorkspacesHeader = (TextView) findViewById(R.id.tv_workspaces_header);
        this.lvWorkspaces = (ListView) findViewById(R.id.lv_workspaces);

        requestForUser();
        requestForWorkspaces();

        getSherlock().setProgressBarIndeterminateVisibility(true);
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
                List<Project> projectList = getProjectList(workspaces);
                lvWorkspaces.setAdapter(new ProjectCellAdapter(this, projectList));
                break;
        }
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
