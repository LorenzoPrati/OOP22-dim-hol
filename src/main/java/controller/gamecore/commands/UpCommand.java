package controller.gamecore.commands;

import controller.gamecore.Controller;
import model.common.Direction;

public class UpCommand implements Command{

    @Override
    public void execute(Controller controller) {
        controller.getWorld().getPlayer().enableMovement();
        controller.getWorld().getPlayer().setDirection(Direction.UP);
    }
}
