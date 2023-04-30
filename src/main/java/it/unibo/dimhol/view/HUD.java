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
            var hudH = entity.getH() / 4;
            var hudW = entity.getW();
            g2.fillRect((int) entity.getX() * 32, (int) entity.getY() * 32, 100, 100);
        }
    }

    public void update(int currentHealth, int maxHealth) {
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
    }

}
