package it.unibo.dimhol.entity;

import it.unibo.dimhol.components.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of Entity interface.
 */
public class EntityImpl implements Entity{
    private final Set<Component> comps = new HashSet<>();

    @Override
    public void addComponent(Component comp) {
        this.comps.add(comp);
    }

    @Override
    public void removeComponent(Component comp) {
        this.comps.remove(comp);
    }

    @Override
    public Component getComponent(Class<? extends Component> compClass) {
        return this.comps.stream().filter(e -> comps.getClass().equals(compClass))
                .findAny()
                .get();
    }

    @Override
    public boolean hasComponent(Class<? extends Component> compClass) {
        return this.comps.stream().filter(c -> c.getClass().equals(compClass))
                .count() == 1;
    }

    @Override
    public boolean hasFamily(Set<Class<? extends Component>> family) {
        return this.comps.stream().filter(c -> family.contains(c.getClass()))
                .count() == family.size();
    }
}
