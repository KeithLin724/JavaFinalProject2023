package Game.ABC;

import Game.gameConstant.PlayerState;

public abstract class BasicMoveABC {
    protected PlayerState playerAction;
    protected boolean moving, attacking;
    protected float x, y;
    protected boolean up, down, right, left;

    protected float playerSpeed; // text

    public BasicMoveABC() {
        this.playerAction = PlayerState.IDLE;
        this.moving = false;
        this.attacking = false;
    }

    protected void setPlayerSpeed(float playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public BasicMoveABC(PlayerState playerAction, boolean moving, boolean attacking) {
        this.playerAction = playerAction;
        this.moving = moving;
        this.attacking = attacking;
    }

    public abstract void updatePosition();
}
