package dimhol.view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.yaml.snakeyaml.Yaml;

public final class ResourceLoader {
    private static final int NUM_PLAYER_SPRITES = 32; 
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;
    private final Map<Integer, ImmutableTriple<BufferedImage, Integer, Integer>> imagesMap = new HashMap<>();
    private BufferedImage tileSet;
    private ArrayList<BufferedImage> tileImages = new ArrayList<>();
    private Map<String, ArrayList<Integer>> dimensions = new HashMap<>();

    public ResourceLoader() {
        try {
            InputStream input = getClass().getResourceAsStream("/config/spritesDimensions.yaml");
            Yaml yaml = new Yaml();
            Map<String, ArrayList<Integer>> mapLoaded = yaml.load(input);
            dimensions.putAll(mapLoaded);
        } catch (Exception e) {
            System.out.println("File not found. ");
        }
        load();
        loadTileSetImages(TILE_WIDTH,TILE_HEIGHT);
    } 
    
    private void load() {
        try {
            int i;
            for (i=1; i <= NUM_PLAYER_SPRITES; i++) {
                this.imagesMap.put(i, new ImmutableTriple<>((ImageIO.read(this.getClass()
                .getResourceAsStream(("/asset/warrior/" + i + ".png")))), dimensions.get("player").get(0),
                 dimensions.get("player").get(1)));
            }
          
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()
                .getResourceAsStream("/asset/enemies/ghost-Sheet.png"))), dimensions.get("enemy").get(0),
                dimensions.get("enemy").get(1)));
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()
            .getResourceAsStream("/asset/enemies/enemiesAttack/enemyAttack.png"))), dimensions.get("enemyMeleeAttack").get(0),
            dimensions.get("enemyMeleeAttack").get(1)));
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()
            .getResourceAsStream("/asset/items/coin2.png"))), dimensions.get("coin").get(0), dimensions.get("coin").get(1)));
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()
            .getResourceAsStream("/asset/items/heart.png"))), dimensions.get("heart").get(0), dimensions.get("heart").get(1)));
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()
            .getResourceAsStream("/asset/bullet/up.png"))), dimensions.get("bullet").get(0), dimensions.get("bullet").get(1)));
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()
            .getResourceAsStream("/asset/bullet/down.png"))), dimensions.get("bullet").get(0), dimensions.get("bullet").get(1)));
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()
            .getResourceAsStream("/asset/bullet/left.png"))), dimensions.get("bullet").get(0), dimensions.get("bullet").get(1)));
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()
            .getResourceAsStream("/asset/bullet/right.png"))), dimensions.get("bullet").get(0), dimensions.get("bullet").get(1)));
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()   
            .getResourceAsStream("/asset/GateRing.png"))), dimensions.get("gate").get(0), dimensions.get("gate").get(1)));
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()
            .getResourceAsStream("/asset/shop/healthPotion.png"))), dimensions.get("shopHeart").get(0), 
            dimensions.get("shopHeart").get(1)));
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()
            .getResourceAsStream("/asset/shop/thunder.png"))), dimensions.get("shopSpeed").get(0), 
            dimensions.get("shopSpeed").get(1)));
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()
            .getResourceAsStream("/asset/hud/font/logo/hudCoin.png"))), dimensions.get("hudCoin").get(0), 
            dimensions.get("hudCoin").get(1)));
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()
            .getResourceAsStream("/asset/hud/font/logo/hudHeart.png"))), dimensions.get("hudHeart").get(0), 
            dimensions.get("hudHeart").get(1)));
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()
            .getResourceAsStream("/asset/boss/walk.png"))), dimensions.get("boss").get(0), 
            dimensions.get("boss").get(1)));
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()
            .getResourceAsStream("/asset/boss/attack.png"))), dimensions.get("boss").get(0), dimensions.get("boss").get(1)));
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()
            .getResourceAsStream("/asset/boss/damage.png"))), dimensions.get("boss").get(0), dimensions.get("boss").get(1)));
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()
            .getResourceAsStream("/asset/boss/death.png"))), dimensions.get("boss").get(0), dimensions.get("boss").get(1)));
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()
            .getResourceAsStream("/asset/boss/idle.png"))), dimensions.get("boss").get(0), dimensions.get("boss").get(1)));
            this.imagesMap.put(i++, new ImmutableTriple<>((ImageIO.read(this.getClass()
                    .getResourceAsStream("/asset/bullet/fireball.png"))), dimensions.get("fireball").get(0), dimensions.get("fireball").get(1)));
            this.imagesMap.put(i, new ImmutableTriple<>((ImageIO.read(this.getClass()
                    .getResourceAsStream("/asset/shopkeeper/DownIdle.png"))), dimensions.get("shopkeeper").get(0), dimensions.get("shopkeeper").get(1)));
        }
        catch (IOException e) {
            System.out.println("Error loading images. ");
        }
    }

    private void loadTileSetImages(final int tileWidth, final int tileHeight) {
        try {
            this.tileSet = ImageIO.read(this.getClass().getResourceAsStream("/asset/map/tileset_complet.png"));
        }
        catch (IOException e) {
            System.out.println("Error loading TileSet image. ");
        }
        System.out.println(tileWidth + " " +  tileHeight);
        var cols = tileSet.getWidth() / tileWidth;
        var rows = tileSet.getHeight() / tileHeight;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.tileImages.add(tileSet.getSubimage(j * tileWidth, i * tileHeight, tileWidth, tileHeight));
            }
        }
    }

    public BufferedImage getImage(final int numImage) {
        return this.imagesMap.get(numImage).getLeft();
    }

    public int getWidth(final int numImage) {
        return this.imagesMap.get(numImage).getMiddle();
    }

    public int getHeigth(final int numImage) {
        return this.imagesMap.get(numImage).getRight();
    }

    public BufferedImage getTileImage(final int index) {
        return this.tileImages.get(index);
    }
}
