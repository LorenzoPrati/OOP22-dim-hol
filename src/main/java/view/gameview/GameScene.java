package view;

import javax.swing.JPanel;

/**
 * The implementation of the main game scene.
 */
public class GameScene implements Scene {

    private GameScenePanel panel;
    
    public GameScene() {
        this.panel = new GameScenePanel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getPanel() {
        return this.panel;
    }

}
