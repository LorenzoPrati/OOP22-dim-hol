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
    private static final int NUM_BOSS_SPRITES = 5;
    private final Map<Integer,ImmutableTriple<BufferedImage,Integer,Integer>> imagesMap = new HashMap<>();
    private BufferedImage tileSet;
    private ArrayList<BufferedImage> tileImages = new ArrayList<>();

    public ResourceLoader(final int tileWidth, final int tileHeight){
        load();
        loadTileSetImages(tileWidth,tileHeight);
    }
    
    private void load(){
        try{
            int i;
            for(i=1; i<=NUM_PLAYER_SPRITES; i++){
                this.imagesMap.put(i,new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream(("/asset/warrior/" + i + ".png")))),48, 48));
            }
//            int j;
//            for (j = 46; j <= NUM_BOSS_SPRITES; j++) {
//                this.imagesMap.put(j, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/boss/" + j + ".png"))), 192, 128));
//
//            }
            this.imagesMap.put(33, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/enemies/ghost-Sheet.png"))), 32, 32));
            this.imagesMap.put(34, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/items/coin.png"))), 32, 32));
            this.imagesMap.put(35, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/items/heart.png"))), 21, 21));
            this.imagesMap.put(36, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/items/key.png"))), 19, 10));
            this.imagesMap.put(37, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/items/chest.png"))), 21, 18));

            this.imagesMap.put(38, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/bullet/up.png"))), 123, 127));
            this.imagesMap.put(39, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/bullet/down.png"))), 123, 127));
            this.imagesMap.put(40, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/bullet/left.png"))), 127, 123));
            this.imagesMap.put(41, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/bullet/right.png"))), 127, 123));

            this.imagesMap.put(43, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/shop/heart.png"))), 21, 21));
            this.imagesMap.put(44, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/shop/thunder.png"))), 512, 512) );
            this.imagesMap.put(45, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/shop/sword.png"))), 900, 900));
            this.imagesMap.put(46, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/boss/46.png"))), 192, 128));
            this.imagesMap.put(47, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/boss/47.png"))), 192, 128));
            this.imagesMap.put(48, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/boss/48.png"))), 192, 128));
            this.imagesMap.put(49, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/boss/49.png"))), 192, 128));
            this.imagesMap.put(50, new ImmutableTriple<>((ImageIO.read(this.getClass().getResourceAsStream("/asset/boss/50.png"))), 192, 128));

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
        var cols = tileSet.getWidth() / 32;
        var rows = tileSet.getHeight() / 32;
        for(int i=0; i < rows; i++){
            for(int j=0; j < cols; j++){
                this.tileImages.add(tileSet.getSubimage(j*32,i*32, 32, 32));
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
