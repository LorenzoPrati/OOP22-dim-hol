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
    private record EnemyData( int currentHealth, int maxHealth, Vector2D pos, BodyShape body){}
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

        try {
            File fontFile = new File("src/main/resources/asset/hud/font/Fipps-Regular.ttf");
            double fontSize = newTileHeight / 3;
            var font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Math.round(fontSize));
            g2d.setFont(font);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        showHealthInfo();
        showCoinInfo();
        showEnemiesHealth();
    }

    private void showHealthInfo() {
        var heartImg = resourceLoader.getImage(47);
        int heartW = Math.toIntExact(Math.round(newTileWidth * HUD_IMAGE_WIDTH));
        int heartH = Math.toIntExact(Math.round(newTileHeight * HUD_IMAGE_HEIGHT));

        g2d.setColor(Color.RED);
        g2d.drawString("Hearts : " + playerCurrentHealth + " / " + playerMaxHealth, heartW + offsetX, heartH + offsetY);
        for (int i = 1; i < playerCurrentHealth + 1; i++) {
            g2d.drawImage(heartImg, (i * heartW + offsetX), heartH + offsetY, heartW, heartH, null);
        }
    }

    private void showCoinInfo() {
        var coinImg = resourceLoader.getImage(46);
        int coinW = Math.toIntExact(Math.round(newTileWidth * HUD_IMAGE_WIDTH));
        int coinH = Math.toIntExact(Math.round(newTileHeight * HUD_IMAGE_HEIGHT));

        g2d.setColor(Color.YELLOW);
        g2d.drawString("Coins : " + coins, coinW + offsetX, coinH * 3 + offsetY);
        for (int i = 1; i < coins / 10 + 1; i++) {
            g2d.drawImage(coinImg, (i * coinW + offsetX), coinH * 3 + offsetY, coinW, coinH, null);
        }
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
                    Math.toIntExact(Math.round(e.pos().getX() * newTileWidth + offsetX)),
                    Math.toIntExact(Math.round(e.pos().getY() * newTileHeight + offsetY - rectH)),
                    Math.toIntExact(Math.round(e.body().getBoundingWidth() * newTileWidth)),
                    Math.toIntExact(Math.round(rectH)));
            g2d.fillRect(
                    Math.toIntExact(Math.round(e.pos().getX() * newTileWidth + offsetX)),
                    Math.toIntExact(Math.round(e.pos().getY() * newTileHeight + offsetY - rectH)),
                    Math.toIntExact(Math.round(e.body().getBoundingWidth() * newTileWidth / e.maxHealth() * e.currentHealth())),
                    Math.toIntExact(Math.round(rectH)));
        }
        enemiesData.clear();
    }
}
