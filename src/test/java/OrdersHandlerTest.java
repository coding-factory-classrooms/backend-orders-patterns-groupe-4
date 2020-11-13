import org.example.OrdersHandler;
import org.example.models.Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrdersHandlerTest {
    private OrdersHandler ordersHandler;

    @Before public void before() {
        ordersHandler = new OrdersHandler();
    }

    @Test public void aCommandIsAddedToTheListSuccess() {
        Order order = new Order();

        Assert.assertEquals(0, ordersHandler.getCommands().size());

        ordersHandler.addOrder(order);
        Assert.assertEquals(1, ordersHandler.getCommands().size());
    }

    @Test public void aCommandIsAddedToTheListFail() {
        Order order = new Order();
        order.setState(Order.State.IN_PROGRESS);

        Assert.assertEquals(0, ordersHandler.getCommands().size());

        ordersHandler.addOrder(order);
        Assert.assertEquals(0, ordersHandler.getCommands().size());
    }

    @Test public void updateOrderSuccess() {
        Order order = new Order();
        order.setState(Order.State.IN_PROGRESS);

        order.setState(Order.State.IN_PROGRESS);
        Assert.assertEquals(Order.State.IN_PROGRESS, order.getState());
    }

    @Test public void updateOrderFromProcessedFail() {
        Order order = new Order();
        order.setState(Order.State.PROCESSED);

        order.setState(Order.State.IN_PROGRESS);
        Assert.assertEquals(Order.State.PROCESSED, order.getState());
    }

    @Test public void updateOrderFromAbortedFail() {
        Order order = new Order();
        order.setState(Order.State.ABORTED);

        order.setState(Order.State.IN_PROGRESS);
        Assert.assertEquals(Order.State.ABORTED, order.getState());
    }

    @Test public void addEntryInList() {
        Assert.assertEquals(0, ordersHandler.getHistory().size());
        Order order = new Order();
        ordersHandler.addOrder(order);
        Assert.assertEquals(1, ordersHandler.getHistory().size());
    }

    @Test public void updateEntryInList() {
        Assert.assertEquals(0, ordersHandler.getHistory().size());
        Order order = new Order();
        order.setOnChangeListener(ordersHandler);
        ordersHandler.addOrder(order);
        order.setState(Order.State.IN_PROGRESS);
        Assert.assertEquals(2, ordersHandler.getHistory().size());
        System.out.println(ordersHandler.getHistory());
    }

    @Test(expected = Test.None.class) public void verifyUndoEmptyHistory() {
        ordersHandler.undoAction();
    }

    @Test public void verifyUndoOnceEntryHistory() {
        Order order = new Order();
        ordersHandler.addOrder(order);
        order.setOnChangeListener(ordersHandler);
        order.setState(Order.State.IN_PROGRESS);
        ordersHandler.undoAction();
        Assert.assertEquals(Order.State.NEW, ordersHandler.getCommands().get(0).getState());
    }

    @Test(expected = Test.None.class) public void verifyRedoEmptyHistory() {
        ordersHandler.redoAction();
    }

    @Test public void verifyRedoOnceEntryHistory() {
        Order order = new Order();
        ordersHandler.addOrder(order);
        order.setOnChangeListener(ordersHandler);

        order.setState(Order.State.IN_PROGRESS);
        ordersHandler.undoAction();

        Assert.assertEquals(Order.State.NEW, ordersHandler.getCommands().get(0).getState());
        ordersHandler.redoAction();

        Assert.assertEquals(Order.State.IN_PROGRESS, ordersHandler.getCommands().get(0).getState());
    }

    @Test public void makeActionAfterUndoRewriteHistorySuccess() {
        Order order = new Order();
        order.setOnChangeListener(ordersHandler);
        ordersHandler.addOrder(order);

        order.setState(Order.State.IN_PROGRESS);
        ordersHandler.undoAction();

        ordersHandler.getCommands().get(0).setState(Order.State.PROCESSED);
        Assert.assertEquals(Order.State.PROCESSED, ordersHandler.getHistory().get(1).getState().get(0).getState());
    }

}
