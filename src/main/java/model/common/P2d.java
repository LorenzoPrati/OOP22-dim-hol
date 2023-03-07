package model.common;

public class P2d {

    private double x;
    private double y;
    
    public P2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public P2d(P2d p) {
        this.x = p.x;
        this.y = p.y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}
