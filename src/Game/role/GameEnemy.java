package Game.role;

import Game.Player;
import Game.gameBackground.GameLevel;
import Game.gameBase.GamePoint;
import Game.role.ABC.GameEnemyABC;
import Game.state.GameCharacterState;
import logic.input.Direction;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static base.BaseGameConstant.SCALE;
import static base.BaseGameConstant.TILES_SIZE;
import static logic.Controller.GameHelpMethods.canMoveHere;
import static logic.Controller.GameHelpMethods.isOnTheFloor;

public class GameEnemy extends GameEnemyABC {

    private static final Logger LOGGER = Logger.getLogger(GameEnemy.class.getName());

    public static final int levelDataID = 0;
    private static float drawXOffset;

    private static GameLevel levelData;

    private Player player;

    private static final float walkSpeed = 0.5f * SCALE;

    // image for flip
    private int flipX = 0, flipW = 1;

    // about the attack box
    private Rectangle2D.Float attackBox;

    // init setting
    {
        this.direction = Direction.LEFT;
    }

    public static void passLevelData(GameLevel levelData) {
        GameEnemy.levelData = levelData;
    }

    public GameEnemy() {
        super();
        this.initAttackBox();
    }

    public GameEnemy(String folderName, float x, float y, GameEnemyType enemyType) {
        super(enemyType);
        this.setXY(x, y);

        try {
            this.setAnimationImage(folderName);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "set animation error", e);
        }

        this.initAttackBox();
    }

    public GameEnemy(float x, float y, BufferedImage[][] image) {
        super();
        this.setXY(x, y);
        this.setResetPoint(GamePoint.buildGamePoint(x, y));
        this.setAnimation(image);

        this.initAttackBox();
    }

    private void initAttackBox() {
        this.attackBox = new Rectangle2D.Float(
                this.point.getIntX(), this.point.getIntY(),
                TILES_SIZE,
                TILES_SIZE);
    }

    public static void passOffset(float xOffset) {
        GameEnemy.drawXOffset = xOffset;
    }

    public void passPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void update() {
        super.update();
        updateAttackBox();
        // this.updateMove();
    }

    private void updateAttackBox() {
        attackBox.x = this.point.getX();
        attackBox.y = this.point.getY();
    }

    @Override
    public void render(Graphics g) {
        var nowImage = this.getAnimationImage(this.gameCharacterState, this.aniIndex);
        var fromPoint = this.point.toIntPoint();

        g.drawImage(nowImage,
                (int) (fromPoint.x - GameEnemy.drawXOffset + flipX),
                fromPoint.y,
                TILES_SIZE * flipW,
                TILES_SIZE,
                null);

        // this.drawHitBox(g, GameEnemy.drawXOffset);
        this.drawAttackBox(g);
    }

    private void drawAttackBox(Graphics g) {
        g.setColor(Color.red);

        g.drawRect((int) (this.attackBox.x - drawXOffset), (int) this.attackBox.y,
                (int) this.attackBox.width,
                (int) this.attackBox.height);
    }

    @Override
    public void updatePosition() {
        boolean firstUpdateStatement = this.firstUpdate
                && !isOnTheFloor(point, HIT_BOX_WIDTH, HIT_BOX_HEIGHT, levelData);

        if (firstUpdateStatement) {
            this.inAir = true;
            this.firstUpdate = false;
        }

        this.updateYPos();
        this.updateXPosAndBehavior();
    }

    private void moveX() {
        float xSpeed = 0;
        switch (this.direction) {
            case LEFT -> {
                xSpeed = -walkSpeed;

                this.flipX = TILES_SIZE;
                this.flipW = -1;
            }
            case RIGHT -> {
                xSpeed = walkSpeed;

                this.flipX = 0;
                this.flipW = 1;
            }

            default -> {
                // None
            }
        }
        GamePoint speedPoint = new GamePoint(xSpeed, 0);

        GamePoint nextXPoint = GamePoint.add(point, speedPoint);

        boolean xCheckStatement = canMoveHere(nextXPoint, HIT_BOX_WIDTH, HIT_BOX_HEIGHT, levelData);

        if (xCheckStatement) {
            if (isOnTheFloor(nextXPoint, HIT_BOX_WIDTH, HIT_BOX_HEIGHT, levelData)) {
                this.point.addToX(xSpeed);
                return;
            }

        }
        changeDirection();
    }

    private void updateXPosAndBehavior() {
        switch (this.gameCharacterState) {

            case IDLE -> this.newEnemyState(GameCharacterState.MOVING);

            case MOVING -> {
                if (canSeePlayer(levelData, this.player)) {
                    turnTowardsPlayer(player);
                }

                if (isPlayerCloseForAttack(player)) {
                    this.newEnemyState(GameCharacterState.ATTACKING);
                }

                this.moveX();
            }

            case ATTACKING -> {
                if (aniIndex == 0) {
                    this.attackChecked = false;
                }

                if (this.aniIndex == 2 && !attackChecked) {
                    this.checkPlayerGetHit(attackBox, player);
                }

            }

            case HIT -> {

            }

            default -> {
                // None
            }

        }

    }

    private void updateYPos() {

        if (!isOnTheFloor(point, HIT_BOX_WIDTH, HIT_BOX_HEIGHT, levelData)) {
            this.inAir = true;
        }

        if (!this.inAir) {
            return;
        }

        GamePoint nextPoint = new GamePoint(0, this.airSpeed);

        if (canMoveHere(GamePoint.add(point, nextPoint), HIT_BOX_WIDTH, HIT_BOX_HEIGHT, levelData)) {
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
    public void setAnimationImage() {

    }

    @Override
    public void setAnimationState() {

    }

    // game logic

}
