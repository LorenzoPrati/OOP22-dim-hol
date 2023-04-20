package it.unibo.dimhol.view;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import it.unibo.dimhol.Engine;
import it.unibo.dimhol.World;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import java.awt.*;

/*A temporary Scene just to debug. */

public class Scene extends JPanel {
    private World world;

    private List<Triple<Integer,Integer,Integer>> renderQueue = new ArrayList<>();

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
        for(var elem :renderQueue){
            var type = elem.getLeft();
            switch(type){
                case 0: { //PLAYER
                    g2.setColor(Color.GREEN);
                    g2.drawRect(elem.getMiddle(), elem.getRight(), 60, 60);
                    break;
                }
                case 1:{ //OBSTACLE
                    g2.setColor(Color.RED);
                    g2.drawRect(elem.getMiddle(), elem.getRight(), 60, 60);
                    break;
                }
                case 2:{ //HEART
                    g2.setColor(Color.YELLOW);
                    g2.drawRect(elem.getMiddle(), elem.getRight(), 60, 60);
                    break;
                }
                case 3:{
                    g2.setColor(Color.BLUE);
                    g2.drawRect(elem.getMiddle(), elem.getRight(), 10, 10);
                    break;
                }
                case 4:{
                    g2.setColor(Color.cyan);
                    g2.drawRect(elem.getMiddle(), elem.getRight(), 60, 60);
                    break;
                }

            }
        }
        renderQueue.clear();
    }

    public void queue(int t, double x, double y) {
        this.renderQueue.add(new ImmutableTriple<>(t,(int)x,(int)y));
    }
    
}
