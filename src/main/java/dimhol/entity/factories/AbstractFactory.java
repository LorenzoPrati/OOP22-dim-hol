package dimhol.entity.factories;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Factory.
 */
public abstract class AbstractFactory {

    protected final Map<String, Map<String, ArrayList<Integer>>> map = new HashMap<>();

    /**
     * Abstract Factory contains a map of game entity graphics.
     */
    public AbstractFactory() {
        try {
            final InputStream input = getClass().getResourceAsStream("/config/animations.yaml");
            final Yaml yaml = new Yaml();
            final Map<String, Map<String, ArrayList<Integer>>> mapLoaded = yaml.load(input);
            map.putAll(mapLoaded);
        } catch (NullPointerException e) {
            System.out.println("File not found. ");
        }
    }
}
