package dimhol.core;

import org.locationtech.jts.math.Vector2D;

import java.util.Optional;
import java.util.Vector;

/**
 * An interface to model input.
 */
public interface Input {

    boolean isInteracting();

    boolean isMoving();

    boolean isMeele();

    boolean isShooting();

    boolean isNormalMeele();

    boolean isSpecialMeele();

    boolean isChargingFireball();

    Optional<Vector2D> getDirection();

    void setUp(boolean up);

    void setDown(boolean down);

    void setLeft(boolean left);

    void setRight(boolean right);

    void setShoot(boolean shoot);

    void setNormalMeele(boolean normalMeele);

    void setSpecialMeele(boolean specialMeele);

    void setInteract(boolean interact);

    void setChargeFireball(boolean chargeFireball);

}
