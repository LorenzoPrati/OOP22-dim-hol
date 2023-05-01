package it.unibo.dimhol.view;

import it.unibo.dimhol.Engine;

public class LogicsImpl implements Logics{

    @Override
    public void startGame(Engine e) {
        e.newGame();
    }

    @Override
    public void quitGame() {
        System.exit(0);
    }

    @Override
    public void setOptionPanel(Engine e) {
       e.getWindow().changePanel(new OptionScreen(e));
    } 
}
