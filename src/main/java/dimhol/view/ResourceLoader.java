package dimhol.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.tuple.ImmutableTriple;

public class ResourceLoader {
    private static final int NUM_PLAYER_SPRITES = 32; 
    private final Map<Integer,ImmutableTriple<BufferedImage,Integer,Integer>> imagesMap = new HashMap<>();
    private BufferedImage tileSet;
    private ArrayList<BufferedImage> tileImages = new ArrayList<>();
    

    public ResourceLoader(){
        load();
        loadTileSetImages(32,32);
    }
    
    private void load(){
        try{
            int i;
            for(i=1; i<=NUM_PLAYER_SPRITES; i++){
                this.imagesMap.put(i,new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream(("/asset/warrior/" + i + ".png")))),48, 48));
            }
            this.imagesMap.put(33, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/enemies/ghost-Sheet.png"))), 32, 32));
            this.imagesMap.put(34, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/items/coin.png"))), 32, 32));
            this.imagesMap.put(35, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/items/heart.png"))), 21, 21));
            this.imagesMap.put(36, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/items/key.png"))), 19, 10));
            this.imagesMap.put(37, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/items/chest.png"))), 21, 18));

            this.imagesMap.put(38, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/bullet/up.png"))), 123, 127));
            this.imagesMap.put(39, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/bullet/down.png"))), 123, 127));
            this.imagesMap.put(40, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/bullet/left.png"))), 127, 123));
            this.imagesMap.put(41, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/bullet/right.png"))), 127, 123));

            this.imagesMap.put(43, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/items/heart.png"))), 21, 21));
            this.imagesMap.put(44, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/shop/thunder.png"))), 512, 512) );
            this.imagesMap.put(45, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/shop/sword.png"))), 900, 900));
            this.imagesMap.put(46, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/hud/font/logo/hudCoin.png"))), 8, 10));
            this.imagesMap.put(47, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/hud/font/logo/hudHeart.png"))), 11, 11));
            this.imagesMap.put(48, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/boss/walk.png"))), 192, 128));
            this.imagesMap.put(49, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/boss/attack.png"))), 192, 128));
            this.imagesMap.put(50, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/boss/damage.png"))), 192, 128));
            this.imagesMap.put(51, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/boss/death.png"))), 192, 128));
            this.imagesMap.put(52, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/boss/idle.png"))), 192, 128));
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
        System.out.println(tileWidth + " " +  tileHeight);
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
