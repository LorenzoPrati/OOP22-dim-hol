package controller.commands;

import javax.tools.Diagnostic;

import controller.Command;
import model.World;
import model.common.Direction;

public class LeftCommand implements Command{

    @Override
    public void execute(World world) {
        world.getPlayer().enableMovement();
        world.getPlayer().setDirection(Direction.LEFT);
    }
    
}
