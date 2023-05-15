package dimhol.gamelevels;

import dimhol.entity.Entity;
import dimhol.map.MapLoaderImpl;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Set;

/**
 *
 */
public class ShopRoomStrategy implements RoomStrategy {
    private final MapLoaderImpl mapLoader;

    /**
     *
     * @param mapLoader
     */
    public ShopRoomStrategy(final MapLoaderImpl mapLoader) {
        this.mapLoader = mapLoader;
    }

    @Override
    public final List<Entity> generate(final Entity entity, final Set<Pair<Integer, Integer>> freeTiles) {
        return null;
    }
}
