package dimhol.view.screens;

import dimhol.core.Engine;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TutorialScreen extends AbstractScreen {
    private static final String ASSET_FOLDER = "src/main/resources/asset/commands/";

    private static final Map<String, String> COMMANDS_MAP = new HashMap<>();

    static {
        COMMANDS_MAP.put("Sword", "Click/Hold Left Mouse Button: Sword");
        COMMANDS_MAP.put("Bullet", "Click/Hold Right Mouse Button: Bullet");
        COMMANDS_MAP.put("Fireball", "Hold 'Z' for 3 seconds and then release: Fireball");
        COMMANDS_MAP.put("WASD", "Movement: 'W-A-S-D'");
        COMMANDS_MAP.put("Interaction", "Interaction: 'E'");
        COMMANDS_MAP.put("Lore", "You must defeat all the enemies, then press 'E' on the Gate to pass to the next room");
        COMMANDS_MAP.put("Lore1", "Collect coins that can be used in the shop and pick up hearts to heal");
        COMMANDS_MAP.put("Lore2", "Press 'E' on the power-ups available in the shop to buy them if you have enough coins");
        COMMANDS_MAP.put("Lore3", "To win the game, you must defeat the boss that spawn in the last room. Let's start the journey!");
    }

    public TutorialScreen(Engine engine) {
        super(engine);
        initializeComponents();
        loadImages();
        add(centerPanel);
    }

    private void initializeComponents() {
        centerPanel.setLayout(new GridBagLayout());
        this.background = new ImageIcon("src/main/resources/asset/bg/Tunnel 12.png");
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    }

    private void loadImages() {
        String[] imageNames = {"Sword", "Bullet", "Fireball", "WASD", "Interaction", "Lore", "Lore1", "Lore2", "Lore3" };

        for (String imageName : imageNames) {
            ImageIcon imageIcon = new ImageIcon(ASSET_FOLDER + imageName + ".png");
            JLabel imageLabel = new JLabel(imageIcon);

            JPanel imagePanel = new JPanel(new GridBagLayout());
            imagePanel.setBackground(Color.lightGray); // Set the background color of the image panel
            imagePanel.add(imageLabel);

            String description = COMMANDS_MAP.get(imageName);
            JLabel descriptionLabel = new JLabel(description);
            descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
            descriptionLabel.setOpaque(true);
            descriptionLabel.setBackground(Color.white); // Set the background color of the description label

            gbc.gridy++;
            centerPanel.add(createVerticalSpacePanel(10, Color.lightGray), gbc); // Add colored vertical space
            gbc.gridy++;
            imagePanel.add(descriptionLabel, gbc);
            centerPanel.add(imagePanel, gbc);

            addVerticalSpace(10); // Add additional vertical space after each image and description
        }

        // Create the button
        JButton startButton = super.createButton(e -> engine.newGame(), "START MATCH", Color.GREEN);
        gbc.gridy++; // Increment the grid y position for the button
        gbc.anchor = GridBagConstraints.CENTER; // Set the anchor to center for the button
        centerPanel.add(createVerticalSpacePanel(10, Color.lightGray), gbc); // Add colored vertical space
        gbc.gridy++;
        centerPanel.add(startButton, gbc);
    }

    private JPanel createVerticalSpacePanel(int height, Color color) {
        JPanel spacePanel = new JPanel();
        spacePanel.setPreferredSize(new Dimension(1, height));
        spacePanel.setBackground(color);
        return spacePanel;
    }

    private void addVerticalSpace(int height) {
        gbc.gridy++;
        gbc.weighty = height;
        centerPanel.add(Box.createVerticalStrut(height), gbc);
        gbc.weighty = 0;
    }
}
