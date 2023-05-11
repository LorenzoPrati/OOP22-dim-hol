package dimhol.gamelevels;

import dimhol.entity.Entity;
import dimhol.entity.factories.GenericFactory;
import dimhol.map.MapLoaderImpl;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class ShopRoomStrategy implements RoomStrategy {
    private MapLoaderImpl mapLoader;

    public ShopRoomStrategy(MapLoaderImpl mapLoader) {
        this.mapLoader = mapLoader;
    }

    @Override
    public List<Entity> generate(Entity entity, Set<Pair<Integer, Integer>> freeTiles) {
        return null;
    }
}
