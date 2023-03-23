package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
// import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import static utilz.Constants.PlayerConstants.*;

public class Player extends Methods {
    private BufferedImage[][] animations;
    private InputStream imageInput;
    private int aniTick, aniIndex, aniSpeed = 35;
    private int playerAction = IDLE;
    private boolean up, down, right, left;
    private boolean moving = false, attacking = false;
    private int imgScaleX, imgScaleY, imageScale = 10;
    private float playerSpeed = 5.0f;

    private static final Logger LOGGER = Logger.getLogger(Player.class.getName());

    public Player(float x, float y) {
        super(x, y);
        importImage();
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    private void setAnimation() {
        int startAni = playerAction;

        if (moving)
            playerAction = MOVING;
        else
            playerAction = IDLE;

        if (attacking) {
            aniSpeed = 20;
            playerAction = ATTACKING;
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
            if (aniIndex >= GetAnimationFrameNumbs(playerAction)) {
                aniIndex = 0;
                attacking = false;
                aniSpeed = 35;
            }
        }
    }

    @Override
    public void importImage() {
        animations = new BufferedImage[5][6];

        for (int i = 0; i < GetAnimationFrameNumbs(IDLE); i++) {
            imageInput = getClass().getResourceAsStream(PLAYER_MAIN_CHARACTER + "IDLE_" + i + ".png");
            try {
                animations[IDLE][i] = ImageIO.read(imageInput);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < GetAnimationFrameNumbs(ATTACKING); i++) {
            imageInput = getClass().getResourceAsStream(PLAYER_MAIN_CHARACTER + "ATTACK_" + i + ".png");
            try {
                animations[ATTACKING][i] = ImageIO.read(imageInput);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "some wrong", e);
                e.printStackTrace();
            }
        }
        for (int i = 0; i < GetAnimationFrameNumbs(MOVING); i++) {
            imageInput = getClass().getResourceAsStream(PLAYER_MAIN_CHARACTER + "MOVE_" + i + ".png");
            try {
                animations[MOVING][i] = ImageIO.read(imageInput);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
