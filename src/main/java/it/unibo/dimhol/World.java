package it.unibo.dimhol;

import it.unibo.dimhol.components.PositionComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.entity.EntityBuilder;
import it.unibo.dimhol.entity.EntityImpl;
import it.unibo.dimhol.entity.GenericEntityFactory;
import it.unibo.dimhol.events.Event;
import it.unibo.dimhol.systems.*;
import it.unibo.dimhol.components.*;
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
    private Scene scene;

    //factories
    private final InputListener input = new InputListener();
    private GenericEntityFactory factory = new GenericEntityFactory();
    private final Queue<Event> eventQueue = new ArrayDeque<>();

    public World() {
        /*
        Add entities
         */
        this.entities.add(factory.createPlayer(200, 150));

        /*
        Add systems
         */
        var inputSystem = new PlayerInputSystem(this, PlayerComponent.class);
        var movementSystem = new MovementSystem(this, PositionComponent.class, MovementComponent.class);
        var collisionSystem = new CollisionSystem(this, BodyComponent.class, PositionComponent.class);
        var renderSystem = new RenderSystem(this, PositionComponent.class, VisualDebugComponent.class);
        var clearCollidedSystem = new ClearCollisionSystem(this, CollisionComponent.class);

        this.systems.add(inputSystem);
        this.systems.add(movementSystem);
        this.systems.add(collisionSystem);
        this.systems.add(clearCollidedSystem);
        this.systems.add(renderSystem);

        /*
        Setup view
         */
        this.scene = new Scene(this);

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
        this.scene.render();
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

    public Scene getScene() {
        return this.scene;
    }
}
