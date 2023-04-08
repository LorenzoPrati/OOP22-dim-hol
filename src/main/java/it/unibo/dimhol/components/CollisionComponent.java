package it.unibo.dimhol.components;

import it.unibo.dimhol.entity.Entity;

public class CollisionComponent implements Component {

    private final Entity entity;

    public CollisionComponent(final Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
