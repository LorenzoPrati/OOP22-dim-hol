package view.gameview;

import javax.swing.JPanel;

import view.Scene;

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
