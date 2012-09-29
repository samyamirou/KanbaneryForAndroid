package com.kanbandroid.rest.request;

import com.kanbandroid.model.Columns;
import com.kanbandroid.model.Project;
import org.springframework.http.ResponseEntity;

public class GetColumnsRequest extends ColumnsRequest {

    public GetColumnsRequest(Project project, String apiKey) {
        super(project, apiKey);
    }

    @Override
    public Columns loadDataFromNetwork() throws Exception {
        ResponseEntity<Columns> response = getResponse();
        return response.getBody();
    }
}
