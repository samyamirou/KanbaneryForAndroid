package com.kanbandroid.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Project extends ModelObject {
    @JsonProperty("_current_user_permission")
    private String currentUserPermission;
    private String name;

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

    @Override
    public String toString() {
        return name;
    }
}
