package it.unibo.dimhol;

import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.Event;
import it.unibo.dimhol.systems.GameSystem;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * A class to model the world where the entities exist.
 */
public class World {

    private static final long PERIOD = 20;
    private final List<Entity> entities = new ArrayList<>();
    private final List<GameSystem> systems = new ArrayList<>();
    //Scene scene;
    //factories
    private final InputListener input = new InputListener();

    private final Queue<Event> eventQueue = new ArrayDeque<>();

    public World() {
        /*
        Add entities
         */

        /*
        Add systems
         */

        /*
        Setup view
         */

    }


    public void gameLoop() {
        long prevTime = System.currentTimeMillis();
        while(true) {
            long currTime = System.currentTimeMillis();
            long dt = currTime - prevTime;
            this.update(dt);
            this.render();
            this.waitForNextFrame(currTime);
            prevTime = currTime;
        }
    }


    private void waitForNextFrame(long startTime) {
        long dt = System.currentTimeMillis() - startTime;
        if (dt < PERIOD) {
            try {
                Thread.sleep(PERIOD - dt);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void render() {
    }

    private void update(final long dt) {
        this.systems.forEach(s -> s.update(dt));
        this.handleEvents();
    }

    public void notifyEvent(final Event ev) {
        this.eventQueue.add(ev);
    }

    private void handleEvents() {
        while(!this.eventQueue.isEmpty()) {
            this.eventQueue.poll().execute(this);
        }
        this.eventQueue.clear();
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public void addEntity(final Entity e) {
        this.entities.add(e);
    }

    public void removeEntity(final Entity e) {
        this.entities.remove(e);
    }

    public InputListener getInput() {
        return input;
    }
}
