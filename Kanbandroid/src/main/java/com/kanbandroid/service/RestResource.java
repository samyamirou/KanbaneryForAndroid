package com.kanbandroid.service;

public enum RestResource {
    LOGIN("https://kanbanery.com/api/v1/");

    private String url;

    RestResource(String s) {
        this.url = s;
    }

    public String getUrl() {
        return url;
    }
}
