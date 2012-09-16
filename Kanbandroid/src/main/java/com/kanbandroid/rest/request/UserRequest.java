package com.kanbandroid.rest.request;

import com.kanbandroid.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

public class UserRequest extends KanbaneryRestContentRequest<User> {

    public UserRequest(String username, String password) {
        super(User.class, "user.json", username, password);
    }

    @Override
    public User loadDataFromNetwork() throws RestClientException {
        ResponseEntity<User> response = getResponse();
        return response.getBody();
    }
}
