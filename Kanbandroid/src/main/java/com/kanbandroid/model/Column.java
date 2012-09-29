package com.kanbandroid.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

public class Column extends ModelObject {
    private String capacity;
    private boolean fixed;
    private String name;
    private Number position;
    @JsonProperty("project_id")
    private Number projectId;
    @JsonProperty("task_time_average")
    private Number taskTimeAverage;
    @JsonProperty("task_time_deviation")
    private Number taskTimeDeviation;
    private Number weight;

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getPosition() {
        return position;
    }

    public void setPosition(Number position) {
        this.position = position;
    }

    public Number getProjectId() {
        return projectId;
    }

    public void setProjectId(Number projectId) {
        this.projectId = projectId;
    }

    public Number getTaskTimeAverage() {
        return taskTimeAverage;
    }

    public void setTaskTimeAverage(Number taskTimeAverage) {
        this.taskTimeAverage = taskTimeAverage;
    }

    public Number getTaskTimeDeviation() {
        return taskTimeDeviation;
    }

    public void setTaskTimeDeviation(Number taskTimeDeviation) {
        this.taskTimeDeviation = taskTimeDeviation;
    }

    public Number getWeight() {
        return weight;
    }

    public void setWeight(Number weight) {
        this.weight = weight;
    }
}
