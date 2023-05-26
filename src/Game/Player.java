package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Game.DataPass.AniData;
import Game.DataPass.GamePlayerSpeedData;
import Game.DataPass.ImageScaleData;
import Game.GUI.GamePlaying;
import Game.GUI.UIConstant.StatusBar;
import Game.Loader.ImageLoader;
// import Game.DataPass.PlayerHitBox;
import Game.Loader.ImageNamePath;
import Game.PLUG.gameDrawer.GameAnimatedDrawer;
import Game.PLUG.gameDrawer.GameRenderOffsetPass;
import Game.audio.GameAudio;
import Game.gameBackground.GameLevel;
import Game.gameBase.GamePoint;
import Game.role.ABC.GameCharacterABC;
import Game.state.GameCharacterState;
import logic.input.Direction;

import static base.BaseGameConstant.TILES_SIZE;
import static base.BaseGameConstant.SCALE;
import static logic.Controller.GameHelpMethods.canMoveHere;
import static logic.Controller.GameHelpMethods.*;

// for put the game character skin
public class Player extends GameCharacterABC
        implements GameAnimatedDrawer, GameRenderOffsetPass {

    private static final Logger LOGGER = Logger.getLogger(Player.class.getName());

    private int[][] levelData;
    private GameLevel level;

    protected float drawXOffset;

    // health bar
    private BufferedImage statusBarImage;
    private int maxHealth = 100;
    private int currentHealth = 100;
    private int healthWidth = StatusBar.HEALTH_BAR_WIDTH.value;

    // attack box
    private Rectangle2D.Float attackBox;
    protected int flipX = 0;
    protected int flipW = 1;

    private boolean attackChecked;

    private GamePlaying gamePlaying;

    protected boolean gameOverEffect;

    public Player() {
        super();
        loadUIImage();
        initAttackBox();
    }

    public Player(AniData aid, ImageScaleData isd, GamePlayerSpeedData gps) {
        super(aid, isd, gps);
        loadUIImage();
        initAttackBox();
    }

    private void loadUIImage() {
        try {
            statusBarImage = ImageLoader.loadImage(GameSourceFilePath.STATUS_HEALTH_BAR_IMAGE);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "load bar image error", e);
        }
    }

    private void initAttackBox() {
        this.attackBox = new Rectangle2D.Float(
                this.point.getIntX(), this.point.getIntY(),
                (int) (20 * SCALE),
                (int) (20 * SCALE));
    }

    public void initWithPoint_testing(float x, float y) {
        this.setXY(x, y);
        this.setAnimationImage();
    }

    public void init(float x, float y) {
        this.setXY(x, y);
        this.setResetPoint(GamePoint.buildGamePoint(x, y));
        // this.setAnimationImage();
    }

    public void setGamePlaying(GamePlaying gamePlaying) {
        this.gamePlaying = gamePlaying;
    }

    public Rectangle2D.Float getAttackBox() {
        return this.attackBox;
    }

    @Override
    public void update() {
        this.updateHealthBar();

        if (this.currentHealth <= 0) {

            if (!this.gameCharacterState.equals(GameCharacterState.DEAD)) {
                this.gameCharacterState = GameCharacterState.DEAD;
                this.aniTick = 0;
                this.aniIndex = 0;
                this.gamePlaying.setPlayerDying(true);
                this.gamePlaying.getGame().getGameAudioPlayer().playEffect(GameAudio.DIE);
                return;
            }

            if (this.aniIndex == GameCharacterState.DEAD.frameNumber - 1 && aniTick >= this.aniSpeed - 1) {
                this.gamePlaying.setGameOver(true);
                this.gamePlaying.getGame().getGameAudioPlayer().stopSong();
                if (gameOverEffect == false) {
                    this.gamePlaying.getGame().getGameAudioPlayer().playEffect(GameAudio.GAMEOVER);
                    gameOverEffect = true;
                }
                // System.out.println("here");
                return;
            }

            updateAnimationTick();

            return;

        }

        this.updateAttackBox();

        // if (state == HIT) {
        // if (aniIndex <= GetSpriteAmount(state) - 3)
        // pushBack(pushBackDir, lvlData, 1.25f);
        // updatePushBackDrawOffset();
        // } else
        // updatePos();

        this.updatePosition();

        if (attacking) {
            checkAttack();
        }

        // update the hit box
        this.updateHitBox();

        this.updateAnimationTick();
        this.setAnimationState();
    }

    protected void checkAttack() {
        if (attackChecked || this.aniIndex != 2) {
            return;
        }
        attackChecked = true;
        gamePlaying.checkEnemyHit(this);
        gamePlaying.getGame().getGameAudioPlayer().playAttackSound();
    }

    private void updateAttackBox() {
        if (this.direction.equals(Direction.RIGHT)) {
            this.attackBox.x = this.point.getX() + HIT_BOX_WIDTH + (int) (SCALE * 10);
        }

        else if (this.direction.equals(Direction.LEFT)) {
            this.attackBox.x = this.point.getX() - HIT_BOX_WIDTH; // - (int) (SCALE * 10)
        }

        this.attackBox.y = this.hitBox.y + (SCALE * 10);
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

    public void changeHealth(int value) {
        // if (this.gameCharacterState.equals(GameCharacterState.HIT)){
        // return ;
        // }

        this.currentHealth += value;

        if (value < 0) {
            this.gamePlaying.getGame()
                    .getGameAudioPlayer()
                    .playEffect(GameAudio.PLAYER_GET_HIT);

            this.setCharacterState(GameCharacterState.HIT);
        }

        if (this.currentHealth <= 0) {
            this.currentHealth = 0;
            // gameOver();
        }

        else if (this.currentHealth >= 100) {
            this.currentHealth = 100;
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
        updateImageDirection();
    }

    private void updateImageDirection() {
        switch (this.direction) {

            case LEFT -> {
                this.flipX = TILES_SIZE;
                this.flipW = -1;
            }

            case RIGHT -> {
                this.flipX = 0;
                this.flipW = 1;
            }

            default -> {
                // None
            }

        }
    }

    private void jump() {
        if (inAir) {
            return;
        }
        gamePlaying.getGame().getGameAudioPlayer().playEffect(GameAudio.JUMP);
        this.inAir = true;
        this.airSpeed = this.jumpSpeed;
    }

    @Override
    public void render(Graphics2D g) {
        var nowImage = this.getAnimationImage(this.gameCharacterState, this.aniIndex);
        var fromPoint = this.point.toIntPoint();

        g.drawImage(nowImage,
                (int) (fromPoint.x - drawXOffset + flipX),
                fromPoint.y,
                TILES_SIZE * flipW,
                TILES_SIZE,
                null);

        // this.drawHitBox(g, drawXOffset);

        this.drawAttackBox(g);

        this.drawUI(g);

    }

    private void drawAttackBox(Graphics g) {
        g.setColor(Color.red);

        g.drawRect((int) (this.attackBox.x - drawXOffset), (int) this.attackBox.y,
                (int) this.attackBox.width,
                (int) this.attackBox.height);
    }

    protected void drawUI(Graphics g) {
        g.drawImage(statusBarImage,
                StatusBar.STATUS_BAR_X.value, StatusBar.STATUS_BAR_Y.value, // xy
                StatusBar.STATUS_BAR_WIDTH.value, StatusBar.STATUS_BAR_HEIGHT.value, // w h
                null);

        g.setColor(Color.red);

        g.fillRect(StatusBar.HEALTH_BAR_X_START.value + StatusBar.STATUS_BAR_X.value,
                StatusBar.HEALTH_BAR_Y_START.value + StatusBar.STATUS_BAR_Y.value,
                this.healthWidth,
                StatusBar.HEALTH_BAR_HEIGHT.value);

    }

    @Override
    public void setAnimationImage() {
        try {
            this.setAnimationImage(ImageNamePath.PLAYER_MAIN_CHARACTER);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "set Animation Image error", e);
        }
    }

    @Override
    public void setAnimationState() {

        GameCharacterState startAni = gameCharacterState;

        if (gameCharacterState.equals(GameCharacterState.HIT)) {
            return;
        }

        if (!gameCharacterState.equals(GameCharacterState.JUMP)) {
            gameCharacterState = (this.direction.isMoving() && (this.dirMove[2] + this.dirMove[3] != 0)
                    ? GameCharacterState.MOVING
                    : GameCharacterState.IDLE);
        }

        if (this.inAir) {
            // System.out.println(this.playerAction);
            this.gameCharacterState = (airSpeed < 0 ? GameCharacterState.JUMP : GameCharacterState.FALLING);

        }

        if (attacking) {
            aniSpeed = 20;
            gameCharacterState = GameCharacterState.ATTACKING;

            if (startAni != GameCharacterState.ATTACKING) {
                this.aniIndex = 1;
                this.aniTick = 0;
            }
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

    private void updateHealthBar() {
        this.healthWidth = (int) ((currentHealth / (float) maxHealth) * StatusBar.HEALTH_BAR_WIDTH.value);
    }

    @Override
    protected void updateAnimationTick() {
        // LOGGER.info("player state :" + this.gameCharacterState);
        this.aniTick++;

        if (this.aniTick >= this.aniSpeed) {
            this.aniTick = 0;
            this.aniIndex++;

            if (this.aniIndex >= gameCharacterState.frameNumber) {
                this.aniIndex = 0;
                this.attacking = false;
                this.aniSpeed = 80;
                this.attackChecked = false;
                // this.setCharacterState();

                if (this.gameCharacterState.equals(GameCharacterState.HIT)) {
                    this.newState(GameCharacterState.IDLE);
                    airSpeed = 0f;
                    if (!isOnTheFloor(point, HIT_BOX_WIDTH, HIT_BOX_HEIGHT, this.level)) {
                        this.inAir = true;
                    }
                }
            }
        }
    }

    @Override
    public void resetAll() {
        super.resetAll();
        this.stopDirection();
        this.gameOverEffect = false;

        this.inAir = false;
        this.attacking = false;

        currentHealth = maxHealth;

        if (!isOnTheFloor(point, HIT_BOX_WIDTH, HIT_BOX_HEIGHT, this.level)) {
            this.inAir = true;
        }

    }

}
