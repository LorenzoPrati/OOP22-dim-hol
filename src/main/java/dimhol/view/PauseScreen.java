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
        this.title = new JLabel(new ImageIcon("src/main/resources/asset/bg/pauseTitle.png"));
        final JButton resumeButton = new JButton("RESUME");
        final JButton homeButton = new JButton("HOME");
        List<JButton> buttons = List.of(resumeButton, homeButton);
        resumeButton.addActionListener(e -> {
            engine.getMainWindow().changePanel(scene.getPanel());
            scene.setupInput();
            engine.resumeGame();

        });
        homeButton.addActionListener(e -> {
            engine.getMainWindow().changePanel(new HomeScreen(engine));
            engine.endGame();
        });
        for(var button: buttons){
            button.setFont(font);
            button.setForeground(new Color(102,0,153));
        }
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        this.add(title,gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(resumeButton, gbc);
        centerPanel.add(homeButton, gbc);
        gbc.weighty = 1;
        this.add(centerPanel); 
    }
    
}
