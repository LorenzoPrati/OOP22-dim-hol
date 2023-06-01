//package dimhol.logic.ai;
//
//import dimhol.entity.Entity;
//import dimhol.entity.factories.EnemyAttackFactory;
//import dimhol.entity.factories.EnemyFactory;
//import dimhol.events.AddEntityEvent;
//import dimhol.events.WorldEvent;
//import dimhol.gamelevels.LevelManager;
//import dimhol.gamelevels.LevelManagerImpl;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public final class SummonMinions extends AbstractAction {
//
//    private static int NUM_MINS = 3;
//    private EnemyFactory enemyFactory;
//    private LevelManager levelManager;
//
////    private static final double MINS_SPEED = 1;
////    private static final double MINS_WIDTH = 0.5;
////    private static final double MINS_HEIGHT = 0.5;
////    private static final int MINS_HEALTH = 1;
//    private static final int MINS_RELOAD = 1;
//
//    public SummonMinions() {
//        this.enemyFactory = new EnemyFactory();
//        setWaitingTime(MINS_RELOAD);
//        this.levelManager = new LevelManagerImpl();
//    }
//
//    @Override
//    public Optional<List<WorldEvent>> execute() {
//        getMovComp().setEnabled(false);
//        if (getAi().getCurrentTime() - getAi().getPrevTime() >= getWaitingTime()) {
//            getAi().setPrevTime(getAi().getCurrentTime());
//            return summonMins();
//        }
//        return Optional.empty();
//    }
//
//    private Optional<List<WorldEvent>> summonMins() {
//        List<WorldEvent> summon = new ArrayList<>();
//        var dir = AttackUtil.getPlayerDirection(getPlayerCentralPos(), getEnemyCentralPos());
//        var pos = AttackUtil.getAttackPos(dir, getEnemyCentralPos(), getEnemyBody().getBodyShape(),
//                EnemyAttackFactory.ENEMY_MINS_WIDTH, EnemyAttackFactory.ENEMY_MINS_HEIGHT);
//        for (int i = 0; i < NUM_MINS; i++) {
//            System.out.println("Performing Summoning mins");
//            summon.add(new AddEntityEvent(getAttackFactory().summonerEntityMinions(pos, getEnemy())));
//        }
//        return Optional.of(summon);
//    }
//}
