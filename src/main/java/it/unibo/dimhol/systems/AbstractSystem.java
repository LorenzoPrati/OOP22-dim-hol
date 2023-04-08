package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.Component;
import it.unibo.dimhol.entity.Entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract implementation of GameSystem interface. Serves the purpose of iterating through entities
 * that belongs to a given family of components.
 */
public abstract class AbstractSystem implements GameSystem{
    protected World world;
    private Set<Class<? extends Component>> family = new HashSet<>();

    public AbstractSystem(World w, Class<? extends Component>... comps) {
        this.world = w;
        this.family.addAll(Arrays.asList(comps));
    }

    @Override
    public void update(long dt) {
        this.world.getEntities().stream().filter(e -> e.hasFamily(this.family)).forEach(e -> this.process(e, dt));
    }

    /**
     * This method gets called on each entity belonging to the system family.
     * Performs the system specific task.
     *
     * @param e the entity to process
     * @param dt the delta time
     */
    public abstract void process(Entity e, long dt);

}
