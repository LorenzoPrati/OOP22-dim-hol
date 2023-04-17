package it.unibo.dimhol.components;

public class HealthComponent implements Component {
    private int maxHealth;
    private int currentHealth;

    public HealthComponent(final int maxHealth){
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(final int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrentHealth() {
        return this.currentHealth;
    }

    public void setCurrentHealth(final int currentHealth) {
        this.currentHealth = currentHealth;
    }

}
