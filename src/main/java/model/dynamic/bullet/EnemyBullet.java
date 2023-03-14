package model.dynamic.bullet;

import org.locationtech.jts.geom.Coordinate;

import model.common.Direction;
import model.common.ObjectType;
import model.common.State;
import model.dynamic.AbstractDynamicObject;
import model.dynamic.player.Player;
import model.physics.CollisionBox;
import model.rooms.Room;

public class EnemyBullet extends AbstractDynamicObject implements Bullet {

    private final int damage;

    public EnemyBullet(Coordinate position, Integer id, ObjectType type, State state, CollisionBox collisionBox,
            Room room, int height, int width, int damage, Direction direction) {
        super(position, id, type, state, collisionBox, room, height, width);
        this.damage = damage;
        this.setDirection(direction);
    }

    @Override
    public void update(long dt) {
        for (var o : this.getRoom().getObjects()) {
            if (this.getCollisionBox().intersects(o.getCollisionBox())) {
                if (o instanceof Player) {
                    Player p = (Player) o;
                    p.takeDamage(this.getDamage());
                }
            }
        }
    }

    @Override
    public int getDamage() {
        return this.damage;
    }

}
