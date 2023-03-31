package Game.ABC;

import Game.gameConstant.PlayerConstants;

public abstract class BasicMoveABC {
    protected int playerAction;
    protected boolean moving, attacking;
    protected boolean up, down, right, left;

    public BasicMoveABC() {
        this.playerAction = PlayerConstants.IDLE;
        this.moving = false;
        this.attacking = false;
    }

    public BasicMoveABC(int playerAction, boolean moving, boolean attacking) {
        this.playerAction = playerAction;
        this.moving = moving;
        this.attacking = attacking;
    }
}
