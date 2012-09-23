package com.kanbandroid.rest.request;

import com.kanbandroid.model.User;
import com.kanbandroid.util.RequestKey;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

public class UserRequest extends KanbaneryRestContentRequest<User> {

    public static final String RESOURCE_PATH = RequestKey.USER.getResourcePath();

    public UserRequest(String username, String password) {
        super(User.class, RESOURCE_PATH, username, password);
    }

    public UserRequest(String apiKey) {
        super(User.class, RESOURCE_PATH, apiKey);
    }

    @Override
    public User loadDataFromNetwork() throws RestClientException {
        ResponseEntity<User> response = getResponse();
        return response.getBody();
    }
}
