package com.kanbandroid.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Project extends ModelObject {
    @JsonProperty("_current_user_permission")
    private String currentUserPermission;
    private String name;
    private String type;

    public String getCurrentUserPermission() {
        return this.currentUserPermission;
    }

    public void setCurrentUserPermission(String currentUserPermission) {
        this.currentUserPermission = currentUserPermission;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
