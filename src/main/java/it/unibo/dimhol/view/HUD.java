package it.unibo.dimhol.view;

import java.awt.*;

public class HUD {
    private int playerCurrentHealth;
    private int playerMaxHealth;
    private final ResourceLoader loader;

    public HUD(ResourceLoader loader) {
        this.loader = loader;
    }

    public void show(Graphics2D g2, double newTileWidth, double newTileHeight) {
        var heartImg = loader.getImage(27);
        g2.setColor(Color.RED);
        g2.drawRect((int) newTileWidth, (int) newTileHeight, (int) (4 * newTileWidth), (int) newTileHeight / 2);
        g2.fillRect((int) newTileWidth, (int) newTileHeight, (int) ((4 * newTileWidth) / playerMaxHealth * playerCurrentHealth), (int) newTileHeight / 2);
        g2.drawString("Hearts: " + playerCurrentHealth + " / " + playerMaxHealth, (float) newTileWidth, (float) newTileHeight - 2);

    }

    public void update(int currentHealth, int maxHealth) {
        this.playerCurrentHealth = currentHealth;
        this.playerMaxHealth = maxHealth;
    }

}
