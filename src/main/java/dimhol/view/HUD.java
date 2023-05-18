package dimhol.view;

import dimhol.logic.collision.BodyShape;
import org.locationtech.jts.math.Vector2D;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HUD {

    private final static double HUD_IMAGE_WIDTH = 0.5;
    private final static double HUD_IMAGE_HEIGHT = 0.5;
    private int playerCurrentHealth;
    private int playerMaxHealth;
    private int coins;
    private final ResourceLoader resourceLoader;
    private double newTileWidth;
    private double newTileHeight;
    private int offsetX;
    private int offsetY;
    private Graphics2D g2d;
    private record EnemyData(int currentHealth, int maxHealth, Vector2D pos, BodyShape body){}
    private final List<EnemyData> enemiesData = new ArrayList<>();

    public HUD(final ResourceLoader loader) {
        this.resourceLoader = loader;
    }

    public void show(final Graphics2D g2d, final double newTileWidth, final double newTileHeight, final int offsetX, final int offsetY) {
        this.newTileWidth = newTileWidth;
        this.newTileHeight = newTileHeight;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.g2d = g2d;

        File font_file = new File("src/main/resources/asset/hud/font/Fipps-Regular.ttf");
        try {
            double fontSize = newTileHeight / 3;
            var font = Font.createFont(Font.TRUETYPE_FONT, font_file).deriveFont(Math.round(fontSize));
            g2d.setFont(font);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        showHealthInfo();
        showCoinInfo();
        showEnemiesHealth();
    }

    private void showHealthInfo() {
        var heartImg = resourceLoader.getImage(35);
        int heartW = (int) (newTileWidth * HUD_IMAGE_WIDTH);
        int heartH = (int) (newTileHeight * HUD_IMAGE_HEIGHT);

        g2d.setColor(Color.RED);
        for (int i = 1; i < playerCurrentHealth + 1; i++) {
            g2d.drawImage(heartImg, (i * heartW + offsetX), heartH + offsetY, heartW, heartH, null);
        }
        g2d.drawString("Hearts : " + playerCurrentHealth + " / " + playerMaxHealth, heartW + offsetX, heartH + offsetY);
    }

    private void showCoinInfo() {
        var coinImg = resourceLoader.getImage(46);
        int coinW = (int) (newTileWidth * HUD_IMAGE_WIDTH);
        int coinH = (int) (newTileHeight * HUD_IMAGE_HEIGHT);

        g2d.setColor(Color.YELLOW);
        for (int i = 1; i < coins / 10 + 1; i++) {
            g2d.drawImage(coinImg, (i * coinW + offsetX), coinH + 50 + offsetY, coinW, coinH, null);
        }
        g2d.drawString("Coins : " + coins, coinW + offsetX, (float) (coinH * 2.9 + offsetY));
    }

    public void updatePlayerHUD(int currentHealth, int maxHealth, int currentAmount) {
        this.playerCurrentHealth = currentHealth;
        this.playerMaxHealth = maxHealth;
        this.coins = currentAmount;
    }

    public void updateEnemiesHUD(int currentHealth, int maxHealth, final Vector2D pos, final BodyShape bodyShape) {
        this.enemiesData.add(new EnemyData(currentHealth, maxHealth, pos, bodyShape));
    }

    private void showEnemiesHealth() {
        g2d.setColor(Color.RED);
        var rectH = 0.1 * newTileHeight;
        for (var e : enemiesData) {
            g2d.drawRect(
                    (int) (e.pos().getX() * newTileWidth + offsetX),
                    (int) (e.pos().getY() * newTileHeight + offsetY - rectH),
                    (int) (e.body().getBoundingWidth() * newTileWidth),
                    (int) (rectH));
            g2d.fillRect(
                    (int) (e.pos().getX() * newTileWidth + offsetX),
                    (int) (e.pos().getY() * newTileHeight + offsetY - rectH),
                    (int) (e.body().getBoundingWidth() * newTileWidth / e.maxHealth() * e.currentHealth()),
                    (int) (rectH));
        }
        enemiesData.clear();
    }
}
