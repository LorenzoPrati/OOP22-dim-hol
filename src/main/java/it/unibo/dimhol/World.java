package it.unibo.dimhol;

import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.entity.GenericFactory;
import it.unibo.dimhol.events.Event;

import it.unibo.dimhol.systems.*;
import it.unibo.dimhol.view.InputListener;
import it.unibo.dimhol.view.Scene;

import java.util.ArrayList;
import java.util.List;

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
    /*
    state of the game
     */
    private boolean gameOver;
    //private boolean result;
    /*
    world
     */
    private final List<Entity> entities;
    private final List<GameSystem> systems;
    private final List<Event> events;
    /*
    map
     */
    //parser
    //level manager
    //

    /**
     * Constructs a world.
     */
    public World() {
        this.entities = new ArrayList<>();
        this.systems = new ArrayList<>();
        this.events = new ArrayList<>();
        this.scene = new Scene();

        /*
        generate first level
         */
        var gf = new GenericFactory();
        this.entities.add(gf.createPlayer(15, 15));

        //set tilemap
        /*
        Setup view
         */
        //set map in scene
        /*
        Add systems
         */
        this.systems.add(new PlayerSystem(this));
        this.systems.add(new AiSystem(this));
        this.systems.add(new MovementSystem(this));
        this.systems.add(new CollisionSystem(this));
        this.systems.add(new PhysicsSystem(this));
        this.systems.add(new ItemSystem(this));
        this.systems.add(new CombatSystem(this));
        this.systems.add(new DeleteDeathEntitiesSystem(this));
        this.systems.add(new ClearCollisionSystem(this));
        this.systems.add( new AnimationSystem(this));
        this.systems.add(new RenderSystem(this));

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
     * Gets called each game loop. Updates the world.
     */
    public void update(double dt) {
        this.systems.forEach(s -> s.update(dt));
        this.handleEvents();
        //System.out.println("num ent " + this.getEntities().size());
        this.scene.render();
    }

    /**
     * Notifies an event to the world to handle.
     *
     * @param ev the event to notify
     */
    public void notifyEvent(final Event ev) {
        this.events.add(ev);
    }

    /**
     * Handle all events.
     */
    private void handleEvents() {
        this.events.stream().forEach(ev -> ev.execute(this));
        this.events.clear();
    }

    /**
     * Gets the entities that currently exist in the world.
     *
     * @return a list containing all the entities in the world
     */
    public List<Entity> getEntities() {
        return this.entities;
    }

    /**
     * Adds an entity to the world.
     *
     * @param e the entity to add
     */
    public void addEntity(final Entity e) {
        this.entities.add(e);
    }

    /**
     * Removes an entity from the world.
     *
     * @param e the entity to remove
     */
    public void removeEntity(final Entity e) {
        this.entities.remove(e);
    }

    /**
     * Gets the input listener that is registered for the world.
     *
     * @return the current input listener
     */
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

    //getTileMap()
}
