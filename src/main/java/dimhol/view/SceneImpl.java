package dimhol.view;

import dimhol.core.Engine;
import dimhol.input.Input;
import dimhol.gamelevels.LevelManager;
import dimhol.gamelevels.map.TileMap;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SceneImpl implements Scene {
    private List<GraphicInfo> renderList = new ArrayList<>();
    private int tileMapWidth;
    private int tileMapHeight;
    private ResourceLoader loader;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private HUD hud;
    private int newTileWidth;
    private int newTileHeight;
    private int offsetX;
    private int offsetY;
    private LevelManager levelManager;
    public final GamePanel scenePanel;
    private TileMap tileMap;
    private InputListener inputListener;

    public SceneImpl(Engine engine){
        this.scenePanel =  new GamePanel(screenSize.getWidth(), screenSize.getHeight());;
        this.loader = new ResourceLoader();
        this.hud = new HUD(loader);
        this.inputListener = new InputListener(engine, this);
        engine.getMainWindow().changePanel(this.scenePanel);
    }

    class GamePanel extends JPanel{
        public GamePanel(final double width, final double height){
            this.setPreferredSize(new Dimension((int)width,(int)height));
            this.setDoubleBuffered(true);
            this.setBackground(Color.BLACK);
            this.setCursor(this.getToolkit().createCustomCursor(
                new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ),
                new Point(),
                null));
        }

        @Override
        public void paintComponent(Graphics g) {
            var tileMapLayers = tileMap.getLayers();
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            for(var layer: tileMapLayers){
                for (int i = 0; i < tileMapWidth; i++) {
                    for (int j = 0; j < tileMapHeight; j++) {
                        var id = layer[i][j].getTileSetId();
                        if (this.getWidth() / tileMapHeight < this.getHeight() / tileMapWidth) {
                            newTileWidth = this.getWidth() / tileMapHeight;
                        } else {
                            newTileWidth = this.getHeight() / tileMapWidth;
                        }
                        newTileHeight = newTileWidth;
                        offsetX = ((this.getWidth() - tileMapHeight * newTileWidth) / 2);
                        offsetY = ((this.getHeight() - tileMapWidth * newTileHeight) / 2);
                        var drawX = newTileHeight * j + offsetX;
                        var drawY = newTileWidth * i + offsetY;
                        g2.drawImage(loader.getTileImage(id), drawX, drawY, newTileWidth, newTileHeight, null);
                    }
                }
            }
            
            for (int i = 0; i < renderList.size(); i++) {
                var imageToCut = loader.getImage(renderList.get(i).getNumImage());
                var img = createCompatibleImage(imageToCut.getSubimage(
                    renderList.get(i).getIndex() * loader.getWidth(renderList.get(i).getNumImage()),
                    0,loader.getWidth(renderList.get(i).getNumImage()),
                    loader.getHeigth(renderList.get(i).getNumImage())));
                double newWidth = newTileWidth * renderList.get(i).getW();
                double newHeight = newTileHeight * renderList.get(i).getH();
                double newX = renderList.get(i).getX() * newTileWidth + offsetX;
                double newY = renderList.get(i).getY() * newTileHeight + offsetY;
                g2.drawImage(img,(int) newX, (int) newY, (int) newWidth, (int) newHeight, null);
            }
            hud.show(g2, newTileWidth, newTileHeight, offsetX, offsetY);
            g2.dispose();
            renderList.clear();
        } 
    }

    private BufferedImage createCompatibleImage(BufferedImage image) {
        GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getDefaultScreenDevice().getDefaultConfiguration();
        if (image.getColorModel().equals(graphicsConfiguration.getColorModel())) {
            return image;
        } else {
            BufferedImage newImage = graphicsConfiguration.createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());
            Graphics2D g2d = newImage.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();
            return newImage;
        }
    }

    @Override
    public void render() {
        this.scenePanel.paintImmediately(0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());
    }

    @Override
    public HUD getHUD() {
        return this.hud;
    }

    @Override
    public void updateList(final GraphicInfo graphicInfo) {
        this.renderList.add(graphicInfo);
    }

    @Override
    public void setupInput() {
        this.scenePanel.setFocusable(true);
        this.scenePanel.requestFocus();
        this.scenePanel.addKeyListener(this.inputListener);
        this.scenePanel.addMouseListener(this.inputListener);
    }

    @Override
    public JPanel getPanel() {
        return this.scenePanel;
    }

    @Override
    public void setMap(TileMap tileMap) {
        this.tileMap = tileMap;
        this.tileMapWidth = tileMap.getWidth();
        this.tileMapHeight = tileMap.getHeight();
    }

    @Override
    public Input getInput() {
        return this.inputListener.getInput();
    }
}