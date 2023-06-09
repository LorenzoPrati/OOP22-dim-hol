package dimhol.view;

import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * Model a class that manages the panels of the game.
 */
public interface MainWindow {

    /**
     * Replaces the current panel with the panel passed as argument.
     * @param panel
     */
    void changePanel(JPanel panel);

    /**
     * Changes the resolution.
     * @param dimension
     */
    void changeResolution(Dimension dimension);

    /**
     * Screen size getter.
     * @return screen size of the monitor
     */
    Dimension getScreenSize();
}

