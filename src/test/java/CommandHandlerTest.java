import org.example.CommandHandler;
import org.example.models.Command;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommandHandlerTest {
    private CommandHandler commandHandler;

    @Before public void before() {
        commandHandler = new CommandHandler();
    }

    @Test public void aCommandIsAddedToTheListSuccess() {
        Command command = new Command();

        Assert.assertEquals(0, commandHandler.getCommands().size());

        commandHandler.addCommand(command);
        Assert.assertEquals(1, commandHandler.getCommands().size());
    }

    @Test public void aCommandIsAddedToTheListFail() {
        Command command = new Command();
        command.setState(Command.State.IN_PROGRESS);

        Assert.assertEquals(0, commandHandler.getCommands().size());

        commandHandler.addCommand(command);
        Assert.assertEquals(0, commandHandler.getCommands().size());
    }

    @Test public void updateOrderSuccess() {
        Command order = new Command();
        order.setState(Command.State.IN_PROGRESS);

        commandHandler.updateOrder(order, Command.State.IN_PROGRESS);
        Assert.assertEquals(Command.State.IN_PROGRESS, order.getState());
    }

    @Test public void updateOrderFromProcessedFail() {
        Command order = new Command();
        order.setState(Command.State.PROCESSED);

        commandHandler.updateOrder(order, Command.State.IN_PROGRESS);
        Assert.assertEquals(Command.State.PROCESSED, order.getState());
    }

    @Test public void updateOrderFromAbortedFail() {
        Command order = new Command();
        order.setState(Command.State.ABORTED);

        commandHandler.updateOrder(order, Command.State.IN_PROGRESS);
        Assert.assertEquals(Command.State.ABORTED, order.getState());
    }
}
