package dimhol.core;

import dimhol.entity.factories.EnemyFactory;
import dimhol.entity.factories.GenericFactory;
import dimhol.entity.Entity;
import dimhol.events.Event;

import dimhol.systems.MapCollisionSystem;
import dimhol.systems.*;
import dimhol.view.Scene;
import dimhol.view.SceneImpl;

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
        this.scene = new SceneImpl();
        this.input = new InputImpl();
        /*
        generate first level
         */
        var gf = new GenericFactory();
        var ef = new EnemyFactory();

        this.entities.add(gf.createPlayer(15, 15));
        this.entities.add(ef.createZombie(3, 4));
        this.entities.add(ef.createZombie(7, 4));
        this.entities.add(ef.createZombie(9, 2));
        this.entities.add(ef.createZombie(7, 9));
        this.entities.add(ef.createZombie(6, 6));

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
