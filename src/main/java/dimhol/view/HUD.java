package dimhol.view;

import java.awt.Graphics2D;

/**
 * This interface exposes methods to update and show the HUD.
 */
public interface HUD {

    /**
     * This method shows the HUD.
     * @param g2d is the Graphics2D
     * @param newTileWidth is the new tile width that changes when the frame changes resolution
     * @param newTileHeight is the new tile height that changes when the frame changes resolution
     * @param offsetX is the offset from the left side screen
     * @param offsetY if the offset from the top side screen
     */
    void show(Graphics2D g2d, double newTileWidth,
              double newTileHeight, int offsetX, int offsetY);

    /**
     * Update the player HUD info.
     * @param currentHealth is the current health
     * @param maxHealth is the max health
     * @param currentAmount is the player's coin amount
     */
    void updateHUD(int currentHealth, int maxHealth, int currentAmount);
}
