package dimhol.logic.ai;

/**
 * This builder class is util to build Action with variable parameters.
 * @param <T>
 */
public final class ActionBuilder<T extends AbstractAction> {

    private final T action;

    /**
     * ActionBuilder constructor, set field a default values, and receive
     * as parameter a .class that define what that ActionBuilder return.
     * @param action is the .class
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public ActionBuilder(final Class<T> action) throws InstantiationException, IllegalAccessException {
        this.action = action.newInstance();
        this.action.aggroRay = Integer.MAX_VALUE;
        this.action.waitingTime = 0;
    }

    /**
     * Add aggro ray parameter.
     * @param aggroRay
     * @return ActionBuilder<T>
     */
    public ActionBuilder<T> addAggroRay(final int aggroRay) {
        this.action.aggroRay = aggroRay;
        return this;
    }

    /**
     * Add waiting time parameter.
     * @param waitingTime
     * @return ActionBuilder<T>
     */
    public ActionBuilder<T> addWaitingTime(final int waitingTime) {
        this.action.waitingTime = waitingTime;
        return this;
    }

    /**
     * Complete the building of a T type object.
     * @return T object
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public T build() throws InstantiationException, IllegalAccessException {
        return action;
    }

}
