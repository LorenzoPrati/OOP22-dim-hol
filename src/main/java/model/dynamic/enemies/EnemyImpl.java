package model.dynamic.enemies;

import java.util.Random;

import org.locationtech.jts.geom.Coordinate;

import model.common.Direction;
import model.common.ObjectType;
import model.common.State;
import model.dynamic.AbstractDynamicObject;
import model.dynamic.LivingCharacter;
import model.dynamic.player.Player;
import model.physics.CollisionBox;
import model.rooms.Room;

public class EnemyImpl extends AbstractDynamicObject implements LivingCharacter, Enemy {

    private final static int VISUAL_RANGE = 5;
    private final static int MAX_STEPS = 5;
    private EnemyStrategy strategy;
    private int health;
    private Player player;
    private int steps;
    private final int damage;


    public EnemyImpl(Coordinate position, Integer id, ObjectType type, State state, CollisionBox collisionBox,
            Room room, int height, int width, EnemyStrategy strategy, int health, Player player, int damage,
            int steps) {
        super(position, id, type, state, collisionBox, room, height, width);
        this.strategy = Math.random() < 0.5 ? new ShooterStrategy() : new ZombieStrategy();
        this.health = health;
        this.player = player;
        this.damage = damage;
        this.steps = MAX_STEPS;
    }

    @Override
    public void takeDamage(int damage) {
        this.setHealth(damage);
    }

    @Override
    public boolean isAlive() {
        return this.getHealth() > 0;
    }

    @Override
    public void updateState() {
        if (this.isMoving()) {
            switch (this.getDirection()) {
                case UP:
                    this.setState(State.WALK_UP);
                    break;
                case DOWN:
                    this.setState(State.WALK_DOWN);
                    break;
                case LEFT:
                    this.setState(State.WALK_LEFT);
                    break;
                case RIGHT:
                    this.setState(State.WALK_RIGHT);
                    break;
            }
        }
    }

    @Override
    public void update(long dt) {

        if (this.isAggroZone(player.getPosition())) {
            this.strategy.execute(this);
        } else {
            this.redirect(this.getDirection());
        }
        this.move(dt);
        if (this.isCollidingWithWalls()) {
            this.bounceBack();
            this.redirect(this.getDirection());
        }
        if (this.getCollisionBox().intersects(player.getCollisionBox())) {
            this.player.takeDamage(this.getDamage());
        }
        this.updateState();

    }

    private void redirect(Direction lastDirection) {
        if (this.getSteps() > 0) {
            this.decreaseSteps();
        } else {
            this.changeDirection();
            if (lastDirection == this.getDirection()) {
                this.redirect(lastDirection);
            }
            this.steps = MAX_STEPS;
        }
    }

    public int getDamage() {
        return damage;
    }

    private boolean isAggroZone(Coordinate position) {
        return position.distance(this.getPosition()) < VISUAL_RANGE;
    }

    public int getHealth() {
        return health;
    }

    public void changeDirection() {
        int pick = new Random().nextInt(Direction.values().length);
        this.setDirection(Direction.values()[pick]);
    }

    public int getSteps() {
        return steps;
    }

    public void decreaseSteps() {
        this.steps--;
    }

    public void setHealth(int damage) {
        this.health -= damage;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public int getFirePower() {
        return this.damage;
    }
}
