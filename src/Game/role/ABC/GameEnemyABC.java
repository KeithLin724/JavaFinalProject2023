package Game.role.ABC;

import Game.DataPass.AniData;
import Game.DataPass.GamePlayerSpeedData;
import Game.DataPass.ImageScaleData;
import Game.PLUG.gameDrawer.GameAnimatedDrawer;
import Game.state.GameCharacterState;

public abstract class GameEnemyABC extends GameCharacterABC implements GameAnimatedDrawer {
    private int enemyType;

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
        this.updateAnimationTick();
    }

    public int getAniIndex() {
        return this.aniIndex;
    }

    public GameCharacterState getEnemyState() {
        return this.gameCharacterState;
    }

}
