package view.gameview.animations;

import java.util.Iterator;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
 * An iterator of BufferedImages.
 */
public class ImagesIterator implements Iterator<BufferedImage> {
    private final int width;
    private final int height;
    private boolean stoppableAnimation;
    private int currentSpriteIndex = 0; 
    private int numSprites;
    private BufferedImage convertedImage;
    private BufferedImage imageToIterate;

    public ImagesIterator(final BufferedImage imageToIterate, final boolean stoppable, final int width, final int height){
        this.width = width;
        this.height = height;
        this.imageToIterate = imageToIterate;
        this.stoppableAnimation = stoppable;
        this.numSprites = imageToIterate.getWidth()/width; 
    }

    @Override
    public boolean hasNext() {
        return !stoppableAnimation ? true : this.currentSpriteIndex == this.numSprites - 1 ? false : true;
    }

    @Override
    public BufferedImage next() {
        if(this.currentSpriteIndex == this.numSprites - 1){
            this.currentSpriteIndex = 0;
        }
        return convert(this.imageToIterate.getSubimage(this.currentSpriteIndex * this.width, this.height, this.width, this.height));
    }

    private BufferedImage convert(BufferedImage subImage){
        this.convertedImage = new BufferedImage( subImage.getWidth(), subImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = this.convertedImage.createGraphics();
        g2.drawImage(subImage, 0, 0, null);
        g2.dispose();
        return this.convertedImage;
    }
}