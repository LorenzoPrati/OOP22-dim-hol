package it.unibo.dimhol.gameLevels;

import it.unibo.dimhol.World;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.entity.GenericFactory;
import it.unibo.dimhol.map.MapLoaderImpl;

import java.util.List;
import java.util.Random;
public class ShopRoomStrategy implements RoomStrategy {
    MapLoaderImpl mapLoader;
    World world;
    public ShopRoomStrategy(World world) {
        this.world = world;
    }
    @Override
    public void generate() {
        //player and items needs to be generated in a shop room.
        GenericFactory player = new GenericFactory();
        double playerX = new Random().nextInt(0, mapLoader.getHeight());
        double playerY = new Random().nextInt(0, mapLoader.getWidth());
        world.addEntity(player.createPlayer(10.0, 10.0));

        GenericFactory shopKeeper = new GenericFactory();
        double shopKeeperX = new Random().nextInt(0, mapLoader.getHeight());
        double shopKeeperY = new Random().nextInt(0, mapLoader.getWidth());
        world.addEntity(player.createPlayer(30.0, 30.0));
    }
    @Override
    public void spawnRewards() {
        // Algorithm to spawn rewards in a shop room.
        //NO REWARDS INTO THE SHOP ROOM.
    }
}
