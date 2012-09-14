package com.kanbandroid.rest.request;

import com.kanbandroid.model.KanbaneryUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

public class UserRequest extends KanbaneryRestContentRequest<KanbaneryUser> {

    public UserRequest(String username, String password) {
        super(KanbaneryUser.class, "user.json", username, password);
    }

    @Override
    public KanbaneryUser loadDataFromNetwork() throws RestClientException {
        ResponseEntity<KanbaneryUser> response = getResponse();
        return response.getBody();
    }
}
