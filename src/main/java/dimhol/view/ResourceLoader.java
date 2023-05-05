package dimhol.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import java.io.IOException;

public class ResourceLoader {
    private final Map<Integer,ImmutableTriple<BufferedImage,Integer,Integer>> imagesMap = new HashMap<>();
    private BufferedImage tileSet;
    private ArrayList<BufferedImage> tileImages = new ArrayList<>();

    public ResourceLoader(final int tileWidth, final int tileHeight){
        load();
        loadTileSetImages(tileWidth,tileHeight);
    }
    
    private void load(){
        try{
            this.imagesMap.put(1, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/attack/WarriorUpAttack01.png"))), 48, 48));
            this.imagesMap.put(2, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/attack/WarriorDownAttack01.png"))), 48, 48));
            this.imagesMap.put(3, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/attack/WarriorLeftAttack01.png"))), 48, 48));
            this.imagesMap.put(4, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/attack/WarriorRightAttack01.png"))), 48, 48));

            this.imagesMap.put(5, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/attack2/WarriorUpAttack02.png"))), 48, 48));
            this.imagesMap.put(6, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/attack2/WarriorDownAttack02.png"))), 48, 48));
            this.imagesMap.put(7, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/attack2/WarriorLeftAttack02.png"))), 48, 48));
            this.imagesMap.put(8, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/attack2/WarriorRightAttack02.png"))), 48, 48));

            this.imagesMap.put(9, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/attack3/WarriorUpAttack03.png"))), 48, 48));
            this.imagesMap.put(10, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/attack3/WarriorDownAttack03.png"))), 48, 48));
            this.imagesMap.put(11, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/attack3/WarriorLeftAttack03.png"))), 48, 48));
            this.imagesMap.put(12, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/attack3/WarriorRightAttack03.png"))), 48, 48));
            
            this.imagesMap.put(13, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/walk/WarriorUpWalk.png"))), 48, 48));
            this.imagesMap.put(14, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/walk/WarriorDownWalk.png"))), 48, 48));
            this.imagesMap.put(15, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/walk/WarriorLeftWalk.png"))), 48, 48));
            this.imagesMap.put(16, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/walk/WarriorRightWalk.png"))), 48, 48));

            this.imagesMap.put(17, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/idle/WarriorUpIdle.png"))), 48, 48));
            this.imagesMap.put(18, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/idle/WarriorDownIdle.png"))), 48, 48));
            this.imagesMap.put(19, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/idle/WarriorLeftIdle.png"))), 48, 48));
            this.imagesMap.put(20, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/idle/WarriorRightIdle.png"))), 48, 48));

            this.imagesMap.put(21, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/hurt/WarriorUpHurt.png"))), 48, 48));
            this.imagesMap.put(22, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/hurt/WarriorDownHurt.png"))), 48, 48));
            this.imagesMap.put(23, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/hurt/WarriorLeftHurt.png"))), 48, 48));
            this.imagesMap.put(24, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/hurt/WarriorRightHurt.png"))), 48, 48));

            this.imagesMap.put(25, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/WarriorUpDeath.png"))), 48, 48));
            this.imagesMap.put(26, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/WarriorDownDeath.png"))), 48, 48));
            this.imagesMap.put(27, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/WarriorLeftDeath.png"))), 48, 48));
            this.imagesMap.put(28, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/WarriorRightDeath.png"))), 48, 48));

            this.imagesMap.put(29, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/WarriorUpClimb.png"))), 48, 48));
            this.imagesMap.put(30, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/WarriorDownClimb.png"))), 48, 48));
            this.imagesMap.put(31, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/WarriorLeftClimb.png"))), 48, 48));
            this.imagesMap.put(32, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/warrior/WarriorRightClimb.png"))), 48, 48));

            this.imagesMap.put(33, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/enemies/ghost-Sheet.png"))), 32, 32));
            this.imagesMap.put(34, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/items/coin.png"))), 32, 32));
            this.imagesMap.put(35, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/items/heart.png"))), 21, 21));
            this.imagesMap.put(36, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/items/key.png"))), 19, 10));
            this.imagesMap.put(37, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/items/chest.png"))), 21, 18));

            this.imagesMap.put(38, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/bullet/up.png"))), 123, 127));
            this.imagesMap.put(39, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/bullet/down.png"))), 123, 127));
            this.imagesMap.put(40, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/bullet/left.png"))), 127, 123));
            this.imagesMap.put(41, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/bullet/right.png"))), 127, 123));

            this.imagesMap.put(43, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/shop/heart.png"))), 21, 21));
            this.imagesMap.put(44, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/shop/thunder.png"))), 512, 512) );
            this.imagesMap.put(45, new ImmutableTriple<>((ImageIO.read(new File("src/main/resources/asset/shop/sword.png"))), 900, 900));
        }
        catch(IOException e){
            System.out.println("Error loading images. ");
        }
    }

    private void loadTileSetImages(int tileWidth, int tileHeight) {
        try{
            this.tileSet = ImageIO.read(new File("src/main/resources/asset/map/ArtTileSet.png"));
        }
        catch(IOException e){
            System.out.println("Error loading TileSet image. ");
        }
        var cols = tileSet.getWidth() / tileWidth;
        var rows = tileSet.getHeight() / tileHeight;
        for(int i=0; i < rows; i++){
            for(int j=0; j < cols; j++){
                this.tileImages.add(tileSet.getSubimage(j*tileWidth,i*tileHeight, tileWidth, tileHeight));
            }
        }
    }

    public BufferedImage getImage(final int numImage){
        return this.imagesMap.get(numImage).getLeft();
    }

    public int getWidth(final int numImage){
        return this.imagesMap.get(numImage).getMiddle();
    }

    public int getHeigth(final int numImage){
        return this.imagesMap.get(numImage).getRight();
    }

    public BufferedImage getTileImage(final int index){
        return this.tileImages.get(index);
    }
}
