package it.unibo.dimhol.components;

import it.unibo.dimhol.entity.Entity;

/**
 * Holds data about a collision.
 */
public class CollisionComponent implements Component {

    private final Entity collided;

    /**
     * Constructs a CollisionComponent with given collided entity.
     *
     * @param collided the collided entity
     */
    public CollisionComponent(final Entity collided) {
        this.collided = collided;
    }

    /**
     * Gets the collided entity.
     *
     * @return the collided entity
     */
    public Entity getCollided() {
        return this.collided;
    }
}
