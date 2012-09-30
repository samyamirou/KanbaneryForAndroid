package com.kanbandroid.rest.request;

import com.kanbandroid.model.Column;
import com.kanbandroid.model.Columns;
import com.kanbandroid.model.Tasks;
import com.kanbandroid.model.Workspace;
import com.kanbandroid.util.RequestKey;
import org.springframework.http.ResponseEntity;

public class GetTasksRequest extends KanbaneryRestContentRequest<Tasks> {

    public GetTasksRequest(Column column, Workspace workspace, String apiKey) {
        super(Tasks.class, String.format(RequestKey.TASKS
                                                     .getResourcePath(), column.getId()), workspace, apiKey);
    }

    @Override
    public Tasks loadDataFromNetwork() throws Exception {
        ResponseEntity<Tasks> response = getResponse();
        return response.getBody();
    }
}
