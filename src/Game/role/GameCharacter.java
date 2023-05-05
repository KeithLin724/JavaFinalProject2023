package Game.role;

import java.awt.Graphics;
import java.io.IOException;

import java.util.logging.Logger;

import Game.DataPass.AniData;
import Game.DataPass.GamePlayerSpeedData;
import Game.DataPass.ImageScaleData;
// import Game.DataPass.PlayerHitBox;
import Game.Loader.ImageNamePath;
import Game.PLUG.gameDrawer.GameAnimatedDrawer;
import Game.PLUG.gameDrawer.GameRenderOffsetPass;
import Game.gameBackground.GameLevel;
import Game.gameBase.GamePoint;
import Game.role.ABC.GameCharacterABC;
import Game.state.GameCharacterState;

import static base.BaseGameConstant.TILES_SIZE;
import static logic.Controller.GameHelpMethods.canMoveHere;
import static logic.Controller.GameHelpMethods.*;

// for put the game character skin
public class GameCharacter extends GameCharacterABC
        implements GameAnimatedDrawer, GameRenderOffsetPass {

    private static final Logger LOGGER = Logger.getLogger(GameCharacter.class.getName());

    private int[][] levelData;
    private GameLevel level;

    private float drawXOffset;

    public GameCharacter() {
        super();
    }

    public GameCharacter(AniData aid, ImageScaleData isd, GamePlayerSpeedData gps) {
        super(aid, isd, gps);
    }

    public void initWithPoint_testing(float x, float y) {
        this.setXY(x, y);
        this.setAnimationImage();
    }

    public void init(float x, float y) {
        this.setXY(x, y);
        // this.setAnimationImage();
    }

    @Override
    public void passOffset(float offset) {
        this.drawXOffset = offset;
    }

    private void updateXPos() {

        int xSpeed = this.dirMove[2] + this.dirMove[3];

        GamePoint nextPoint = new GamePoint(xSpeed, 0);

        if (!canMoveHere(GamePoint.add(point, nextPoint), HIT_BOX_WIDTH, HIT_BOX_HEIGHT, this.level)) {
            return;
        }

        this.point.addToX(xSpeed);
    }

    // check up and down
    private void updateYPos() {

        if (!this.inAir) {
            return;
        }

        GamePoint nextPoint = new GamePoint(0, this.airSpeed);

        if (canMoveHere(GamePoint.add(point, nextPoint), HIT_BOX_WIDTH, HIT_BOX_HEIGHT, this.level)) {
            this.point.addToY(this.airSpeed);

            this.airSpeed += this.gravity;
            return;
        }

        if (airSpeed > 0) { // FALLING
            this.resetInAir();
        } else {
            airSpeed = fallSpeedAfterCollision;
        }

    }

    @Override
    public void updatePosition() {
        // moving
        if (this.gameCharacterState == GameCharacterState.JUMP) {
            jump();
        }
        // check left right
        if (this.dirMove[2] == 0 && this.dirMove[3] == 0 && !this.inAir) {
            return;
        }

        // if (this.dirMove[2] + this.dirMove[3] == 0) {
        // this.setPlayerState(PlayerState.IDLE);
        // }

        // check is it in air
        if (!this.inAir && !isOnTheFloor(this.point, HIT_BOX_WIDTH, HIT_BOX_HEIGHT, this.level)) {
            this.inAir = true;
        }

        updateYPos();
        updateXPos();

    }

    private void jump() {
        if (inAir) {
            return;
        }

        this.inAir = true;
        this.airSpeed = this.jumpSpeed;
    }

    @Override
    public void render(Graphics g) {
        var nowImage = this.getAnimationImage(this.gameCharacterState, this.aniIndex);
        var fromPoint = this.point.toIntPoint();

        g.drawImage(nowImage,
                (int) (fromPoint.x - drawXOffset), fromPoint.y,
                TILES_SIZE, TILES_SIZE,
                null);

        // this.drawHitBox(g, drawXOffset);

    }

    @Override
    public void setAnimationImage() {
        try {
            this.setAnimationImage(ImageNamePath.PLAYER_MAIN_CHARACTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAnimationState() {

        GameCharacterState startAni = gameCharacterState;

        if (!gameCharacterState.equals(GameCharacterState.JUMP)) {
            gameCharacterState = (this.direction.isMoving() && (this.dirMove[2] + this.dirMove[3] != 0)
                    ? GameCharacterState.MOVING
                    : GameCharacterState.IDLE);
        }

        if (this.inAir) {
            // System.out.println(this.playerAction);
            this.gameCharacterState = (airSpeed < 0 ? GameCharacterState.JUMP : GameCharacterState.FALLING);
            // if (airSpeed < 0) {
            // this.playerAction = PlayerState.JUMP;
            // } else {
            // this.playerAction = PlayerState.FALLING;
            // }
        }

        if (attacking) {
            aniSpeed = 20;
            gameCharacterState = GameCharacterState.ATTACKING;
        }

        if (startAni != gameCharacterState) {
            this.resetAniTick();
        }
    }

    public void setLevelData(int[][] levelData) {
        this.levelData = levelData;
    }

    public void setLevel(GameLevel levelData) {
        this.level = levelData;

        // check is in air
        if (!isOnTheFloor(point, HIT_BOX_WIDTH, HIT_BOX_HEIGHT, this.level)) {
            this.inAir = true;
        }
    }

    // public void updateAnimationTick() {
    // aniTick++;
    // if (aniTick >= aniSpeed) {
    // aniTick = 0;
    // aniIndex++;
    // if (aniIndex >= gameCharacterState.frameNumber) {
    // aniIndex = 0;
    // attacking = false;
    // aniSpeed = 80;
    // }
    // }
    // }

    @Override
    public void update() {
        this.updatePosition();

        // update the hit box
        this.updateHitBox();

        this.updateAnimationTick();
        this.setAnimationState();
    }

}
