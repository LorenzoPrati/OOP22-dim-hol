package dimhol.view.screens;

import dimhol.core.Engine;
import dimhol.view.Scene;
import java.awt.Color;
import java.awt.GridBagConstraints;
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
        super(engine);
        super.setBackground("/asset/bg/Interior 6.png");
        Color color = new Color(R, G, B);
        this.add(super.createLabel("/asset/bg/pauseTitle.png"), gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        super.getCenterPanel().setLayout(new GridBagLayout());
        super.getCenterPanel().add(super.createButton(e -> {
            engine.getMainWindow().changePanel(scene.getPanel());
            scene.setupInput();
            engine.resumeGame();
        }, "RESUME", color), gbc);
        super.getCenterPanel().add(super.createButton(e -> {
            engine.getMainWindow().changePanel(new HomeScreen(engine));
            engine.endGame();
        }, "HOME", color), gbc);
        gbc.weighty = 1;
        this.add(super.getCenterPanel()); 
    }
}

