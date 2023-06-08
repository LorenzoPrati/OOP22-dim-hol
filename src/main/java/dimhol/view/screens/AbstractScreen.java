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

/**
 * An abstract class to create similar  menus and screens.
 */
public abstract class AbstractScreen extends JPanel {
    protected static final int INSETS = 10;
    protected static final int FONT_SIZE = 20;
    protected final Engine engine;
    protected ImageIcon background;
    protected Font font;
    protected JPanel centerPanel; 
    protected JLabel title;
    protected GridBagConstraints gbc;

    /**
     * Creates an AbstractScreen.
     * @param engine
     */
    public AbstractScreen(final Engine engine) {
        this.engine = engine;
        this.centerPanel = new JPanel();
        this.font = new Font("Helvetica", Font.BOLD, FONT_SIZE);
        this.gbc = new GridBagConstraints();
        setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridBagLayout());
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
    }

    /**
     * {@inheritDoc}}
     */
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawImage(background.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }

    /**
     * Method used to set the background image of the screen.
     * @param path
     */
    public void setBackground(final String path) {
        try {
            this.background = new ImageIcon(ImageIO.read(AbstractScreen.class.getResourceAsStream(path)));
        } catch (IOException e) {
            System.out.print("Error loading background menu images");
        }
    }

    /**
     * A method to create a button.
     * @param actionListener action to perform after the button has been clicked.
     * @param title string to write on the button.
     * @param color color of the string written on the button.
     * @return a JButton.
     */
    public JButton createButton(final ActionListener actionListener, final String title, final Color color) {
        var button = new JButton(title);
        button.addActionListener(actionListener);
        button.setFont(this.font);
        button.setForeground(color);
        return button;
    }

    /**
     * A method to create a Jlabel using an image.
     * @param path of the image.
     * @return a JLabel.
     */
    public JLabel createLabel(final String path) {
        JLabel label = new JLabel();
        try {
            label = new JLabel(((new ImageIcon(ImageIO.read(AbstractScreen.class.getResourceAsStream(path))))));
        } catch (IOException e) {
            System.out.print("Error title menu images");
        }
        return label;
    }
}
