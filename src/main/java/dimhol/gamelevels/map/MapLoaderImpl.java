package dimhol.gamelevels.map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * The class is responsible for parsing an XML file and loading a map from it.
 */
public final class MapLoaderImpl implements MapLoader {
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;
    private List<Tile[][]> mapTileLayers;

    /**
     * Loads a map from an XML file.
     *
     * @param inputStream The input stream of the XML file.
     * @throws MapLoadingException If an error occurs while loading the map.
     */
    public void loadMap(final InputStream inputStream) throws MapLoadingException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();

            Element rootElement = doc.getDocumentElement();
            tileWidth = Integer.parseInt(rootElement.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(rootElement.getAttribute("tileheight"));

            NodeList layerNodeList = doc.getElementsByTagName("layer");
            width = Integer.parseInt(layerNodeList.item(0).getAttributes().getNamedItem("width").getNodeValue());
            height = Integer.parseInt(layerNodeList.item(0).getAttributes().getNamedItem("height").getNodeValue());

            mapTileLayers = new ArrayList<>();
            IntStream.range(0, layerNodeList.getLength()).forEach(i ->
                    mapTileLayers.add(createTileMatrix(layerNodeList.item(i))));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new MapLoadingException("Error loading the map.", e);
        }
    }

    private Tile[][] createTileMatrix(Node layerNode) {
        Element layerElement = (Element) layerNode;
        NodeList propertyNodes = layerElement.getElementsByTagName("property");
        Element dataElement = (Element) layerElement.getElementsByTagName("data").item(0);

        String[] lines = dataElement.getFirstChild().getTextContent().split("[\n|,]");
        List<String> nonEmptyLines = Arrays.stream(lines).filter(line -> !line.isEmpty()).toList();
        Tile[][] matrix = new Tile[width][height];

        int tileMapIdIndex = 0;
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                for (int propertyIndex = 0; propertyIndex < propertyNodes.getLength(); propertyIndex++) {
                    Element property = (Element) propertyNodes.item(propertyIndex);
                    int tileMapId = Integer.parseInt(nonEmptyLines.get(tileMapIdIndex));
                    if (tileMapId == Integer.parseInt(property.getAttribute("tileMapIdInt"))) {
                        if (property.hasAttribute("walkableBool") && property.hasAttribute("tileSetIdInt")) {
                            boolean walkable = Boolean.parseBoolean(property.getAttribute("walkableBool"));
                            int tileSetId = Integer.parseInt(property.getAttribute("tileSetIdInt"));
                            matrix[row][col] = new TileImpl(tileSetId, walkable);
                        }
                    }
                }
                tileMapIdIndex++;
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
        try (InputStream inputStream = getClass().getResourceAsStream("/config/map/normal-room.xml")) {
            loadMap(inputStream);
            return getMap();
        } catch (IOException e) {
            throw new MapLoadingException("Error loading the normal room map.", e);
        }
    }

    @Override
    public TileMap loadShopRoom() {
        try (InputStream inputStream = getClass().getResourceAsStream("/config/map/normal-room.xml")) {
            loadMap(inputStream);
            return getMap();
        } catch (IOException e) {
            throw new MapLoadingException("Error loading the shop room map.", e);
        }
    }

    @Override
    public TileMap loadBossRoom() {
        try (InputStream inputStream = getClass().getResourceAsStream("/config/map/normal-room.xml")) {
            loadMap(inputStream);
            return getMap();
        } catch (IOException e) {
            throw new MapLoadingException("Error loading the boss room map.", e);
        }
    }

    @Override
    public TileMap loadCustomMap(final InputStream inputStream) throws MapLoadingException {
        loadMap(inputStream);
        return getMap();
    }

    private TileMap getMap() {
        return new TileMapImpl(mapTileLayers, width, height, tileWidth, tileHeight);
    }

    @Override
    public TileMap getTileMap() {
        return null;
    }
}
