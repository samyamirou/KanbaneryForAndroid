package com.kanbandroid.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Task extends ModelObject {

    private boolean blocked;
    @JsonProperty("column_id")
    private Number columnId;
    @JsonProperty("creator_id")
    private Number creatorId;
    private String deadline;
    private String description;
    @JsonProperty("estimate_id")
    private Number estimateId;
    @JsonProperty("global_in_context_url")
    private String globalInContextUrl;
    @JsonProperty("moved_at")
    private String movedAt;
    @JsonProperty("owner_id")
    private Number ownerId;
    private Number position;
    private Number priority;
    @JsonProperty("ready_to_pull")
    private boolean readyToPull;
    @JsonProperty("task_type_id")
    private Number taskTypeId;
    private String title;
    private Number weight;

    @Override
    public String toString() {
        return title;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Number getColumnId() {
        return columnId;
    }

    public void setColumnId(Number columnId) {
        this.columnId = columnId;
    }

    public Number getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Number creatorId) {
        this.creatorId = creatorId;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Number getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(Number estimateId) {
        this.estimateId = estimateId;
    }

    public String getGlobalInContextUrl() {
        return globalInContextUrl;
    }

    public void setGlobalInContextUrl(String globalInContextUrl) {
        this.globalInContextUrl = globalInContextUrl;
    }

    public String getMovedAt() {
        return movedAt;
    }

    public void setMovedAt(String movedAt) {
        this.movedAt = movedAt;
    }

    public Number getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Number ownerId) {
        this.ownerId = ownerId;
    }

    public Number getPosition() {
        return position;
    }

    public void setPosition(Number position) {
        this.position = position;
    }

    public Number getPriority() {
        return priority;
    }

    public void setPriority(Number priority) {
        this.priority = priority;
    }

    public boolean isReadyToPull() {
        return readyToPull;
    }

    public void setReadyToPull(boolean readyToPull) {
        this.readyToPull = readyToPull;
    }

    public Number getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(Number taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Number getWeight() {
        return weight;
    }

    public void setWeight(Number weight) {
        this.weight = weight;
    }
}
