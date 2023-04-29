package it.unibo.dimhol.view;

import it.unibo.dimhol.Engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A class to register keyboard and mouse input.
 */
public class InputListener implements KeyListener, MouseListener {

    private final Engine engine;
    private boolean up, down, left, right;
    private boolean normalAttack;
    private boolean shooting;
    private boolean specialAttack;

    public InputListener(Engine engine) {
        this.engine = engine;
    }

    public boolean isMoving() {
        return up || down || left || right;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isNormalAttack() {
        return normalAttack;
    }

    public boolean isShooting() {
        return shooting;
    }

    public boolean isSpecialAttack() {
        return specialAttack;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> this.up = true;
            case KeyEvent.VK_S -> this.down = true;
            case KeyEvent.VK_A -> this.left = true;
            case KeyEvent.VK_D -> this.right = true;
            case KeyEvent.VK_Q -> this.specialAttack = true;
            case KeyEvent.VK_ESCAPE -> this.engine.stopGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> this.up = false;
            case KeyEvent.VK_S -> this.down = false;
            case KeyEvent.VK_A -> this.left = false;
            case KeyEvent.VK_D -> this.right = false;
            case KeyEvent.VK_Q -> this.specialAttack = true;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> this.normalAttack = true;
            case MouseEvent.BUTTON3 -> this.shooting = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> this.normalAttack = false;
            case MouseEvent.BUTTON3 -> this.shooting = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
