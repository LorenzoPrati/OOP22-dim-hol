package dimhol.view.hud;

import dimhol.view.ResourceLoader;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class draw the player HUD, show player's health and coins.
 */
public final class HUDImpl implements HUD {

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
     * Pixel art font.
     */
    public static final String FIPPS_REGULAR_FONT = "/asset/hud/font/Fipps-Regular.ttf";
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
    public HUDImpl(final ResourceLoader loader) {
        this.resourceLoader = loader;
    }

    @Override
    public void show(final Graphics2D g2d, final double newTileWidth,
                     final double newTileHeight, final int offsetX, final int offsetY) {

        setFont(g2d, newTileHeight);
        showHealthInfo(g2d, offsetX, offsetY, newTileWidth, newTileHeight);
        showCoinInfo(g2d, offsetX, offsetY, newTileWidth, newTileHeight);
    }

    private void setFont(final Graphics2D g2d, final double newTileHeight) {
        final InputStream fontFile = getClass().getResourceAsStream(FIPPS_REGULAR_FONT);
        final double fontSize = newTileHeight / FONT_SIZE_DIVISOR;
        final Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Math.round(fontSize));
        } catch (FontFormatException | IOException e) {
            throw (IllegalStateException) new IllegalStateException().initCause(e);
        }
        g2d.setFont(font);
    }

    private void showHealthInfo(final Graphics2D g2d, final int offsetX, final int offsetY,
                                final double newTileWidth, final double newTileHeight) {
        final var heartImg = resourceLoader.getImage(HEART_IMAGE_CODE);
        final int heartW = Math.toIntExact(Math.round(newTileWidth * HUD_IMAGE_WIDTH));
        final int heartH = Math.toIntExact(Math.round(newTileHeight * HUD_IMAGE_HEIGHT));

        g2d.setColor(Color.RED);
        g2d.drawString("Hearts : " + playerCurrentHealth + " / " + playerMaxHealth, heartW + offsetX, heartH + offsetY);
        for (int i = 1; i < playerCurrentHealth + 1; i++) {
            g2d.drawImage(heartImg, i * heartW + offsetX, heartH + offsetY, heartW, heartH, null);
        }
    }

    private void showCoinInfo(final Graphics2D g2d, final int offsetX, final int offsetY,
                              final double newTileWidth, final double newTileHeight) {
        final var coinImg = resourceLoader.getImage(COIN_IMAGE_CODE);
        final int coinW = Math.toIntExact(Math.round(newTileWidth * HUD_IMAGE_WIDTH));
        final int coinH = Math.toIntExact(Math.round(newTileHeight * HUD_IMAGE_HEIGHT));

        g2d.setColor(Color.YELLOW);
        g2d.drawString("Coins : " + coins, coinW + offsetX, coinH * COIN_HIGH_MUL + offsetY);
        for (int i = 1; i < coins / COIN_AMOUNT_DIVISOR + 1; i++) {
            g2d.drawImage(coinImg, i * coinW + offsetX, coinH * COIN_HIGH_MUL + offsetY, coinW, coinH, null);
        }
    }

    @Override
    public void setHUDInfo(final int currentHealth, final int maxHealth, final int currentAmount) {
        this.playerCurrentHealth = currentHealth;
        this.playerMaxHealth = maxHealth;
        this.coins = currentAmount;
    }

}
