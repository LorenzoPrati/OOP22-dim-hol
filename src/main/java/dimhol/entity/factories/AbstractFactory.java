package dimhol.entity.factories;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Factory.
 */
public abstract class AbstractFactory {

    private final Map<String, Map<String, ArrayList<Integer>>> map = new HashMap<>();

    /**
     * Abstract Factory contains a map of game entity graphics.
     */
    public AbstractFactory() {
        
        final InputStream input = AbstractFactory.class.getResourceAsStream("/config/animations.yaml");
        final Yaml yaml = new Yaml();
        final Map<String, Map<String, ArrayList<Integer>>> mapLoaded = yaml.load(input);
        map.putAll(mapLoaded);
        assert input != null;
        try{
            input.close();
        } catch (IOException e) {
            System.out.println("Failed to close stream.");
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
