package dimhol.entity;

import dimhol.components.Component;

import java.util.Set;
import java.util.UUID;
import java.util.NoSuchElementException;

/**
 * Represents an entity i.e. an object meant to hold references to {@link Component}.
 */
public interface Entity {

    /**
     * Gets the unique id associated with the entity.
     *
     * @return the id of the entity.
     */
    UUID getId();

    /**
     * Adds a component to the entity.
     *
     * @param component the component to be added
     */
    void addComponent(Component component);

    /**
     * Removes a component from the entity.
     *
     * @param component the component to be removed
     */
    void removeComponent(Component component);

    /**
     * Should be called after {@link Entity#hasComponent(Class)}
     * Gets the component of the given class.
     *
     * @param componentClass the class to find among entity's components
     * @return a component instance of @param
     * @throws NoSuchElementException if the component of
     * class @param is not present
     */
    Component getComponent(Class<? extends Component> componentClass);

    /**
     * Checks if the entity has a component of the given class.
     *
     * @param componentClass the class to find among entity's components
     * @return true if the entity has an instance of @param
     */
    boolean hasComponent(Class<? extends Component> componentClass);

    /**
     * Checks if the entity belongs to given family i.e. a set of components.
     *
     * @param family the family to check
     * @return true if the entity belongs to @param
     */
    boolean hasFamily(Set<Class<? extends Component>> family);

    /**
     * Gets all the components of the entity.
     *
     * @return the components of the entity
     */
    Set<Component> getComponents();
}
