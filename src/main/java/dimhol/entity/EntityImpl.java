package dimhol.entity;

import dimhol.components.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of Entity interface.
 */
public final class EntityImpl implements Entity {
    private final Set<Component> comps = new HashSet<>();

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
}
