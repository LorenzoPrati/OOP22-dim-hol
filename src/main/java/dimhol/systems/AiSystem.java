package dimhol.systems;

import dimhol.components.AiComponent;
import dimhol.components.PlayerComponent;
import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.events.WorldEvent;

/**
 * A system to manage enemies.
 */
public final class AiSystem extends AbstractSystem {

    public AiSystem() {
        super(AiComponent.class);
    }

    @Override
    public void process(final Entity enemy, final double dt, final World world) {
        var enemyAI = (AiComponent) enemy.getComponent(AiComponent.class);
        enemyAI.updateTime(dt);
        for (var action : enemyAI.getRoutine()) {
            action.setPlayer(getPlayer((world)));
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

    private Entity getPlayer(final World world) {
        Entity player = null;
        for (var entity : world.getEntities()) {
            if (entity.hasComponent(PlayerComponent.class)) {
                player = entity;
            }
        }
        return player;
    }

}
