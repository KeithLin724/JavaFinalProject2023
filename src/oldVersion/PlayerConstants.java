package oldVersion;

public class PlayerConstants {
    public static final int IDLE = 0;
    public static final int MOVING = 1;
    public static final int JUMP = 2;
    public static final int ATTACKING = 3;
    public static final int HIT = 4;

    public static int GetAnimationFrameNumbs(int playerAction) {
        switch (playerAction) {
            case IDLE:
                return 6;
            case ATTACKING:
                return 3;
            case MOVING:
            case HIT:
            case JUMP:
                return 2;

            default:
                return 1;
        }
    }
}
