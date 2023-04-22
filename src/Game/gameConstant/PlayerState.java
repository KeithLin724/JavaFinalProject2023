package Game.gameConstant;

/**
 * This is an enum class named `PlayerState` that defines the different states a
 * player can be in a
 * game. Each state is assigned a number and has a method
 * `getAnimationFrameNumbs()` that returns the
 * number of animation frames associated with that state. The constructor
 * initializes the `num` field
 * with the assigned number for each state. The `switch` statement in the
 * `getAnimationFrameNumbs()`
 * method returns the appropriate number of animation frames based on the
 * current state.
 */
public enum PlayerState {
    IDLE(0),
    MOVING(1),
    JUMP(2),
    ATTACKING(3),
    HIT(4),
    FALLING(5);

    public final int num;

    PlayerState(int number) {
        this.num = number;
    }

    public int getAnimationFrameNumbs() {
        switch (this) {
            case IDLE:
                return 6;
            case ATTACKING:
                return 3;
            case MOVING:
            case HIT:
            case JUMP:
            case FALLING:
                return 2;

            default:
                return 1;
        }
    }
}
