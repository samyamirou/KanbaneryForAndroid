package com.kanbandroid.rest.request;

import com.kanbandroid.model.Columns;
import com.kanbandroid.model.Project;
import com.kanbandroid.util.RequestKey;
import de.akquinet.android.androlog.Log;

public abstract class ColumnsRequest extends KanbaneryRestContentRequest<Columns> {

    public ColumnsRequest(Project project, String apiKey) {
        super(Columns.class, String.format(RequestKey.COLUMNS
                                                            .getResourcePath(), project.getId()), project.getWorkspace(), apiKey);
        Log.v("columns request", "Columns request : " + String.format(RequestKey.COLUMNS
                                                                                .getResourcePath(), project.getId()));
    }
}
