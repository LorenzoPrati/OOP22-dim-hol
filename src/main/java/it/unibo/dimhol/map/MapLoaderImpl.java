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
    private Tile[][] map;
    private final int width;
    private final int height;
    private final int tileWidth;
    private final int tileHeight;
    private final List<Tile[][]> mapTileLayers;
    NodeList layerNodeList;
    Element layerElement;

    /**
     * Constructor for MapLoaderImpl.
     *
     * @param tileWidth
     * @param tileHeight
     * @param width
     * @param height
     * @param mapTileLayers
     */
    public MapLoaderImpl(int tileWidth, int tileHeight, int width, int height, List<Tile[][]> mapTileLayers) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.width = 0;
        this.height = 0;
        this.mapTileLayers = new ArrayList<>();
    }

    /**
     * Load a map from a given XML file.
     *
     * @param fileName The name of the XML file to load the map from.
     * @return true if the map has been successfully loaded, false otherwise.
     */
    public MapLoaderImpl loadMapFromXmlFile(final String fileName) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(fileName));
            //Normalize the document by removing empty spaces and combining adjacent text nodes.
            doc.getDocumentElement().normalize();
            Element rootElement = doc.getDocumentElement();
            int width = Integer.parseInt(rootElement.getAttribute("width"));
            int height = Integer.parseInt(rootElement.getAttribute("height"));
            int tileWidth = Integer.parseInt(rootElement.getAttribute("tilewidth"));
            int tileHeight = Integer.parseInt(rootElement.getAttribute("tileheight"));

            NodeList layerNodeList = doc.getElementsByTagName("layer");
            if (layerNodeList.getLength() == 0)
                return null;

            List<Tile[][]> mapTileLayers = new ArrayList<>();
            for (int i = 0; i < layerNodeList.getLength(); i++) {
                mapTileLayers.add(getTileMatrix(layerNodeList.item(i), width, height));
            }

            return new MapLoaderImpl(width, height, tileWidth, tileHeight, mapTileLayers);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            System.out.println("ERROR: The map has not been parsed!");
        }
        return null;
    }

    private Tile[][] getTileMatrix(Node item, int width, int height) {
        Element layerElement = (Element) item;
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
    @Override
    public List<Tile[][]> getMapTileLayers() {
        return mapTileLayers;
    }
}