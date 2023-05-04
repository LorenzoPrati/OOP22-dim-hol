package it.unibo.dimhol.view;

public class GraphicInfo {
    private final int numImage;
    private final int index;
    private final double x;
    private final double y;
    private final double w;
    private final double h;

    public GraphicInfo(final int index, final int numImage, final double x, final double y, final double w, final double h){
        this.index = index;
        this.numImage = numImage;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public int getIndex() {
        return this.index;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
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
