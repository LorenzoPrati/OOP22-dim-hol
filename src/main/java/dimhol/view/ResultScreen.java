package dimhol.view;

import dimhol.core.Engine;

import javax.swing.*;
import java.awt.*;

/**
 * Result screen.
 */
public class ResultScreen extends AbstractScreen {

    /**
     * Constructs a ResultScreen.
     *
     * @param engine the engine
     * @param result the result of the match
     */
    public ResultScreen(final Engine engine, final boolean result) {
        super(engine);
        var homeButton = new JButton("RETURN HOME");
        homeButton.addActionListener(e -> this.engine.getMainWindow().changePanel(new HomeScreen(engine)));
        homeButton.setFont(font);
        homeButton.setForeground(new Color(102,0,153));
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(homeButton, gbc);
        gbc.weighty = 1;
        this.add(centerPanel);
        this.background = new ImageIcon(result ? "src/main/resources/asset/bg/win_bg.png"
                : "src/main/resources/asset/bg/lose_bg.png");
    }
}
