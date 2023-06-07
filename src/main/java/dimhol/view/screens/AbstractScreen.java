package dimhol.view.screens;

import dimhol.core.Engine;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.IOException;

public abstract class AbstractScreen extends JPanel{
    public static final int INSETS = 10;
    protected final Engine engine;
    protected ImageIcon background;
    protected Font font;
    protected JPanel centerPanel; 
    protected JLabel title;
    protected GridBagConstraints gbc;

    public AbstractScreen(final Engine engine) {
        this.engine = engine;
        this.centerPanel = new JPanel();
        this.font = new Font("Helvetica", Font.BOLD, 20);
        this.gbc = new GridBagConstraints();
        setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridBagLayout());
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }

    public void setBackground(String path){
        try{
            this.background = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream(path)));
        } catch(IOException e){
            System.out.print("Error loading background menu images");
        }
    }

    public JButton createButton(ActionListener actionListener, String title, Color color) {
        var button = new JButton(title);
        button.addActionListener(actionListener);
        button.setFont(this.font);
        button.setForeground(color);
        return button;
    }

    public JLabel createLabel (String path){
        JLabel label = new JLabel();
        try{
            label = new JLabel(((new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream(path))))));
        } catch(IOException e){
            System.out.print("Error title menu images");
        }
        return label;
    }
}
