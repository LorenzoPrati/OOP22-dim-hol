package dimhol.view;

import javax.swing.JPanel;
import dimhol.gamelevels.map.TileMap;
import dimhol.input.Input;
import dimhol.view.HUD.HUD;

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
     * @return the HUD.
     */
    HUD getHUD();

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
}
