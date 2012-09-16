package com.kanbandroid.rest.request;

import android.util.Log;
import com.google.common.base.Strings;
import com.kanbandroid.model.Workspace;
import com.kanbandroid.rest.UrlConstants;
import com.octo.android.rest.client.request.springandroid.RestContentRequest;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public abstract class KanbaneryRestContentRequest<T> extends RestContentRequest<T> {

    protected String url;
    private String username;
    private String password;
    private String apiKey;
    private Class<T> resultClass;
    private HttpEntity<T> requestEntity;

    private KanbaneryRestContentRequest(Class<T> clazz, String resourcePath) {
        super(clazz);
        this.resultClass = clazz;
        this.url = UrlConstants.BASE_URL + resourcePath;
    }

    public KanbaneryRestContentRequest(Class<T> clazz, String resourcePath, String username, String password) {
        this(clazz, resourcePath);
        this.username = username;
        this.password = password;
    }

    public KanbaneryRestContentRequest(Class<T> clazz, String resourcePath, String apiKey) {
        this(clazz, resourcePath);
        this.apiKey = apiKey;
    }

    public KanbaneryRestContentRequest(Class<T> clazz, String resourcePath, Workspace workspace, String username, String password) {
        this(clazz, resourcePath, username, password);
        formatWorkspaceUrl(workspace);
    }

    public KanbaneryRestContentRequest(Class<T> clazz, String resourcePath, Workspace workspace, String apiKey) {
        this(clazz, resourcePath, apiKey);
        formatWorkspaceUrl(workspace);
    }

    private void formatWorkspaceUrl(Workspace workspace) {
        this.url = String.format(UrlConstants.WORKSPACE_URL, workspace.getName());
    }

    protected ResponseEntity<T> getResponse() throws RestClientException {
        initializeRequest();
        RestTemplate restTemplate = getRestTemplate();
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, resultClass);
    }

    private void initializeRequest() {
        Log.d(getClass().getName(), "Calling web service " + url + " ...");

        HttpHeaders requestHeaders = new HttpHeaders();

        if (apiKey != null) {
            requestHeaders.set("X-Kanbanery-ApiToken", apiKey);
        } else if (!Strings.isNullOrEmpty(username) && !Strings.isNullOrEmpty(password)) {
            requestHeaders.setAuthorization(new HttpBasicAuthentication(username,password));
        } else {
            throw new RestClientException("Error, no API Key or username/password available");
        }

        this.requestEntity = new HttpEntity<T>(requestHeaders);
    }
}
