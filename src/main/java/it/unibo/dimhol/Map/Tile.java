package it.unibo.dimhol.Map;
public class Tile {
    //TODO: private List<Effect> effects;

    private boolean collidable;

    public Tile(boolean collidable) {
        this.collidable = collidable;
    }

    public boolean isCollidable() {
        return collidable;
    }

}
