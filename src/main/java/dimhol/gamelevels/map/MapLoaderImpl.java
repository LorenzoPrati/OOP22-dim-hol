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
     * Constructor for the map loader implementation.
     */
    public MapLoaderImpl() {
        mapTileLayers = new ArrayList<>();
    }

    /**
     * Loads a map from an XML file.
     *
     * @param inputStream The input stream of the XML file.
     * @throws MapLoadingException If an error occurs while loading the map.
     */
    public void loadMap(final InputStream inputStream) {
        try {
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            final Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();

            final Element rootElement = doc.getDocumentElement();
            tileWidth = Integer.parseInt(rootElement.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(rootElement.getAttribute("tileheight"));

            final NodeList layerNodeList = doc.getElementsByTagName("layer");
            width = Integer.parseInt(layerNodeList.item(0).getAttributes().getNamedItem("width").getNodeValue());
            height = Integer.parseInt(layerNodeList.item(0).getAttributes().getNamedItem("height").getNodeValue());

            mapTileLayers = new ArrayList<>();
            IntStream.range(0, layerNodeList.getLength()).forEach(i ->
                    mapTileLayers.add(createTileMatrix(layerNodeList.item(i))));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new MapLoadingException("Error loading the map.", e);
        }
    }

    /**
     * Creates a tile matrix from the given layer node.
     *
     * @param layerNode The XML node representing a map layer.
     * @return The created tile matrix.
     */
    private Tile[][] createTileMatrix(final Node layerNode) {
        final Element layerElement = (Element) layerNode;
        final NodeList propertyNodes = layerElement.getElementsByTagName("property");
        final Element dataElement = (Element) layerElement.getElementsByTagName("data").item(0);

        final String[] lines = dataElement.getFirstChild().getTextContent().split("[\n|,]");
        final List<String> nonEmptyLines = Arrays.stream(lines).filter(line -> !line.isEmpty()).toList();
        Tile[][] matrix = new Tile[width][height];

        int tileMapIdIndex = 0;
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                for (int propertyIndex = 0; propertyIndex < propertyNodes.getLength(); propertyIndex++) {
                    final Element property = (Element) propertyNodes.item(propertyIndex);
                    final int tileMapId = Integer.parseInt(nonEmptyLines.get(tileMapIdIndex));
                    if (tileMapId == Integer.parseInt(property.getAttribute("tileMapIdInt"))
                            && property.hasAttribute("walkableBool") && property.hasAttribute("tileSetIdInt")) {
                        final boolean walkable = Boolean.parseBoolean(property.getAttribute("walkableBool"));
                        final int tileSetId = Integer.parseInt(property.getAttribute("tileSetIdInt"));
                        matrix[row][col] = new TileImpl(tileSetId, walkable);
                    }

                }
                tileMapIdIndex++;
            }
        }
        return matrix;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tile[][]> getMapTileLayers() {
        return new ArrayList<>(mapTileLayers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TileMap loadNormalRoom() {
        try (InputStream inputStream = getClass().getResourceAsStream("/config/map/Classic-room.xml")) {
            return loadRoomMap(inputStream);
        } catch (IOException e) {
            throw new MapLoadingException("Error loading the normal room map.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TileMap loadShopRoom() {
        try (InputStream inputStream = getClass().getResourceAsStream("/config/map/Shop-room.xml")) {
            return loadRoomMap(inputStream);
        } catch (IOException e) {
            throw new MapLoadingException("Error loading the shop room map.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TileMap loadBossRoom() {
        try (InputStream inputStream = getClass().getResourceAsStream("/config/map/Boss-room.xml")) {
            return loadRoomMap(inputStream);
        } catch (IOException e) {
            throw new MapLoadingException("Error loading the boss room map.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TileMap loadRoomMap(final InputStream inputStream) {
        loadMap(inputStream);
        return getTileMap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TileMap getTileMap() {
        return new TileMapImpl(mapTileLayers, width, height, tileWidth, tileHeight);
    }
}
