package dimhol.systems;

import dimhol.core.WorldImpl;
import dimhol.entity.Entity;
import dimhol.events.Event;
import dimhol.components.AiComponent;
import dimhol.components.PlayerComponent;

/**
 * A system for manage enemies.
 */
public final class AiSystem extends AbstractSystem {

    private Entity player;

    /**
     *
     * @param world .
     */
    public AiSystem(final WorldImpl world) {
        super(world, AiComponent.class);
        for (var entity : world.getEntities()) {
            if (entity.hasComponent(PlayerComponent.class)) {
                this.player = entity;
            }
        }
    }

    @Override
    public void process(final Entity enemy, double dt) {
        var enemyAI = (AiComponent) enemy.getComponent(AiComponent.class);
        for (var action : enemyAI.getRoutine()) {
            action.setPlayer(player);
            if (action.canExecute(enemy)) {
                var routineExecute = action.execute();
                if (routineExecute.isPresent()) {
                    for (Event event : routineExecute.get()) {
                        this.world.notifyEvent(event);
                    }
                }
                break;
            }
        }
    }

}
