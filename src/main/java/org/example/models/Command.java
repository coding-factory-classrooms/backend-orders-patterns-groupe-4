package org.example.models;

import org.example.OnOrderChangeListener;
import org.example.controllers.CommandsController;
import org.example.models.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Command {
    public void setOnChangeListener(OnOrderChangeListener onChangeListener) {
        this.listener = onChangeListener;
    }

    public enum State {
        NEW,
        IN_PROGRESS,
        PROCESSED,
        ABORTED
    }

    private List<Item> items;
    private State state;

    private OnOrderChangeListener listener;

    public Command() {
        this.items = new ArrayList<>();
        this.state = State.NEW;
    }

    public void addItem(Item item) {
        items.add(item);
        if (this.listener != null) this.listener.onOrderChange();    }

    public List<Item> getItems() {
        return items;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        if (this.getState() == Command.State.PROCESSED || this.getState() == Command.State.ABORTED) {
            return;
        }

        this.state = state;
        if (this.listener != null) this.listener.onOrderChange();
    }

    @Override
    public String toString() {
        return "Command{" +
                "items=" + items +
                ", state=" + state +
                '}';
    }
}
