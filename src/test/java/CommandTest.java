import org.example.OnOrderChangeListener;
import org.example.models.Command;
import org.example.models.items.Console;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CommandTest {

    private Command command;

    @Before
    public void before() {
        command = new Command();
    }

    @Test
    public void checkIfListenerIsCalledWhenUpdatingItems() {
        OnOrderChangeListener onOrderChangeListener = Mockito.mock(OnOrderChangeListener.class);
        command.setOnChangeListener(onOrderChangeListener);
        command.addItem(new Console());

        Mockito.verify(onOrderChangeListener).onOrderChange();
    }

    @Test
    public void checkIfListenerIsCalledWhenUpdatingState() {
        OnOrderChangeListener onOrderChangeListener = Mockito.mock(OnOrderChangeListener.class);
        command.setOnChangeListener(onOrderChangeListener);
        command.setState(Command.State.IN_PROGRESS);

        Mockito.verify(onOrderChangeListener).onOrderChange();
    }
}
