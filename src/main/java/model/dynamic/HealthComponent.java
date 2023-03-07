package model.dynamic;

public class HealthComponent {

    private int maxHealth;
    /**
     * a value from 0 (death) to 50 (max capacity)
     */
    private int health;
    Player user;

    public void increaseOf(int amount) {
        var newHealth = this.health += amount;
        if ( newHealth < this.maxHealth) {
            this.health = newHealth;
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

}
