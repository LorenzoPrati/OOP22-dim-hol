package dimhol.view;

import dimhol.core.Engine;
import javax.swing.*;
import java.awt.*;

public class MainWindowImpl implements MainWindow {
    private final JFrame frame;
    JPanel currPanel;
    public MainWindowImpl(final Engine engine) {
        this.frame = new JFrame();
        this.currPanel = new HomeScreen(engine);
        this.frame.getContentPane().add(currPanel);
        this.frame.setUndecorated(false);
        this.frame.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
        this.frame.pack();
        this.frame.setVisible(true);
        this.frame.setFocusable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void changePanel(final JPanel panel) {
        this.frame.remove(currPanel);
        this.frame.add(panel);
        this.currPanel = panel;
        this.frame.repaint();
        this.frame.revalidate();
    }

    @Override
    public void setDimension(final Dimension dimension) {
        this.frame.setSize(dimension);
    }
}
