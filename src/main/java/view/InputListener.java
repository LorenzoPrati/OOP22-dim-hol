package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import controller.Controller;
import controller.Command;

import javax.swing.event.MouseInputListener;

public class InputListener implements KeyListener , MouseInputListener{
    private Controller controller;

    public InputListener(Controller c){
        this.controller = c;
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_W:
                this.controller.notifyPressed(new UpCommand());
                break;
            case KeyEvent.VK_S:
                this.controller.notifyPressed(new DownCommand());
                break;
            case KeyEvent.VK_A:
                this.controller.notifyPressed(new LeftCommand());
                break;
            case KeyEvent.VK_D:
                this.controller.notifyPressed(new RightCommand());
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        switch(me.getButton()){
            case MouseEvent.BUTTON1: 
                controller.notifyPressed(new LeftClickCommand);
                break;
            case MouseEvent.BUTTON3:
                controller.notifyPressed(new RightClickCommand);
                break;
            default: break;
        }  
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }
    
}
