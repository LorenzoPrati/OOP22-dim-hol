package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.AiComponent;
import it.unibo.dimhol.components.Component;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.components.PlayerComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.AddEntityEvent;
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
    public void process(final Entity enemy, final long dt) {
        var enemyAI = (AiComponent) enemy.getComponent(AiComponent.class);

        for (var routine : enemyAI.getRoutines()) {
            if (routine.canExecute(player, enemy)) {
                var routineExecute = routine.execute(enemy);
                if (routineExecute.isPresent()) {
                    for (Event event : routineExecute.get()) {
                        this.world.notifyEvent(event);
                    }
                    routineExecute.get().clear();
                }
            }
            break;
        }
    }

}
