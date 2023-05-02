package it.unibo.dimhol.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import it.unibo.dimhol.Engine;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class OptionScreen extends JPanel {
    private final Engine engine;
    private ImageIcon bg;

    public OptionScreen(Engine e) {
        this.engine = e;
        this.bg = new ImageIcon("src/res/bg/optionMenu.png");
        ImageIcon iconTitle = new ImageIcon("src/res/bg/options3.png");
        JButton homeButton = new JButton("RETURN HOME");
        JLabel labelTitle = new JLabel(iconTitle);
        String[] optionsToChoose = {"1280x720", "Full screen"};
        Font f = new Font("Helvetica", Font.BOLD, 17);
        Font f2 = new Font("Helvetica", Font.BOLD, 20);
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel centerPanel = new JPanel();
        JPanel optionListPanel = new JPanel();
        JComboBox<String> list = new JComboBox<>(optionsToChoose);
        JButton doneButton = new JButton("DONE");
        JLabel labelResolution = new JLabel("CHOOSE RESOLUTION: ");

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

        setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridBagLayout());
        optionListPanel.setLayout(new GridBagLayout());

        list.setFont(f);
        doneButton.setFont(f);
        labelResolution.setFont(f2);
        homeButton.setFont(f2);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        this.add(labelTitle, gbc);
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
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(bg.getImage(), 0 , 0, this.getWidth(), this.getHeight(), null);
    }
}
