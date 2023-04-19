package Game.ABC;

import java.util.Arrays;

import Game.gameConstant.PlayerState;
import logic.input.Direction;

public abstract class BasicMoveABC {
    protected PlayerState playerAction;

    protected boolean attacking;
    protected float x, y;
    protected float playerSpeed; // text

    protected Direction direction;
    protected boolean[] dirMove = { false, false, false, false };

    protected static final boolean[] CHECK_DIR = { false, false, false, false };

    public abstract void updatePosition();

    public BasicMoveABC() {
        this.playerAction = PlayerState.IDLE;
        this.attacking = false;
        this.direction = Direction.NONE;
    }

    public BasicMoveABC(PlayerState playerAction, Direction direction, boolean attacking) {
        this.playerAction = playerAction;
        this.direction = direction;
        this.attacking = attacking;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setXY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    protected void setPlayerSpeed(float playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerAction = playerState;
    }

    public void setDirection(Direction direction, boolean isMoving) {
        this.direction = direction;
        this.dirMove[direction.index()] = isMoving;

        if (Arrays.equals(this.dirMove, CHECK_DIR)) {
            this.direction = Direction.NONE;
        }
    }

}
