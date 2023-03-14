package model.dynamic.enemies;

import model.common.Direction;
import model.dynamic.bullet.BulletFactory;
import model.dynamic.bullet.BulletFactoryImpl;

public class ShooterStrategy implements EnemyStrategy {

    BulletFactory bulletFactory = new BulletFactoryImpl();;
    
    @Override
    public void execute(Enemy enemy) {
        bulletFactory.spawnEnemyBullet(enemy, Direction.UP);
        bulletFactory.spawnEnemyBullet(enemy, Direction.DOWN);
        bulletFactory.spawnEnemyBullet(enemy, Direction.LEFT);
        bulletFactory.spawnEnemyBullet(enemy, Direction.RIGHT);
    }
    

}
