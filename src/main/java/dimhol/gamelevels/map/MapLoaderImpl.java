package dimhol.gamelevels.map;

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
public final class MapLoaderImpl implements MapLoader {
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;
    private List<Tile[][]> mapTileLayers;
    private NodeList layerNodeList;
    private Element layerElement;

    /**
     * Constructor for LoadMapImpl that loads a map from an XML file.
     *
     * @param fileName The name of the XML file to load the map from.
     */
    private void loadMap(final String fileName) {

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
         * Validate the width and height values.
         * If they are not positive, display an error and set default values.
         */
        if (width <= 0 || height <= 0) {
            System.err.println("Invalid width or height values in the XML file.");
        }

        /*
         * Get the first layer element by using "item(0)" and store it in a "node" variable "layerNode".
         */
        mapTileLayers = new ArrayList<>();
        for (int i = 0; i < layerNodeList.getLength(); i++) {
            mapTileLayers.add(getTileMatrix(layerNodeList.item(i)));
        }
    }

    private Tile[][] getTileMatrix(final Node item) {
        Element layerElement = (Element) item.getChildNodes();
        NodeList propertyNodes = layerElement.getElementsByTagName("property");
        Element dataElement = (Element) layerElement.getElementsByTagName("data").item(0);

        String[] line = dataElement.getFirstChild().getTextContent().split("[\n|,]");
        List<String> nline = Arrays.stream(line).filter(e -> !e.equals("")).toList();
        TileImpl[][] matrix = new TileImpl[width][height];

        int k = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int l = 0; l < propertyNodes.getLength(); l++) {
                    Element property = (Element) propertyNodes.item(l);
                    if (Integer.parseInt((nline.get(k))) == Integer.parseInt(property.getAttribute("tileMapIdInt"))) {

                        if (property.hasAttribute("walkableBool") && property.hasAttribute("tileSetIdInt")) {
                            matrix[i][j] = new TileImpl(Integer.parseInt(property.getAttribute("tileSetIdInt")),
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
    public List<Tile[][]> getMapTileLayers() {
        return new ArrayList<>(mapTileLayers);
    }

    @Override
    public TileMap loadNormalRoom() {
        loadMap("src/main/resources/config/map/nice-map.xml");
        return getMap();
    }

    private TileMap getMap() {
        return new TileMapImpl(this.getMapTileLayers(), width, height, tileWidth, tileHeight);
    }

    @Override
    public TileMap loadShopRoom() {
        loadMap("src/main/resources/config/map/nice-map.xml");
        return getMap();
    }

    @Override
    public TileMap loadBossRoom() {
        loadMap("src/main/resources/config/map/nice-map.xml");
        return getMap();
    }

    @Override
    public TileMap loadCustomMap() {
        loadMap("");
        return getMap();
    }

    @Override
    public TileMap getTileMap() {
        return null;
    }
}
