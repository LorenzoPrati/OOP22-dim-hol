//package dimhol.gamelevels.bossactions;
//
//import dimhol.entity.factories.EnemyAttackFactory;
//import dimhol.events.AddEntityEvent;
//import dimhol.events.WorldEvent;
//import dimhol.logic.enemyAI.AbstractAction;
//import dimhol.logic.enemyAI.AttackUtil;
//import dimhol.systems.CollisionSystem;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public final class AreaOfAttack extends AbstractAction {
//    private double areaRadius;
//    private double damage;
//    private double areaEffectsAttackReloadTime = 5.0;
//    private CollisionSystem collisionSystem;
//
//    public AreaOfAttack(double areaRadius, double damage) {
//        setAggroRay(areaRadius);
//        setWaitingTime(areaEffectsAttackReloadTime);
//    }
//
//    @Override
//    public Optional<List<WorldEvent>> execute() {
//        System.out.println("Performing attack area");
//        getMovComp().setEnabled(false);
//        if (getAi().getCurrentTime() - getAi().getPrevTime() >= getWaitingTime()) {
//            getAi().setPrevTime(getAi().getCurrentTime());
//            return findEntitiesInRange();
//        }
//        return Optional.empty();
//    }
//
//    private Optional<List<WorldEvent>> findEntitiesInRange() {
//        List<WorldEvent> inRangeEntities = new ArrayList<>();
//        var dir = AttackUtil.getPlayerDirection(getPlayerCentralPos(), getEnemyCentralPos());
//        var pos = AttackUtil.getAttackPos(dir, getEnemyCentralPos(), getEnemyBody().getBodyShape(),
//                EnemyAttackFactory.BOSS_AREA_ATTACK_WIDTH, EnemyAttackFactory.BOSS_AREA_ATTACK_HEIGHT);
//
//        inRangeEntities.add(new AddEntityEvent(getAttackFactory().createBossAreaAttack(pos, getEnemy())));
//        return Optional.of(inRangeEntities);
//    }
//}
