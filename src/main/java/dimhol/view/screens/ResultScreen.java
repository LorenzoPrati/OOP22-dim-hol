package dimhol.view.screens;

import dimhol.core.Engine;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.io.Serial;

/**
 * Result screen.
 */
public class ResultScreen extends AbstractScreen {

    @Serial
    private final static long serialVersionUID = 7705218068700224049L;

    private static final String WIN_BACKGROUND_PATH = "/asset/bg/winScreen.png";
    private static final String LOSE_BACKGROUND_PATH = "/asset/bg/loseScreen.png";
    private static final String WIN_MESSAGE = "/asset/bg/You-won.png";
    private static final String LOSE_MESSAGE = "/asset/bg/You-lost.png";

    /**
     * Constructs a ResultScreen.
     *
     * @param engine the engine
     * @param result the result of the match
     */
    public ResultScreen(final Engine engine, final boolean result) {
        super.setBackground(result ? WIN_BACKGROUND_PATH : LOSE_BACKGROUND_PATH);
        this.add(super.createLabel(result ? WIN_MESSAGE : LOSE_MESSAGE), super.getGbc());
        super.setGbcAnchorCenter();
        super.setGbcFillHorizontal();
        super.getCenterPanel().setLayout(new GridBagLayout());
        super.getCenterPanel().add(super.createButton(e -> engine.getMainWindow().changePanel(new HomeScreen(engine)),
                "RETURN HOME", result ? Color.GREEN : Color.RED), super.getGbc());
        super.getGbc().weighty = 1;
        this.add(super.getCenterPanel());
    }
}
