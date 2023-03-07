package controller;

import view.UpperView;
import view.UpperViewImpl;

/**
 * Manager of game controller and various view
 */
public class UpperControllerImpl implements UpperController {

    UpperView view;
    Controller game;

    public UpperControllerImpl() {
        this.view = new UpperViewImpl(new HomeScreen());
    }

    public void notifyStartGame() {
        this.game = new ControllerImpl();
        this.view.setScreen(this.game.getView());
        this.game.mainLoop();
    }

    public void notifyPauseGame() {
        this.game.setRunning(false);
        this.view.setScreen(new PauseMenu());
    }

    public void notifyResumeGame() {
        this.view.setScreen(this.game.getView());
        this.game.setRunning(true);
    }

    public void notifyTutorialScreen() {
        this.view.setScreen(new TutorialScreen());
    }

    public void notifyFinishedGame(Boolean result) {
        if (result) {
            this.view.setScreen(new WinGameScreen());
        } else {
            this.view.setScreen(new LoseGameScreen());
        }
    }

    public void notifyHomeScreen() {
        this.view.setScreen(new HomeScreen());
    }

    public void notifyLoadScreen() {
        this.view.setScreen(new LoadScreen());
    }
}
