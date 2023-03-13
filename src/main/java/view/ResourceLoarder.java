package view;

import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

import org.apache.commons.lang3.tuple.Pair;

import model.common.ObjectType;
import model.common.State;
import java.awt.image.BufferedImage;

/**
 * A class which loads all the game's images before starting the game.
 */
public class ResourceLoarder {
    private Map<ObjectType,Pair<Integer,Integer>> dimensions = new HashMap<>();
    private Map<ObjectType,Map<State,BufferedImage>> spriteSheet = new HashMap<>();

    public ResourceLoarder(){
        fillDimensionMap();
        loadPlayerImages();
        loadCoinImages();  
    }

    private void fillDimensionMap(){
        this.dimensions.put(ObjectType.PLAYER,Pair.of(32,32));
        this.dimensions.put(ObjectType.COIN,Pair.of(32,32));
    }

    private void loadPlayerImages(){
        Map<State,BufferedImage> p = new HashMap<>();
        try{
            p.put(State.MOVING_DOWN, ImageIO.read(getClass().getResourceAsStream("/resources/player/WarriorDownWalk.png")));
            p.put(State.MOVING_UP, ImageIO.read(getClass().getResourceAsStream("/resources/player/WarriorUpWalk.png")));
            p.put(State.MOVING_RIGHT, ImageIO.read(getClass().getResourceAsStream("/resources/player/WarriorRightWalk.png")));
            p.put(State.MOVING_LEFT, ImageIO.read(getClass().getResourceAsStream("/resources/player/WarriorLeftWalk.png")));
            p.put(State.ATTACKING_DOWN, ImageIO.read(getClass().getResourceAsStream("/resources/player/WarriorDownAttack01.png")));
            p.put(State.ATTACKING_UP, ImageIO.read(getClass().getResourceAsStream("/resources/player/WarriorUpAttack01.png")));
            p.put(State.ATTACKING_RIGHT, ImageIO.read(getClass().getResourceAsStream("/resources/player/WarriorRightAttack01.png")));
            p.put(State.ATTACKING_LEFT, ImageIO.read(getClass().getResourceAsStream("/resources/player/WarriorLeftAttack01.png")));
        }
        catch(Exception e){
            System.out.println("Error loading player's images");
        }
        this.spriteSheet.put(ObjectType.PLAYER,p);
    }

    private void loadCoinImages(){
        Map<State,BufferedImage> c = new HashMap<>();
        try{
            c.put(State.IDLE, ImageIO.read(getClass().getResourceAsStream("/resources/item/coin.png")));
        }
        catch(Exception e){
            System.out.println("Error loading coin's images");
        }
        this.spriteSheet.put(ObjectType.COIN,c);
    }

    public BufferedImage getStateImage(ObjectType type, State state){
        BufferedImage image = null;
        for(var e : this.spriteSheet.entrySet()){
            if(e.getKey().equals(type)){
                for(var e2: e.getValue().entrySet()){ 
                    if(e2.getKey().equals(state)){
                        image =  e2.getValue();
                    }
                }
            }   
        }
        return image;
    }

    public int getWidthImage(ObjectType t){
        return this.dimensions.entrySet().stream().filter(e -> e.getKey().equals(t)).map(e -> e.getValue()).findAny().get().getLeft();
    }

    public int getHeightImage(ObjectType t) {
        return this.dimensions.entrySet().stream().filter(e -> e.getKey().equals(t)).map(e -> e.getValue()).findAny().get().getRight();
    }
}
