package org.example.models;

import org.example.models.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Command {
    public enum State {
        NEW,
        IN_PROGRESS,
        PROCESSED,
        ABORTED
    }

    private List<Item> items;
    private State state;

    public Command() {
        this.items = new ArrayList<>();
        this.state = State.NEW;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
