package view;

import javax.swing.JFrame;

/**
 * ViewImpl.
 */
public class ViewImpl implements View {

    private JFrame frame;
    private Scene currentScene = null;

    public ViewImpl() {
        this.frame = new JFrame();
        /*
         * TODO: complete the implementation of the frame. 
         */
        this.frame.getContentPane().add(currentScene.getPanel());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScene(Scene s) {
        this.frame.remove(currentScene);
        currentScene = s;
        this.frame.getContentPane().add(currentScene.getPanel());
        this.frame.revalidate();
        this.frame.repaint();
    }
    
}
