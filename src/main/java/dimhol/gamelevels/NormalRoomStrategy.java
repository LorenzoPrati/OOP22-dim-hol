package dimhol.gamelevels;

import dimhol.components.PositionComponent;
import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.map.MapLoad;
import org.apache.commons.lang3.tuple.Pair;
import org.locationtech.jts.math.Vector2D;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class NormalRoomStrategy implements RoomStrategy {
    private final MapLoad mapLoad;
    private final LevelManager levelManager;
    private final World world;

//    private static final int ENEMY_DENSITY = 10;
    public NormalRoomStrategy(World world, MapLoad mapLoad, LevelManager level) {
        this.world = world;
        this.mapLoad = mapLoad ;
        this.levelManager = level;
    }

    @Override
    public List<Entity> generate(Entity entity, Set<Pair<Integer, Integer>> freeTiles) {
        return null;
    }

    private void placePlayer(Entity entity, Set<Pair<Integer, Integer>> freeTiles) {
        var playerPosition = (PositionComponent) entity.getComponent(PositionComponent.class);
        var freeRandomTile = freeTiles.stream().toList().get(new Random().nextInt(freeTiles.size()));
        playerPosition.setPos(new Vector2D(freeRandomTile.getLeft(), freeRandomTile.getRight()));
    }
}
