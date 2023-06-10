package dimhol.view;

import dimhol.levels.map.TileMap;
import dimhol.input.Input;

import javax.swing.JPanel;

/**
 * Models the scene where all the entities will be drawn. 
 */
public interface Scene {
   /**
    * A method wich adds to the list, a graphic component foreach entity.
    * @param graphicInfo
    */
    void updateList(GraphicInfo graphicInfo);

    /**
     * A method to render the game.
     */
    void render();

    /**
     * A method to setup the input.
     */
    void setupInput();

    /**
     * @return the scene's panel.
     */
    JPanel getPanel();

    /**
     * A method to set the map using the TileMap passed as argument.
     * @param tileMap
     */
    void setMap(TileMap tileMap);

    /**
     * @return the input.
     */
    Input getInput();

    /**
     * Update the HUD.
     * @param currentHealth player's current health
     * @param maxHealth player's max health
     * @param currentAmount player's coins
     */
    void updateHUD(int currentHealth, int maxHealth, int currentAmount);
}
