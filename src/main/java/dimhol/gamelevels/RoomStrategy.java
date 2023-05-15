package dimhol.gamelevels;

import dimhol.entity.Entity;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Set;

/**
 * The RoomStrategy interface is used to define the behavior of each different game room.
 * Implementations of this interface provide methods to generate entities for the game room, such as enemies and the player
 * and position them within the room.
 */
public interface RoomStrategy {
    /**
     * Generate entities for the game room.
     * This method should be implemented to create the enemies, the player,
     * and position them into the room according to the specific strategy of the room.
     * @param entity The main entity is the player of the game room.
     * @param freeTiles The set of available tiles within the room, where entities can be placed.
     * @return A List of entities generated for the game room.
     */
    List<Entity> generate(Entity entity, Set<Pair<Integer, Integer>> freeTiles);
}
