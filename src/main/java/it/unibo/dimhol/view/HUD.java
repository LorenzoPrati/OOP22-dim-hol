package it.unibo.dimhol.view;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class HUD {
    private int playerCurrentHealth;
    private int playerMaxHealth;
    private int coins;
    private final ResourceLoader loader;
    private final static double W = 0.7;
    private final static double H = 0.7;
    private double newTileWidth;
    private double newTileHeight;
    private int offsetX;
    private int offsetY;
    private Font font;

    public HUD(ResourceLoader loader) {
        this.loader = loader;
    }

    public void show(Graphics2D g2, double newTileWidth, double newTileHeight, int offsetX, int offsetY) {
        this.newTileWidth = newTileWidth;
        this.newTileHeight = newTileHeight;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        File font_file = new File("src/res/hud/monogram.ttf");
        try {
            font = Font.createFont(Font.ROMAN_BASELINE, font_file).deriveFont((float) (newTileHeight / 1.5));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        showHealthInfo(g2);
        showCoinInfo(g2);
    }

    private void showCoinInfo(Graphics2D g2) {
        var coinImg = loader.getImage(26).getSubimage(0,0,32, 32);
        int coinW = (int) (newTileWidth * W);
        int coinH = (int) (newTileHeight * W);

        g2.setColor(Color.YELLOW);
        g2.drawString("Coins: " + coins, coinW + offsetX, (float) (coinH * 2.3 + offsetY));
    }

    private void showHealthInfo(Graphics2D g2) {
        var heartImg = loader.getImage(35);
        g2.setColor(Color.RED);
        int heartW = (int) (newTileWidth * W);
        int heartH = (int) (newTileHeight * H);
        for (int i = 1; i < playerCurrentHealth + 1; i++) {
            g2.drawImage(heartImg, (i * heartW + offsetX), heartH + offsetY, heartW, heartH, null);
        }
        //g2.drawImage(heartImg, (100 + offsetX), 100 + offsetY, heartW, heartH, null);
        g2.setFont(font);
        g2.drawString("Heart: " + playerCurrentHealth + "/" + playerMaxHealth, heartW + offsetX, heartH + offsetY);
    }

    public void update(int currentHealth, int maxHealth, int currentAmount) {
        this.playerCurrentHealth = currentHealth;
        this.playerMaxHealth = maxHealth;
        this.coins = currentAmount;
    }

}
