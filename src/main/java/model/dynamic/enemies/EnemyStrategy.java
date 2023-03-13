package model.dynamic.enemies;

/**
 * Define the AI of the enmies.
 */
public interface EnemyStrategy {
    
    /**
     * @param coordinate
     * @param enemyImpl
     * 
     */
    void execute(Enemy enemy);
}
