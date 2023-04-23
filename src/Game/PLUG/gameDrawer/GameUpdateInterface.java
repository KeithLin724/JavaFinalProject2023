package Game.PLUG.gameDrawer;

/**
 * This code defines a functional interface called `GameUpdateInterface`. A
 * functional interface is an
 * interface that has only one abstract method. In this case, the interface has
 * a single method called
 * `update()` that takes no parameters and returns no value. This interface can
 * be used to define a
 * callback function that can be passed as an argument to another method.
 */
@FunctionalInterface
public interface GameUpdateInterface {
    /**
     * The function "update" does not have any parameters and its purpose is not
     * specified.
     */
    public void update();
}
