package controller.gamecore.commands;
import controller.gamecore.Controller;

/**
 * Command to handle the input.
 */
public interface Command {
    void execute(Controller controller);
}
