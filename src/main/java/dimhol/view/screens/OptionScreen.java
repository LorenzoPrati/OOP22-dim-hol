package dimhol.view.screens;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import dimhol.core.Engine;

/**
 * A class to create an options menu.
 */
public class OptionScreen extends AbstractScreen {
    private static final int FONT2_SIZE = 17;
    private static final int W1 = 800;
    private static final int H1 = 600;
    private static final int W2 = 800;
    private static final int H2 = 1280;
    private static final int W3 = 1920;
    private static final int H3 = 1080;
    private final Map<String, Dimension> mapResolutions =  new HashMap<>() {
        {
            put("800x600", new Dimension(W1, H1));
            put("800x1280", new Dimension(W2, H2));
            put("1920x1080 (recommended)", new Dimension(W3, H3));
        }
    };

    /**
     * Creates an options menu.
     * @param engine
     */
    public OptionScreen(final Engine engine) {
        super(engine);
        super.setBackground("/asset/bg/optionScreen.png");
        JLabel labelResolution = new JLabel("CHOOSE RESOLUTION: ");
        Font font2 = new Font("Helvetica", Font.BOLD, FONT2_SIZE);
        JPanel optionListPanel = new JPanel();
        JComboBox<String> comboBox = new JComboBox<>();
        for (var resolution: mapResolutions.keySet()) {
            comboBox.addItem(resolution);
        }
        optionListPanel.setLayout(new GridBagLayout());
        comboBox.setFont(font2);
        comboBox.setForeground(Color.BLACK);
        labelResolution.setFont(super.getFont());
        labelResolution.setForeground(Color.BLACK);
        this.add(super.createLabel("/asset/bg/options.png"), super.getGbc());
        optionListPanel.add(labelResolution, super.getGbc());
        optionListPanel.add(comboBox, super.getGbc());
        optionListPanel.add(super.createButton((e -> {
            engine.switchDebugMode();
            var button = (JButton) e.getSource();
            button.setText(engine.isDebug() ? "DISABLE DEBUG MODE" : "ENABLE DEBUG MODE");
            }), (engine.isDebug() ? "DISABLE DEBUG MODE" : "ENABLE DEBUG MODE"), Color.BLACK), super.getGbc());
        optionListPanel.add(super.createButton(l -> {
            var selecteResolution = comboBox.getItemAt(comboBox.getSelectedIndex());
            var res = mapResolutions.get(selecteResolution);
            engine.getMainWindow().changeResolution(new Dimension(res));
            }, "DONE", Color.BLACK), super.getGbc());
        super.setGbcAnchorCenter();
        super.setGbcFillHorizontal();
        super.getGbc().insets = new Insets(INSETS, INSETS, INSETS, INSETS);
        super.getCenterPanel().setLayout(new GridBagLayout());
        super.getCenterPanel().add(optionListPanel, super.getGbc());
        super.getCenterPanel().add(super.createButton(l -> engine.getMainWindow().changePanel(new HomeScreen(engine)), 
            "HOME", Color.BLACK), super.getGbc());
        super.getGbc().weighty = 1;
        this.add(super.getCenterPanel()); 
    } 
}

