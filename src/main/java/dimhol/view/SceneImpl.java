package dimhol.view;

import dimhol.core.Engine;
import dimhol.input.Input;
import dimhol.levels.map.TileMap;
import dimhol.levels.map.TileMapImpl;
import dimhol.view.hud.HUD;
import dimhol.view.hud.HUDImpl;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import java.awt.Point;

/**
 * Implements the scene where all the entities will be drawn.
 */
public class SceneImpl implements Scene {
    @Serial
    private static final long serialVersionUID = -5780101342803153304L;
    private final List<GraphicInfo> renderList = new ArrayList<>();
    private int tileMapWidth;
    private int tileMapHeight;
    private final ResourceLoader loader;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final HUD hud;
    private int newTileWidth;
    private int newTileHeight;
    private int offsetX;
    private int offsetY;
    private final GamePanel scenePanel;
    private TileMap tileMap;
    private final InputListener inputListener;

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
        @Serial
        private static final long serialVersionUID = -2493205788678484540L;
        GamePanel(final double width, final double height) {
            this.setPreferredSize(new Dimension((int) width, (int) height));
            this.setDoubleBuffered(true);
            this.setBackground(Color.BLACK);
            this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(), null));
        }

        @Override
        public void paintComponent(final Graphics g) {
            final var tileMapLayers = tileMap.getLayers();
            super.paintComponent(g);
            if (g instanceof Graphics2D) {
                final Graphics2D g2 = (Graphics2D) g;
                for (final var layer : tileMapLayers) {
                    for (int i = 0; i < tileMapWidth; i++) {
                        for (int j = 0; j < tileMapHeight; j++) {
                            final var id = layer[i][j].getTileSetId();
                            if (this.getWidth() / tileMapHeight < this.getHeight() / tileMapWidth) {
                                newTileWidth = this.getWidth() / tileMapHeight;
                            } else {
                                newTileWidth = this.getHeight() / tileMapWidth;
                            }
                            newTileHeight = newTileWidth;
                            offsetX = (this.getWidth() - tileMapHeight * newTileWidth) / 2;
                            offsetY = (this.getHeight() - tileMapWidth * newTileHeight) / 2;
                            final var drawX = newTileHeight * j + offsetX;
                            final var drawY = newTileWidth * i + offsetY;
                            g2.drawImage(loader.getTileImage(id), drawX, drawY, newTileWidth, newTileHeight, null);
                        }
                    }
                }
                for (final GraphicInfo graphicInfo : renderList) {
                    final var imageToCut = loader.getImage(graphicInfo.getNumImage());
                    final var img = createCompatibleImage(imageToCut.getSubimage(
                            graphicInfo.getIndex() * loader.getWidth(graphicInfo.getNumImage()),
                            0, loader.getWidth(graphicInfo.getNumImage()),
                            loader.getHeigth(graphicInfo.getNumImage())));
                    final double newWidth = newTileWidth * graphicInfo.getW();
                    final double newHeight = newTileHeight * graphicInfo.getH();
                    final double newX = graphicInfo.getX() * newTileWidth + offsetX;
                    final double newY = graphicInfo.getY() * newTileHeight + offsetY;
                    g2.drawImage(img, (int) newX, (int) newY, (int) newWidth, (int) newHeight, null);
                }
                hud.show(g2, newTileWidth, newTileHeight, offsetX, offsetY);
                g2.dispose();
                renderList.clear();
            }
        } 
    }

    private BufferedImage createCompatibleImage(final BufferedImage image) {
        final GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getDefaultScreenDevice().getDefaultConfiguration();
        if (image.getColorModel().equals(graphicsConfiguration.getColorModel())) {
            return image;
        } else {
            final BufferedImage newImage = graphicsConfiguration.createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());
            final Graphics2D g2d = newImage.createGraphics();
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
    @SuppressFBWarnings(value = { "EI_EXPOSE_REP", "EI_EXPOSE_REP2" },
            justification = "I prefer to suppress these spotBugs warnings")
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

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updateHUD(final int currentHealth, final int maxHealth, final int currentAmount) {
        this.hud.setHUDInfo(currentHealth, maxHealth, currentAmount);
    }
}
