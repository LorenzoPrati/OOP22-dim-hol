package dimhol.view;

import dimhol.core.Engine;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.*;

public class HomeScreen extends AbstractScreen {

    public HomeScreen(Engine engine) {
        super(engine);
        this.background = new ImageIcon("src/main/resources/asset/bg/mainMenu.png");
        this.title = new JLabel(new ImageIcon("src/main/resources/asset/bg/dimension holiday.png"));
        final JButton startButton = new JButton("PLAY");
        final JButton exitButton = new JButton("QUIT");
        final JButton optionsButton = new JButton("OPTIONS");
        List<JButton> buttons = List.of(startButton,optionsButton,exitButton);
        startButton.addActionListener(e -> {
            engine.newGame();
        });
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        optionsButton.addActionListener(e ->{
            engine.getMainWindow().changePanel(new OptionScreen(engine));
        });
        for(var button: buttons){
            button.setFont(this.font);
            button.setForeground(new Color(102,0,153));
        }
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        this.add(this.title,gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(startButton, gbc);
        centerPanel.add(optionsButton, gbc);
        centerPanel.add(exitButton, gbc);
        gbc.weighty = 1;
        this.add(centerPanel);
    }
    
}