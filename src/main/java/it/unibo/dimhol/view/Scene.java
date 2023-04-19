package it.unibo.dimhol.view;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import it.unibo.dimhol.Engine;
import it.unibo.dimhol.World;
import java.awt.*;

/*A temporary Scene just to debug. */

public class Scene extends JPanel {
    private World world;
    private ResourceLoader loader = new ResourceLoader();
    private List<GraphicInfo> renderList = new ArrayList<>();

    public Scene(World world){
        this.world = world;
        this.setBackground(Color.BLACK);
        /*
        Debug
         */
        var b = new JButton("win");
        b.addActionListener(e -> { world.setGameOver(); world.setResult(true);});
        this.add(b);
        var b1 = new JButton("lose");
        b1.addActionListener(e -> {world.setGameOver(); world.setResult(false);});
        this.add(b1);
        /*
        hides mouse cursor
         */
        this.setCursor(this.getToolkit().createCustomCursor(
                new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ),
                new Point(),
                null));
    }

    public void setInput(final InputListener input) {
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(input);
        this.addMouseListener(input);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for(var elem :renderList){
           g2.drawImage(new Animation(elem.getIndex(), elem.getNumImage(), loader).getImageToDraw(),
                (int)elem.getX(), (int)elem.getY(), 60, 60, null);
        }
        renderList.clear();
    }

    public void toList(final int index, final int numImage, final double x,final double y) {
        this.renderList.add(new GraphicInfo(numImage,index,x,y));
    }
    
}
