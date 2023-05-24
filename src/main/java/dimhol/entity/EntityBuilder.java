package dimhol.entity;

import dimhol.components.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * A simple builder for entities.
 */
public class EntityBuilder {

    private final Set<Component> components;

    /**
     * Constructs an entity builder.
     */
    public EntityBuilder() {
        this.components = new HashSet<>();
    }

    /**
     * Adds the given component to the builder.
     *
     * @param component the component to be added
     * @return the builder
     */
    public EntityBuilder add(final Component component) {
        this.components.add(component);
        return this;
    }

    /**
     * This method gets called when all components
     * have been added.
     *
     * @return the complete entity
     */
    public Entity build() {
        return new EntityImpl(this.components);
    }
}
