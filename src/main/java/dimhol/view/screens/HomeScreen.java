package dimhol.view.screens;

import dimhol.core.Engine;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/**
 * A class which create the home menu.
 */
public class HomeScreen extends AbstractScreen {
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
     * Creates a home screen.
     * @param engine
     */
    public HomeScreen(final Engine engine) {
        super(engine);
        super.setBackground("/asset/bg/Fortress 1.png");
        Color color = new Color(R, G, B);
        this.add(super.createLabel("/asset/bg/HomeScreenTitle.png"), gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(super.createButton((e -> engine.newGame()), "PLAY", color), gbc);
        centerPanel.add(super.createButton((e -> engine.getMainWindow().changePanel(new OptionScreen(engine))), "OPTIONS",
            color), gbc);
        centerPanel.add(super.createButton((e -> Runtime.getRuntime().exit(0)), "QUIT", color), gbc);
        gbc.weighty = 1;
        this.add(centerPanel);
    }
}
