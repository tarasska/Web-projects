package ru.itmo.tpl.model;

public class User {
    private final long id;
    private final String handle;
    private final String name;
    private HandleColor color;

    public User(long id, String handle, String name, HandleColor color) {
        this.id = id;
        this.handle = handle;
        this.name = name;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public String getName() {
        return name;
    }

    public HandleColor getColor() { return color;}

    public enum HandleColor {
        RED,
        GREEN,
        BLUE
    }
}