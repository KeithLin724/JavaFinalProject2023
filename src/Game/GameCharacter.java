package Game;

import java.awt.Graphics;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Game.ABC.GameCharacterABC;
import Game.DataPass.AniData;
import Game.DataPass.GamePlayerSpeedData;
import Game.DataPass.ImageScaleData;
import Game.Loader.ImageLoader;
import Game.Loader.ImageNamePath;
import Game.PLUG.GameCharacterInterface;
import Game.PLUG.GameRenderInterface;
import Game.gameConstant.PlayerState;

// for put the game character skin
public class GameCharacter extends GameCharacterABC implements GameCharacterInterface, GameRenderInterface {
    private static final Logger LOGGER = Logger.getLogger(GameCharacter.class.getName());

    public GameCharacter() {
        super();
    }

    public GameCharacter(AniData aid, ImageScaleData isd, GamePlayerSpeedData gps) {
        super(aid, isd, gps);
    }

    public void init(float x, float y) {
        this.setXY(x, y);
        this.setAnimationImage();
    }

    @Override
    public void updatePosition() {
        this.moving = false;

        // GameLambda<Boolean> booleanLambda = (a, b) -> a && !b;

        if (this.left && !this.right) {
            this.x -= this.playerSpeed;
            moving = true;
        } else if (this.right && !this.left) {
            this.x += this.playerSpeed;
            this.moving = true;
        }

        if (this.up && !this.down) {
            this.y -= this.playerSpeed;
            this.moving = true;
        } else if (this.down && !this.up) {
            this.y += this.playerSpeed;
            this.moving = true;
        }
    }

    @Override
    public void render(Graphics g) {
        var nowImage = this.getAnimationImage(this.playerAction, this.aniIndex);
        var scalePoint = this.getImageScalePoint(nowImage);

        g.drawImage(nowImage,
                (int) this.x, (int) this.y,
                scalePoint.x, scalePoint.y,
                null);

    }

    @Override
    public void setAnimationImage() {
        try {
            this.setAnimation(ImageLoader.loadCharacter(ImageNamePath.PLAYER_MAIN_CHARACTER, 5, 6));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "load image error", e);
            e.printStackTrace();
        }
    }

    @Override
    public void setAnimationState() {

        PlayerState startAni = playerAction;

        playerAction = (moving ? PlayerState.MOVING : PlayerState.IDLE);

        if (attacking) {
            aniSpeed = 20;
            playerAction = PlayerState.ATTACKING;
        }

        if (startAni != playerAction) {
            aniTick = 0;
            aniIndex = 0;
        }
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
        this.updateAnimationTick();
        this.setAnimationState();
    }

}
