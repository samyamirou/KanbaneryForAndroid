package com.kanbandroid.rest.request;

import com.kanbandroid.model.Workspaces;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

public class WorkspacesRequest extends KanbaneryRestContentRequest<Workspaces> {

    public static final String WORKSPACE_RESOURCE_PATH = "user/workspaces.json";

    public WorkspacesRequest(String apiKey) {
        super(Workspaces.class, WORKSPACE_RESOURCE_PATH, apiKey);
    }

    @Override
    public Workspaces loadDataFromNetwork() throws RestClientException {
        ResponseEntity<Workspaces> response = getResponse();
        return response.getBody();
    }
}
