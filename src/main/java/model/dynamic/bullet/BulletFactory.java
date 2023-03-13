package model.dynamic.bullet;

import model.common.Direction;
import model.dynamic.enemies.Enemy;
import model.dynamic.player.Player;

/**
 * Factory of bullets.
 */
public interface BulletFactory {
   
    /**
     * Spawn a player bullet.
     */
    Bullet spawnPlayerBullet(Player player, Direction direction);

    /**
     * Spawn an enemy bullet.
     */
    Bullet spawnEnemyBullet(Enemy enemy, Direction direction);
}
