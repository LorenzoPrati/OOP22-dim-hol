package model.dynamic.enemies;

import model.common.Direction;

public class ZombieStrategy implements EnemyStrategy {

    @Override
    public void execute(Enemy enemy) {
        if (enemy.getPlayer().getPosition().getX() < enemy.getPosition().getX()) {
            enemy.setDirection(Direction.LEFT);
        } else if (enemy.getPlayer().getPosition().getX() > enemy.getPosition().getX()) {
            enemy.setDirection(Direction.RIGHT);
        } else if (enemy.getPlayer().getPosition().getY() < enemy.getPosition().getY()) {
            enemy.setDirection(Direction.UP);
        } else {
            enemy.setDirection(Direction.DOWN);
        }
    }
    
}
