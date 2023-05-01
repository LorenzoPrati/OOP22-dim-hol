package it.unibo.dimhol.components;

import it.unibo.dimhol.logic.collision.BodyShape;

/**
 * Holds data about the body of an entity.
 */
public class BodyComponent implements Component {

    private BodyShape bodyShape;
    private boolean solid;

    /**
     * Constructs a BodyComponent with given body shape and solidity.
     *
     * @param bs is the body shape
     * @param solid the solid property flag. True to make the body solid, false otherwise.
     */
    public BodyComponent(final BodyShape bs, final boolean solid) {
        this.bodyShape = bs;
        this.solid = solid;
    }

    /**
     * Gets the geometric representation of the body.
     *
     * @return the body shape
     */
    public BodyShape getBodyShape() {
        return this.bodyShape;
    }

    /**
     * Checks the body solidity.
     *
     * @return true if the body is solid, false otherwise.
     */
    public boolean isSolid() {
        return solid;
    }
}
