package it.unibo.dimhol.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class ResourceLoader {
    private Map<Integer,BufferedImage> imagesMap = new HashMap<>();
   
    public ResourceLoader(){
        load();
    }

    private void load(){
        try{
            this.imagesMap.put(1, ImageIO.read(new File("src/res/warrior/attack/WarriorUpAttack01.png")));
            this.imagesMap.put(2, ImageIO.read(new File("src/res/warrior/attack/WarriorDownAttack01.png")));
            this.imagesMap.put(3, ImageIO.read(new File("src/res/warrior/attack/WarriorLeftAttack01.png")));
            this.imagesMap.put(4, ImageIO.read(new File("src/res/warrior/attack/WarriorRightAttack01.png")));
            this.imagesMap.put(5, ImageIO.read(new File("src/res/warrior/walk/WarriorDownWalk.png")));
            this.imagesMap.put(6, ImageIO.read(new File("src/res/warrior/walk/WarriorUpWalk.png")));
            this.imagesMap.put(7, ImageIO.read(new File("src/res/warrior/walk/WarriorLeftWalk.png")));
            this.imagesMap.put(8, ImageIO.read(new File("src/res/warrior/walk/WarriorRightWalk.png")));
            //TODO: add image state idle for the player
            this.imagesMap.put(10, ImageIO.read(new File("src/res/enemies/ghost-Sheet.png")));
            this.imagesMap.put(11, ImageIO.read(new File("src/res/items/coin.png")));
            this.imagesMap.put(12, ImageIO.read(new File("src/res/items/heart.png")));
            this.imagesMap.put(13, ImageIO.read(new File("src/res/items/key.png")));
        }
        catch(IOException e){
            System.out.println("Error loading images");
        }
    }

    public BufferedImage getImage(final int numImage){
        return imagesMap.get(numImage);
    }
}
