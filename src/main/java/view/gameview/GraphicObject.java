package view.gameview;

import java.awt.image.BufferedImage;
import model.common.ObjectType;
import model.common.State;
import view.gameview.animations.Animation;

/**
 * 
 */
public interface GraphicObject {

    ObjectType getType();

    void setX(double x, ObjectType t);

    void setY(double y, ObjectType t);

    int getX();

    int getY();

    BufferedImage getImage();

    boolean changedState(State newState);

    void setState(State newState);

    void setAnimation(Animation a);

}
