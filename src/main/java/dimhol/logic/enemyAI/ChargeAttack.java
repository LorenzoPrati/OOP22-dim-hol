/* ChargeAttack.java */

package dimhol.logic.enemyAI;

import dimhol.events.AddEntityEvent;
import dimhol.events.WorldEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * It is a strategy that performs a charge attack towards the player.
 */
public final class ChargeAttack extends AbstractAction {

    private final double chargeSpeed;
    private final int chargeAttackDamage;

    /**
     * ChargeAttack constructor.
     * @param chargeSpeed the speed of the charge attack
     * @param chargeAttackRay the damage inflicted by the charge attack
     */
    public ChargeAttack(final double chargeSpeed, final int chargeAttackDamage,final int chargeAttackRay, final int chargeAttackWaitingTime) {
        this.chargeSpeed = chargeSpeed;
        this.chargeAttackDamage = chargeAttackDamage;
        setAggroRay(chargeAttackRay);
        setWaitingTime(chargeAttackWaitingTime);
    }

    @Override
    public Optional<List<WorldEvent>> execute() {
        getMovComp().setEnabled(true);
        getMovComp().setSpeed(chargeSpeed);
        var direction = AttackUtil.getPlayerDirection(getPlayerCentralPos(), getEnemyCentralPos());
        getMovComp().setDir(direction);
        if (getAi().getCurrentTime() - getAi().getPrevTime() >= getWaitingTime()) {
            getAi().setPrevTime(getAi().getCurrentTime());
            return chargeAttack();
        }
        return Optional.empty();
    }

    private Optional<List<WorldEvent>> chargeAttack() {
        List<WorldEvent> events = new ArrayList<>();
        events.add(new AddEntityEvent(getAttackFactory().createChargeAttack(getEnemy())));
        return Optional.of(events);
    }
}
