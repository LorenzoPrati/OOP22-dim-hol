package dimhol.entity.factories;

import dimhol.components.BodyComponent;
import dimhol.components.MovementComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.logic.util.DirectionUtil;
import org.locationtech.jts.math.Vector2D;

public class AbstractAttackFactory extends AbstractFactory {

    private static final double DIVISOR = 2;

    /**
     * This method positions the attack; whatever it is, next to the body of the entity exactly
     * centered with respect to its height or width (depending on the direction).
     * @return the attack position
     */
    public Vector2D getAttackPos(final Entity shooter, final double attackWidth, final double attackHeight) {

        double bulletX;
        double bulletY;
        var posComp = (PositionComponent) shooter.getComponent(PositionComponent.class);
        var bodyComp = (BodyComponent) shooter.getComponent(BodyComponent.class);
        var shooterX = posComp.getPos().getX();
        var shooterY = posComp.getPos().getY();
        var shooterWidth = bodyComp.getBodyShape().getBoundingWidth();
        var shooterHeight = bodyComp.getBodyShape().getBoundingHeight();
        var centralShooterY = shooterY + (shooterHeight / DIVISOR);
        var centralShooterX = shooterX + (shooterWidth / DIVISOR);
        var halfAttackHeight = attackHeight / DIVISOR;
        var halfAttackWidth = attackWidth / DIVISOR;

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

    public Vector2D getDirection(final Entity entity) {
        var movComp = (MovementComponent) entity.getComponent(MovementComponent.class);
        return movComp.getDir();
    }

}
