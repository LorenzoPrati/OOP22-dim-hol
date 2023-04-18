package it.unibo.dimhol.view;

import java.awt.image.BufferedImage;

public class PlayerAnimation {
    private static int PLAYER_SPRITE_WIDTH = 48;
    private static int PLAYER_SPRITE_HEIGHT = 48;
    private final String state;
    private final int index;
    private final ResourceLoader loader;

    public PlayerAnimation(String state, int index, ResourceLoader loader) {
        this.state = state;
        this.index = index; 
        this.loader = loader;
    }

    public BufferedImage getImageToDraw() {
        var imageToCut = loader.getPlayerImage(state);
        return imageToCut.getSubimage(index*PLAYER_SPRITE_WIDTH, 0*PLAYER_SPRITE_HEIGHT, 
            PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT);
    }

}
