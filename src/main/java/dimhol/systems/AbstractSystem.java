package dimhol.systems;

import dimhol.core.WorldImpl;
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

    protected final WorldImpl world;
    private final Set<Class<? extends Component>> family = new HashSet<>();

    /**
     * Constructs a system to operates on a given world and family of components.
     *
     * @param w the world
     * @param comps the family of components
     */
    public AbstractSystem(final WorldImpl w, final Class<? extends Component>... comps) {
        this.world = w;
        this.family.addAll(Arrays.asList(comps));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(double dt) {
        this.world.getEntities().stream()
                .filter(e -> e.hasFamily(this.family))
                .forEach(e -> this.process(e, dt));
    }

    /**
     * This method gets called on each entity belonging to the system family.
     * Performs the system specific task.
     *
     * @param e  the entity to process
     * @param dt
     */
    public abstract void process(final Entity e, double dt);
}