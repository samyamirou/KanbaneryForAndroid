package com.kanbandroid.view.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.kanbandroid.R;
import com.kanbandroid.rest.request.WorkspacesRequest;
import com.kanbandroid.model.Project;
import com.kanbandroid.model.User;
import com.kanbandroid.model.Workspace;
import com.kanbandroid.view.adapter.ProjectListAdapter;
import com.octo.android.rest.client.ContentManager;
import com.octo.android.rest.client.exception.ContentManagerException;
import com.octo.android.rest.client.persistence.DurationInMillis;
import com.octo.android.rest.client.request.ContentRequest;
import com.octo.android.rest.client.request.RequestListener;
import de.akquinet.android.androlog.Log;

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

        loadUserFromCache();
        loadWorkspacesFromCache();

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
