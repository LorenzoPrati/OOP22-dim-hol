package dimhol.entity.factories;

import dimhol.components.BodyComponent;
import dimhol.components.MovementComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.logic.util.DirectionUtil;
import org.locationtech.jts.math.Vector2D;

/**
 * This class has util to set the attack position.
 */
public class BaseAttackFactory extends BaseFactory {

    /**
     * This field is util to get mid of the width or height.
     */
    private static final double DIVISOR = 2;

    /**
     * This method positions the attack; whatever it is, next to the body of the entity exactly
     * centered with respect to its height or width (depending on the direction).
     * @param shooter
     * @param attackWidth
     * @param attackHeight
     * @return the attack position
     */
    public Vector2D getAttackPos(final Entity shooter, final double attackWidth, final double attackHeight) {

        double bulletX;
        double bulletY;
        final var posComp = (PositionComponent) shooter.getComponent(PositionComponent.class);
        final var bodyComp = (BodyComponent) shooter.getComponent(BodyComponent.class);
        final var shooterX = posComp.getPos().getX();
        final var shooterY = posComp.getPos().getY();
        final var shooterWidth = bodyComp.getBodyShape().getBoundingWidth();
        final var shooterHeight = bodyComp.getBodyShape().getBoundingHeight();
        final var centralShooterY = shooterY + (shooterHeight / DIVISOR);
        final var centralShooterX = shooterX + (shooterWidth / DIVISOR);
        final var halfAttackHeight = attackHeight / DIVISOR;
        final var halfAttackWidth = attackWidth / DIVISOR;

        switch (DirectionUtil.getStringFromVec(getDirection(shooter))) {
            case "right" -> {
                bulletX = shooterX + shooterWidth;
                bulletY = centralShooterY - halfAttackHeight;
            }
            case "left" -> {
                bulletX = shooterX - attackWidth;
                bulletY = centralShooterY - halfAttackHeight;
            }
            case "down" -> {
                bulletX = centralShooterX - halfAttackWidth;
                bulletY = shooterY + shooterHeight;
            }
            default -> { // up
                bulletX = centralShooterX - halfAttackWidth;
                bulletY = shooterY - attackHeight;
            }

        }
        return new Vector2D(bulletX, bulletY);
    }

    /**
     * Entity direction getter.
     * @param entity
     * @return the direction
     */
    public final Vector2D getDirection(final Entity entity) {
        final var movComp = (MovementComponent) entity.getComponent(MovementComponent.class);
        return movComp.getDir();
    }

}
