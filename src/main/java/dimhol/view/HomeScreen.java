package dimhol.view;

import dimhol.core.Engine;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JCheckBox;

public class HomeScreen extends AbstractScreen {
    public static final int INSETS = 10;

    public HomeScreen(Engine engine) {
        super(engine);
        super.setBackground("/asset/bg/mainMenu.png");
        Color color = new Color(102,0,153);
        var debugButton = new JCheckBox("DEBUG MODE ON", false);
        debugButton.setFont(this.font);
        debugButton.setForeground(color);
        this.add(super.createLabel("/asset/bg/HomeScreenTitle.png"),gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(super.createButton((e -> engine.newGame()), "PLAY", color), gbc);
        centerPanel.add(super.createButton((e -> engine.getMainWindow().changePanel(new OptionScreen(engine))), "OPTIONS",
            color), gbc);
        centerPanel.add(super.createButton((e -> System.exit(0)), "QUIT", color), gbc);
        gbc.insets = new Insets(INSETS, INSETS, INSETS, INSETS);
        centerPanel.add(debugButton, gbc);
        centerPanel.add(super.createButton((e -> {
            if(debugButton.isSelected()){
                engine.setDebugMode(true);
            }
            else{
                engine.setDebugMode(false);
            }
            }), "CONFIRM CHOICE", color), gbc);
        gbc.weighty = 1;
        this.add(centerPanel);
    }
}