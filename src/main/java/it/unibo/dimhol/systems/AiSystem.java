package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.AiComponent;
import it.unibo.dimhol.components.PlayerComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.Event;

/**
 * A system for manage enemies.
 */
public final class AiSystem extends AbstractSystem {

    private Entity player;

    /**
     *
     * @param world .
     */
    public AiSystem(final World world) {
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
                var routineExecute = action.execute(enemy);
                if (routineExecute.isPresent()) {
                    for (Event event : routineExecute.get()) {
                        this.world.notifyEvent(event);
                    }
                    routineExecute.get().clear();
                }
                break;
            }
        }
    }

}
