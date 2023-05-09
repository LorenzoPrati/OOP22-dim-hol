package dimhol.view;

import dimhol.core.Engine;

import java.util.LinkedList;
import java.util.List;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PauseExampleScreen extends JPanel {
    private final Engine engine;
    private ImageIcon bg;
    public PauseExampleScreen(final Engine engine) {
        this.engine = engine;
        this.bg = new ImageIcon("src/main/resources/asset/bg/pauseScreen.jpg");
        final JButton resumeButton = new JButton("RESUME");
        final JButton homeButton = new JButton("HOME");
        final JPanel centerPanel = new JPanel();
        List<JButton> buttons = new LinkedList<>();
        ImageIcon icon = new ImageIcon("src/main/resources/asset/bg/pauseTitle.png");
        JLabel title = new JLabel(icon);
        Font f = new Font("Helvetica", Font.BOLD, 20);
        GridBagConstraints gbc = new GridBagConstraints();

        buttons.add(resumeButton);
        buttons.add(homeButton);

        setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridBagLayout());

        resumeButton.addActionListener(e -> {
            engine.resumeGame();
        });
        
        homeButton.addActionListener(e -> {
            engine.getWindow().changePanel(new HomeScreen(engine));
            engine.endGame();
        });

        for(var button: buttons){
            button.setFont(f);
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
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(bg.getImage(), 0 , 0, this.getWidth(), this.getHeight(), null);
    }
}
