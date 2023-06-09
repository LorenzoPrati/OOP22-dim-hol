package dimhol.view.screens;

import dimhol.core.Engine;
import dimhol.view.Scene;
import java.awt.Color;
import java.awt.GridBagLayout;

/**
 * A class for creating a pause screen.
 */
public class PauseScreen extends AbstractScreen {
    /**
     * Value of red colour needed to create the new color.
     */
    private static final int R = 102;
    /**
     * Value of green colour needed to create the new color.
     */
    private static final int G = 0;
    /**
     * Value of blue colour needed to create the new color.
     */
    private static final int B = 153;

    /**
     * Creates a pause screen.
     * @param engine
     * @param scene
     */
    public PauseScreen(final Engine engine, final Scene scene) {
        super.setBackground("/asset/bg/pauseScreen.png");
        Color color = new Color(R, G, B);
        this.add(super.createLabel("/asset/bg/pauseTitle.png"), super.getGbc());
        super.setGbcAnchorCenter();
        super.setGbcFillHorizontal();
        super.getCenterPanel().setLayout(new GridBagLayout());
        super.getCenterPanel().add(super.createButton(e -> {
            engine.getMainWindow().changePanel(scene.getPanel());
            scene.setupInput();
            engine.resumeGame();
        }, "RESUME", color), super.getGbc());
        super.getCenterPanel().add(super.createButton(e -> {
            engine.getMainWindow().changePanel(new HomeScreen(engine));
            engine.endGame();
        }, "HOME", color), super.getGbc());
        super.getGbc().weighty = 1;
        this.add(super.getCenterPanel()); 
    }
}

