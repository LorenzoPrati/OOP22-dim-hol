package dimhol.core;

import dimhol.components.*;
import dimhol.entity.Entity;
import dimhol.events.WorldEvent;
import dimhol.gamelevels.LevelManager;
import dimhol.gamelevels.LevelManagerImpl;
import dimhol.input.Input;
import dimhol.systems.*;
import dimhol.view.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementation of World interface.
 */
public class WorldImpl implements World {

    private final List<Entity> entities;
    private final List<GameSystem> systems;
    private final List<WorldEvent> events;
    private final LevelManager levelManager;
    private final Scene scene;
    private boolean gameOver;
    private boolean win;
    
    /**
     * Constructs a world.
     */
    public WorldImpl(final Engine engine) {
        this.entities = new ArrayList<>();
        this.systems = new ArrayList<>();
        this.events = new ArrayList<>();
        this.levelManager = new LevelManagerImpl();
        this.scene = new SceneImpl(engine);
        this.scene.setupInput();
        /*
        Add systems
         */
        this.systems.add(new PlayerInputSystem());
        this.systems.add(new AISystem());
        this.systems.add(new MovementSystem());
        this.systems.add(new MapCollisionSystem());
        this.systems.add(new CollisionSystem());
        this.systems.add(new PhysicsSystem());
        this.systems.add(new ItemSystem());
        this.systems.add(new InteractableSystem());
        this.systems.add(new CombatSystem());
        this.systems.add(new CheckHealthSystem());
        this.systems.add(new ClearAttackSystem());
        this.systems.add(new ClearCollisionSystem());
        this.systems.add(new AnimationSystem());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        this.systems.forEach(s -> s.update(this, deltaTime));
        this.handleEvents();
        this.prepareRender();
        this.scene.render();
    }

    /**
     * Find entities that need to be rendered on screen and update scene.
     */
    private void prepareRender() {
        var renderList = new ArrayList<Pair<Integer,GraphicInfo>>();
        this.getEntities().stream()
                .filter(e -> e.hasComponent(AnimationComponent.class))
                .forEach(e -> {
                    /*
                    Gets generic entity graphics elements
                     */
                    var pos = (PositionComponent) e.getComponent(PositionComponent.class);
                    var body = (BodyComponent) e.getComponent(BodyComponent.class);
                    var anim = (AnimationComponent) e.getComponent(AnimationComponent.class);
                    renderList.add(new ImmutablePair<>(pos.getZ(),
                            new GraphicInfo(anim.getIndex(),
                                    anim.getImage(),
                                    pos.getPos(),
                                    body.getBodyShape().getBoundingWidth(),
                                    body.getBodyShape().getBoundingHeight())));
                    /*
                     * Gets hud elements
                     */
                    if (e.hasComponent(PlayerComponent.class)) {
                        var health = (HealthComponent) e.getComponent(HealthComponent.class);
                        var coins = (CoinPocketComponent) e.getComponent(CoinPocketComponent.class);
                        this.scene.getHUD().updateHUD(health.getCurrentHealth(),
                                                            health.getMaxHealth(),
                                                            coins.getCurrentAmount());
                    }
                });
        renderList.stream()
                .sorted(Comparator.comparing(Pair::getKey))
                .forEach(l -> this.scene.updateList(l.getValue()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entity> getEntities() {
        return new ArrayList<>(this.entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyEvent(final WorldEvent event) {
        this.events.add(event);
    }

    /**
     * Handles the events present in the event queue.
     */
    private void handleEvents() {
        this.events.forEach(ev -> ev.execute(this));
        this.events.clear();
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


    @Override
    public void changeLevel() {
        var newEntities = this.levelManager.changeLevel(this.getEntities());
        this.entities.clear();
        this.entities.addAll(newEntities);
        this.scene.setMap(this.getLevelManager().getTileMap());
    }

    @Override
    public LevelManager getLevelManager() {
        return this.levelManager;
    }

    @Override
    public void setWin(boolean win) {
        this.win = win;
        this.gameOver = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWin() {
        return this.win;
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
    public Input getInput() {
        return this.scene.getInput();
    }
}
