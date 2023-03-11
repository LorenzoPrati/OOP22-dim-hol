package controller;

import controller.gamecore.Controller;
import controller.gamecore.ControllerImpl;
import view.View;
import view.ViewImpl;
import view.otherview.HomeScene;
import view.otherview.LoadScene;
import view.otherview.LoseScene;
import view.otherview.PauseScene;
import view.otherview.TutorialScene;
import view.otherview.WinScene;

/**
 * EngineImpl.
 */
public class EngineImpl implements Engine {

    private View view;
    private Controller game;

    public EngineImpl() {
        this.view = new ViewImpl();
        this.view.setScene(new HomeScene(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyStart() {
        this.game = new ControllerImpl();
        //this.view.setScene(this.game.getGameScene(this));
        this.game.mainLoop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyPause() {
        this.game.setRunning(false);
        this.view.setScene(new PauseScene(this));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyResume() {
        this.game.setRunning(true);
        //this.view.setScene(this.game.getGameScene(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyTutorial() {
        this.view.setScene(new TutorialScene(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyGameEnd(Boolean result) {
        this.view.setScene(result ? new WinScene(this) : new LoseScene(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyHome() {
        this.view.setScene(new HomeScene(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadScene() {
        this.view.setScene(new LoadScene(this));
    }
    
}
