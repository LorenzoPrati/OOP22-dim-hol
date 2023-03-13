package model.dynamic.bullet;

import model.common.Direction;
import model.common.ObjectType;
import model.common.State;
import model.dynamic.enemies.Enemy;
import model.dynamic.player.Player;

public class BulletFactoryImpl implements BulletFactory {

    private final static int HEIGHT_ENEMY_BULLET = 10;
    private final static int WIDTH_ENEMY_BULLET = 10;
    private final static int HEIGHT_PLAYER_BULLET = 10;
    private final static int WIDTH_PLAER_BULLET = 10;

    @Override
    public Bullet spawnEnemyBullet(Enemy enemy, Direction direction) {
        return new EnemyBullet(enemy.getPosition(), null, ObjectType.ENEMY_BULLET, State.IDLE, null, enemy.getRoom(),
                HEIGHT_ENEMY_BULLET, WIDTH_ENEMY_BULLET, enemy.getFirePower(), direction);
    }

    @Override
    public Bullet spawnPlayerBullet(Player player, Direction direction) {
        return new PlayerBullet(player.getPosition(), null, ObjectType.PLAYER_BULLET, State.IDLE, null,
                player.getRoom(), HEIGHT_PLAYER_BULLET, WIDTH_PLAER_BULLET, player.getFirePower(), direction);
    }

}
