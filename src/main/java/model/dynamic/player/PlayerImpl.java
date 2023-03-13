package model.dynamic.player;

import java.util.LinkedList;

import org.locationtech.jts.geom.Coordinate;

import model.common.ObjectType;
import model.common.State;
import model.dynamic.AbstractDynamicObject;
import model.dynamic.LivingCharacter;
import model.dynamic.enemies.Enemy;
import model.items.Item;
import model.physics.CollisionBox;
import model.rooms.Room;

public class PlayerImpl extends AbstractDynamicObject implements Player, LivingCharacter {

    private Health healthComponent;
    private int coins;
    private boolean attacking;
    private boolean shooting;
    private int meeleDamage;
    private int ammunition;
    private int firepower;

    public PlayerImpl(Coordinate position, Integer id, ObjectType type, State state, CollisionBox collisionBox,
            Room room,
            int height, int width) {
        super(position, id, type, state, collisionBox, room, height, width);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update(long dt) {
        this.updateState();
        if (this.isMoving()) {
            this.move(dt);
            if (this.isCollidingWithWalls()) {
                this.bounceBack();
            }
        }
        for (var o : new LinkedList<>(this.getRoom().getObjects())) {
            if (this.getCollisionBox().intersects(o.getCollisionBox())) {
                if (o instanceof Item) {
                    Item i = (Item) o;
                    if (true /* i.canBeUsedOn(this) */) {
                        i.applyEffect(this);
                    }
                } else if (o instanceof Enemy && this.attacking) {
                    Enemy e = (Enemy) o;
                    e.takeDamage(this.meeleDamage);
                }
            }
        }
        if (this.isShooting()) {
            // check delay
            // this.getRoom().addObjects(/*new Bullet(this) */);
            // ammunition--
        }
    }

    @Override
    public void updateState() {
        if (this.isMoving()) {
            switch (this.getDirection()) {
                case UP:
                    this.setState(State.WALK_UP);
                case DOWN:
                    this.setState(State.WALK_DOWN);
                case LEFT:
                    this.setState(State.WALK_LEFT);
                case RIGHT:
                    this.setState(State.WALK_RIGHT);
            }
        }
        if (this.isAttacking()) {
            switch (this.getDirection()) {
                case UP:
                    this.setState(State.ATTACK_UP);
                case DOWN:
                    this.setState(State.ATTACK_DOWN);
                case LEFT:
                    this.setState(State.ATTACK_LEFT);
                case RIGHT:
                    this.setState(State.ATTACK_RIGHT);
            }
        }
        if (!this.isMoving() || !this.isAttacking()) {
            this.setState(State.IDLE);
        }
    }

    public Health getHealth() {
        return healthComponent;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int amount) {
        this.coins += amount;
    }

    public boolean isAttacking() {
        return this.attacking;
    }

    @Override
    public boolean isAlive() {
        return this.getHealth().getValue() > 0;
    }

    private boolean isShooting() {
        return this.shooting;
    }

    @Override
    public void takeDamage(int amount) {
        this.getHealth().decrease(amount);
    }

    @Override
    public int getFirePower() {
        return this.firepower;
    }

}
