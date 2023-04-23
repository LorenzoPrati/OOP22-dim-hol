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

    /**
     * View representation of the state of the world.
     */
    private Scene scene;
    /**
     * Class that registers user input.
     */
    private InputListener input;

    private final List<Entity> entities = new ArrayList<>();
    private final List<GameSystem> systems = new ArrayList<>();
    private GenericFactory gf = new GenericFactory();
    private final Queue<Event> eventQueue = new ArrayDeque<>();

    private boolean gameOver;
    private boolean result;

    /**
     * Constructs a world.
     */
    public World() {
        /*
        Add entities
         */


        //this.entities.add(gf.createHeart(400,560));

        this.entities.add(gf.createHeart(400,500));
        this.entities.add(gf.createZombieEnemy(400,300));
        this.entities.add(gf.createHeart(400,550));
        this.entities.add(gf.createShooterEnemy(470,300));
        this.entities.add(gf.createPlayer(200, 150));

        /*
        Add systems
         */
        this.systems.add(new PlayerSystem(this));
        this.systems.add(new AiSystem(this));
        this.systems.add(new MovementSystem(this));
        this.systems.add(new CollisionSystem(this));
        this.systems.add(new PhysicsSystem(this));
        this.systems.add(new ItemSystem(this));
        this.systems.add(new ClearCollisionSystem(this));
        this.systems.add(new RenderSystem(this));
        /*
        Setup view
         */
        this.scene = new Scene();
    }

    /**
     * Registers an input listener so that the world can react to user input.
     *
     * @param input the input listener to register for the world
     */
    public void setInputListener(InputListener input) {
        this.input = input;
    }

    /**
     * Gets the Scene registered for the world.
     *
     * @return the current Scene
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Gets called each game loop. Updates the world by delta time.
     *
     * @param dt the delta time
     */
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

    public InputListener getInputListener() {
        return this.input;
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game is complete or the player dies,
     * false otherwise
     */
    public boolean isGameOver() {
        return this.gameOver;
    }

    /**
     * Sets that the game is over.
     */
    public void setGameOver() {
        this.gameOver = true;
    }


    //public boolean getResult() {
        //return result;
    //}

    //public void setResult(final boolean result) {
        //this.result = result;
    //}


}
