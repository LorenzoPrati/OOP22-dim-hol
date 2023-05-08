package dimhol.logic.ai;

import java.util.function.BiFunction;

public class ActionBuilder<T extends AbstractAction> {

    private int aggroRay;
    private int waitingTime;
    private final BiFunction<Integer, Integer, T> construcor;

    public ActionBuilder(BiFunction<Integer, Integer, T> construcor) {
        this.construcor = construcor;
        this.aggroRay = 0;
        this.waitingTime = 0;
    }

    public ActionBuilder<T> addAggroRay(final int aggroRay) {
        this.aggroRay = aggroRay;
        return this;
    }

    public ActionBuilder<T> addWaitingTime(final int waitingTime) {
        this.waitingTime = waitingTime;
        return this;
    }

    public T build() {
        return construcor.apply(aggroRay, waitingTime);
    }
}
