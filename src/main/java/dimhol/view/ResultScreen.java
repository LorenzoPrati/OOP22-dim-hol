package dimhol.view;

import dimhol.core.Engine;

import javax.swing.*;
import java.awt.*;

/**
 * Result screen.
 */
public class ResultScreen extends AbstractScreen {

    private final static String WIN_BACKGROUND_PATH = "src/main/resources/asset/bg/winScreen.png";
    private final static String LOSE_BACKGROUND_PATH = "src/main/resources/asset/bg/loseScreen.png";

    /**
     * Constructs a ResultScreen.
     *
     * @param engine the engine
     * @param result the result of the match
     */
    public ResultScreen(final Engine engine, final boolean result) {
        super(engine);
        this.background = new ImageIcon(result ? WIN_BACKGROUND_PATH : LOSE_BACKGROUND_PATH);
        /*
        todo label
         */
        var homeButton = super.createButton(e -> engine.getMainWindow().changePanel(new HomeScreen(engine)),
                "RETURN HOME", result? Color.YELLOW : Color.RED);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(homeButton, gbc);
        gbc.weighty = 1;
        this.add(centerPanel);
    }
}
