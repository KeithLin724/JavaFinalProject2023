package Game.state;

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
    IDLE(0, 5, "IDLE_"),
    MOVING(1, 3, "MOVE_"),
    JUMP(2, 7, "JUMP_"),
    FALLING(3, 7, "FALL_"),
    ATTACKING(4, 3, "ATTACK_"),
    HIT(5, 5, "HIT_"),
    DEAD(6, 5, "DEAD_");

    public final int saveArrayIndex;
    public final int frameNumber;
    public final String imageString;

    public static final PlayerState[] ALL_PLAYER_STATES = { IDLE, MOVING, JUMP, FALLING, ATTACKING, HIT, DEAD };

    PlayerState(int number, int frameNumber, String imageString) {
        this.saveArrayIndex = number;
        this.frameNumber = frameNumber;
        this.imageString = imageString;
    }

}
