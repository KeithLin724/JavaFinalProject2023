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
    protected int[] dirMove = { 0, 0, 0, 0 };

    // protected static final boolean[] CHECK_DIR = { false, false, false, false };

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

    /**
     * This function sets the direction and movement status of a player object in a
     * game.
     * 
     * @param direction The direction the player is moving in. It is an enum type
     *                  called "Direction".
     * @param isMoving  A boolean value indicating whether the player is currently
     *                  moving in the
     *                  specified direction. If true, the player is moving; if
     *                  false, the player is not moving.
     */
    public void setDirection(Direction direction, boolean isMoving) {
        this.direction = direction;

        int index = direction.index(), isMovingNum = (isMoving ? 1 : 0);

        this.dirMove[index] = isMovingNum * (int) ((float) Math.pow(-1, index + 1) * this.playerSpeed);

        if (Arrays.stream(this.dirMove).allMatch(x -> x == 0)) {
            this.direction = Direction.NONE;
        }
    }

}
