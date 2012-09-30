package com.kanbandroid.view.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.actionbarsherlock.app.ActionBar;
import com.kanbandroid.R;
import com.kanbandroid.model.Column;
import com.kanbandroid.model.Task;
import com.kanbandroid.model.Tasks;
import com.kanbandroid.model.Workspace;
import com.kanbandroid.rest.request.GetTasksRequest;
import com.kanbandroid.util.RequestKey;
import com.kanbandroid.view.activity.ContentActivity;
import com.octo.android.robospice.ContentManager;
import com.octo.android.robospice.exception.ContentManagerException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.ContentRequest;
import com.octo.android.robospice.request.RequestListener;
import de.akquinet.android.androlog.Log;

public class ColumnFragment extends ListFragment implements RequestListener<Tasks> {

    public static final String COLUMN_KEY = "column";
    public static final String WORKSPACE_KEY = "workspace";
    public static final String API_KEY = "api_key";
    private Column column;
    private Workspace workspace;
    private String apiKey;
    private Tasks tasks;

    public static ColumnFragment newInstance(Column column, Workspace workspace, String apiKey) {
        ColumnFragment fragment = new ColumnFragment();

        Bundle args = new Bundle();
        args.putSerializable(COLUMN_KEY, column);
        args.putSerializable(WORKSPACE_KEY, workspace);
        args.putSerializable(API_KEY, apiKey);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.column = (Column) getArguments().getSerializable(COLUMN_KEY);
        this.workspace = (Workspace) getArguments().getSerializable(WORKSPACE_KEY);
        this.apiKey = (String) getArguments().getSerializable(API_KEY);

        requestForTasks();
    }

    private void requestForTasks() {
        ContentActivity activity = (ContentActivity) getActivity();
        ContentManager contentManager = activity.getContentManager();
        ContentRequest<Tasks> contentRequest = new GetTasksRequest(column, workspace, apiKey);
        String cacheKey = String.format(RequestKey.TASKS.getCacheKey(), column.getId());
        contentManager.execute(contentRequest, cacheKey, DurationInMillis.ONE_HOUR, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.column_fragment, container, false);
    }

    @Override
    public void onRequestSuccess(Tasks tasks) {
        this.tasks = tasks;
        setListAdapter(new ArrayAdapter<Task>(getActivity(), R.layout.list_item, tasks));
        updateTabTitle(column, tasks);
    }

    private void updateTabTitle(Column column, Tasks tasks) {
        ActionBar supportActionBar = ((ContentActivity) getActivity()).getSupportActionBar();
        ActionBar.Tab tab = supportActionBar.getTabAt(column.getPosition().intValue() - 1);
        tab.setText(column.getName() + " (" + tasks.size() + ")");
    }

    @Override
    public void onRequestFailure(ContentManagerException contentManagerException) {
        Log.e(contentManagerException.getMessage());
    }
}
