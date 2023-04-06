package Game.gameConstant;

public enum PlayerState {
    IDLE(0),
    MOVING(1),
    JUMP(2),
    ATTACKING(3),
    HIT(4);

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
                return 2;

            default:
                return 1;
        }
    }
}
