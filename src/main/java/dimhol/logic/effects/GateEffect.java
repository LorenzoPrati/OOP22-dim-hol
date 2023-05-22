package dimhol.logic.effects;

import dimhol.components.PlayerComponent;
import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.events.ChangeRoomEvent;

public class GateEffect implements Effect {
    protected World worldToNotify;
    public GateEffect(World w){
        this.worldToNotify = w;
    }

    @Override
    public boolean canUseOn(Entity entity) {
        return entity.hasComponent(PlayerComponent.class);
    }

    @Override
    public void applyOn(Entity entity) {
        this.worldToNotify.notifyEvent(new ChangeRoomEvent());
    }
    
}
