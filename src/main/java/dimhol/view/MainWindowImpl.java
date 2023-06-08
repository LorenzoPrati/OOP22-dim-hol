package dimhol.view;

import dimhol.core.Engine;
import dimhol.view.screens.HomeScreen;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A class which manages the panels.
 */
public class MainWindowImpl implements MainWindow {
    private final JFrame frame;

    /**
     * Creates a MainWindowImpl.
     * @param engine
     */
    public MainWindowImpl(final Engine engine) {
        this.frame = new JFrame();
        this.frame.getContentPane().add(new HomeScreen(engine));
        this.frame.setUndecorated(false);
        this.frame.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 
            (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
        this.frame.pack();
        this.frame.setResizable(false);
        this.frame.setVisible(true);
        this.frame.setFocusable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changePanel(final JPanel panel) {
        this.frame.remove(frame.getContentPane().getComponents()[frame.getContentPane().getComponentCount() - 1]);
        this.frame.add(panel);
        this.frame.repaint();
        this.frame.revalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeResolution(final Dimension dimension) {
        this.frame.setSize(new Dimension(dimension));
    }
}
