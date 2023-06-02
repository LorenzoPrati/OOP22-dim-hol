package dimhol.view;

import dimhol.core.Engine;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PauseScreen extends AbstractScreen {

    public PauseScreen(Engine engine, final Scene scene) {
        super(engine);
        try{
            this.background = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/asset/bg/pauseScreen.jpg")));
        } catch(Exception e){
            System.out.print("Error loading menu images");
        }
        Color color = new Color(102,0,153);
        try{
            this.add(createLabel((new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/asset/bg/pauseTitle.png"))))), gbc);
        } catch(Exception e){
            System.out.print("Error loading menu images");
        }
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(createButton(e -> {
            engine.getMainWindow().changePanel(scene.getPanel());
            scene.setupInput();
            engine.resumeGame();
        }, "RESUME", color), gbc);
        centerPanel.add(createButton(e -> {
            engine.getMainWindow().changePanel(new HomeScreen(engine));
            engine.endGame();
        }, "HOME", color), gbc);
        gbc.weighty = 1;
        this.add(centerPanel); 
    }
    
}
