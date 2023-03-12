package controller.gamecore;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import controller.Engine;
import controller.gamecore.commands.Command;
import model.World;
import model.WorldImpl;
import view.Scene;
import view.gameview.GameScene;

/**
 * This class serve as the controller:
 */
public class ControllerImpl implements Controller {

    private final World world;
    private final Scene scene;
    private boolean isRunning;
    private final static long PERIOD = 20;
    private final List<Integer> identifiers;
    private final Queue<Command> commands;
    private final Engine engine;

    public ControllerImpl(Engine engine) {
        this.identifiers = new LinkedList<>();
        this.commands = new LinkedBlockingDeque<>();
        this.world = new WorldImpl();
        this.scene = new GameScene();
        this.engine = engine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mainLoop() {
        long previous = System.currentTimeMillis();
        while (isRunning || !this.world.isFinished()) {
            if (this.world.roomCleared()) {
                // TODO: load the new map.
                this.engine.loadScene();
                this.world.changeRoom(null); // TODO: insert what we found before with the map loader.
                this.engine.stopLoadScene();
            }
            long current = System.currentTimeMillis();
            long elapsed = current - previous;
            this.updateInput();
            this.update(elapsed);
            this.render();
            this.waitForNextFrame(current);
            previous = current;
        }
        // TODO: Tells to the up-controller to display the screen "WIN" or "LOOSE".
    }

    /**
     * Waits for the next frame.
     * 
     * @param start
     */
    private void waitForNextFrame(long start) {
        long dt = System.currentTimeMillis() - start;
        if (dt < PERIOD) {
            try {
                Thread.sleep(PERIOD - dt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void updateInput() {
        this.world.getPlayer().reset();
        if (!this.commands.isEmpty()) {
            this.commands.poll().execute(this);
        }
    }

    public void update(long dt) {
        world.update(dt);
        this.updateId();
    }

    /**
     * Updates the list of game objects and their IDs
     */
    public void updateId() {
        for (var id : this.identifiers) {
            if (this.world.getGameObject(id).isEmpty()) {
                this.identifiers.remove(id);
            }
        }
        for (var obj : this.world.getGameObjectList()) {
            if (!this.identifiers.contains(obj.getId())) {
                this.identifiers.add(obj.getId());
            }
        }
    }

    public void render() {
        // TODO: implent rendering

        for (var id : this.identifiers) {
            var obj = this.world.getGameObject(id);
            this.scene.getPanel().update(obj.getPosition().getX(), obj.getPosition().getY(), obj.getState(),
                    obj.getType(), obj.getId());
        }

        this.scene.getPanel().draw();
        this.scene.getPanel().showUI();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyPressed(Command c) {
        this.commands.add(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public World getWorld() {
        return this.world;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Engine getEngine() {
        return this.engine;
    }
}
