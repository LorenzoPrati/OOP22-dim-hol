package dimhol.components;

/**
 *
 */
public class PowerComponent implements Component {

    private final double powerLevel;

    public PowerComponent(double powerLevel) {
        this.powerLevel = powerLevel;
    }

    public double getPowerLevel() {
        return powerLevel;
    }
}
