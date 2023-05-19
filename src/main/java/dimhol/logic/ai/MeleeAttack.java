package dimhol.logic.ai;

import dimhol.entity.factories.EnemyAttackFactory;
import dimhol.events.AddEntityEvent;
import dimhol.events.WorldEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class MeleeAttack extends AbstractAction {

    public MeleeAttack(double meleeReloadTime) {
        setWaitingTime(meleeReloadTime);
    }

    /**
     * This method, which extends that of the abstract class, is responsible for resetting the aggroRay field so that
     * the enemy activates its close-range attack strategy with a near certainty of hitting it.
     * @return if the strategy is executable
     */
    public boolean canExecute() {
        var newAggro = AttackUtil.getMeleeRay(getEnemyPos().getPos(), getEnemyCentralPos(),
                getPlayerPos().getPos(), getPlayerCentralPos());
        setAggroRay(newAggro);
        return super.canExecute();
    }

    @Override
    public Optional<List<WorldEvent>> execute() {
        System.out.println("execute");
        getMovComp().setEnabled(false);
        if (getAi().getCurrentTime() - getAi().getPrevTime() >= getWaitingTime()) {
            getAi().setPrevTime(getAi().getCurrentTime());
            return meleeAttack();
        }
        return Optional.empty();
    }

    private Optional<List<WorldEvent>> meleeAttack() {
        List<WorldEvent> attacks = new ArrayList<>();
        var dir = AttackUtil.getPlayerDirection(getPlayerCentralPos(), getEnemyCentralPos());
        var pos = AttackUtil.getAttackPos(dir, getEnemyCentralPos(), getEnemyBody().getBodyShape(),
                EnemyAttackFactory.ENEMY_MELEE_WIDTH, EnemyAttackFactory.ENEMY_MELEE_HEIGHT);

        attacks.add(new AddEntityEvent(getAttackFactory().createEnemyMeleeAttack(pos, getEnemy())));
        return Optional.of(attacks);
    }
}
