package it.unibo.dimhol.entity;

import it.unibo.dimhol.components.Component;

import java.util.Set;

/**
 * Represents an entity i.e. an object meant to hold references to {@link Component}.
 */
public interface Entity {

    /**
     * Adds a component to the entity.
     *
     * @param comp the component to be added
     */
    void addComponent(Component comp);

    /**
     * Removes a component from the entity.
     *
     * @param comp the component to be removed
     */
    void removeComponent(Component comp);

    /**
     *
     * @param compClass the class to find among entity's components
     * @return a component instance of @param
     */
    Component getComponent(Class<? extends Component> compClass);

    /**
     *
     * @param compClass the class to find among entity's components.
     * @return true if the entity has an instance of @param
     */
    boolean hasComponent(Class<? extends Component> compClass);

    /**
     * Verify if entity belongs to given family i.e. a set of components.
     *
     * @param family the family to verify
     * @return true if the entity belongs to @param
     */
    boolean hasFamily(Set<Class<? extends Component>> family);
}
