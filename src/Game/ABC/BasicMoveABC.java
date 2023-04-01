package Game.ABC;

import Game.gameConstant.PlayerState;

public abstract class BasicMoveABC {
    protected PlayerState playerAction;
    protected boolean moving, attacking;
    protected boolean up, down, right, left;

    public BasicMoveABC() {
        this.playerAction = PlayerState.IDLE;
        this.moving = false;
        this.attacking = false;
    }

    public BasicMoveABC(PlayerState playerAction, boolean moving, boolean attacking) {
        this.playerAction = playerAction;
        this.moving = moving;
        this.attacking = attacking;
    }
}
