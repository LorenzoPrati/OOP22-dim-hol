package it.unibo.dimhol.view;

import java.awt.*;
import java.util.List;

public class HUD {
    private int currentHealth;
    private int maxHealth;
    private List<GraphicInfo> renderList;

    public HUD(List<GraphicInfo> renderList) {
        this.renderList = renderList;
    }

    public void show(Graphics2D g2) {
        g2.setColor(Color.RED);
        for (int i = 0; i < renderList.size(); i++) {
            var entity = renderList.get(i);
            //g2.fillRect(entity.getX() - entity.g, 50, 400 / maxHealth * currentHealth, 20);
        }
        g2.drawRect(50, 50, 400, 20);
        g2.drawString( currentHealth + "  /  " + maxHealth, 50, 85);

        //g2.drawString("coins:" + coinAmount, 10, 60);
    }

    public void update(int currentHealth, int maxHealth) {
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
    }

}
