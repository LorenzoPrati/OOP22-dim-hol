package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.AiComponent;
import it.unibo.dimhol.components.Component;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.components.PlayerComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.AddEntityEvent;

/**
 * A system for manage enemies.
 */
public final class AiSystem extends AbstractSystem {

    private Entity player;

    /**
     *
     * @param world .
     * @param comps .
     */
    @SafeVarargs
    public AiSystem(final World world, final Class<? extends Component>... comps) {
        super(world, comps);
        for (var entity : world.getEntities()) {
            if (entity.hasComponent(PlayerComponent.class)) {
                this.player = entity;
            }
        }
    }

    @Override
    public void process(final Entity enemy, final long dt) {
        var enemyAI = (AiComponent) enemy.getComponent(AiComponent.class);
        var enemyMov = (MovementComponent) enemy.getComponent(MovementComponent.class);

        for (var routine : enemyAI.getRoutines()) {
            if (routine.canExecute(player, enemy)) {
                if (routine.execute(enemy).isPresent()) {
                    for (Entity entity : routine.execute(enemy).get()) {
                        enemyMov.setEnabled(false);
                        this.world.notifyEvent(new AddEntityEvent(entity));
                    }
                }
            }
            break;
        }
    }

}
