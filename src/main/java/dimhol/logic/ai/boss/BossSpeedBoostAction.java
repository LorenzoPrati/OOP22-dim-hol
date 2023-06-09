package dimhol.logic.ai.boss;

import dimhol.components.HealthComponent;
import dimhol.events.WorldEvent;
import dimhol.logic.ai.AbstractAction;

import java.util.List;
import java.util.Optional;

/**
 * The speed boost action performed within a range of life.
 */
public class BossSpeedBoostAction extends AbstractAction {

    private static final double BOSS_SPEED_BOOST = 3;
    private static final double THRESHOLD_ACTIVATION = 8;
    private boolean used;

    public BossSpeedBoostAction() {
        used = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<WorldEvent>> execute() {
        used = true;
        getMovComp().setSpeed(getMovComp().getSpeed() + BOSS_SPEED_BOOST);
        return Optional.empty();
    }

    @Override
    public final boolean canExecute() {
        final var bossHealth = (HealthComponent) getEnemy().getComponent(HealthComponent.class);
        return bossHealth.getCurrentHealth() <= THRESHOLD_ACTIVATION && !used;
    }
}
