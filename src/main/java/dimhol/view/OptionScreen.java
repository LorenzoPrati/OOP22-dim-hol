package dimhol.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import dimhol.core.Engine;

public class OptionScreen extends AbstractScreen {

    public OptionScreen(Engine engine) {
        super(engine);
        this.background = new ImageIcon("src/main/resources/asset/bg/optionMenu.png");
        this.title= new JLabel(new ImageIcon("src/main/resources/asset/bg/options.png"));
        JButton homeButton = new JButton("HOME");
        JButton doneButton = new JButton("DONE");
        JLabel labelResolution = new JLabel("CHOOSE RESOLUTION: ");
        String[] optionsToChoose = {"1280x720", "Full screen"};
        Font font2 = new Font("Helvetica", Font.BOLD, 17);
        JPanel optionListPanel = new JPanel();
        JComboBox<String> list = new JComboBox<>(optionsToChoose);
        homeButton.addActionListener(l ->{
            engine.getWindow().changePanel(new HomeScreen(engine));
        });
        doneButton.addActionListener(l ->{
            switch(list.getSelectedIndex()){
                case 0:{
                    //TO DO: set resolution to 1280x720
                }
                case 1:{
                    //TO DO: set full screen
                }
            }
        });
        optionListPanel.setLayout(new GridBagLayout());
        list.setFont(font2);
        doneButton.setFont(font);
        labelResolution.setFont(font);
        homeButton.setFont(font);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        this.add(title, gbc);
        optionListPanel.add(labelResolution, gbc);
        optionListPanel.add(list, gbc);
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
