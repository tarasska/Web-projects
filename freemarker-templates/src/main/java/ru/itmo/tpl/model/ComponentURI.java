package ru.itmo.tpl.model;

public class ComponentURI {
    private final String uri;
    private final String name;

    public ComponentURI(String uri, String name) {
        this.uri = uri;
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }
}