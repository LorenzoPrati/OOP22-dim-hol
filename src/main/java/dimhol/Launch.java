package dimhol;

import dimhol.core.EngineImpl;

/**
 * Launches the game.
 */
public final class Launch {

    private Launch() {
    }

    /**
     * The class responsible for launching the game.
     *
     * @param args the main arguments
     */
    public static void main(final String[] args) {
        new EngineImpl();
    }
}
