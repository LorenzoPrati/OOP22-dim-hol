package it.unibo.dimhol.components;

import it.unibo.dimhol.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds data about collisions.
 */
public class CollisionComponent implements Component {

    private final List<Entity> collidedList;

    /**
     * Constructs a CollisionComponent with empty collided list.
     */
    public CollisionComponent() {
        this.collidedList = new ArrayList<>();
    }

    /**
     * Adds the given entity to the collided list.
     *
     * @param e the entity that collided
     */
    public void addCollided(final Entity e) {
        this.collidedList.add(e);
    }

    /**
     * Gets the collided entities.
     *
     * @return the list containing the collided entities
     */
    public List<Entity> getCollided() {
        return this.collidedList;
    }
}
