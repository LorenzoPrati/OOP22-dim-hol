package dimhol.view;

import dimhol.core.Engine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResultScreen extends AbstractScreen {

    private final Engine engine;
    private final String message;
    Timer timer;
    int timeLeft;

    public ResultScreen(Engine engine, boolean result) {
        super(engine);
        this.engine = engine;
        var b = new JButton("return home");
        b.addActionListener(e -> this.engine.getMainWindow().changePanel(new HomeScreen(engine)));
        centerPanel.add(b);
        this.message = result ? "WIN" : "LOSE";
        try {
            File fontFile = new File("src/main/resources/asset/hud/font/Fipps-Regular.ttf");
            double fontSize = 50;
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Math.round(fontSize));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        this.background = new ImageIcon(result ? "src/main/resources/asset/bg/win_bg.png"
                : "src/main/resources/asset/bg/lose_bg.png");

        this.setLayout(new FlowLayout());
        b.setPreferredSize(new Dimension(900,100));
        //this.add(b);
        b.setFont(font);

        this.timeLeft = 5000; //5 seconds

        this. timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                timeLeft -= 1000;
                if (timeLeft <= 0) {
                    engine.getMainWindow().changePanel(new HomeScreen(engine));
                    timer.stop();
                }
            }
        });

        timer.start();

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        title = new JLabel(message);
        title.setFont(font);

        this.add(title, gbc);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.YELLOW);
        this.drawCenteredString(g, "returning home in ... " + timeLeft/1000 + " seconds",
                new Rectangle(this.getWidth(), this.getHeight()), font);
    }

    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    private BufferedImage loadWinImage() {
        try {
            return ImageIO.read(new File("src/main/resources/asset/bg/win_bg.png"));
        } catch (IOException e) {
            System.out.println("error");
        }
        return null;
    }

    private BufferedImage loadLoseImage() {
        try {
            return ImageIO.read(new File("src/main/resources/asset/bg/lose_bg.png"));
        } catch (IOException e) {
            System.out.println("error");
        }
        return null;
    }
}
