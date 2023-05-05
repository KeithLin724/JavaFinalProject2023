package Game.role;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Game.gameBackground.GameLevel;
import Game.gameBase.GamePoint;
import Game.role.ABC.GameEnemyABC;
import Game.state.GameCharacterState;
import logic.input.Direction;

import static base.BaseGameConstant.TILES_SIZE;
import static base.BaseGameConstant.SCALE;
import static logic.Controller.GameHelpMethods.isOnTheFloor;
import static logic.Controller.GameHelpMethods.canMoveHere;

public class GameEnemy extends GameEnemyABC {

    public static final int levelDataID = 0;
    private static float drawXOffset;

    private static GameLevel levelData;

    private boolean firstUpdate = true;

    private static final float walkSpeed = 0.5f * SCALE;

    {
        this.direction = Direction.LEFT;
    }

    public static void passLevelData(GameLevel levelData) {
        GameEnemy.levelData = levelData;
    }

    public GameEnemy() {
        super();
    }

    public GameEnemy(String folderName, float x, float y, int enemyType) {
        super(enemyType);
        this.setXY(x, y);

        try {
            this.setAnimationImage(folderName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameEnemy(float x, float y, BufferedImage[][] image) {
        super();
        this.setXY(x, y);
        this.setAnimation(image);
    }

    public static void passOffset(float xOffset) {
        GameEnemy.drawXOffset = xOffset;
    }

    @Override
    public void updatePosition() {

    }

    @Override
    public void update() {
        super.update();
        this.updateMove();
    }

    private void updateMove() {

        boolean firstUpdateStatement = this.firstUpdate
                && !isOnTheFloor(point, HIT_BOX_WIDTH, HIT_BOX_HEIGHT, levelData);

        if (firstUpdateStatement) {
            this.inAir = true;
            this.firstUpdate = false;
        }

        this.updateYPos();
        this.updateXPos();
    }

    private void updateXPos() {
        switch (this.gameCharacterState) {
            case IDLE -> {
                this.setPlayerState(GameCharacterState.MOVING);
            }

            case MOVING -> {
                float xSpeed = 0;
                switch (this.direction) {
                    case LEFT -> {
                        xSpeed = -walkSpeed;
                    }
                    case RIGHT -> {
                        xSpeed = walkSpeed;
                    }

                    default -> {
                        // None
                    }
                }
                GamePoint speedPoint = new GamePoint(xSpeed, 0);

                GamePoint nextXPoint = GamePoint.add(point, speedPoint);

                boolean xCheckStatement = canMoveHere(nextXPoint, HIT_BOX_WIDTH, HIT_BOX_HEIGHT, levelData); // &&

                if (xCheckStatement) {
                    if (isOnTheFloor(nextXPoint, HIT_BOX_WIDTH, HIT_BOX_HEIGHT, levelData)) {
                        this.point.addToX(xSpeed);
                        return;
                    }

                }
                changeDirection();
            }

            default -> {
                // None
            }

        }

    }

    private void changeDirection() {
        switch (this.direction) {
            case LEFT -> {
                this.direction = Direction.RIGHT;
            }
            case RIGHT -> {
                this.direction = Direction.LEFT;
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
    public void render(Graphics g) {
        var nowImage = this.getAnimationImage(this.gameCharacterState, this.aniIndex);
        var fromPoint = this.point.toIntPoint();

        g.drawImage(nowImage,
                (int) (fromPoint.x - GameEnemy.drawXOffset), fromPoint.y,
                TILES_SIZE, TILES_SIZE,
                null);

        this.drawHitBox(g, GameEnemy.drawXOffset);
    }

    @Override
    public void setAnimationImage() {

    }

    @Override
    public void setAnimationState() {

    }
}
