package dimhol;

import java.util.List;
import org.junit.jupiter.api.Test;
import dimhol.components.CoinPocketComponent;
import dimhol.components.HealthComponent;
import dimhol.components.ItemComponent;
import dimhol.components.PlayerComponent;
import dimhol.entity.Entity;
import dimhol.entity.factories.GenericFactory;
import dimhol.entity.factories.ItemFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests items' effects.
 */
class ItemTest {
    Entity player;
    Entity heartItem;
    Entity coinItem;

    ItemTest(){
        final var genericFactory = new GenericFactory();
        final var itemFactory = new ItemFactory();
        this.player = genericFactory.createPlayer(0, 0);
        this.heartItem = itemFactory.createHeart(1, 0);
        this.coinItem = itemFactory.createCoin(0, 1);
    }

    @Test
    void testHeartEffect(){
        var healthComp = (HealthComponent) player.getComponent(HealthComponent.class);
        var itemComp = (ItemComponent) heartItem.getComponent(ItemComponent.class);
        /*case: effect can't be applied */
        itemComp.applyEffect(player, List.of(PlayerComponent.class));
        assertEquals(healthComp.getCurrentHealth(), healthComp.getMaxHealth());
        /*case: effect can be applied */
        healthComp.setCurrentHealth(healthComp.getCurrentHealth() - 1); 
        itemComp.applyEffect(player, List.of(PlayerComponent.class));
        assertEquals(healthComp.getCurrentHealth(), healthComp.getMaxHealth());
    }

    @Test
    void testCoinEffect(){
        var pocketComp = (CoinPocketComponent) player.getComponent(CoinPocketComponent.class);
        var itemComp = (ItemComponent) coinItem.getComponent(ItemComponent.class);
        var coinAmountBeforeEffect = pocketComp.getCurrentAmount();
        itemComp.applyEffect(player, List.of(PlayerComponent.class));
        assertEquals(pocketComp.getCurrentAmount(), coinAmountBeforeEffect + 1);
    }
}
