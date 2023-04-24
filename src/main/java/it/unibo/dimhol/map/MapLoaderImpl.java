package it.unibo.dimhol.map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The class is responsible for parsing an XML file and loading a map from it.
 */
public class MapLoaderImpl implements LoadMap {
    private Tile[][] map;
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;
    private List<Tile[][]> mapTileLayers;

    /**
     * Constructor for LoadMapImpl that loads a map from an XML file.
     * @param fileName The name of the XML file to load the map from.
     */
    public MapLoaderImpl(final String fileName) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(fileName));
            /**
             * Normalize the document by removing empty spaces and combining adjacent text nodes.
             */
            doc.getDocumentElement().normalize();
            /**
             * Get the root element of the document.
             */
            var rootElement = doc.getDocumentElement();
            tileWidth = Integer.parseInt(rootElement.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(rootElement.getAttribute("tileheight"));
            /**
             * Get the list of all layer.
             */
            NodeList dataNodeList = doc.getElementsByTagName("data");
            /**
             * Get the list of all the layer elements into the document.
             */
            NodeList layerNodeList = doc.getElementsByTagName("layer");
            /**
             * Get the first layer element by using "item(0)" and store it in a "node" variable "layerNode".
             */
            mapTileLayers = new ArrayList<>();
            for (int i = 0; i < layerNodeList.getLength(); i++) {
                mapTileLayers.add(getTileMatrix(layerNodeList.item(i)));
            }
            Node layerNode1 = layerNodeList.item(1);
            /**
             * Get the first child of the data element, which contains the matrix data as string.
             */
            Node stringMatrix = dataNodeList.item(0).getFirstChild(); //layer 0
            /**
             * Print the name of the root element.
             */
            System.out.println("Root element:" + rootElement.getNodeName());
            System.out.println("Map parsed:");
            System.out.println(stringMatrix.getTextContent());
            System.out.println("Print Successful!");
            this.map = new Tile[height][width];
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
    private Tile[][] getTileMatrix(Node item) {
        String[] line = item.getTextContent().split("[\n|,]");
        List<String> nline = Arrays.stream(line).filter(e -> !e.equals("")).toList();
        Tile[][] matrix = new Tile[width][height];

        int k = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (Integer.parseInt((nline.get(k))) == 1) {
                    matrix[i][j] = new Tile(false);
                } else matrix[i][j] = new Tile(true);
            }
        }
        return matrix;
    }

//    /**
//     * Test it:
//     * @param args
//     */
//    public static void main(final String[] args) {
//        LoadMapImpl parser = new LoadMapImpl("src/main/java/it/unibo/dimhol/map/mapResources/FirstTryTiled.xml");
//        Tile[][] map = parser.getMap();
//        int width = parser.getWidth();
//        int height = parser.getHeight();
//        int tileWidth = parser.getTileWidth();
//        int tileHeight = parser.getTileHeight();
//    }

    @Override
    public Tile[][] getMap() {
        return new Tile[width][height];
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getTileWidth() {
        return tileWidth;
    }

    @Override
    public int getTileHeight() {
        return tileHeight;
    }

    public List<Tile[][]> getMapTileLayers() {
        return mapTileLayers;
    }
}
