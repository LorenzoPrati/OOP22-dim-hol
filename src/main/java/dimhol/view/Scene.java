package dimhol.view;

import javax.swing.JPanel;
import dimhol.map.MapLoad;

/**
 * Models the scene where all the entities will be drawn. 
 */
public interface Scene {
    /**
     * A method wich adds to the list all the following information about each entity.
     * @param index the current sprite to be drawn.
     * @param numImage the number to get the image from the resource loader.
     * @param x the x of the entity's position.
     * @param y the y of the entity's position.
     * @param w the bodyComponent's width.
     * @param h the bodyComponent's height.
     */
    void toList(final int index, final int numImage, final double x, final double y,
        final double w,final double h);

    /**
     * 
     */
    void render();

    /**
     * 
     * @return
     */
    HUD getPlayerHUD();

    /**
     * @return the MapLoad.
     */
    MapLoad getMapLoader();

    /**
     * 
     * @param input
     */
    void setInput(final InputListener input);

    /**
     * @return the scene's panel.
     */
    JPanel getPanel();
    
}
