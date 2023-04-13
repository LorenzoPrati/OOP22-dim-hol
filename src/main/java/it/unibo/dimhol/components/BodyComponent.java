package it.unibo.dimhol.components;

import it.unibo.dimhol.commons.shapes.BodyShape;

public class BodyComponent implements Component {

    private BodyShape bs;

    public BodyComponent(final BodyShape bs) {
        this.bs = bs;
    }

    public BodyShape getBs() {
        return bs;
    }
}
