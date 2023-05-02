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


    private Scene scene;
    private Input input;
    private final List<Entity> entities;
    private final List<GameSystem> systems;
    private final List<Event> events;
    private boolean gameOver;

    /**
     * Constructs a world.
     */
    public World() {
        this.entities = new ArrayList<>();
        this.systems = new ArrayList<>();
        this.events = new ArrayList<>();
        this.scene = new Scene();
        this.input = new InputImpl();
        /*
        generate first level
         */
        var gf = new GenericFactory();
        this.entities.add(gf.createHeart(10,10));
        this.entities.add(gf.createHeart(13,13));
        this.entities.add(gf.createPlayer(15, 15));
        this.entities.add(gf.createZombieEnemy(16,8));
        this.entities.add(gf.createShooterEnemy(13,5));

        /*
        Add systems
         */
        this.systems.add(new PlayerSystem(this));
        this.systems.add(new AiSystem(this));
        this.systems.add(new MovementSystem(this));
        this.systems.add(new CollisionSystem(this));
        this.systems.add(new PhysicsSystem(this));
        this.systems.add(new ItemSystem(this));
        this.systems.add(new InteractionSystem(this));
        this.systems.add(new CombatSystem(this));
        this.systems.add(new CheckHealthSystem(this));
        this.systems.add(new ClearCollisionSystem(this));
        this.systems.add( new AnimationSystem(this));
        this.systems.add(new RenderSystem(this));

    }

    /**
     * Gets called each game loop. Updates the world.
     */
    public void update(double dt) {
        this.systems.forEach(s -> s.update(dt));
        this.handleEvents();
        this.scene.render();
    }


    public void notifyEvent(final Event ev) {
        this.events.add(ev);
    }


    private void handleEvents() {
        this.events.stream().forEach(ev -> ev.execute(this));
        this.events.clear();
    }

    /**
     * Gets the entities currently alive in the world.
     *
     * @return the list of world entities
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
     * Gets the input.
     *
     * @return the world input.
     */
    public Input getInput() {
        return this.input;
    }

    /**
     * Gets the view representation of the world.
     *
     * @return the world scene.
     */
    public Scene getScene() {
        return this.scene;
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

    /**
     * Checks match result.
     *
     * @return true if player defeated the boss, false otherwise.
     */
    public boolean getResult() {
        return false;
    }
}
