package dimhol.systems;

import dimhol.components.AIComponent;
import dimhol.components.PlayerComponent;
import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.events.WorldEvent;

/**
 * A system to manage enemies.
 */
public final class AISystem extends AbstractSystem {

    /**
     * Constructs an AISystem.
     */
    public AISystem() {
        super(AIComponent.class);
    }

    @Override
    protected void process(final Entity enemy, final double deltaTime, final World world) {
        var enemyAI = (AIComponent) enemy.getComponent(AIComponent.class);
        enemyAI.updateTime(deltaTime);
        for (var action : enemyAI.getRoutine()) {
            world.getEntities()
                    .stream()
                    .filter(e -> e.hasComponent(PlayerComponent.class))
                    .findFirst()
                    .ifPresent(action::setPlayer);
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
