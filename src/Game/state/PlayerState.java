package Game.state;

import java.awt.Frame;

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
    IDLE(0, 6, "IDLE_"),
    MOVING(1, 2, "MOVE_"),
    JUMP(2, 2, "JUMP_"),
    ATTACKING(3, 3, "ATTACK_"),
    HIT(4, 2, "HIT_"),
    FALLING(5, 2, "FALLING_");

    public final int num;
    public final int frameNumber;
    public final String imageString;

    public static final PlayerState[] ALL_PLAYER_STATES = { IDLE, MOVING, JUMP, ATTACKING, FALLING };

    PlayerState(int number, int frameNumber, String imageString) {
        this.num = number;
        this.frameNumber = frameNumber;
        this.imageString = imageString;
    }

}
