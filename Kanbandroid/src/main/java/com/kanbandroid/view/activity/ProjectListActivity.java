package com.kanbandroid.view.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.kanbandroid.R;
import com.kanbandroid.model.Workspace;
import com.kanbandroid.view.adapter.DefaultAdapter;
import com.kanbandroid.view.cell.WorkspaceCellView;

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
    protected void handleRequestSuccess() {
        super.handleRequestSuccess();
        getSherlock().setProgressBarIndeterminateVisibility(false);
        initializeLayout();
    }

    private void initializeLayout() {
        tvWorkspacesHeader.setText(user.getName());
        lvWorkspaces.setAdapter(new DefaultAdapter<Workspace>(this, WorkspaceCellView.class, workspaces));
    }
}
