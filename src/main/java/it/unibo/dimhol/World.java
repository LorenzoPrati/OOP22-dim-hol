package it.unibo.dimhol;

import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.entity.GenericFactory;
import it.unibo.dimhol.events.Event;
import it.unibo.dimhol.systems.*;
import it.unibo.dimhol.view.InputListener;
import it.unibo.dimhol.view.Scene;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * A class to model the world where the entities exist.
 */
public class World {

    private Engine engine;
    private final List<Entity> entities = new ArrayList<>();
    private final List<GameSystem> systems = new ArrayList<>();
    private Scene scene;
    private final InputListener input;
    private GenericFactory gf = new GenericFactory();
    private final Queue<Event> eventQueue = new ArrayDeque<>();
    private boolean gameOver;
    private boolean result;

    public World(final Engine engine) {
        /*
        Add entities
         */
        this.entities.add(gf.createPlayer(200, 150));
        this.entities.add(gf.createObstacle(100,100));
        this.entities.add(gf.createZombieEnemy(300,100));
        this.entities.add(gf.createShooterEnemy(300,200));
        this.entities.add(gf.createZombieEnemy(300,400));
        /*
        Add systems
         */
        this.systems.add(new PlayerInputSystem(this));
        this.systems.add(new MovementSystem(this));
        this.systems.add(new CollisionSystem(this));
        this.systems.add(new ClearCollisionSystem(this));
        this.systems.add(new RenderSystem(this));
        this.systems.add(new AiSystem(this));
        /*
        Setup view
         */
        this.engine = engine;
        this.input = new InputListener(engine);
        this.scene = new Scene(this);
    }

    public void update(final long dt) {
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

    public Scene getScene() {
        return this.scene;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(final boolean result) {
        this.result = result;
    }

    public void setGameOver() {
        this.gameOver = true;
    }
}
