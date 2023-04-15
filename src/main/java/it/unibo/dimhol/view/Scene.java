package it.unibo.dimhol.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import it.unibo.dimhol.Engine;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import java.awt.*;

/*A temporary Scene just to debug. */

public class Scene extends JPanel {

    private List<Triple<Integer,Integer,Integer>> renderQueue = new ArrayList<>();

    public Scene(){
        this.setBackground(Color.BLACK);
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
                    break;
                }
                case 1:{ //ENEMY
                    g2.setColor(Color.RED);
                    break;
                }
                case 2:{ //COIN
                    g2.setColor(Color.YELLOW);
                    break;
                }
            }
            g2.drawRect(elem.getMiddle(), elem.getRight(), 60, 60);
        }
        renderQueue.clear();
    }

    public void queue(int t, double x, double y) {
        this.renderQueue.add(new ImmutableTriple<>(t,(int)x,(int)y));
    }
    
}
