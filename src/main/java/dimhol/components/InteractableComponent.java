package dimhol.components;

import java.util.Optional;
import java.util.function.BiConsumer;
import dimhol.core.World;
import dimhol.entity.Entity;

public class InteractableComponent implements Component {
    private BiConsumer<Entity,World> effect;
    private Optional<Integer> price;

    public InteractableComponent(BiConsumer<Entity,World> effect,Optional<Integer> price) {
        this.effect = effect;
        this.price = price;
    }

    public void applyEffect(Entity entity, World world){
        this.effect.accept(entity, world);
    } 
    
    public int getPrice() {
        return this.price.get();
    }

    public boolean hasPriceToPay(){
        return this.price != null;
    }

}