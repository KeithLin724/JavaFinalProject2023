package logic.input;

/**
 * This is a Java enum called `Direction` that represents the four cardinal
 * directions (UP, DOWN, LEFT,
 * RIGHT) and a NONE direction. Each direction has a `cmd` string and an `index`
 * integer associated
 * with it. The `isMoving()` method returns true for all directions except NONE.
 * This enum can be used
 * to represent movement or orientation in a game or application.
 */
public enum Direction {
    UP("UP", 0),
    DOWN("DOWN", 1),
    LEFT("LEFT", 2),
    RIGHT("RIGHT", 3),

    NONE("NONE", -1);

    public final String cmd;

    public final int index;

    Direction(String cmdString, int index) {
        this.cmd = cmdString;
        this.index = index;
    }

    /**
     * The function checks if the object is moving by returning true if it is not
     * equal to NONE.
     * 
     * @return The method `isMoving()` returns a boolean value. It returns `true` if
     *         the object calling the
     *         method is not equal to `NONE`, and `false` otherwise.
     */
    public boolean isMoving() {
        return !this.equals(NONE);
    }

}
