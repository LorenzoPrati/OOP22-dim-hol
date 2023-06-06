package dimhol.view;

import dimhol.core.Engine;
import dimhol.view.screens.AbstractScreen;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class TutorialScreen extends AbstractScreen {

    public TutorialScreen(Engine engine) {
        super(engine);
        this.background = new ImageIcon("src/main/resources/asset/bg/winScreen.png");
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(super.createButton(e -> engine.newGame(),"START MATCH", Color.GREEN), gbc);
        gbc.weighty = 1;
        this.add(centerPanel);
    }
}
