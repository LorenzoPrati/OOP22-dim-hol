package it.unibo.dimhol.view;

public class GraphicInfo {
    private final String type;
    private final String state;
    private final int index;
    private final double x;
    private final double y;

    public GraphicInfo(final String type, final String state, final int index, final double x, final double y){
        this.type = type;
        this.state = state;
        this.index = index;
        this.x = x;
        this.y = y;
    }
    
    public String getType() {
        return this.type;
    }

    public String getState() {
        return this.state;
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
    
}
