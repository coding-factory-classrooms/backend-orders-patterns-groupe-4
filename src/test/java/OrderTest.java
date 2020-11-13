import org.example.OnOrderChangeListener;
import org.example.models.Order;
import org.example.models.items.consoles.Console;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class OrderTest {

    private Order order;

    @Before
    public void before() {
        order = new Order();
    }

    @Test
    public void checkIfListenerIsCalledWhenUpdatingItems() {
        OnOrderChangeListener onOrderChangeListener = Mockito.mock(OnOrderChangeListener.class);
        order.setOnChangeListener(onOrderChangeListener);
        order.addItem(new Console());

        Mockito.verify(onOrderChangeListener).onOrderChange();
    }

    @Test
    public void checkIfListenerIsCalledWhenUpdatingState() {
        OnOrderChangeListener onOrderChangeListener = Mockito.mock(OnOrderChangeListener.class);
        order.setOnChangeListener(onOrderChangeListener);
        order.setState(Order.State.IN_PROGRESS);

        Mockito.verify(onOrderChangeListener).onOrderChange();
    }
}
