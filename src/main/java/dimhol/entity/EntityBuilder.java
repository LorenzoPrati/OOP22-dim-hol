package dimhol.entity;

import dimhol.components.Component;

/**
 * A simple builder for entities.
 */
public class EntityBuilder {

    private final Entity e = new EntityImpl();

    /**
     * Adds the given component to the builder.
     *
     * @param c the component to be added
     * @return the builder
     */
    public EntityBuilder add(Component c) {
        e.addComponent(c);
        return this;
    }

    /**
     * This method gets called when all components
     * have been added.
     *
     * @return the complete entity
     */
    public Entity build() {
        return this.e;
    }
}
