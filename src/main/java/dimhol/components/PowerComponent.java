package dimhol.components;

/**
 *
 */
public class PowerComponent implements Component {

    private final double powerLevel;

    /**
     *
     * @param powerLevel The
     */
    public PowerComponent(final double powerLevel) {
        this.powerLevel = powerLevel;
    }

    public double getPowerLevel() {
        return powerLevel;
    }
}
