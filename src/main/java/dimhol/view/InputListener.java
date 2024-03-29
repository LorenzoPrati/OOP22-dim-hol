package dimhol.view;

import dimhol.core.Engine;
import dimhol.input.Input;
import dimhol.input.InputImpl;
import dimhol.view.screens.PauseScreen;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A class to listen to keyboard and mouse input.
 */
public final class InputListener implements KeyListener, MouseListener {

    private final Engine engine;
    private final Input input;
    private final PauseScreen pauseScreen;

    /**
     * Constructs an InputListener.
     *
     * @param engine the engine
     * @param scene the class handling the game scene
     */
    public InputListener(final Engine engine, final Scene scene) {
        this.engine = engine;
        this.input = new InputImpl();
        this.pauseScreen = new PauseScreen(engine, scene);
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> this.input.setUp(true);
            case KeyEvent.VK_S -> this.input.setDown(true);
            case KeyEvent.VK_A -> this.input.setLeft(true);
            case KeyEvent.VK_D -> this.input.setRight(true);
            case KeyEvent.VK_Z -> this.input.setChargingFireball(true);
            case KeyEvent.VK_E -> this.input.setInteracting(true);
            case KeyEvent.VK_ESCAPE -> {
                this.engine.pauseGame();
                this.engine.getMainWindow().changePanel(this.pauseScreen);
            }
            default -> {
            }
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> this.input.setUp(false);
            case KeyEvent.VK_S -> this.input.setDown(false);
            case KeyEvent.VK_A -> this.input.setLeft(false);
            case KeyEvent.VK_D -> this.input.setRight(false);
            case KeyEvent.VK_Z -> this.input.setChargingFireball(false);
            case KeyEvent.VK_E -> this.input.setInteracting(false);
            default -> {
            }
        }
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> this.input.setAttacking(true);
            case MouseEvent.BUTTON3 -> this.input.setShooting(true);
            default -> {
            }
        }
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> this.input.setAttacking(false);
            case MouseEvent.BUTTON3 -> this.input.setShooting(false);
            default -> {
            }
        }
    }

    /**
     * Gets the Input class associated with this InputListener.
     *
     * @return a copy of the input
     */
    public Input getInput() {
        return this.input.clone();
    }

    @Override
    public void mouseClicked(final MouseEvent e) {

    }

    @Override
    public void mouseEntered(final MouseEvent e) {

    }

    @Override
    public void mouseExited(final MouseEvent e) {

    }

    @Override
    public void keyTyped(final KeyEvent e) {

    }

}
