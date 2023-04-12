package it.unibo.dimhol;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import java.awt.*;

/*A temporary Scene just to debug. */

public class Scene {
    private final  World world;
    private final  JFrame frame;
    private final Panel panel;
    private List<Triple<Integer,Integer,Integer>> renderQueue = new ArrayList<>();

    public Scene(final World world){
        this.world = world;
        this.frame = new JFrame();
        this.panel = new Panel();
        this.frame.getContentPane().add(panel);
        this.frame.setPreferredSize(new Dimension(900, 900));
        this.frame.pack();
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.addKeyListener(this.world.getInput());
    }

    class Panel extends JPanel{
        public Panel(){
            this.setBackground(Color.BLACK);
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
    }

    public void render(){
        this.frame.repaint();
    }

    public void queue(int t, double x, double y) {
        this.renderQueue.add(new ImmutableTriple<>(t,(int)x,(int)y));
    }
    
}
