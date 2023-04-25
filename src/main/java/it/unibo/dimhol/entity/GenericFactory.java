package it.unibo.dimhol.entity;

import it.unibo.dimhol.ai.MathUtilities;
import it.unibo.dimhol.ai.RoutineFactory;
import it.unibo.dimhol.commons.shapes.RectBodyShape;
import it.unibo.dimhol.components.*;
import it.unibo.dimhol.effects.IncreaseCurrentHealthEffect;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.locationtech.jts.math.Vector2D;
import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.File;

/**
 * Implementation of a factory to create various entities.
 */
public class GenericFactory {

    private static final double BULLET_WIDTH = 10;
    private static final double BULLET_HEIGHT = 10;
    private static final double MELEE_WIDTH = 60;
    private static final double MELEE_HEIGHT = 60;
    private static final double PLAYER_SPEED = 6;
    private static final double ENEMY_SPEED = 3;
    private static final int W = 60;
    private static final int H = 60;
    private final Map<String,Map<String,ArrayList<Integer>>> map = new HashMap<>();

    public GenericFactory() {
        try{
            InputStream input = new FileInputStream(new File("src/main/java/it/unibo/dimhol/ConfigFile.yaml"));
            Yaml yaml = new Yaml();
            Map<String,Map<String,ArrayList<Integer>>> mapLoaded = yaml.load(input);
            map.putAll(mapLoaded);
        }
        catch(FileNotFoundException e){
            System.out.println("File not found. ");
        }
    }

    public Entity createPlayer(final double x, final double y) {
        return new EntityBuilder().add(new PlayerComponent())
                .add(new PositionComponent(new Vector2D(x,y), 0))
                .add(new MovementComponent(new Vector2D(0,0),PLAYER_SPEED, false))
                .add(new BodyComponent(new RectBodyShape(W,H), true))
                .add(new HealthComponent(10))
                .add(new AnimationComponent(map.get("player"),"walking up"))
                .build();
    }

    public Entity createHeart(final double x, final double y){
        return new EntityBuilder()
            .add(new PositionComponent(new Vector2D(x, y), 0))
            .add(new BodyComponent(new RectBodyShape(W,H), false))
            .add(new PickableComponent(new IncreaseCurrentHealthEffect(1)))
            .add(new AnimationComponent(this.map.get("heart"), "idle"))
            .build();
    }

    public Entity createZombieEnemy(final double x, final double y) {
        return new EntityBuilder()
                .add(new AiComponent(new RoutineFactory().createZombieRoutine()))
                .add(new PositionComponent(new Vector2D(x, y), 0))
                .add(new MovementComponent(new Vector2D(0,0), ENEMY_SPEED, true))
                .add(new BodyComponent(new RectBodyShape(W, H), true))
                .add(new AnimationComponent(map.get("enemy"),"idle"))
                .build();
    }

    public Entity createShooterEnemy(final double x, final double y) {
        return new EntityBuilder()
                .add(new AiComponent(new RoutineFactory().createShooterRoutine()))
                .add(new PositionComponent(new Vector2D(x, y), 0))
                .add(new MovementComponent(new Vector2D(0,0), ENEMY_SPEED, true))
                .add(new BodyComponent(new RectBodyShape(W, H), true))
                .add(new AnimationComponent(map.get("enemy"),"idle"))
                .build();
    }

    public Entity createBullet(final double dirX, final double dirY, final Entity entity) {
        PositionComponent entityPos = (PositionComponent) entity.getComponent(PositionComponent.class);
        BodyComponent entityBody = (BodyComponent) entity.getComponent(BodyComponent.class);
        return new EntityBuilder()
                .add(new PositionComponent(MathUtilities.setAttackPosition(dirX, dirY, entityPos.getPos(),
                        entityBody.getBodyShape(), BULLET_WIDTH, BULLET_HEIGHT), 0))
                .add(new MovementComponent(new Vector2D(dirX, dirY), 3, true))
                .add(new BodyComponent(new RectBodyShape(BULLET_WIDTH, BULLET_HEIGHT), false))
                .add(new AnimationComponent(map.get("coin"), "idle"))
                .build();
    }

    public Entity createMeleeAttack(final double dirX, final double dirY, final Entity entity) {
        PositionComponent entityPos = (PositionComponent) entity.getComponent(PositionComponent.class);
        BodyComponent entityBody = (BodyComponent) entity.getComponent(BodyComponent.class);
        return new EntityBuilder()
                .add(new PositionComponent(MathUtilities.setAttackPosition(dirX, dirY, entityPos.getPos(), entityBody.getBodyShape(), MELEE_WIDTH, MELEE_HEIGHT), 0))
                .add(new BodyComponent(new RectBodyShape(MELEE_WIDTH, MELEE_HEIGHT), false))
                .build();
    }

}
