package dimhol.view;

import dimhol.gamelevels.map.TileMap;
import dimhol.input.Input;

import javax.swing.*;

/**
 * Models the scene where all the entities will be drawn. 
 */
public interface Scene {
    /**
     * A method wich adds to the list, a graphic component foreach entity.
     */
    void updateList(GraphicInfo graphicInfo);

    /**
     * 
     */
    void render();

    /**
     * 
     * @return
     */
    HUD getHUD();

    /**
     * 
     * 
     */
    void setupInput();

    /**
     * @return the scene's panel.
     */
    JPanel getPanel();

    /**
     * 
     * @param tileMap
     */
    void setMap(TileMap tileMap);

    Input getInput();
    
}
