package dimhol.core;

import dimhol.entity.Entity;
import dimhol.events.WorldEvent;

import dimhol.gamelevels.LevelManager;
import dimhol.gamelevels.LevelManagerImpl;
import dimhol.systems.MapCollisionSystem;
import dimhol.systems.*;
import dimhol.view.Scene;
import dimhol.view.SceneImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of World interface.
 */
public class WorldImpl implements World {

    private final Scene scene;
    private final Input input;
    private List<Entity> entities;
    private final List<GameSystem> systems;
    private final List<WorldEvent> events;
    private final LevelManager levelManager;
    private boolean gameOver;

    /**
     * Constructs a world.
     */
    public WorldImpl() {
        this.entities = new ArrayList<>();
        this.systems = new ArrayList<>();
        this.events = new ArrayList<>();
        this.levelManager = new LevelManagerImpl();
        this.scene = new SceneImpl(this);
        this.input = new InputImpl();

        /*
        generate first level
         */
        levelManager.changeLevel(this.getEntities()).forEach(this::addEntity);

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
        this.systems.add(new CombatSystem(this));
        this.systems.add(new CheckHealthSystem(this));
        this.systems.add(new ClearCollisionSystem(this));
        this.systems.add(new AnimationSystem(this));
        this.systems.add(new RenderSystem(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        this.systems.forEach(s -> s.update(deltaTime));
        this.handleEvents();
        this.scene.render();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(this.entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEntity(final Entity entity) {
        this.entities.add(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeEntity(final Entity entity) {
       this.entities.remove(entity);
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
    public boolean isWin() {
        return false;
        //todo
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyEvent(final WorldEvent event) {
        this.events.add(event);
    }

    @Override
    public LevelManager getLevelManager() {
        return this.levelManager;
    }

    @Override
    public void changeLevel() {
        this.entities = this.levelManager.changeLevel(this.getEntities());
    }

    /**
     * Handles the events present in the event queue.
     */
    private void handleEvents() {
        this.events.forEach(ev -> ev.execute(this));
        this.events.clear();
    }
}
