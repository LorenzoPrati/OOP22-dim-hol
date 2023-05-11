package dimhol.entity.factories;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Factory.
 */
public abstract class AbstractFactory {

    protected final Map<String, Map<String, ArrayList<Integer>>> map = new HashMap<>();

    public AbstractFactory() {
        try {
            InputStream input = new FileInputStream(new File("src/main/resources/config/animations.yaml"));
            Yaml yaml = new Yaml();
            Map<String, Map<String, ArrayList<Integer>>> mapLoaded = yaml.load(input);
            map.putAll(mapLoaded);
        } catch (FileNotFoundException e) {
            System.out.println("File not found. ");
        }
    }
}
