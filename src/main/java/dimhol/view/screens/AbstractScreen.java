package dimhol.view.screens;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
import java.io.Serial;

/**
 * An abstract class to create similar menus and screens.
 */
public abstract class AbstractScreen extends JPanel {
    @Serial
    private static final long serialVersionUID = -8156943716103350031L;
    public static final int INSETS = 10;
    public static final int FONT_SIZE = 20;
    private ImageIcon background;
    private final Font font;
    private final JPanel centerPanel; 
    private final GridBagConstraints gbc;

    /**
     * Creates an AbstractScreen.
     */
    public AbstractScreen() {
        this.centerPanel = new JPanel();
        this.font = new Font("Helvetica", Font.BOLD, FONT_SIZE);
        this.gbc = new GridBagConstraints();
        setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridBagLayout());
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
    }

    /**
     * @return the main font.
     */
    @Override
    public Font getFont() {
        return this.font;
    }

    /**
     * @return the centerPanel.
     */
    @SuppressFBWarnings("EI_EXPOSED_REP")
    public JPanel getCenterPanel() {
        return this.centerPanel;
    }

    /**
     * @return the gbc field.
     */
    public GridBagConstraints getGbc() {
        return gbc;
    }

    /**
     * Method to set the gbc constants.
     */
    public void setGbcAnchorCenter() {
        this.gbc.anchor = GridBagConstraints.CENTER;
    }

    /**
     * Method to set the gbc constants.
     */
    public void setGbcFillHorizontal() {
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
            throw new IllegalStateException();
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
        final var button = new JButton(title);
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
            label = new JLabel(new ImageIcon(ImageIO.read(AbstractScreen.class.getResourceAsStream(path))));
        } catch (IOException e) {
            throw new IllegalStateException();
        }
        return label;
    }
}
