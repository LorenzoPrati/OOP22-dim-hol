package it.unibo.dimhol.view;

public class GraphicInfo {
    private final int numImage;
    private final int index;
    private final double x;
    private final double y;

    public GraphicInfo(final int index, final int numImage, final double x, final double y){
        this.index = index;
        this.numImage = numImage;
        this.x = x;
        this.y = y;
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

    
}
