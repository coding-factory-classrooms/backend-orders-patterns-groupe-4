package org.example.mementos;

import org.example.models.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrdersOriginator {
    public OrdersMemento save(List<Order> orders) {
        List<Order> clonedList = this.clone(orders);
        return new OrdersMemento(clonedList);
    }

    public List<Order> restore(OrdersMemento memento) {
        return new ArrayList<>(memento.getState());
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

    public static class OrdersMemento {
        private final List<Order> state;

        public List<Order> getState() {
            return state;
        }

        public OrdersMemento(List<Order> state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return Arrays.toString(this.state.toArray());
        }
    }
}
