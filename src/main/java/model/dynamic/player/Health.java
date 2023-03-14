package model.dynamic.player;

public class Health {

    private int maxHealth;
    private int health;
    private final Player user;

    public Health(int maxHealth, int health, Player user) {
        this.maxHealth = maxHealth;
        this.health = health;
        this.user = user;
    }

    public void increaseOf(int amount) {
        var newHealth = this.health += amount;
        if (newHealth < this.maxHealth) {
            this.health = newHealth;
        }
    }

    public void decrease(int amount) {
        this.health -= amount;
    }

    public int getValue() {
        return health;
    }

    public int getMaxValue() {
        return maxHealth;
    }

}
