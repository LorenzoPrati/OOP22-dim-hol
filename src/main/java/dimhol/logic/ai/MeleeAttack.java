package dimhol.logic.ai;

import dimhol.components.AiComponent;
import dimhol.components.MovementComponent;
import dimhol.entity.factories.AttackFactory;
import dimhol.events.AddEntityEvent;
import dimhol.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class MeleeAttack extends AbstractAction {

    public MeleeAttack(int meleeReloadTime) {
        setWaitingTime(meleeReloadTime);
    }

    /**
     * This method, which extends that of the abstract class, is responsible for resetting the aggroRay field so that
     * the enemy activates its close-range attack strategy with a near certainty of hitting it.
     * @return if the strategy is executable
     */
    public boolean canExecute() {
        setAggroRay(AttackUtil.getMeleeRay(getEnemyPos().getPos(), getEnemyCentralPos(),
                getPlayerPos().getPos(), getPlayerCentralPos()));
        return super.canExecute();
    }

    @Override
    public Optional<List<Event>> execute() {
        System.out.println("melee...");
        var movComp = (MovementComponent) getEnemy().getComponent(MovementComponent.class);
        var aiComp = (AiComponent) getEnemy().getComponent(AiComponent.class);
        movComp.setEnabled(false);
        aiComp.setPrevTime(aiComp.getCurrentTime());
        return meleeAttack();
    }

    private Optional<List<Event>> meleeAttack() {
        List<Event> attacks = new ArrayList<>();
        var dir = AttackUtil.getPlayerDirection(getPlayerCentralPos(), getEnemyCentralPos());
        var pos = AttackUtil.getAttackPos(dir, getEnemyCentralPos(), getEnemyBody().getBodyShape(),
                AttackFactory.MELEE_WIDTH, AttackFactory.MELEE_HEIGHT);

        attacks.add(new AddEntityEvent(getAttackFactory().createMeleeAttack(pos, getEnemy())));
        return Optional.of(attacks);
    }
}
