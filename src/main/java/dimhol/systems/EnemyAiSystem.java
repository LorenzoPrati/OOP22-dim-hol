package dimhol.systems;

import dimhol.components.AIComponent;
import dimhol.components.PlayerComponent;
import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.events.WorldEvent;

/**
 * A system to manage enemies.
 */
public final class EnemyAiSystem extends AbstractSystem {

    public EnemyAiSystem() {
        super(AIComponent.class);
    }

    @Override
    public void process(final Entity enemy, final double dt, final World world) {
        var enemyAI = (AIComponent) enemy.getComponent(AIComponent.class);
        enemyAI.updateTime(dt);
        for (var action : enemyAI.getRoutine()) {
            action.setPlayer(world.getEntities().stream()
                    .filter(e -> e.hasComponent(PlayerComponent.class))
                    .findFirst().get());
            action.setEnemy(enemy);
            if (action.canExecute()) {
                var routineExecute = action.execute();
                if (routineExecute.isPresent()) {
                    for (WorldEvent event : routineExecute.get()) {
                        world.notifyEvent(event);
                    }
                }
                break;
            }
        }
    }

}
