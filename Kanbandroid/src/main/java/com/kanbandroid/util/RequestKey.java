package com.kanbandroid.util;

public enum RequestKey {
    USER("user", "user.json"),
    WORKSPACES("workspaces", "user/workspaces.json"),
    COLUMNS("project[%s]/columns", "projects/%s/columns.json");

    private final String cacheKey;
    private final String resourcePath;

    public String getCacheKey() {
        return cacheKey;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    RequestKey(String cacheKey, String resourcePath) {

        this.cacheKey = cacheKey;
        this.resourcePath = resourcePath;
    }
}
