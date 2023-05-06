package Game.role.ABC;

import Game.DataPass.AniData;
import Game.DataPass.GamePlayerSpeedData;
import Game.DataPass.ImageScaleData;
import Game.PLUG.gameDrawer.GameAnimatedDrawer;
import Game.gameBackground.GameLevel;
import Game.state.GameCharacterState;
import logic.input.Direction;

import static base.BaseGameConstant.TILES_SIZE;
import static logic.Controller.GameHelpMethods.isSightClear;

public abstract class GameEnemyABC extends GameCharacterABC implements GameAnimatedDrawer {
    private int enemyType;
    protected static final float ATTACK_DISTANCE = TILES_SIZE;
    protected static final float SEE_DISTANCE = ATTACK_DISTANCE * 5;

    {
        this.aniSpeed = 25;
    }

    public GameEnemyABC() {
        super();
    }

    public GameEnemyABC(int enemyType) {
        super();
        this.enemyType = enemyType;
    }

    public GameEnemyABC(AniData aid, ImageScaleData isd, GamePlayerSpeedData gps, int enemyType) {
        super(aid, isd, gps);
        this.enemyType = enemyType;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(int enemyType) {
        this.enemyType = enemyType;
    }

    @Override
    public void update() {
        this.updatePosition();
        this.updateAnimationTick();
        this.updateHitBox();
    }

    public int getAniIndex() {
        return this.aniIndex;
    }

    protected void newEnemyState(GameCharacterState state) {
        this.setCharacterState(state);

        this.aniTick = 0;
        this.aniIndex = 0;
    }

    public GameCharacterState getEnemyState() {
        return this.gameCharacterState;
    }

    protected void changeDirection() {
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
    //////////////////////////

    protected void turnTowardsPlayer(GameCharacterABC player) {
        if (player.point.getX() > this.point.getX()) {
            this.direction = Direction.RIGHT;
        } else {
            this.direction = Direction.LEFT;
        }
    }

    protected boolean isPlayerInRange(GameCharacterABC player) {
        float absValue = Math.abs(player.point.getX() - this.point.getX());

        return absValue <= GameEnemyABC.SEE_DISTANCE;
    }

    protected boolean isPlayerCloseForAttack(GameCharacterABC player) {
        if ((int) player.point.getY() != (int) this.point.getY()) {
            return false;
        }

        float absValue = Math.abs(player.point.getX() - this.point.getX());

        return absValue <= GameEnemyABC.ATTACK_DISTANCE;
    }

    protected boolean canSeePlayer(GameLevel levelData, GameCharacterABC player) {

        int playerTileY = (int) (player.point.getY() / TILES_SIZE);
        int enemyTileY = (int) (this.point.getY() / TILES_SIZE);

        if (playerTileY == enemyTileY) { // same Y
            if (isPlayerInRange(player)) {
                if (isSightClear(levelData, this, player)) {
                    return true;
                }
            }
        }
        return false;

    }

    @Override
    protected void updateAnimationTick() {
        this.aniTick++;

        if (this.aniTick >= this.aniSpeed) {
            this.aniTick = 0;
            this.aniIndex++;

            if (this.aniIndex >= gameCharacterState.frameNumber) {
                this.aniIndex = 0;
                if (this.gameCharacterState == GameCharacterState.ATTACKING) {
                    this.gameCharacterState = GameCharacterState.IDLE;
                }
                // this.attacking = false;
                // this.aniSpeed = 80;
            }
        }
    }

}
