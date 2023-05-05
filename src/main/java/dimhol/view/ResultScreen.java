package dimhol.view;

import dimhol.core.Engine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResultScreen extends JPanel {

    private Engine engine;
    private boolean result;

    public ResultScreen(Engine engine, boolean result) {
        this.engine = engine;
        this.result = result;
        var b = new JButton("return home");
        b.addActionListener(e -> this.engine.getWindow().changePanel(new HomeScreen(engine)));
        this.add(b);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.result ? loadWinImage() : loadLoseImage(), 0, 0, this.getWidth(),this.getHeight(), null);
    }

    private BufferedImage loadWinImage() {
        try {
            return ImageIO.read(new File("src/res/bg/win_bg.png"));
        } catch (IOException e) {
            System.out.println("error");
        }
        return null;
    }

    private BufferedImage loadLoseImage() {
        try {
            return ImageIO.read(new File("src/res/bg/lose_bg.png"));
        } catch (IOException e) {
            System.out.println("error");
        }
        return null;
    }
}
