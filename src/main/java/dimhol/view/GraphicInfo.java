package dimhol.view;

import org.locationtech.jts.math.Vector2D;

public class GraphicInfo {
    private final int numImage;
    private final int index;
    private Vector2D position;
    private final double w;
    private final double h;

    public GraphicInfo(final int index, final int numImage, Vector2D position, final double w, final double h){
        this.index = index;
        this.numImage = numImage;
        this.position = position;
        this.w = w;
        this.h = h;
    }
    
    public int getIndex() {
        return this.index;
    }

    public double getX() {
        return this.position.getX();
    }

    public double getY() {
        return this.position.getY();
    }

    public int getNumImage() {
        return this.numImage;
    }

    public double getW() {
        return this.w;
    }

    public double getH() {
        return this.h;
    }
}
