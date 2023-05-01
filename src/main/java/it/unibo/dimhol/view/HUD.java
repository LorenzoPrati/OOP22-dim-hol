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
        for (int i = 0; i < playerCurrentHealth; i++) {
            g2.drawImage(heartImg, (int) (i * 15 + newTileWidth), (int) newTileHeight, (int) newTileWidth * 2, (int) newTileHeight * 2, null);
        }
        g2.drawString("Heart: " + playerCurrentHealth + " / " + playerMaxHealth, (int) newTileWidth * 2, (int) (3 * newTileHeight));

    }

    public void update(int currentHealth, int maxHealth) {
        this.playerCurrentHealth = currentHealth;
        this.playerMaxHealth = maxHealth;
    }

}
