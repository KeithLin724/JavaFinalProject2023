package Game;

import java.awt.Graphics;
import java.io.IOException;

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
    protected float x, y;

    public GameCharacter() {
        super(null, null, null);
    }

    public GameCharacter(AniData aid, ImageScaleData isd, GamePlayerSpeedData gps) {
        super(aid, isd, gps);
    }

    public void init(float x, float y) {
        this.x = x;
        this.y = y;

        try {
            this.setAnimationImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePosition() {
        this.moving = false;

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
    public void setUp(boolean up) {
        this.up = up;
    }

    @Override
    public void setDown(boolean down) {
        this.down = down;
    }

    @Override
    public void setLeft(boolean left) {
        this.left = left;
    }

    @Override
    public void setRight(boolean right) {
        this.right = right;
    }

    @Override
    public void render(Graphics g) {
        this.imgScaleX = animations[this.playerAction.num][this.aniIndex].getWidth() / this.imageScale;
        this.imgScaleY = animations[this.playerAction.num][this.aniIndex].getHeight() / this.imageScale;
        g.drawImage(animations[this.playerAction.num][this.aniIndex],
                (int) this.x, (int) this.y,
                this.imgScaleX, this.imgScaleY,
                null);
    }

    @Override
    public void setAnimationImage() throws IOException {
        this.animations = ImageLoader.loadCharacter(ImageNamePath.PLAYER_MAIN_CHARACTER, 5, 6);
    }

    @Override
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
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
