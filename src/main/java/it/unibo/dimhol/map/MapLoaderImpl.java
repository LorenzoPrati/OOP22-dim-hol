package it.unibo.dimhol.map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
public class MapLoaderImpl implements MapLoad {
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;
    private List<Tile[][]> mapTileLayers;
    NodeList layerNodeList;
    Element layerElement;

    /**
     * Constructor for LoadMapImpl that loads a map from an XML file.
     * @param fileName The name of the XML file to load the map from.
     */
    public MapLoaderImpl(final String fileName) {

        Element rootElement = null;
        Document doc = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(new File(fileName));
            /*
             * Normalize the document by removing empty spaces and combining adjacent text nodes.
             */
            doc.getDocumentElement().normalize();
            rootElement = doc.getDocumentElement();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        /*
         * Get the root element of the document.
         */
        assert rootElement != null;
        tileWidth = Integer.parseInt(rootElement.getAttribute("tilewidth"));
        tileHeight = Integer.parseInt(rootElement.getAttribute("tileheight"));

        /*
         * Get the list of all the layer elements into the document.
         */
        layerNodeList = doc.getElementsByTagName("layer");
        layerElement = (Element) layerNodeList.item(0);
        width = Integer.parseInt(layerElement.getAttribute("width"));
        height = Integer.parseInt(layerElement.getAttribute("height"));
        /*
         * Get the first layer element by using "item(0)" and store it in a "node" variable "layerNode".
         */
        mapTileLayers = new ArrayList<>();
        for (int i = 0; i < layerNodeList.getLength(); i++) {
            mapTileLayers.add(getTileMatrix(layerNodeList.item(i)));
        }
    }

    private Tile[][] getTileMatrix(Node item) {
        Element layerElement = (Element) item.getChildNodes();
        NodeList propertyNodes = layerElement.getElementsByTagName("property");
        Element dataElement = (Element) layerElement.getElementsByTagName("data").item(0);

        String[] line = dataElement.getFirstChild().getTextContent().split("[\n|,]");
        List<String> nline = Arrays.stream(line).filter(e -> !e.equals("")).toList();
        Tile[][] matrix = new Tile[width][height];

        int k = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int l = 0; l < propertyNodes.getLength(); l++) {
                    Element property = (Element) propertyNodes.item(l);
                    if (Integer.parseInt((nline.get(k))) == Integer.parseInt(property.getAttribute("tileMapIdInt"))) {

                        if (property.hasAttribute("walkableBool") && property.hasAttribute("tileSetIdInt")) {
                            matrix[i][j] = new Tile(Integer.parseInt(property.getAttribute("tileSetIdInt")),
                                    Boolean.parseBoolean(property.getAttribute("walkableBool")));
                        }
                    }
                }
                k++;
            }
        }
        return matrix;
    }

    @Override
    public int getTileWidth() {
        return tileWidth;
    }

    @Override
    public int getTileHeight() {
        return tileHeight;
    }

    @Override
    public TileMap getTileMap() {
        return new TileMap(getMapTileLayers().get(0));
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public  int getHeight() {
        return height;
    }

    @Override
    public List<Tile[][]> getMapTileLayers() {
        return mapTileLayers;
    }
}