package it.unibo.dimhol.view;

import it.unibo.dimhol.Engine;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class HomeScreen extends JPanel {
    private final Engine engine;
    public HomeScreen(final Engine engine) {
        this.engine = engine;
        this.setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        final JButton startButton = new JButton("PLAY");
        final JButton exitButton = new JButton("EXIT");
        final JButton optionsButton = new JButton("OPTIONS");
        final JPanel centerPanel = new JPanel();
        final JPanel northPanel = new JPanel();
        ImageIcon icon = new ImageIcon("src/res/bg/dimension holiday.png");
        ImageIcon ghostIcon = new ImageIcon("src/res/bg/ghost.gif");
        JLabel gameName = new JLabel(icon);
        Font f = new Font("Helvetica", Font.BOLD, 30);
        Color c = new Color(0, 102,0);

        startButton.addActionListener(e -> {
            this.engine.newGame();
        });
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        /*optionsButton.addActionListener(e ->{
            this.engine.getWindow().changePanel(new OptionPanel(engine));
        });*/

        startButton.setFont(f);
        exitButton.setFont(f);
        optionsButton.setFont(f);
        startButton.setBackground(c);
        exitButton.setBackground(c);
        optionsButton.setBackground(c);
        startButton.setForeground(Color.WHITE);
        exitButton.setForeground(Color.WHITE);
        optionsButton.setForeground(Color.WHITE);
        
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        northPanel.add(gameName);
        northPanel.setBackground(Color.WHITE);
        this.add(northPanel,gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(new JLabel(ghostIcon),gbc);
        centerPanel.add(startButton, gbc);
        centerPanel.add(exitButton, gbc);
        centerPanel.add(optionsButton, gbc);

        gbc.weighty = 1;
        this.add(centerPanel);
        
    }
}