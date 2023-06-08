package dimhol.view;

import dimhol.core.Engine;
import dimhol.view.screens.HomeScreen;

import javax.swing.*;
import java.awt.*;

public class MainWindowImpl implements MainWindow {
    private final JFrame frame;
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

    @Override
    public void changePanel(final JPanel panel) {
        this.frame.remove(frame.getContentPane().getComponents()[frame.getContentPane().getComponentCount()-1]);
        this.frame.add(panel);
        this.frame.repaint();
        this.frame.revalidate();
    }

    @Override
    public void changeResolution(final Dimension dimension) {
        this.frame.setSize(new Dimension(dimension));
    }
}
