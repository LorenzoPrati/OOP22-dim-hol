package it.unibo.dimhol.view;

import java.awt.*;

public class HUD {
    private int playerCurrentHealth;
    private int playerMaxHealth;
    private final ResourceLoader loader;
    private int heartW;
    private int heartH;
    private final static double W = 0.7;
    private final static double H = 0.7;

    public HUD(ResourceLoader loader) {
        this.loader = loader;
    }

    public void show(Graphics2D g2, double newTileWidth, double newTileHeight, int offsetX, int offsetY) {
        var heartImg = loader.getImage(27);
        g2.setColor(Color.RED);
        heartW = (int) (newTileWidth * W);
        heartH = (int) (newTileHeight * H);
        for (int i = 1; i < playerCurrentHealth - 1; i++) {

            g2.drawImage(heartImg, (i * heartW + offsetX), heartH + offsetY, heartW, heartH, null);
            //g2.drawImage(heartImg, (i * heartW + offsetX), (int) newTileHeight + offsetY, heartW, heartH, null);
            //g2.drawRect((i * heartW + offsetX), (int) newTileHeight + offsetY, heartW, heartH);
        }
        g2.setFont(new Font(Font.MONOSPACED,  Font.BOLD, (int) newTileHeight / 2));
        g2.drawString("Heart: " + playerCurrentHealth + " / " + playerMaxHealth, heartW + offsetX, (float) ((heartH + offsetY) * 2.5));
    }

    public void update(int currentHealth, int maxHealth) {
        this.playerCurrentHealth = currentHealth;
        this.playerMaxHealth = maxHealth;
    }

}
