//package dimhol.logic.ai.boss;
//
//import dimhol.events.AddEntityEvent;
//import dimhol.events.WorldEvent;
//import dimhol.logic.ai.AbstractAction;
//import dimhol.logic.ai.BehviourUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class HealRegenerationAction extends AbstractAction {
//
//    private final double bossHealRegeneration;
//
//    /**
//     * Constructs an BossMeleeAttackAction.
//     *
//     * @param HealAggro     the radius of the area in which the presence
//     *                            of an enemy (the player) activates this strategy
//     * @param bossHealRegeneration the damage caused by the boss's area attack
//     * @param bossHealReloadTime the time to reload the area attack
//     */
//    public HealRegenerationAction(final double HealAggro, final double bossHealRegeneration,
//                                  final double bossHealReloadTime) {
//        setAggroRay(HealAggro);
//        this.bossHealRegeneration = bossHealRegeneration;
//        setWaitingTime(bossHealReloadTime);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Optional<List<WorldEvent>> execute() {
//        getMovComp().setEnabled(false);
//        var direction = BehviourUtil.getPlayerDirection(getPlayerCentralPos(), getEnemyCentralPos());
//        getMovComp().setDir(direction);
//        if (getAi().getCurrentTime() - getAi().getPrevTime() >= getWaitingTime()) {
//            getAi().setPrevTime(getAi().getCurrentTime());
//            return performBossMeleeAttack();
//        }
//        return Optional.empty();
//    }
//
//    private Optional<List<WorldEvent>> performBossMeleeAttack() {
//        List<WorldEvent> events = new ArrayList<>();
//        events.add(new AddEntityEvent(getAttackFactory().createBossMeleeAttack(getEnemy())));
//        return Optional.of(events);
//    }
//}
