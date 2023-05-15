package dimhol.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import dimhol.core.Engine;

public class OptionScreen extends AbstractScreen {
    private final Map<String, Pair<Integer, Integer>> mapResolutions =  new HashMap<>() {
        {
            put("800x600", new ImmutablePair<>(800, 600));
            put("1280x720", new ImmutablePair<>(1280, 720));
            put("1280x1024", new ImmutablePair<>(1280, 1024));
            put("1600x900", new ImmutablePair<>(1600, 900));
            put("1920x1080 (recommended)", new ImmutablePair<>(1920, 1080));
        }
    };
    public OptionScreen(Engine engine) {
        super(engine);
        this.background = new ImageIcon("src/main/resources/asset/bg/optionMenu.png");
        this.title= new JLabel(new ImageIcon("src/main/resources/asset/bg/options.png"));
        JButton homeButton = new JButton("HOME");
        JButton doneButton = new JButton("DONE");
        JLabel labelResolution = new JLabel("CHOOSE RESOLUTION: ");
        Font font2 = new Font("Helvetica", Font.BOLD, 17);
        JPanel optionListPanel = new JPanel();
        JComboBox<String> comboBox = new JComboBox<>();
        for(var resolution: mapResolutions.keySet()){
            comboBox.addItem(resolution);
        }
        homeButton.addActionListener(l -> engine.getWindow().changePanel(new HomeScreen(engine)));
        doneButton.addActionListener(l ->{
            var selecteResolution = comboBox.getItemAt(comboBox.getSelectedIndex());
            var res = mapResolutions.get(selecteResolution);
            engine.getWindow().setSize(res.getLeft(), res.getRight());
        });
        optionListPanel.setLayout(new GridBagLayout());
        comboBox.setFont(font2);
        doneButton.setFont(font);
        labelResolution.setFont(font);
        homeButton.setFont(font);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        this.add(title, gbc);
        optionListPanel.add(labelResolution, gbc);
        optionListPanel.add(comboBox, gbc);
        optionListPanel.add(doneButton, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(optionListPanel, gbc);
        centerPanel.add(homeButton,gbc);
        gbc.weighty = 1;
        this.add(centerPanel); 
    } 
}
