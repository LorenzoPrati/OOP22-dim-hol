package dimhol.entity;

import dimhol.components.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Implementation of Entity interface.
 */
public final class EntityImpl implements Entity {

    private final Set<Component> components;
    private final UUID id;

    /**
     * Constructs an entity with the given components.
     *
     * @param components the components to add to the entity
     */
    public EntityImpl(final Set<Component> components) {
        this.components = new HashSet<>(components);
        this.id = UUID.randomUUID();
    }

    /**
     * Constructs an entity copy of another entity.
     *
     * @param other the entity to copy
     */
    public EntityImpl(final Entity other) {
        this.components = new HashSet<>(other.getComponents());
        this.id = other.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addComponent(final Component component) {
        this.components.add(component);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeComponent(final Component component) {
        this.components.remove(component);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Component getComponent(final Class<? extends Component> componentClass) {
        return this.components.stream()
                .filter(componentClass::isInstance)
                .findAny()
                .get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasComponent(final Class<? extends Component> componentClass) {
        return this.components.stream()
                .anyMatch(componentClass::isInstance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasFamily(final Set<Class<? extends Component>> family) {
        return this.components.stream()
                .filter(c -> family.contains(c.getClass()))
                .count() == family.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Component> getComponents() {
        return new HashSet<>(this.components);
    }
}
