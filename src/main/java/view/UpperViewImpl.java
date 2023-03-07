package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.qos.logback.core.spi.ScanException;

public class UpperViewImpl implements UpperView{
    
    JFrame frame;
    Scene currentScene;
    public UpperViewImpl(Scene scene) {
        this.frame = new JFrame();
        currentScene = scene;
        // draw the frame
        this.frame.getContentPane().add(currentScene);
    }

    @Override
    public void setScreen(Scene s) {
        this.frame.remove(currentScene);
        this.frame.getContentPane().add(s);
        this.frame.revalidate();
        this.frame.repaint();
    }
}
