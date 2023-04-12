package it.unibo.dimhol.components;

/* A temporary component just for debug. This component contains an integer which represents the type of the entity */
public class VisualDebugComponent implements Component {
    private final int type;

    public VisualDebugComponent(final int type){
        this.type = type;
    }

    public int getType() {
        return this.type;
    }
}
