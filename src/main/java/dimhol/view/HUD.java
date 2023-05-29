package dimhol.view;

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

/**
 * This class draw the player HUD, show player's health and coins.
 */
public final class HUD {

    /**
     * Image heart code.
     */
    private static final int HEART_IMAGE_CODE = 45;
    /**
     * Image coin code.
     */
    private static final int COIN_IMAGE_CODE = 44;
    /**
     * HUD Image width.
     */
    private static final double HUD_IMAGE_WIDTH = 0.5;
    /**
     * HUD Image height.
     */
    private static final double HUD_IMAGE_HEIGHT = 0.5;
    /**
     * Font size divisor.
     */
    private static final int FONT_SIZE_DIVISOR = 3;
    /**
     * Coin amount divisor.
     */
    private static final  int COIN_AMOUNT_DIVISOR = 10;
    /**
     * Coin high-position multiplier.
     */
    public static final int COIN_HIGH_MUL = 3;
    private int playerCurrentHealth;
    private int playerMaxHealth;
    private int coins;
    private final ResourceLoader resourceLoader;

    /**
     * HUD constructor.
     * @param loader is the image resource loader.
     */
    public HUD(final ResourceLoader loader) {
        this.resourceLoader = loader;
    }

    /**
     * This method shows the HUD.
     * @param g2d is the Graphics2D
     * @param newTileWidth is the new tile width that changes when the frame changes resolution
     * @param newTileHeight is the new tile height that changes when the frame changes resolution
     * @param offsetX is the offset from the left side screen
     * @param offsetY if the offset from the top side screen
     */
    public void show(final Graphics2D g2d, final double newTileWidth,
                     final double newTileHeight, final int offsetX, final int offsetY) {
        try {
            File fontFile = new File("src/main/resources/asset/hud/font/Fipps-Regular.ttf");
            double fontSize = newTileHeight / FONT_SIZE_DIVISOR;
            var font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Math.round(fontSize));
            g2d.setFont(font);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        showHealthInfo(g2d, offsetX, offsetY, newTileWidth, newTileHeight);
        showCoinInfo(g2d, offsetX, offsetY, newTileWidth, newTileHeight);
    }

    private void showHealthInfo(final Graphics2D g2d, final int offsetX, final int offsetY,
                                final double newTileWidth, final double newTileHeight) {
        var heartImg = resourceLoader.getImage(HEART_IMAGE_CODE);
        int heartW = Math.toIntExact(Math.round(newTileWidth * HUD_IMAGE_WIDTH));
        int heartH = Math.toIntExact(Math.round(newTileHeight * HUD_IMAGE_HEIGHT));

        g2d.setColor(Color.RED);
        g2d.drawString("Hearts : " + playerCurrentHealth + " / " + playerMaxHealth, heartW + offsetX, heartH + offsetY);
        for (int i = 1; i < playerCurrentHealth + 1; i++) {
            g2d.drawImage(heartImg, (i * heartW + offsetX), heartH + offsetY, heartW, heartH, null);
        }
    }

    private void showCoinInfo(final Graphics2D g2d, final int offsetX, final int offsetY,
                              final double newTileWidth, final double newTileHeight) {
        var coinImg = resourceLoader.getImage(COIN_IMAGE_CODE);
        int coinW = Math.toIntExact(Math.round(newTileWidth * HUD_IMAGE_WIDTH));
        int coinH = Math.toIntExact(Math.round(newTileHeight * HUD_IMAGE_HEIGHT));

        g2d.setColor(Color.YELLOW);
        g2d.drawString("Coins : " + coins, coinW + offsetX, coinH * COIN_HIGH_MUL + offsetY);
        for (int i = 1; i < coins / COIN_AMOUNT_DIVISOR + 1; i++) {
            g2d.drawImage(coinImg, i * coinW + offsetX, coinH * COIN_HIGH_MUL + offsetY, coinW, coinH, null);
        }
    }

    /**
     * Update the player HUD info.
     * @param currentHealth is the current health
     * @param maxHealth is the max health
     * @param currentAmount is the player's coin amount
     */
    public void updateHUD(final int currentHealth, final int maxHealth, final int currentAmount) {
        this.playerCurrentHealth = currentHealth;
        this.playerMaxHealth = maxHealth;
        this.coins = currentAmount;
    }

}
