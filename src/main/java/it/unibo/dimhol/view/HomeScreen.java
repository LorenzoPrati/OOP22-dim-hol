package it.unibo.dimhol.view;

import it.unibo.dimhol.Engine;
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
    private Logics logic; 
    
    public HomeScreen(final Engine engine) {
        this.engine = engine;
        this.logic = new LogicsImpl();
        this.bg = new ImageIcon("src/res/bg/mainMenu.jpg");
        final JButton startButton = new JButton("PLAY");
        final JButton exitButton = new JButton("QUIT");
        final JButton optionsButton = new JButton("OPTIONS");
        final JPanel centerPanel = new JPanel();
        final JPanel northPanel = new JPanel();
        List<JButton> buttons = new LinkedList<>();
        ImageIcon icon = new ImageIcon("src/res/bg/dimension holiday.png");
        ImageIcon ghostIcon = new ImageIcon("src/res/bg/ghost.gif");
        JLabel gameName = new JLabel(icon);
        Font f = new Font("Helvetica", Font.BOLD, 30);
        Color c = new Color(0, 102,0);
        GridBagConstraints gbc = new GridBagConstraints();

        buttons.add(startButton);
        buttons.add(optionsButton);
        buttons.add(exitButton);

        this.setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridBagLayout());
       
        startButton.addActionListener(e -> {
            logic.startGame(engine);
        });
        exitButton.addActionListener(e -> {
            logic.quitGame();
        });
        optionsButton.addActionListener(e ->{
            logic.setOptionPanel(engine);
        });

        for(var button: buttons){
            button.setFont(f);
            button.setBackground(c);
            button.setForeground(Color.WHITE);
        }
        
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        northPanel.add(gameName);
        northPanel.setBackground(Color.WHITE);
        this.add(northPanel,gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
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