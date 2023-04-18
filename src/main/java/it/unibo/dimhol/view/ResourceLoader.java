package it.unibo.dimhol.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ResourceLoader {
    private Map<String,BufferedImage> playerImages = new HashMap<>();
    private BufferedImage enemyImages;
    private Map<String,BufferedImage> shopItemsImages = new HashMap<>();
    private Map<String,BufferedImage> bulletImages = new HashMap<>();
    private BufferedImage coinImage;
    private BufferedImage heartImage;
    private BufferedImage keyImage;

    public ResourceLoader(){
        playerSpritesLoader();
        enemySpritesLoader();
        itemSpritesLoader();
        backgroundLoader();
    }


    /*carica immagini del player */
    private void playerSpritesLoader(){
        try{
            this.playerImages.put("attacking down", ImageIO.read(new File("src/res/warrior/attack/WarriorDownAttack01.png")));
            this.playerImages.put("attacking up", ImageIO.read(new File("src/res/warrior/attack/WarriorUpAttack01.png")));
            this.playerImages.put("attacking left", ImageIO.read(new File("src/res/warrior/attack/WarriorLeftAttack01.png")));
            this.playerImages.put("attacking right", ImageIO.read(new File("src/res/warrior/attack/WarriorRightAttack01.png")));
            this.playerImages.put("walking down", ImageIO.read(new File("src/res/warrior/walk/WarriorDownWalk.png")));
            this.playerImages.put("walking up", ImageIO.read(new File("src/res/warrior/walk/WarriorUpWalk.png")));
            this.playerImages.put("walking left", ImageIO.read(new File("src/res/warrior/walk/WarriorLeftWalk.png")));
            this.playerImages.put("walking right", ImageIO.read(new File("src/res/warrior/walk/WarriorRightWalk.png")));
        }
        catch(IOException e){
            System.out.println("Error loading images");
        }
    }

    private void enemySpritesLoader() {
        try{
            this.enemyImages = ImageIO.read(new File("src/res/enemies/ghost-Sheet.png"));
        }
        catch(IOException e){
            System.out.println("Error loading enemies images");
        }
    }

    private void itemSpritesLoader() {
        try{
            this.coinImage = ImageIO.read(new File("src/res/items/coin.png"));
            this.heartImage = ImageIO.read(new File("src/res/items/heart.png"));
            this.keyImage = ImageIO.read(new File("src/res/items/key.png"));
        }
        catch(IOException e){
            System.out.println("Error loading items images");
        }
    }

    private void backgroundLoader(){

    }

    public BufferedImage getPlayerImage(String state){
        return playerImages.get(state);
    }
}
