package it.unibo.dimhol.components;

public class InteractorComponent implements Component{

    private boolean interacting;

    public void setInteracting(final boolean interacting) {
        this.interacting = interacting;
    }

    public boolean isInteracting() {
        return interacting;
    }
}

