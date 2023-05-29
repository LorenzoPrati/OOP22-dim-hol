package dimhol.view;

import java.util.List;
import javax.swing.JPanel;

import dimhol.core.Input;
import dimhol.gamelevels.map.TileMap;

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
