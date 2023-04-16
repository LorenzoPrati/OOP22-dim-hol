package it.unibo.dimhol.components;

import it.unibo.dimhol.commons.shapes.BodyShape;

public class BodyComponent implements Component {

    private BodyShape bs;
    private boolean solid;

    public BodyComponent(final BodyShape bs, final boolean solid) {
        this.bs = bs;
        this.solid = solid;
    }

    public BodyShape getBs() {
        return bs;
    }

    public boolean isSolid() {
        return solid;
    }
}
