package controller.gamecore.commands;

import controller.gamecore.Controller;

/*
 * The command Pattern -> separetes the request for an action from the execution of that action.
 *                        Each request is ecapsulated, and the obj can then be passed around and executed later.
 *                        In the game each input event can be ecapsulated as a command obj, and a separate input handler can 
 *                        handle the execution of those commands.
 */

public interface Command {
    void execute(Controller controller);
}
