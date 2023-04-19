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


/**
 * The class is responsible for parsing an XML file and loading a map from it.
 */
public class LoadMapImpl implements LoadMap {
    private static final int ROWS = 20;
    private static final int COLS = 20;
    private int[][] map;
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;

    /**
     * Constructor for LoadMapImpl that loads a map from an XML file.
     * @param fileName The name of the XML file to load the map from.
     */
    public LoadMapImpl(final String fileName) {
        try {
            File inputFile = new File(fileName);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("C:\\RepoProgettoECS\\OOP22-dim-hol\\src\\main\\java\\it\\unibo\\dimhol\\map\\parsing\\pom\\PrimaProvaTiled.xml");

            /**
             * Normalize the document by removing empty spaces and combining adjacent text nodes.
             */
            doc.getDocumentElement().normalize();
            /**
             * Get the root element of the document.
             */
            var map = doc.getDocumentElement();
            /**
             * Print the name of the root element.
             */
            System.out.println("Root element:" + map.getNodeName());
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
            Node layerNode = layerNodeList.item(0);
            /**
             * Get the first child of the data element, which contains the matrix data as string.
             */
            Node stringMatrix = dataNodeList.item(0).getFirstChild(); //layer 0
            System.out.println(stringMatrix.getTextContent());
            this.map = new int[height][width];
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Testing with a specific main (temporary).
     * @param args
     */
    public static void main(final String[] args) {
        LoadMapImpl parser = new LoadMapImpl("src\\main\\java\\it\\unibo\\dimhol\\map\\parsing\\pom\\PrimaProvaTiled.xml");

        int[][] map = parser.getMap();
        int width = parser.getWidth();
        int height = parser.getHeight();
        int tileWidth = parser.getTileWidth();
        int tileHeight = parser.getTileHeight();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(" ");
            }
            System.out.println("here");
        }
        //TODO: Do something with the map data...
    }

    @Override
    public int[][] getMap() {
        return new int[0][];
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getTileWidth() {
        return 0;
    }

    @Override
    public int getTileHeight() {
        return 0;
    }
}
