package dimhol.view.screens;

import dimhol.core.Engine;
import dimhol.view.Scene;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class PauseScreen extends AbstractScreen {

    public PauseScreen(Engine engine, final Scene scene) {
        super(engine);
        super.setBackground("/asset/bg/pauseScreen.jpg");
        Color color = new Color(102,0,153);
        this.add(super.createLabel("/asset/bg/pauseTitle.png"),gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(super.createButton(e -> {
            engine.getMainWindow().changePanel(scene.getPanel());
            scene.setupInput();
            engine.resumeGame();
        }, "RESUME", color), gbc);
        centerPanel.add(super.createButton(e -> {
            engine.getMainWindow().changePanel(new HomeScreen(engine));
            engine.endGame();
        }, "HOME", color), gbc);
        gbc.weighty = 1;
        this.add(centerPanel); 
    }
    
}
