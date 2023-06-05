package dimhol.systems;

import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.components.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract implementation of GameSystem interface. Serves the purpose of iterating through entities
 * that belongs to a given family of components.
 */
public abstract class AbstractSystem implements GameSystem {

    private final Set<Class<? extends Component>> family = new HashSet<>();

    /**
     * Constructs a system to operates on a given world and family of components.
     *
     * @param comps the family of components
     */
    public AbstractSystem(final Class<? extends Component>... comps) {
        this.family.addAll(Arrays.asList(comps));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final World world, final double deltaTime) {
        world.getEntities().stream()
                .filter(e -> e.hasFamily(this.family))
                .forEach(e -> this.process(e, deltaTime, world));
    }

    /**
     * This method gets called on each entity belonging to the system family.
     * Performs the system specific task.
     *
     * @param entity the entity to process
     * @param deltaTime the delta time
     * @param world the world
     */
    protected abstract void process(Entity entity, double deltaTime, World world);
}
