package dimhol.view;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import dimhol.map.*;
import java.awt.*;

public class SceneImpl implements Scene{
    //private JFrame frame;
    private List<GraphicInfo> renderList = new ArrayList<>();
    private MapLoad mapLoader = new MapLoaderImpl("src/main/resources/config/map/nice-map.xml");
    private ResourceLoader loader = new ResourceLoader(mapLoader.getTileWidth(), mapLoader.getTileHeight());
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private HUD hud = new HUD(loader);
    private int newTileWidth;
    private int newTileHeight;
    private int offsetX;
    private int offsetY;
    public GamePanel scenePanel;

    public SceneImpl(){
        this.scenePanel =  new GamePanel(screenSize.getWidth(), screenSize.getHeight());;
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
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            var layers = mapLoader.getMapTileLayers();
            for(var layer: layers){
            for (int i = 0; i < mapLoader.getWidth(); i++) {
                for (int j = 0; j < mapLoader.getHeight(); j++) {
                    var id = layer[i][j].getTileSetId();
                    if (this.getWidth() / mapLoader.getHeight() < this.getHeight() / mapLoader.getWidth()) {
                        newTileWidth = this.getWidth() / mapLoader.getHeight();
                    } else {
                        newTileWidth = this.getHeight() / mapLoader.getWidth();
                    }
                    newTileHeight = newTileWidth;
                    offsetX = ((this.getWidth() - mapLoader.getHeight() * newTileWidth) / 2);
                    offsetY = ((this.getHeight() - mapLoader.getWidth() * newTileHeight) / 2);
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
    public HUD getPlayerHUD() {
        return this.hud;
    }

    @Override
    public MapLoad getMapLoader() {
        return this.mapLoader;
    }

    @Override
    public void toList(int index, int numImage, double x, double y, double w, double h) {
        this.renderList.add(new GraphicInfo(index, numImage, x, y, w,h));
    }

    @Override
    public void setInput(InputListener input) {
        this.scenePanel.setFocusable(true);
        this.scenePanel.requestFocus();
        this.scenePanel.addKeyListener(input);
        this.scenePanel.addMouseListener(input);
    }

    @Override
    public JPanel getPanel() {
        return this.scenePanel;
    }
}