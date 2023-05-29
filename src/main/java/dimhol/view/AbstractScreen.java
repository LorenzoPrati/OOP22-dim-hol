package dimhol.view;

import dimhol.core.Engine;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public abstract class AbstractScreen extends JPanel{
    protected final Engine engine;
    protected ImageIcon background;
    protected Font font;
    protected JPanel centerPanel; 
    protected JLabel title;
    protected GridBagConstraints gbc;
    public AbstractScreen(final Engine engine){
        this.engine = engine;
        this.centerPanel = new JPanel();
        this.font = new Font("Helvetica", Font.BOLD,20);
        this.gbc = new GridBagConstraints();
        setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridBagLayout());
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
