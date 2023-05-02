package it.unibo.dimhol.view;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import it.unibo.dimhol.map.MapLoaderImpl;
import it.unibo.dimhol.map.*;
import it.unibo.dimhol.systems.GameSystem;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;

/*A temporary Scene just to debug. */

public class Scene extends JPanel {

    private List<Triple<Integer,Integer,Integer>> renderQueue = new ArrayList<>();
    private MapLoad mapLoader = new MapLoaderImpl("src/main/java/it/unibo/dimhol/map/mapResources/nice-map.xml");
    private ResourceLoader loader = new ResourceLoader(mapLoader.getTileWidth(), mapLoader.getTileHeight());
    private List<GraphicInfo> renderList = new ArrayList<>();
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int newTileWidth;
    private int newTileHeight;
    private int offsetX;
    private int offsetY;
    private HUD hud = new HUD(loader);

    public Scene(){

        this.setDoubleBuffered(true);
        this.setBackground(Color.BLACK);

        /*
        Debug
         */

        /*
        hides mouse cursor
         */
        this.setCursor(this.getToolkit().createCustomCursor(
                new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ),
                new Point(),
                null));
    }

    public void setInput(final InputListener input) {
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(input);
        this.addMouseListener(input);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //System.out.println(mapLoader.getWidth()); //20
        //System.out.println(mapLoader.getHeight()); //30
        var layers = mapLoader.getMapTileLayers();

        for (var layer : layers) {
            for (int i = 0; i < mapLoader.getWidth(); i++) {
                for (int j = 0; j < mapLoader.getHeight(); j++) {
                    var id = layer[i][j].getTileSetId();

                    if (this.getWidth() / mapLoader.getHeight() < this.getHeight() / mapLoader.getWidth()) {
                        newTileWidth = this.getWidth() / mapLoader.getHeight();
                        newTileHeight = newTileWidth;
                    } else {
                        newTileWidth = this.getHeight() / mapLoader.getWidth();
                        newTileHeight = newTileWidth;
                    }

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
                    0,
                    loader.getWidth(renderList.get(i).getNumImage()), loader.getHeigth(renderList.get(i).getNumImage())));

            double newWidth = newTileWidth * renderList.get(i).getW();
            double newHeight = newTileHeight * renderList.get(i).getH();
            double newX = renderList.get(i).getX() * newTileWidth + offsetX;
            double newY = renderList.get(i).getY() * newTileHeight + offsetY;

            g2.drawImage(img,
                    (int) newX, (int) newY, (int) newWidth, (int) newHeight, null);
            g2.drawRect((int) newX, (int) newY, (int) newWidth, (int) newHeight);
        }
        hud.show(g2, newTileWidth, newTileHeight, offsetX, offsetY);

        g2.dispose();
        renderList.clear();
    }

    public void toList (final int index, final int numImage, final double x, final double y, final double w,final double h){
        this.renderList.add(new GraphicInfo(index, numImage, x, y, w,h));
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

    public void render(){
        //this.repaint();
        this.paintImmediately(0,0,this.getWidth(),this.getHeight());
    }

    public HUD getPlayerHUD() {
        return this.hud;
    }
}
