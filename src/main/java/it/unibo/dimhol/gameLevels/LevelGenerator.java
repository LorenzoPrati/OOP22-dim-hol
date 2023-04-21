package it.unibo.dimhol.gameLevels;

import it.unibo.dimhol.World;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.entity.GenericFactory;
import it.unibo.dimhol.events.AddEntityEvent;
import it.unibo.dimhol.map.LoadMap;
import it.unibo.dimhol.map.LoadMapImpl;

public class LevelGenerator {
    private World world;
    private LoadMap iter;
    private GenericFactory genericFactory
    private LoadMapImpl map;
    private int currentLevel;

    public LevelGenerator(World world, GenericFactory genericFactory) {
        this.world = world;
        this.genericFactory = genericFactory;
        currentLevel = 0;
    }

    //Load the map:
    public void loadMap(String fileName) {
        LoadMapImpl loadMap = new LoadMapImpl(fileName);
        map = loadMap.getMap();
    }
    //Add entity:
    public void addEntity(Entity entity, int x, int y) {
        world.notifyEvent(new AddEntityEvent(entity)); //x and y are already specified.
    }
    //Generate Level:
    public void generateLevel() {
        //Load map:
        loadMap("src/main/java/it/unibo/dimhol/map/mapResources/FirstTryTiled.xml");
        //Set the map:
        world.setMap(map); //to be added to the world.
//        world.setGraphicalMap();
        //Add player and enemies:
        addEntity(genericFactory.createPlayer(5, 5));
        addEntity(genericFactory.createShooterEnemy(10,10));

        //Change level:
        world.changeLevel(); //to be added to the world.

    }
}
