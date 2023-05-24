package dimhol.view;

import javax.swing.JPanel;

/**
 * Models the scene where all the entities will be drawn. 
 */
public interface Scene {
    /**
     * A method wich adds to the list, a graphic component foreach entity.
     */
    void toList(GraphicInfo graphicInfo);

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
     * @param input
     */
    void setInput(final InputListener input);

    /**
     * @return the scene's panel.
     */
    JPanel getPanel();
    
}
