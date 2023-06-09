package dimhol.view.screens;

import dimhol.core.Engine;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.io.Serial;

/**
 * A class which create the home menu.
 */
public class HomeScreen extends AbstractScreen {

    @Serial
    private static final long serialVersionUID = -916578027671877105L;
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
        super.setBackground("/asset/bg/homeScreen.png");
        Color color = new Color(R, G, B);
        this.add(super.createLabel("/asset/bg/HomeScreenTitle.png"), super.getGbc());
        super.setGbcAnchorCenter();
        super.setGbcFillHorizontal();
        super.getCenterPanel().setLayout(new GridBagLayout());
        super.getCenterPanel().add(super.createButton(e -> engine.newGame(), "PLAY", color), super.getGbc());
        super.getCenterPanel().add(super.createButton(e -> engine.getMainWindow().changePanel(new OptionScreen(engine)), "OPTIONS",
            color), super.getGbc());
            super.getCenterPanel().add(super.createButton(e -> Runtime.getRuntime().exit(0), "QUIT", color), super.getGbc());
            super.getGbc().weighty = 1;
        this.add(super.getCenterPanel());
    }
}
