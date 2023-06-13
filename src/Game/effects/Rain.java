package Game.effects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import Game.GameSourceFilePath;
import Game.Loader.ImageLoader;
import Game.PLUG.gameDrawer.GameAnimatedDrawer;
import Game.PLUG.gameDrawer.GameRenderOffsetPass;
import Game.gameBase.GamePoint;

import static base.BaseGameConstant.GAME_WIDTH;
import static base.BaseGameConstant.GAME_HEIGHT;

public class Rain implements GameAnimatedDrawer, GameRenderOffsetPass {
    private static final Logger LOGGER = Logger.getLogger(Rain.class.getName());

    private GamePoint[] drops;
    private SecureRandom random;
    private float rainSpeed = 1.25f;
    private BufferedImage rainImage;

    private float xLevelOffset;

    public Rain() {
        this.random = new SecureRandom();
        drops = new GamePoint[1000];
        try {
            loadImage();
            initDrops();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "load Raining image error", e);
        }

    }

    private void loadImage() throws IOException {
        this.rainImage = ImageLoader.loadImage(GameSourceFilePath.RAINING_IMAGE);
    }

    private void initDrops() {
        for (int i = 0; i < drops.length; i++) {
            drops[i] = getRandomPos();
        }
    }

    /**
     * This function returns a randomly generated GamePoint object with a new
     * x-coordinate and a
     * y-coordinate generated using a random integer within the range of
     * GAME_HEIGHT.
     * 
     * @return The method `getRandomPos()` is returning a `GamePoint` object.
     */
    private GamePoint getRandomPos() {
        return GamePoint.buildGamePoint(getNewX(), random.nextInt(GAME_HEIGHT));
    }

    /**
     * This function returns a random float value within a certain range, with an
     * additional offset.
     * 
     * @return The method `getNewX()` is returning a `float` value.
     */
    private float getNewX() {
        float value = (-GAME_WIDTH) + random.nextInt((int) (GAME_WIDTH * 3f)) + xLevelOffset;
        return value;
    }

    @Override
    public void passOffset(float offset) {
        this.xLevelOffset = offset;
    }

    @Override
    public void update() {
        Arrays.stream(this.drops)
                .forEach(item -> {

                    item.addToY(rainSpeed);

                    if (item.getY() >= GAME_HEIGHT) {
                        item.setY(-20);
                        item.setX(getNewX());
                    }

                });
    }

    @Override
    public void render(Graphics2D g) {
        Arrays.stream(this.drops)
                .forEach(item -> g.drawImage(rainImage,
                        (int) (item.getX() - xLevelOffset), item.getIntY(),
                        3, 12,
                        null));
    }

}
