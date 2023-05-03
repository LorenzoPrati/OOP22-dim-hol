package it.unibo.dimhol.core;

import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.entity.GenericFactory;
import it.unibo.dimhol.events.Event;

import it.unibo.dimhol.systems.MapCollisionSystem;
import it.unibo.dimhol.systems.*;
import it.unibo.dimhol.view.Scene;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of World interface.
 */
public class WorldImpl implements World {

    private Scene scene;
    private Input input;
    private final List<Entity> entities;
    private final List<GameSystem> systems;
    private final List<Event> events;
    private boolean gameOver;

    /**
     * Constructs a world.
     */
    public WorldImpl() {
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
        this.entities.add(gf.createZombieEnemy(10, 4));
        this.entities.add(gf.createShooterEnemy(10, 7));
        /*
        Add systems
         */
        this.systems.add(new PlayerSystem(this));
        this.systems.add(new AiSystem(this));
        this.systems.add(new MovementSystem(this));
        this.systems.add(new MapCollisionSystem(this));
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
     * {@inheritDoc}
     */
    @Override
    public void update(double dt) {
        this.systems.forEach(s -> s.update(dt));
        this.handleEvents();
        this.scene.render();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entity> getEntities() {
        return this.entities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEntity(final Entity e) {
        this.entities.add(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeEntity(final Entity e) {
        this.entities.remove(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Input getInput() {
        return this.input;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Scene getScene() {
        return this.scene;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameOver() {
        return this.gameOver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameOver() {
        this.gameOver = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getResult() {
        return false;
    }

    public void notifyEvent(final Event ev) {
        this.events.add(ev);
    }

    private void handleEvents() {
        this.events.stream().forEach(ev -> ev.execute(this));
        this.events.clear();
    }
}
