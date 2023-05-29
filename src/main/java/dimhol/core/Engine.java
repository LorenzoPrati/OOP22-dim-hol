package dimhol.core;

import dimhol.view.MainWindow;

public interface Engine {

    void newGame();

    void stopGame();

    void resumeGame();

    void endGame();

    MainWindow getMainWindow();
}
