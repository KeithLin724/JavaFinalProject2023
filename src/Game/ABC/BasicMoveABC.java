package Game.ABC;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

// import Game.DataPass.PlayerHitBox;
import Game.gameBase.GamePoint;
import Game.gameConstant.PlayerState;
import logic.input.Direction;

import static base.BaseGameConstant.TILES_SIZE;

public abstract class BasicMoveABC {
    public static final int HIT_BOX_WIDTH = TILES_SIZE - 7;
    public static final int HIT_BOX_HEIGHT = TILES_SIZE - 7;

    protected PlayerState playerAction;
    protected boolean attacking;

    protected GamePoint point;
    protected float playerSpeed; // text

    protected Direction direction;
    protected int[] dirMove = { 0, 0, 0, 0 };

    protected Rectangle2D.Float hitBox;

    public abstract void updatePosition();

    public BasicMoveABC() {
        this.playerAction = PlayerState.IDLE;
        this.attacking = false;
        this.direction = Direction.NONE;
        this.point = new GamePoint();

        this.initHitBox();
    }

    public BasicMoveABC(PlayerState playerAction, GamePoint point, Direction direction, boolean attacking) {
        this.playerAction = playerAction;
        this.direction = direction;
        this.attacking = attacking;
        this.point = point;

        this.initHitBox();

    }

    ////////////////////

    protected void initHitBox(GamePoint point, int width, int height) {
        this.hitBox = new Rectangle2D.Float((int) point.x, (int) point.y, width, height);
    }

    protected void initHitBox() {
        this.hitBox = new Rectangle2D.Float(point.x, point.y, HIT_BOX_WIDTH, HIT_BOX_HEIGHT);
    }

    // public void setPlayerHitBox(PlayerHitBox playerHitBox) {
    // this.hitBox = new Rectangle2D.Float((int) playerHitBox.x(), (int)
    // playerHitBox.y(),
    // playerHitBox.width(),
    // playerHitBox.height());
    // }

    protected void updateHitBox() {
        this.hitBox.x = point.x;
        this.hitBox.y = point.y;
    }

    public Rectangle2D.Float getHitBox() {
        return this.hitBox;
    }

    protected void drawHitBox(Graphics g) {
        // for debugging
        g.setColor(Color.PINK);
        g.drawRect((int) hitBox.x, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }

    ////////////////////

    public void setX(float x) {
        this.point.x = x;
    }

    public void setY(float y) {
        this.point.y = y;
    }

    public void setXY(float x, float y) {
        this.point.setAll(x, y);
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

    public void stopDirection() {
        this.playerAction = PlayerState.IDLE;
        this.direction = Direction.NONE;
        this.dirMove = new int[] { 0, 0, 0, 0 };
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
