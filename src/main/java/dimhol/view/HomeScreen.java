package dimhol.view;

import dimhol.core.Engine;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class HomeScreen extends AbstractScreen {

    public HomeScreen(Engine engine) {
        super(engine);
        super.setBackground("/asset/bg/mainMenu.png");
        Color color = new Color(102,0,153);
        this.add(super.createLabel("/asset/bg/HomeScreenTitle.png"),gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(super.createButton((e -> engine.newGame()), "PLAY", color), gbc);
        centerPanel.add(super.createButton((e -> engine.getMainWindow().changePanel(new OptionScreen(engine))), "OPTIONS",
            color), gbc);
        centerPanel.add(super.createButton(e -> {
            engine.setDebugMode(!engine.isDebug());
        }, "ENABLE/DISABLE DEBUG", Color.RED), gbc);
        centerPanel.add(super.createButton((e -> System.exit(0)), "QUIT", color), gbc);
        gbc.weighty = 1;
        this.add(centerPanel);
    }
}