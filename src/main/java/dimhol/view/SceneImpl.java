package dimhol.view;

import dimhol.core.Engine;
import dimhol.input.Input;
import dimhol.gamelevels.map.TileMap;
import dimhol.gamelevels.map.TileMapImpl;
import dimhol.view.HUD.HUD;
import dimhol.view.HUD.HUDImpl;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import java.awt.Point;

/**
 * Implements the scene where all the entities will be drawn.
 */
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
    private final GamePanel scenePanel;
    private TileMap tileMap;
    private InputListener inputListener;

    /**
     * .
     * @param engine
     */
    public SceneImpl(final Engine engine) {
        this.scenePanel = new GamePanel(screenSize.getWidth(), screenSize.getHeight());
        this.loader = new ResourceLoader();
        this.hud = new HUDImpl(loader);
        this.inputListener = new InputListener(engine, this);
        engine.getMainWindow().changePanel(this.scenePanel);
    }

    /**
     * Creates a GamePanel.
     */
    class GamePanel extends JPanel {
        GamePanel(final double width, final double height) {
            this.setPreferredSize(new Dimension((int) width, (int) height));
            this.setDoubleBuffered(true);
            this.setBackground(Color.BLACK);
            this.setCursor(this.getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(), null));
        }

        @Override
        public void paintComponent(final Graphics g) {
            var tileMapLayers = tileMap.getLayers();
            super.paintComponent(g);
            @SuppressWarnings({"unchecked"})
            Graphics2D g2 = (Graphics2D) g;
            for (var layer: tileMapLayers) {
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
                    0, loader.getWidth(renderList.get(i).getNumImage()), 
                    loader.getHeigth(renderList.get(i).getNumImage())));
                double newWidth = newTileWidth * renderList.get(i).getW();
                double newHeight = newTileHeight * renderList.get(i).getH();
                double newX = renderList.get(i).getX() * newTileWidth + offsetX;
                double newY = renderList.get(i).getY() * newTileHeight + offsetY;
                g2.drawImage(img, (int) newX, (int) newY, (int) newWidth, (int) newHeight, null);
            }
            hud.show(g2, newTileWidth, newTileHeight, offsetX, offsetY);
            g2.dispose();
            renderList.clear();
        } 
    }

    private BufferedImage createCompatibleImage(final BufferedImage image) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        this.scenePanel.paintImmediately(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HUD getHUD() {
        return this.hud;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateList(final GraphicInfo graphicInfo) {
        this.renderList.add(graphicInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupInput() {
        this.scenePanel.setFocusable(true);
        this.scenePanel.requestFocus();
        this.scenePanel.addKeyListener(this.inputListener);
        this.scenePanel.addMouseListener(this.inputListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getPanel() {
        return this.scenePanel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMap(final TileMap tileMap) {
        this.tileMap = new TileMapImpl(tileMap.getLayers(), tileMap.getWidth(), 
            tileMap.getHeight(), tileMap.getTileWidth(), tileMap.getTileHeight());
        this.tileMapWidth = tileMap.getWidth();
        this.tileMapHeight = tileMap.getHeight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Input getInput() {
        return this.inputListener.getInput();
    }
}
