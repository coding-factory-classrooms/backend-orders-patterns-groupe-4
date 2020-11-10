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
}
