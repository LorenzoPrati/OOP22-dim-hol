package dimhol.entity.factories;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * AbstractFactory open the graphic configuration file.
 */
public class BaseFactory {

    private final Map<String, Map<String, ArrayList<Integer>>> map = new HashMap<>();

    /**
     * Abstract Factory contains a map of game entity graphics.
     */
    BaseFactory() {
        final InputStream input = BaseFactory.class.getResourceAsStream("/config/animations.yaml");
        final Yaml yaml = new Yaml();
        final Map<String, Map<String, ArrayList<Integer>>> mapLoaded = yaml.load(input);
        map.putAll(mapLoaded);
        try {
            input.close();
        } catch (IOException e) {
            throw (IllegalStateException) new IllegalStateException().initCause(e);
        }
    }

    /**
     * Animation map getter.
     * @return the animation's map
     */
    public Map<String, Map<String, ArrayList<Integer>>> getAnimationsMap() {
        return Collections.unmodifiableMap(map);
    }
}
