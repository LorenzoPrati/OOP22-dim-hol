package dimhol.view.screens;

import dimhol.core.Engine;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 * The game tutorial screen, which shows commands and the rules of the game.
 */
public class TutorialScreen extends AbstractScreen {

    @Serial
    private static final long serialVersionUID = 2370387720425495860L;

    private static final String ASSET_FOLDER = "/asset/commands/";

    private static final Map<String, String> COMMANDS_MAP = new HashMap<>();
    private static final int PANEL_COLUMN_INDEX = 0;
    private static final int DESCRIPTION_COLUMN_INDEX = 1;
    private static final int LORE_LABEL_COLUMN_SPAN = 2;
    private static final int SPACE_PANEL_HEIGHT = 10;

    static {
        COMMANDS_MAP.put("Sword", "Click/Hold Left Mouse Button: Sword");
        COMMANDS_MAP.put("Bullet", "Click/Hold Right Mouse Button: Bullet");
        COMMANDS_MAP.put("Fireball", "Hold 'Z' for 3 seconds and then release: Fireball");
        COMMANDS_MAP.put("WASD", "Movement: 'W-A-S-D'");
        COMMANDS_MAP.put("Interaction", "Interaction: 'E'");
        COMMANDS_MAP.put("Lore", "You must defeat all the enemies, "
                + "then press 'E' on the Gate to pass to the next room");
        COMMANDS_MAP.put("Lore1", "Press 'ESC' to pause the game in any moment");
        COMMANDS_MAP.put("Lore2", "Press 'E' on the power-ups available in the shop "
                + "to buy them if you have collected enough coins");
        COMMANDS_MAP.put("Lore3", "To win the game, you must defeat the boss that spawn in the last room. "
                + "Let's start the journey!");
    }

    /**
     * Creates a new instance of the TutorialScreen.
     *
     * @param engine the game engine
     */
    public TutorialScreen(final Engine engine) {
        initializeComponents();
        loadScreen(engine);
        add(super.getCenterPanel());
    }

    /**
     * Initializes the components of the tutorial screen.
     */
    private void initializeComponents() {
        super.getCenterPanel().setLayout(new GridBagLayout());
        super.setBackground("/asset/bg/tutorialScreen.png");
        super.setGbcAnchorCenter();
        super.setGbcFillHorizontal();
    }

    /**
     * Loads the content of the tutorial screen.
     *
     * @param engine the game engine
     */
    private void loadScreen(final Engine engine) {
        createImagePanel();
        createLoreLabels();
        addStartButton(engine);
    }

    /**
     * Creates the image panel that displays the command images and descriptions.
     */
    private void createImagePanel() {
        final String[] imageNames = {"Sword", "Bullet", "Fireball", "WASD", "Interaction"};
        final JPanel imagePanel = new JPanel(new GridBagLayout());
        imagePanel.setBackground(Color.lightGray);

        int gridY = 0;

        for (final String imageName : imageNames) {
            imagePanel.add(super.createLabel(ASSET_FOLDER + imageName + ".png"), createGbc(PANEL_COLUMN_INDEX, gridY));
            final String description = COMMANDS_MAP.get(imageName);
            final JLabel descriptionLabel = createDescriptionLabel(description);
            imagePanel.add(descriptionLabel, createGbc(DESCRIPTION_COLUMN_INDEX, gridY));
            gridY++;
        }

        super.getCenterPanel().add(imagePanel, getGbc());
    }

    /**
     * Creates a description label with the given text.
     *
     * @param description the text of the description label
     * @return the created description label
     */
    private JLabel createDescriptionLabel(final String description) {
        final JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setOpaque(true);
        descriptionLabel.setBackground(Color.white);
        return descriptionLabel;
    }

    /**
     * Creates the lore labels that provide additional information about the game.
     */
    private void createLoreLabels() {
        final String[] loreList = {"Lore", "Lore1", "Lore2", "Lore3"};
        final JPanel imagePanel = (JPanel) super.getCenterPanel().getComponent(0);
        int gridY = imagePanel.getComponentCount();

        for (final String string : loreList) {
            final String description = COMMANDS_MAP.get(string);
            final JLabel descriptionLabel = createDescriptionLabel(description);
            imagePanel.add(descriptionLabel, createGbc(PANEL_COLUMN_INDEX, gridY, LORE_LABEL_COLUMN_SPAN));
            gridY++;
        }
    }

    /**
     * Adds the start button to the tutorial screen.
     *
     * @param engine the game engine
     */
    private void addStartButton(final Engine engine) {
        final JButton startButton = super.createButton(e -> engine.newGame(), "START MATCH", Color.GREEN);
        getGbc().gridy++;
        setGbcAnchorCenter();
        getCenterPanel().add(createVerticalSpacePanel(SPACE_PANEL_HEIGHT, Color.lightGray), getGbc());
        getGbc().gridy++;
        getCenterPanel().add(startButton, getGbc());
    }

    /**
     * Creates GridBagConstraints with the specified grid x and y positions.
     *
     * @param gridX the grid x position
     * @param gridY the grid y position
     * @return the created GridBagConstraints
     */
    private GridBagConstraints createGbc(final int gridX, final int gridY) {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(INSETS, INSETS, INSETS, INSETS);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        return gbc;
    }

    /**
     * Creates GridBagConstraints with the specified grid x and y positions and column span.
     *
     * @param gridx     the grid x position
     * @param gridy     the grid y position
     * @param gridwidth the column span
     * @return the created GridBagConstraints
     */
    private GridBagConstraints createGbc(final int gridx, final int gridy, final int gridwidth) {
        final GridBagConstraints gbc = createGbc(gridx, gridy);
        gbc.gridwidth = gridwidth;
        return gbc;
    }

    /**
     * Creates a JPanel that represents vertical space with the specified height and color.
     *
     * @param height the height of the space panel
     * @param color  the color of the space panel
     * @return the created space panel
     */
    private JPanel createVerticalSpacePanel(final int height, final Color color) {
        final JPanel spacePanel = new JPanel();
        spacePanel.setPreferredSize(new Dimension(1, height));
        spacePanel.setBackground(color);
        return spacePanel;
    }
}
