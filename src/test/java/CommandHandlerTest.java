import junit.framework.AssertionFailedError;
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

        order.setState(Command.State.IN_PROGRESS);
        Assert.assertEquals(Command.State.IN_PROGRESS, order.getState());
    }

    @Test public void updateOrderFromProcessedFail() {
        Command order = new Command();
        order.setState(Command.State.PROCESSED);

        order.setState(Command.State.IN_PROGRESS);
        Assert.assertEquals(Command.State.PROCESSED, order.getState());
    }

    @Test public void updateOrderFromAbortedFail() {
        Command order = new Command();
        order.setState(Command.State.ABORTED);

        order.setState(Command.State.IN_PROGRESS);
        Assert.assertEquals(Command.State.ABORTED, order.getState());
    }

    @Test public void addEntryInList() {
        Assert.assertEquals(0,commandHandler.getHistory().size());
        Command order = new Command();
        commandHandler.addCommand(order);
        Assert.assertEquals(1,commandHandler.getHistory().size());
    }

    @Test public void updateEntryInList() {
        Assert.assertEquals(0,commandHandler.getHistory().size());
        Command order = new Command();
        order.setOnChangeListener(commandHandler);
        commandHandler.addCommand(order);
        order.setState(Command.State.IN_PROGRESS);
        Assert.assertEquals(2,commandHandler.getHistory().size());
        System.out.println(commandHandler.getHistory());
    }

    @Test(expected = Test.None.class) public void verifyUndoEmptyHistory() {
        commandHandler.undoAction();
    }

    @Test public void verifyUndoOnceEntryHistory() {
        Command order = new Command();
        commandHandler.addCommand(order);
        order.setState(Command.State.IN_PROGRESS);
        commandHandler.undoAction();
        Assert.assertEquals(Command.State.NEW, commandHandler.getCommands().get(0).getState());
    }

    @Test(expected = Test.None.class) public void verifyRedoEmptyHistory() {
        commandHandler.redoAction();
    }

    @Test public void verifyRedoOnceEntryHistory() {
        Command order = new Command();
        commandHandler.addCommand(order);
        order.setOnChangeListener(commandHandler);

        order.setState(Command.State.IN_PROGRESS);
        commandHandler.undoAction();

        Assert.assertEquals(Command.State.NEW, commandHandler.getCommands().get(0).getState());
        commandHandler.redoAction();

        Assert.assertEquals(Command.State.IN_PROGRESS, commandHandler.getCommands().get(0).getState());
    }
}
