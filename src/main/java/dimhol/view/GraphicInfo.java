package dimhol.view;

import org.locationtech.jts.math.Vector2D;

/**
 * A class with cointains useful information to draw the game entities.
 */
public final class GraphicInfo {
    private final int numImage;
    private final int index;
    private final Vector2D position;
    private final double w;
    private final double h;

    /**
     * Each GraphicInfo should contain the following information. 
     * @param index of the sprite to draw.
     * @param numImage
     * @param position
     * @param w
     * @param h
     */
    public GraphicInfo(final int index, final int numImage, final Vector2D position, final double w, final double h) {
        this.index = index;
        this.numImage = numImage;
        this.position = position;
        this.w = w;
        this.h = h;
    }

    /**
     * @return the index of the sprite to draw.
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * @return the x component of the position.
     */
    public double getX() {
        return this.position.getX();
    }

    /**
     * @return the y component of the position.
     */
    public double getY() {
        return this.position.getY();
    }

    /**
     * @return the image number.
     */
    public int getNumImage() {
        return this.numImage;
    }

    /**
     * @return the width.
     */
    public double getW() {
        return this.w;
    }

    /**
     * @return the heigth.
     */
    public double getH() {
        return this.h;
    }
}
