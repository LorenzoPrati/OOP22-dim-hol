package dimhol.view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.yaml.snakeyaml.Yaml;

/**
 * A class for loading all the images necessary for animation.
 */
public final class ResourceLoader {
    private static final int NUM_PLAYER_SPRITES = 32;
    private static final String STRING_BULLET = "bullet";
     private static final String STRING_BOSS = "boss";
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;
    private final Map<Integer, ImmutableTriple<BufferedImage, Integer, Integer>> imagesMap = new HashMap<>();
    private final List<BufferedImage> tileImages = new ArrayList<>();
    private final Map<String, ArrayList<Integer>> dimensions = new HashMap<>();

    /**
     * Constructs a map containing images and gets sprite dimensions from a yaml file.
     * 
     */
    public ResourceLoader() {
        final InputStream input = ResourceLoader.class.getResourceAsStream("/config/spritesDimensions.yaml");
        final Yaml yaml = new Yaml();
        final Map<String, ArrayList<Integer>> mapLoaded = yaml.load(input);
        dimensions.putAll(mapLoaded);
        load();
        loadTileSetImages(TILE_WIDTH, TILE_HEIGHT);
        try {
            input.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } 
    } 

    private void updateMap(final int i, final String path, final String string) {
        try {
            this.imagesMap.put(i, new ImmutableTriple<>(ImageIO.read(this.getClass()
            .getResourceAsStream(path)), dimensions.get(string).get(0),
            dimensions.get(string).get(1)));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void load() {
        try {
            int i;
            for (i = 1; i <= NUM_PLAYER_SPRITES; i++) {
                updateMap(i, "/asset/warrior/" + i + ".png", "player");
            }
            updateMap(i++, "/asset/enemies/ghost-Sheet.png", "enemy");
            updateMap(i++, "/asset/enemies/enemiesAttack/enemyAttack.png", "enemyMeleeAttack");
            updateMap(i++, "/asset/items/coin2.png", "coin");
            updateMap(i++, "/asset/items/heart.png", "heart");
            updateMap(i++, "/asset/bullet/up.png", STRING_BULLET);
            updateMap(i++, "/asset/bullet/down.png", STRING_BULLET);
            updateMap(i++, "/asset/bullet/left.png", STRING_BULLET);
            updateMap(i++, "/asset/bullet/right.png", STRING_BULLET);
            updateMap(i++, "/asset/GateRing.png", "gate");
            updateMap(i++, "/asset/shop/healthPotion.png", "shopHeart");
            updateMap(i++, "/asset/shop/thunder.png", "shopSpeed");
            updateMap(i++, "/asset/hud/font/logo/hudCoin.png", "hudCoin");
            updateMap(i++, "/asset/hud/font/logo/hudHeart.png", "hudHeart");
            updateMap(i++, "/asset/boss/walk.png", STRING_BOSS);
            updateMap(i++, "/asset/boss/attack.png", STRING_BOSS);
            updateMap(i++, "/asset/boss/damage.png", STRING_BOSS);
            updateMap(i++, "/asset/boss/death.png", STRING_BOSS);
            updateMap(i++, "/asset/boss/idle.png", STRING_BOSS);
            updateMap(i++, "/asset/bullet/fireball.png", "fireball");
            updateMap(i, "/asset/shopkeeper/DownIdle.png", "shopkeeper");
        } catch (IllegalStateException e) {
           throw new IllegalStateException(e);
        }
    }

    private void loadTileSetImages(final int tileWidth, final int tileHeight) {
        BufferedImage tileSet;
        try {
            tileSet = ImageIO.read(this.getClass().getResourceAsStream("/asset/map/tileset_complet.png"));
        } catch (IOException e) {
           throw new IllegalStateException(e);
        }
        final var cols = tileSet.getWidth() / tileWidth;
        final var rows = tileSet.getHeight() / tileHeight;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.tileImages.add(tileSet.getSubimage(j * tileWidth, i * tileHeight, tileWidth, tileHeight));
            }
        }
    }

    /**
     * Method for getting the necessary BufferedImage.
     * @param numImage of the wanted image.
     * @return a buffered image.
     */
    public BufferedImage getImage(final int numImage) {
        return this.imagesMap.get(numImage).getLeft();
    }

    /**
     * @param numImage
     * @return the width of the image corresponding to the number.
     */
    public int getWidth(final int numImage) {
        return this.imagesMap.get(numImage).getMiddle();
    }

    /**
     * @param numImage
     * @return the heigth of the image corresponding to the number.
     */
    public int getHeigth(final int numImage) {
        return this.imagesMap.get(numImage).getRight();
    }

    /**
     * @param index
     * @return the buffered image of the tile corresponding to the index passed.
     */
    public BufferedImage getTileImage(final int index) {
        return this.tileImages.get(index);
    }
}
