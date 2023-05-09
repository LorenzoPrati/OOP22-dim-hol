package dimhol.logic.ai;

/**
 * This builder class is util to build Action with variable parameters.
 * @param <T>
 */
public class ActionBuilder<T extends AbstractAction> {

    private final T action;

    public ActionBuilder(Class<T> action) throws InstantiationException, IllegalAccessException {
        this.action = action.newInstance();
        this.action.aggroRay = Integer.MAX_VALUE;
        this.action.waitingTime = 0;
    }

    public ActionBuilder<? extends AbstractAction> addAggroRay(final int aggroRay) {
        this.action.aggroRay = aggroRay;
        return this;
    }

    public ActionBuilder<? extends AbstractAction> addWaitingTime(final int waitingTime) {
        this.action.waitingTime = waitingTime;
        return this;
    }

    public T build() throws InstantiationException, IllegalAccessException {
        return action;
    }

}
