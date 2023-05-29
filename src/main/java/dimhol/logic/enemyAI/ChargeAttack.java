//package dimhol.logic.ai;
//
//import dimhol.components.HealthComponent;
//import dimhol.entity.Entity;
//import dimhol.events.WorldEvent;
//import org.locationtech.jts.math.Vector2D;
//
//import java.util.List;
//import java.util.Optional;
//
//public final class ChargeAttack extends AbstractAction {
//    private double chargeSpeed;
//    private double damage;
//
//    public ChargeAttack(double chargeSpeed, int damage) {
//        this.chargeSpeed = chargeSpeed;
//        this.damage = damage;
//    }
//
//    @Override
//    public Optional<List<WorldEvent>> execute() {
//        System.out.println("Performing charge attack");
//        // Logic for charge attack
//        // Calculate the target position to charge towards (e.g., player's position)
//        // ...
//        // Example: calculate the direction towards the player
//        Vector2D targetPosition = calculateTargetPosition();
//        // Move the Boss towards the target position with the charge speed
//        getMovementComponent().setMovementDirection(targetPosition.subtract(getPositionComponent().getPosition()).normalize().multiply(chargeSpeed));
//        return Optional.empty(); // No events generated during the charge attack
//    }
//}
