package org.example;

import org.example.mementos.OrdersOriginator;
import org.example.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrdersHandler implements OnOrderChangeListener {
    private List<Order> orders;

    private List<OrdersOriginator.OrdersMemento> history;
    private final OrdersOriginator originator;
    private int historyIndex;

    public OrdersHandler() {
        this.orders = new ArrayList<>();
        this.history = new ArrayList<>();
        this.originator = new OrdersOriginator();
        historyIndex = 0;
    }

    public List<Order> getCommands() {
        return orders;
    }

    public void addOrder(Order order) {
        if (!order.getState().equals(Order.State.NEW)) {
            return;
        }

        this.orders.add(order);
        this.saveState();
    }

    public void undoAction() {
        int undoIndex = historyIndex - 1;
        if (undoIndex < 0) {
            return;
        }
        historyIndex = undoIndex;
        this.orders =  this.originator.restore(this.history.get(historyIndex));
    }

    public void redoAction() {
        int redoIndex = historyIndex + 1;
        if (redoIndex > this.history.size()) {
            return;
        }
        historyIndex = redoIndex;
        this.orders = this.originator.restore(this.history.get(historyIndex));
    }

    @Override
    public void onOrderChange() {
        this.saveState();
    }

    public void saveState() {
        if (this.historyIndex < this.getHistory().size()) {
            this.history = this.history.subList(0, this.historyIndex + 1);
        }

        this.history.add(this.originator.save(this.orders));
        this.historyIndex = this.history.size() - 1;
    }

    public List<OrdersOriginator.OrdersMemento> getHistory() {
        return history;
    }

    public int getHistoryIndex() {
        return this.historyIndex;
    }
}

