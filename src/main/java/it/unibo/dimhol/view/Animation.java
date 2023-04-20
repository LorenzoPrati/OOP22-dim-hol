package it.unibo.dimhol.view;

import java.awt.image.BufferedImage;

public class Animation {
    private final int index;
    private final int numImage;
    private final ResourceLoader loader;

    public Animation( int index, int numImage, ResourceLoader loader) {
        this.index = index; 
        this.numImage = numImage;
        this.loader = loader;
    }

    public BufferedImage getImageToDraw() {
        var imageToCut = loader.getImage(this.numImage);
        return imageToCut.getSubimage(index*48, 0*48, 48, 48); //TO FIX 
    }

}
