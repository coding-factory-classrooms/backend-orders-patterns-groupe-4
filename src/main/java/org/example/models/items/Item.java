package org.example.models.items;

public abstract class Item {
    public enum Type {
        GOODIE,
        VIDEO_GAME,
        CONSOLE
    }

    private String name;

    public abstract Type getItemType();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
