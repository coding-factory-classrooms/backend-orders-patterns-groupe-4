import org.example.History;
import org.example.OnOrderChangeListener;
import org.example.models.Command;
import org.example.models.items.Console;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class CommandTest {

    private Command order;

    @Before
    public void before() {
        order = new Command();
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
        order.setState(Command.State.IN_PROGRESS);

        Mockito.verify(onOrderChangeListener).onOrderChange();
    }
}
