package controller.gamecore.commands;

import controller.gamecore.Controller;

public class EscCommand implements Command{

    @Override
    public void execute(Controller controller) {
        controller.getEngine().notifyPause();
    }
    
    
}
