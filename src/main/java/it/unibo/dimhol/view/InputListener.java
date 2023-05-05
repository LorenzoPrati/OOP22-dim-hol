package it.unibo.dimhol.view;

import it.unibo.dimhol.core.Engine;
import it.unibo.dimhol.core.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A class to register keyboard and mouse input.
 */
public class InputListener implements KeyListener, MouseListener {

    private final Engine engine;
    private final Input input;

    public InputListener(final Engine engine, final Input input) {
        this.engine = engine;
        this.input = input;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> this.input.setUp(true);
            case KeyEvent.VK_S -> this.input.setDown(true);
            case KeyEvent.VK_A -> this.input.setLeft(true);
            case KeyEvent.VK_D -> this.input.setRight(true);
            case KeyEvent.VK_Q -> this.input.setSpecialMeele(true);
            case KeyEvent.VK_Z -> this.input.setChargeFireball(true);
            case KeyEvent.VK_E -> this.input.setInteract(true);
            case KeyEvent.VK_ESCAPE -> this.engine.stopGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> this.input.setUp(false);
            case KeyEvent.VK_S -> this.input.setDown(false);
            case KeyEvent.VK_A -> this.input.setLeft(false);
            case KeyEvent.VK_D -> this.input.setRight(false);
            case KeyEvent.VK_Q -> this.input.setSpecialMeele(false);
            case KeyEvent.VK_Z -> this.input.setChargeFireball(false);
            case KeyEvent.VK_E -> this.input.setInteract(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> this.input.setNormalMeele(true);
            case MouseEvent.BUTTON3 -> this.input.setShoot(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> this.input.setNormalMeele(false);
            case MouseEvent.BUTTON3 -> this.input.setShoot(false);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
