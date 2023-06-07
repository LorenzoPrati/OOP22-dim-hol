package dimhol.components;

/**
 * A component mantain information about living entities' health.
 */
public class HealthComponent implements Component {
    private int maxHealth;
    private int currentHealth;

    /**
     * Constructs a HealthComponent.
     * @param maxHealth
     */
    public HealthComponent(final int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    /**
     * @return the current maximum health.
     */
    public int getMaxHealth() {
        return this.maxHealth;
    }

    /**
     * A method to set the new max health.
     * @param maxHealth
     */
    public void setMaxHealth(final int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * @return the current health.
     */
    public int getCurrentHealth() {
        return this.currentHealth;
    }

    /**
     * A method to set the current health.
     * @param currentHealth
     */
    public void setCurrentHealth(final int currentHealth) {
        this.currentHealth = currentHealth;
    }
}
