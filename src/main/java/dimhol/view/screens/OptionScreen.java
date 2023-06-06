package dimhol.view.screens;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import dimhol.core.Engine;

public class OptionScreen extends AbstractScreen {
    public static final int INSETS = 10;
    public static final int LABEL_ORIGINAL_W = 10;
    public static final int LABEL_ORIGINAL_H = 10;
    private final Map<String, Dimension> mapResolutions =  new HashMap<>() {
        {
            put("800x600", new Dimension(800, 600));
            put("800x1280", new Dimension(800, 1280));
            put("1280x720", new Dimension(1280, 720));
            put("1920x1080 (recommended)", new Dimension(1920, 1080));
        }
    };
    public OptionScreen(Engine engine) {
        super(engine);
        super.setBackground("/asset/bg/optionMenu.png");
        JLabel labelResolution = new JLabel("CHOOSE RESOLUTION: ");
        Font font2 = new Font("Helvetica", Font.BOLD, 17);
        JPanel optionListPanel = new JPanel();
        JComboBox<String> comboBox = new JComboBox<>();
        for (var resolution: mapResolutions.keySet()){
            comboBox.addItem(resolution);
        }
        optionListPanel.setLayout(new GridBagLayout());
        comboBox.setFont(font2);
        comboBox.setForeground(Color.BLACK);
        labelResolution.setFont(font);
        labelResolution.setForeground(Color.BLACK);
        this.add(super.createLabel("/asset/bg/options.png"),gbc);
        optionListPanel.add(labelResolution, gbc);
        optionListPanel.add(comboBox, gbc);
        optionListPanel.add(super.createButton(l -> {
            var selectedResolution = comboBox.getItemAt(comboBox.getSelectedIndex());
            var res = mapResolutions.get(selectedResolution);
            engine.getMainWindow().changeResolution(new Dimension(res));}, "DONE", Color.BLACK), gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(INSETS, INSETS, INSETS, INSETS);
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(optionListPanel, gbc);
        centerPanel.add(super.createButton(l -> engine.getMainWindow().changePanel(new HomeScreen(engine)),
            "HOME", Color.BLACK), gbc);
        gbc.weighty = 1;
        this.add(centerPanel); 
    } 

}
