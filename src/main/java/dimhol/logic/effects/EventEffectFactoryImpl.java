package dimhol.logic.effects;

import java.util.Optional;
import dimhol.components.Component;
import dimhol.components.PlayerComponent;
import dimhol.entity.Entity;
import dimhol.events.ChangeRoomEvent;
import dimhol.events.WorldEvent;

public class EventEffectFactoryImpl implements EventEffectFactory {
    private Effect createEventEffect(final WorldEvent event, final Class<? extends Component> component){
        return new Effect() {

            @Override
            public boolean canUseOn(Entity entity) {
                return entity.hasComponent(component);
            }

            @Override
            public Optional<WorldEvent> applyOn(Entity entity) {
                return Optional.of(event);
            }
        };
    }

    public Effect gateEffect(){
        return createEventEffect(new ChangeRoomEvent(), PlayerComponent.class);
    }
}
