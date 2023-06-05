package dimhol.components;

/**
 * Holds data about an interactor that is the ability to interact with
 * objects that has an {@link InteractableComponent}.
 */
public class InteractorComponent implements Component {

    /**
     * The condition of the interaction.
     */
    private boolean interacting;

    /**
     * Sets the interaction state.
     *
     * @param interacting the interaction state to set
     */
    public void setInteracting(final boolean interacting) {
        this.interacting = interacting;
    }

    /**
     * Checks if the interaction is happening.
     *
     * @return true if the interaction is happening
     */
    public boolean isInteracting() {
        return this.interacting;
    }
}

