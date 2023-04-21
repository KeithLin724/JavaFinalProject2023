package Game;

import java.awt.Graphics;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import Game.ABC.GameCharacterABC;
import Game.DataPass.AniData;
import Game.DataPass.GamePlayerSpeedData;
import Game.DataPass.ImageScaleData;
// import Game.DataPass.PlayerHitBox;
import Game.Loader.ImageLoader;
import Game.Loader.ImageNamePath;
import Game.PLUG.GameCharacterInterface;
import Game.PLUG.GameRenderInterface;
import Game.gameBase.GamePoint;
import Game.gameConstant.PlayerState;
import gameBackground.GameLevel;

import static base.BaseGameConstant.TILES_SIZE;
import static logic.Controller.GameHelpMethods.canMoveHere;

// for put the game character skin
public class GameCharacter extends GameCharacterABC implements GameCharacterInterface, GameRenderInterface {
    private static final Logger LOGGER = Logger.getLogger(GameCharacter.class.getName());

    private int[][] levelData;
    private GameLevel level;

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
    public void updatePosition() {
        if (Arrays.stream(this.dirMove).allMatch(x -> x == 0)) {
            return;
        }
        int xSpeed = this.dirMove[2] + this.dirMove[3];
        int ySpeed = this.dirMove[0] + this.dirMove[1];

        GamePoint nextPoint = new GamePoint(xSpeed, ySpeed);

        // this.point.addToX(this.dirMove[2] + this.dirMove[3]);
        // this.point.addToY(this.dirMove[0] + this.dirMove[1]);
        if (canMoveHere(GamePoint.add(point, nextPoint), HIT_BOX_WIDTH, HIT_BOX_HEIGHT, this.level)) {
            this.point.addToX(xSpeed);
            this.point.addToY(ySpeed);
        }
    }

    @Override
    public void render(Graphics g) {
        var nowImage = this.getAnimationImage(this.playerAction, this.aniIndex);
        // var scalePoint = this.getImageScalePoint(nowImage).toIntPoint();
        var fromPoint = this.point.toIntPoint();

        g.drawImage(nowImage,
                fromPoint.x, fromPoint.y,
                TILES_SIZE, TILES_SIZE,
                null);

        this.drawHitBox(g);

    }

    public void setAnimationImage(String filePath, int characterStateNum, int frameNumber) {
        try {
            this.setAnimation(ImageLoader.loadCharacter(filePath, characterStateNum, frameNumber));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "load image error", e);
            e.printStackTrace();
        }
    }

    @Override
    public void setAnimationImage() {
        this.setAnimationImage(ImageNamePath.PLAYER_MAIN_CHARACTER, 5, 6);
    }

    @Override
    public void setAnimationState() {

        PlayerState startAni = playerAction;

        playerAction = (this.direction.isMoving() ? PlayerState.MOVING : PlayerState.IDLE);

        if (attacking) {
            aniSpeed = 20;
            playerAction = PlayerState.ATTACKING;
        }

        if (startAni != playerAction) {
            this.resetAniTick();
        }
    }

    public void setLevelData(int[][] levelData) {
        this.levelData = levelData;
    }

    public void setLevel(GameLevel levelData) {
        this.level = levelData;
    }

    @Override
    public void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= playerAction.getAnimationFrameNumbs()) {
                aniIndex = 0;
                attacking = false;
                aniSpeed = 35;
            }
        }
    }

    public void update() {
        this.updatePosition();

        // update the hit box
        this.updateHitBox();

        this.updateAnimationTick();
        this.setAnimationState();
    }

}
