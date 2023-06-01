package dimhol.view;

import dimhol.core.Engine;
import java.util.List;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

public class PauseScreen extends AbstractScreen {

    public PauseScreen(Engine engine, final Scene scene) {
        super(engine);
        this.background = new ImageIcon("src/main/resources/asset/bg/pauseScreen.jpg");
        Color color = new Color(102,0,153);
        this.add(createLabel(new ImageIcon("src/main/resources/asset/bg/pauseTitle.png")),gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(createButton(e -> {
            engine.getMainWindow().changePanel(scene.getPanel());
            scene.setupInput();
            engine.resumeGame();
        }, "RESUME", color), gbc);
        centerPanel.add(createButton(e -> {
            engine.getMainWindow().changePanel(new HomeScreen(engine));
            engine.endGame();
        }, "HOME", color), gbc);
        gbc.weighty = 1;
        this.add(centerPanel); 
    }
    
}
