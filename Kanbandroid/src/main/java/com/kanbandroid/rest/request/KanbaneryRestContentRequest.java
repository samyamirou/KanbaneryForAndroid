package com.kanbandroid.rest.request;

import android.util.Log;
import com.google.common.base.Strings;
import com.kanbandroid.model.Workspace;
import com.kanbandroid.rest.UrlConstants;
import com.octo.android.robospice.request.springandroid.RestContentRequest;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public abstract class KanbaneryRestContentRequest<T> extends RestContentRequest<T> {

    public static final String HEADER_NAME_API_TOKEN = "X-Kanbanery-ApiToken";
    protected String url;
    private String username;
    private String password;
    private String apiKey;
    private HttpEntity<T> requestEntity;

    private KanbaneryRestContentRequest(Class<T> clazz, String resourcePath) {
        super(clazz);
        this.url = UrlConstants.BASE_URL + resourcePath;
    }

    private KanbaneryRestContentRequest(Class<T> clazz, String resourcePath, Workspace workspace) {
        super(clazz);
        this.url = String.format(UrlConstants.WORKSPACE_URL, workspace.getName()) + resourcePath;
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
        this(clazz, resourcePath, workspace);
        this.username = username;
        this.password = password;
    }

    public KanbaneryRestContentRequest(Class<T> clazz, String resourcePath, Workspace workspace, String apiKey) {
        this(clazz, resourcePath, workspace);
        this.apiKey = apiKey;
    }

    private ResponseEntity<T> sendResponse(HttpMethod httpMethod) throws RestClientException {
        initializeRequest();
        RestTemplate restTemplate = getRestTemplate();
        return restTemplate.exchange(url, httpMethod, requestEntity, getResultType());
    }

    protected ResponseEntity<T> getResponse() throws RestClientException {
        return sendResponse(HttpMethod.GET);
    }

    protected ResponseEntity<T> postResponse() throws RestClientException {
        return sendResponse(HttpMethod.POST);
    }

    protected ResponseEntity<T> putResponse() throws RestClientException {
        return sendResponse(HttpMethod.PUT);
    }

    protected ResponseEntity<T> deleteResponse() throws RestClientException {
        return sendResponse(HttpMethod.DELETE);
    }

    private void initializeRequest() {
        Log.d(getClass().getName(), "Calling web service " + url + " ...");

        HttpHeaders requestHeaders = new HttpHeaders();

        if (apiKey != null) {
            requestHeaders.set(HEADER_NAME_API_TOKEN, apiKey);
        } else if (!Strings.isNullOrEmpty(username) && !Strings.isNullOrEmpty(password)) {
            requestHeaders.setAuthorization(new HttpBasicAuthentication(username,password));
        } else {
            throw new RestClientException("Error, no API Key or username/password available");
        }

        this.requestEntity = new HttpEntity<T>(requestHeaders);

    }
}
