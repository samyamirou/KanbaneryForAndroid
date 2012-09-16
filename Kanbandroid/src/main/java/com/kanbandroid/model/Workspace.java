package com.kanbandroid.model;

import java.util.List;

public class Workspace extends ModelObject {
    private String name;
    private List<Project> projects;
    private String type;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
