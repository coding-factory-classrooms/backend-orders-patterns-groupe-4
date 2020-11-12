package org.example;

import org.example.models.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrdersHandler implements OnOrderChangeListener{
    public List<Order> orders;
    private List<OrdersMemento> history;
    private int historyIndex;

    public OrdersHandler() {
        this.orders = new ArrayList<>();
        this.history = new ArrayList<>();
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
        this.setState();
    }

    public void undoAction() {
        int undoIndex = historyIndex - 1;
        if (undoIndex < 0) {
            return;
        }
        historyIndex = undoIndex;
        this.orders = new ArrayList<>(this.history.get(historyIndex).getState());
    }

    public void redoAction() {
        int redoIndex = historyIndex + 1;
        if (redoIndex > this.history.size()) {
            return;
        }
        historyIndex = redoIndex;
        this.orders = new ArrayList<>(this.history.get(historyIndex).getState());
    }

    @Override
    public void onOrderChange() {
        this.setState();
    }

    public void setState() {
        this.history.add(new OrdersMemento(this.orders));
        this.historyIndex = this.history.size() - 1;
    }

    public List<OrdersMemento> getHistory() {
        return history;
    }

    public int getHistoryIndex() {
        return this.historyIndex;
    }

    public class OrdersMemento {
        private List<Order> state;

        public List<Order> getState() {
            return state;
        }

        public OrdersMemento(List<Order> state) {
            this.state = this.clone(state);
        }

        private List<Order> clone(List<Order> orders) {
            List<Order> orderList = new ArrayList<>();

            for (Order order : orders) {
                Order copyOrder = new Order();
                copyOrder.setState(order.getState());
                copyOrder.getItems().addAll(order.getItems());
                orderList.add(copyOrder);
            }
            return orderList;
        }

        @Override
        public String toString() {
            return Arrays.toString(this.state.toArray());
        }
    }
}

