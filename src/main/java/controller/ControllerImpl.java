package controller;

import java.util.List;

import model.World;

/**
 * This class serve as the controller:
 */
public class ControllerImpl implements Controller {

    private World world;
    //TODO: private View view;
    private boolean isRunning;
    private final static long PERIOD = 20;
    List<Integer> gameObjects;

    @Override
    public void mainLoop() {
        long previous = System.currentTimeMillis();
        while (isRunning || !this.world.isFinished()) {
            if (this.world.roomCleared()) {
                //TODO: load the new map.
                this.world.changeRoom(null);    //TODO: insert what we found before with the map loader.
            }
            long current = System.currentTimeMillis();
            long elapsed = current - previous;
            this.updateInput();
            this.update(elapsed);
            this.render();
            this.waitForNextFrame(current);
            previous = current;
        }
        //TODO: Tells to the up-controller to display the screen "WIN" or "LOOSE".

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
     * This takes the input and handle it:
     */
    public void inputaHandler() {
        //TODO: to be added
    }

    /**
     * Change the Main Loop state:  
     * @param isRunning
     */
    @Override
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
    
    /**
     * Starts the Main Loop:
     * @return isRunning
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * 
     */

    public void updateInput() {
        // TODO: implement input handling. 
    }

    /**
     * 
     * @param dt
     */
    public void update(long dt) {
        world.update(dt);
        this.updateId();
    }

    /**
     * Updates the list of game objects and their IDs
     */
    public void updateId() {
        for (var id : this.gameObjects) {
            if (this.world.getGameObject(id) == null) {
                this.gameObjects.remove(id);
            }
        }
        for (var obj : this.world.getGameObjectList()) {
            if (!this.gameObjects.contains(obj.getId())) {
                this.gameObjects.add(obj.getId());
            }
        }
    }

    public void render() {
        //TODO: implent rendering

        for (var id : this.gameObjects) {
            var obj = this.world.getGameObject(id);
            //TODO: this.view.update(obj.x, obj.y, obj.state, obj.type, id);
        }
        //TODO: this.view.draw();
        //TODO: this.view.showUI();

    }    
}
