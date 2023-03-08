package controller.commands;

import controller.Command;
import model.World;
import model.common.Direction;

public class RightCommand implements Command{

    @Override
    public void execute(World world) {
        world.getPlayer().enableMovement();
        world.getPlayer().setDirection(Direction.RIGHT);
    }
    
}
