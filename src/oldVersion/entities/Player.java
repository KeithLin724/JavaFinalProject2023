package oldVersion.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
// import java.io.InputStream;
// import javax.imageio.ImageIO;

import Game.Loader.ImageLoader;
import Game.Loader.ImageNamePath;
import Game.gameConstant.PlayerConstants;

public class Player extends Methods {
    private BufferedImage[][] animations;
    // private InputStream imageInput;
    private int aniTick, aniIndex, aniSpeed = 35;
    private int playerAction = PlayerConstants.IDLE;
    private boolean up, down, right, left;
    private boolean moving = false, attacking = false;
    private int imgScaleX, imgScaleY, imageScale = 10;
    private float playerSpeed = 5.0f;

    public Player(float x, float y) {
        super(x, y);
        this.importImage();
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    private void setAnimation() {
        int startAni = playerAction;

        // if (moving)
        // playerAction = MOVING;
        // else
        // playerAction = IDLE;
        // TODO: new update using clear if
        playerAction = (moving ? PlayerConstants.MOVING : PlayerConstants.IDLE);

        if (attacking) {
            aniSpeed = 20;
            playerAction = PlayerConstants.ATTACKING;
        }

        if (startAni != playerAction) {
            aniTick = 0;
            aniIndex = 0;
        }
    }

    public void render(Graphics g) {
        imgScaleX = animations[playerAction][aniIndex].getWidth() / imageScale;
        imgScaleY = animations[playerAction][aniIndex].getHeight() / imageScale;
        g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, imgScaleX, imgScaleY, null);
    }

    public void updatePosition() {
        moving = false;

        if (left && !right) {
            x -= playerSpeed;
            moving = true;
        } else if (right && !left) {
            x += playerSpeed;
            moving = true;
        }

        if (up && !down) {
            y -= playerSpeed;
            moving = true;
        } else if (down && !up) {
            y += playerSpeed;
            moving = true;
        }
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= PlayerConstants.GetAnimationFrameNumbs(playerAction)) {
                aniIndex = 0;
                attacking = false;
                aniSpeed = 35;
            }
        }
    }

    @Override
    public void importImage() {
        try {
            animations = ImageLoader.loadCharacter(ImageNamePath.PLAYER_MAIN_CHARACTER, 5, 6);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

}
