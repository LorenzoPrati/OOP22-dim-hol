package dimhol.logic.player;

public abstract class AbstractPlayerState implements PlayerState{

    private final String desc;

    public AbstractPlayerState(String desc) {
        this.desc = desc;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

}
