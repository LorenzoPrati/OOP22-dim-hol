package dimhol.entity;

import dimhol.components.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Implementation of Entity interface.
 */
public final class EntityImpl implements Entity {

    private final Set<Component> comps;
    private final UUID id;

    public EntityImpl(final Entity other) {
        this.id = other.getId();
        this.comps = new HashSet<>(other.getComponents());
    }

    public EntityImpl(final UUID id) {
        this.id = id;
        this.comps = new HashSet<>();
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public void addComponent(final Component comp) {
        this.comps.add(comp);
    }

    @Override
    public void removeComponent(final Component comp) {
        this.comps.remove(comp);
    }

    @Override
    public Component getComponent(final Class<? extends Component> compClass) {
        return this.comps.stream().filter(e -> e.getClass() == compClass)
                .findAny()
                .get();
    }

    @Override
    public boolean hasComponent(final Class<? extends Component> compClass) {
        return this.comps.stream().filter(c -> c.getClass() == compClass)
                .count() == 1;
    }

    @Override
    public boolean hasFamily(final Set<Class<? extends Component>> family) {
        return this.comps.stream().filter(c -> family.contains(c.getClass()))
                .count() == family.size();
    }

    @Override
    public Set<Component> getComponents() {
        return Collections.unmodifiableSet(this.comps);
    }
}
