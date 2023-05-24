package dimhol.entity.factories;

import dimhol.components.BodyComponent;
import dimhol.components.MovementComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.logic.collision.BodyShape;
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
        var movComp = (MovementComponent) shooter.getComponent(MovementComponent.class);
        var posComp = (PositionComponent) shooter.getComponent(PositionComponent.class);
        var bodyComp = (BodyComponent) shooter.getComponent(BodyComponent.class);
        var shooterCentralPos = this.getCentralPosition(posComp.getPos(), bodyComp.getBodyShape());

        switch (DirectionUtil.getStringFromVec(movComp.getDir())) {
            case "right" -> {
                bulletX = shooterCentralPos.getX() + (bodyComp.getBodyShape().getBoundingWidth() / 2);
                bulletY = shooterCentralPos.getY() - (attackHeight / 2);
            }
            case "left" -> {
                bulletX = shooterCentralPos.getX() - (bodyComp.getBodyShape().getBoundingWidth() / 2) - attackHeight;
                bulletY = shooterCentralPos.getY() - (attackWidth / 2);
            }
            case "down" -> {
                bulletX = shooterCentralPos.getX() - (attackHeight / 2);
                bulletY = shooterCentralPos.getY() + (bodyComp.getBodyShape().getBoundingHeight() / 2);
            }
            default -> { // up
                bulletX = shooterCentralPos.getX() - (attackHeight / 2);
                bulletY = shooterCentralPos.getY() - (bodyComp.getBodyShape().getBoundingHeight() / 2) - attackWidth;
            }

        }

        return new Vector2D(bulletX, bulletY);
    }

    /**
     * This method returns an entity's central position.
     * @param entityPos entity position (top-left point)
     * @param entityBody entity body
     * @return central position
     */
    private Vector2D getCentralPosition(final Vector2D entityPos, final BodyShape entityBody) {
        return new Vector2D(entityPos.getX() + (entityBody.getBoundingWidth() / DIVISOR),
                entityPos.getY() + (entityBody.getBoundingHeight() / DIVISOR));
    }

    public Vector2D getDirection(final Entity entity) {
        var movComp = (MovementComponent) entity.getComponent(MovementComponent.class);
        return movComp.getDir();
    }

}
