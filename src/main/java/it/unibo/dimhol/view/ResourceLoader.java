package it.unibo.dimhol.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.tuple.ImmutableTriple;

public class ResourceLoader {
    private final Map<Integer,ImmutableTriple<BufferedImage,Integer,Integer>> imagesMap = new HashMap<>();
   
    public ResourceLoader(){
        load();
    }
    
    private void load(){
        try{
            this.imagesMap.put(1, new ImmutableTriple<>((ImageIO.read(new File("src/res/warrior/attack/WarriorUpAttack01.png"))), 48, 48));
            this.imagesMap.put(2, new ImmutableTriple<>((ImageIO.read(new File("src/res/warrior/attack/WarriorDownAttack01.png"))), 48, 48));
            this.imagesMap.put(3, new ImmutableTriple<>((ImageIO.read(new File("src/res/warrior/attack/WarriorLeftAttack01.png"))), 48, 48));
            this.imagesMap.put(4, new ImmutableTriple<>((ImageIO.read(new File("src/res/warrior/attack/WarriorRightAttack01.png"))), 48, 48));
            
            this.imagesMap.put(5, new ImmutableTriple<>((ImageIO.read(new File("src/res/warrior/walk/WarriorUpWalk.png"))), 48, 48));
            this.imagesMap.put(6, new ImmutableTriple<>((ImageIO.read(new File("src/res/warrior/walk/WarriorDownWalk.png"))), 48, 48));
            this.imagesMap.put(7, new ImmutableTriple<>((ImageIO.read(new File("src/res/warrior/walk/WarriorLeftWalk.png"))), 48, 48));
            this.imagesMap.put(8, new ImmutableTriple<>((ImageIO.read(new File("src/res/warrior/walk/WarriorRightWalk.png"))), 48, 48));

            this.imagesMap.put(9, new ImmutableTriple<>((ImageIO.read(new File("src/res/warrior/idle/WarriorUpIdle.png"))), 48, 48));
            this.imagesMap.put(10, new ImmutableTriple<>((ImageIO.read(new File("src/res/warrior/idle/WarriorDownIdle.png"))), 48, 48));
            this.imagesMap.put(11, new ImmutableTriple<>((ImageIO.read(new File("src/res/warrior/idle/WarriorLeftIdle.png"))), 48, 48));
            this.imagesMap.put(12, new ImmutableTriple<>((ImageIO.read(new File("src/res/warrior/idle/WarriorRightIdle.png"))), 48, 48));
            
            this.imagesMap.put(13, new ImmutableTriple<>((ImageIO.read(new File("src/res/enemies/ghost-Sheet.png"))), 32, 32));
            this.imagesMap.put(14, new ImmutableTriple<>((ImageIO.read(new File("src/res/items/coin.png"))), 32, 32));
            this.imagesMap.put(15, new ImmutableTriple<>((ImageIO.read(new File("src/res/items/heart.png"))), 64, 64));
            this.imagesMap.put(16, new ImmutableTriple<>((ImageIO.read(new File("src/res/items/key.png"))), 19, 10));
            
        }

        catch(IOException e){
            System.out.println("Error loading images");
        }
    }

    public BufferedImage getImage(final int numImage){
        return imagesMap.get(numImage).getLeft();
    }

    public int getWidth(final int numImage){
        return imagesMap.get(numImage).getMiddle();
    }

    public int getHeigth(final int numImage){
        return imagesMap.get(numImage).getRight();
    }
}
