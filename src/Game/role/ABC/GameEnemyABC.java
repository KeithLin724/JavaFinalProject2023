package Game.role.ABC;

import Game.DataPass.AniData;
import Game.DataPass.GamePlayerSpeedData;
import Game.DataPass.ImageScaleData;
import Game.PLUG.gameDrawer.GameAnimatedDrawer;
import Game.Player;
import Game.gameBackground.GameLevel;
import Game.role.GameEnemyType;
import Game.state.GameCharacterState;
import logic.input.Direction;

import java.awt.geom.Rectangle2D;

import static base.BaseGameConstant.TILES_SIZE;
import static logic.Controller.GameHelpMethods.isSightClear;

public abstract class GameEnemyABC extends GameCharacterABC implements GameAnimatedDrawer {
    protected GameEnemyType enemyType;
    protected static final float ATTACK_DISTANCE = TILES_SIZE;
    protected static final float SEE_DISTANCE = ATTACK_DISTANCE * 5;

    protected int maxHealth;
    protected int currentHealth;

    protected boolean firstUpdate = true;
    private boolean active = true;
    protected boolean attackChecked;

    {
        this.aniSpeed = 25;
    }

    public GameEnemyABC() {
        super();
        // default
        this.setEnemyType(GameEnemyType.ENEMY_TYPE_1);
    }

    public GameEnemyABC(GameEnemyType enemyType) {
        super();
        this.setEnemyType(GameEnemyType.ENEMY_TYPE_1);
    }

    public GameEnemyABC(AniData aid, ImageScaleData isd, GamePlayerSpeedData gps, GameEnemyType enemyType) {
        super(aid, isd, gps);
        this.setEnemyType(GameEnemyType.ENEMY_TYPE_1);
    }

    protected void resetHealth(int health) {
        this.maxHealth = health;
        this.currentHealth = this.maxHealth;
    }

    public boolean isActive() {
        return this.active;
    }

    public GameEnemyType getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(GameEnemyType enemyType) {
        this.enemyType = enemyType;
        this.resetHealth(this.enemyType.health);
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

    public void getHurt(int amount) {
        currentHealth -= amount;
        // System.out.println("get hurt");
        if (currentHealth <= 0) {
            newEnemyState(GameCharacterState.DEAD);
        } else {
            newEnemyState(GameCharacterState.HIT);
        }
    }

    protected void checkPlayerGetHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.getHitBox())) {
            player.changeHealth(-enemyType.damage);
        }
        attackChecked = true;
    }

    public GameCharacterState getEnemyState() {
        return this.gameCharacterState;
    }

    protected void changeDirection() {
        switch (this.direction) {
            case LEFT -> this.direction = Direction.RIGHT;

            case RIGHT -> this.direction = Direction.LEFT;

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

    public void resetAll() {
        this.firstUpdate = true;
        currentHealth = maxHealth;
        this.newEnemyState(GameCharacterState.IDLE);
        this.active = true;
    }

    @Override
    protected void updateAnimationTick() {
        this.aniTick++;

        if (this.aniTick >= this.aniSpeed) {
            this.aniTick = 0;
            this.aniIndex++;

            if (this.aniIndex >= gameCharacterState.frameNumber) {
                this.aniIndex = 0;

                switch (this.gameCharacterState) {
                    case ATTACKING, HIT -> this.setCharacterState(GameCharacterState.IDLE);

                    case DEAD -> active = false;

                    default -> {
                        // None
                    }

                }

            }
        }
    }

}
