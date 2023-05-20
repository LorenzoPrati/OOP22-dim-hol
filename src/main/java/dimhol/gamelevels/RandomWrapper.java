package dimhol.gamelevels;

import java.util.Random;

/**
 * This class provides controlled access to the random number generation functionality.
 */
public class RandomWrapper {
    private final Random random;

    /**
     * Constructs a RandomWrapper object with a new instance of the Random class.
     */
    public RandomWrapper() {
        this.random = new Random();
    }

    /**
     * Returns the next pseudorandom, uniformly distributed int value from this random number generator's sequence.
     *
     * @param bound the upper bound (exclusive). Must be positive.
     * @return the next pseudorandom, uniformly distributed int value between 0 (inclusive) and bound (exclusive).
     */
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
