package dimhol.view;

import dimhol.core.Engine;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import javax.imageio.ImageIO;

public class HomeScreen extends AbstractScreen {

    public HomeScreen(Engine engine) {
        super(engine);
        try{
            this.background = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/asset/bg/mainMenu.png")));
        } catch(Exception e){
            System.out.print("Error loading menu images");
        }
        
        Color color = new Color(102,0,153);
        this.add(createLabel(new ImageIcon("src/main/resources/asset/bg/dimension holiday.png")),gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(createButton((e -> engine.newGame()), "PLAY", color), gbc);
        centerPanel.add(createButton((e -> System.exit(0)), "QUIT", color), gbc);
        centerPanel.add(createButton((e -> engine.getMainWindow().changePanel(new OptionScreen(engine))), "OPTIONS",
            color), gbc);
        gbc.weighty = 1;
        this.add(centerPanel);
    }
    
}