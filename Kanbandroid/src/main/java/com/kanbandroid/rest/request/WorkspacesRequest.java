package com.kanbandroid.rest.request;

import com.kanbandroid.model.User;
import com.kanbandroid.model.Workspace;
import com.kanbandroid.model.Workspaces;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.util.List;

public class WorkspacesRequest extends KanbaneryRestContentRequest<Workspaces> {

    public WorkspacesRequest(String apiKey) {
        super(Workspaces.class, "user/workspaces.json", apiKey);
    }

    @Override
    public Workspaces loadDataFromNetwork() throws RestClientException {
        ResponseEntity<Workspaces> response = getResponse();
        return response.getBody();
    }
}
