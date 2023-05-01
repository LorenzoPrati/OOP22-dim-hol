package it.unibo.dimhol.gameLevels;

import it.unibo.dimhol.World;
import it.unibo.dimhol.entity.GenericFactory;
import it.unibo.dimhol.map.MapLoaderImpl;

import java.util.Random;
public class ShopRoomStrategy implements RoomStrategy, RewardSpawner {
    private MapLoaderImpl mapLoader;

    public ShopRoomStrategy(MapLoaderImpl mapLoader) {
        this.mapLoader = mapLoader;
    }
    @Override
    public void generate() {
        //player and items needs to be generated in a shop room.
        GenericFactory player = new GenericFactory();
        double playerX = new Random().nextInt(0, mapLoader.getWidth() - 1);
        double playerY = new Random().nextInt(0, mapLoader.getHeight() - 1);
        //TODO: change
        //        world.addEntity(player.createPlayer(playerX, playerY));

        GenericFactory shopKeeper = new GenericFactory();
        double shopKeeperX = new Random().nextInt(0, mapLoader.getWidth() - 1);
        double shopKeeperY = new Random().nextInt(0, mapLoader.getHeight() - 1);
        //TODO: change
        //world.addEntity(player.createPlayer(shopKeeperX, shopKeeperY));
    }

    @Override
    public void placePlayer() {

    }

    @Override
    public void spawnRewards() {
        // Algorithm to spawn rewards in a shop room.
        //NO REWARDS INTO THE SHOP ROOM.
    }
}
