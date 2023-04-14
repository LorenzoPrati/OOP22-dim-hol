package it.unibo.dimhol;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A class to register keyboard and mouse input.
 */
public class InputListener implements KeyListener, MouseListener {

    private boolean up, down, left, right;
    private boolean leftClick, rightClick;

    public boolean anyMoveKeyPressed() {
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

    public boolean isLeftClick() {
        return leftClick;
    }

    public boolean isRightClick() {
        return rightClick;
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
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> this.up = false;
            case KeyEvent.VK_S -> this.down = false;
            case KeyEvent.VK_A -> this.left = false;
            case KeyEvent.VK_D -> this.right = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> this.leftClick = true;
            case MouseEvent.BUTTON3 -> this.rightClick = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> this.leftClick = false;
            case MouseEvent.BUTTON3 -> this.rightClick = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
