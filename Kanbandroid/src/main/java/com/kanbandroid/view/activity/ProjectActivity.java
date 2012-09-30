package com.kanbandroid.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.actionbarsherlock.app.ActionBar;
import com.kanbandroid.R;
import com.kanbandroid.model.Columns;
import com.kanbandroid.model.Project;
import com.kanbandroid.model.Tasks;
import com.kanbandroid.rest.request.GetColumnsRequest;
import com.kanbandroid.util.RequestKey;
import com.kanbandroid.view.adapter.ColumnsTabAdapter;
import com.kanbandroid.view.fragment.ColumnFragment;

public class ProjectActivity extends ContentActivity {

    private Project project;
    private Columns columns;
    public static final String PROJECT_INTENT_EXTRA = "project_extra";
    private ViewPager pager;
    private ColumnsTabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_pager);
        this.project = (Project) getIntent().getSerializableExtra(PROJECT_INTENT_EXTRA);
        this.pager = (ViewPager) findViewById(R.id.pager);
        requestForColumns();
    }

    private void requestForColumns() {
        requestForData(new GetColumnsRequest(project, apiKey), RequestKey.COLUMNS, project.getId());
    }

    @Override
    protected void handleRequestSuccess(RequestKey requestKey, Object requestedData) {
        super.handleRequestSuccess(requestKey, requestedData);
        switch (requestKey) {
            case COLUMNS:
                this.columns = (Columns) requestedData;
                initializeLayout(requestKey);
                break;
        }
    }

    private void initializeLayout(RequestKey requestKey) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        this.adapter = new ColumnsTabAdapter(this, getSupportActionBar(), this.pager, project.getWorkspace(), apiKey, this.columns);
    }
}
