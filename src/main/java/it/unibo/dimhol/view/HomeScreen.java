package it.unibo.dimhol.view;

import it.unibo.dimhol.core.Engine;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class HomeScreen extends JPanel {
    private final Engine engine;
    private ImageIcon bg;

    public HomeScreen(final Engine engine) {
        this.engine = engine;
        this.bg = new ImageIcon("src/res/bg/mainMenu3.png");
        final JButton startButton = new JButton("P L A Y");
        final JButton exitButton = new JButton("Q U I T");
        final JButton optionsButton = new JButton("O P T I O N S");
        final JPanel centerPanel = new JPanel();
        List<JButton> buttons = new LinkedList<>();
        ImageIcon icon = new ImageIcon("src/res/bg/dimension holiday2.png");ImageIcon ghostIcon = new ImageIcon("src/res/bg/ghost.gif");
        JLabel gameName = new JLabel(icon);
        Font f = new Font("Helvetica", Font.BOLD, 20);
        GridBagConstraints gbc = new GridBagConstraints();

        buttons.add(startButton);
        buttons.add(optionsButton);
        buttons.add(exitButton);

        
        setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridBagLayout());
       
        startButton.addActionListener(e -> {
            engine.newGame();
        });
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        optionsButton.addActionListener(e ->{
            engine.getWindow().changePanel(new OptionScreen(engine));
        });

        for(var button: buttons){
            button.setFont(f);
            button.setForeground(new Color(102,0,153));
        }
        
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

    
        this.add(gameName,gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(startButton, gbc);
        centerPanel.add(optionsButton, gbc);
        centerPanel.add(exitButton, gbc);

        gbc.weighty = 1;
        this.add(centerPanel); 
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(bg.getImage(), 0 , 0, this.getWidth(), this.getHeight(), null);
    }
}