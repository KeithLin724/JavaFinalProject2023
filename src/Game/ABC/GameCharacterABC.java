package Game.ABC;

import java.awt.image.BufferedImage;

import Game.DataPass.AniData;
import Game.DataPass.GamePlayerSpeedData;
import Game.DataPass.ImageScaleData;
// import Game.DataPass.PlayerHitBox;
import Game.gameBase.GameCalculator;
import Game.gameBase.GameUnitPair;
import Game.state.PlayerState;

public abstract class GameCharacterABC extends BasicMoveABC {

    protected BufferedImage[][] animations;

    protected int aniTick, aniIndex, aniSpeed; // text
    protected int imgScaleX, imgScaleY, imageScale; // text

    public GameCharacterABC() {
        super();
    }

    public GameCharacterABC(AniData aid, ImageScaleData isd, GamePlayerSpeedData gps) {
        super();

        this.setAniThing(aid);
        this.setImageScale(isd);
        this.setGamePlayerSpeedData(gps);
        // this.setPlayerHitBox(playerHitBox);
    }

    /**
     * The function sets the animation data for a given object.
     * 
     * @param aid An object of type AniData, which contains information about an
     *            animation such as the
     *            current tick, index, and speed.
     */
    public void setAniThing(AniData aid) {
        this.aniTick = aid.aniTick();
        this.aniIndex = aid.aniIndex();
        this.aniSpeed = aid.aniSpeed();
    }

    /**
     * The function sets the image scale values based on the provided ImageScaleData
     * object.
     * 
     * @param isd isd is an object of the class ImageScaleData, which contains
     *            information about the
     *            scaling of an image.
     */
    public void setImageScale(ImageScaleData isd) {
        this.imgScaleX = isd.imgScaleX();
        this.imgScaleY = isd.imgScaleY();
        this.imageScale = isd.imageScale();
    }

    /**
     * The function sets the player speed data for a game using the input
     * GamePlayerSpeedData object.
     * 
     * @param gps The parameter "gps" is an object of the class
     *            "GamePlayerSpeedData".
     */
    public void setGamePlayerSpeedData(GamePlayerSpeedData gps) {
        super.setPlayerSpeed(gps.playSpeed());
    }

    /**
     * The function sets the animation images for an object.
     * 
     * @param image A 2D array of BufferedImage objects representing the frames of
     *              an animation. The first
     *              dimension represents the different animations (e.g. walking,
     *              jumping, attacking), while the second
     *              dimension represents the individual frames within each
     *              animation.
     */
    public void setAnimation(BufferedImage[][] image) {
        this.animations = image;
    }

    protected void resetAniTick() {
        this.aniTick = 0;
        this.aniIndex = 0;
    }

    /**
     * This function returns a specific animation image based on the player's state
     * and frame index.
     * 
     * @param characterState The parameter characterState is an object of the
     *                       PlayerState class which
     *                       contains information about the current state of the
     *                       player character, such as whether they are
     *                       standing, walking, jumping, or attacking.
     * @param frameIndex     frameIndex is an integer value representing the index
     *                       of the frame in the
     *                       animation sequence. It is used to determine which image
     *                       to retrieve from the animations array.
     * @return A BufferedImage object.
     */
    public BufferedImage getAnimationImage(PlayerState characterState, int frameIndex) {
        return this.animations[characterState.saveArrayIndex][frameIndex % characterState.frameNumber];
    }

    /**
     * The function returns the scaled length of an image based on the image scale
     * factor.
     * 
     * @param length The length parameter is an integer value representing the
     *               length of an image.
     * @return The method `scaleFunction` is returning an integer value which is the
     *         result of dividing the
     *         `length` parameter by the `imageScale` instance variable.
     */
    private int scaleFunction(int length) {
        return length / this.imageScale;
    }

    /**
     * The function returns a Point object that represents the scale of a given
     * BufferedImage using a
     * scaleFunction.
     * 
     * @param animationsImage A BufferedImage object representing an image used for
     *                        animations.
     * @return A Point object is being returned.
     */
    public GameUnitPair getImageScalePoint(BufferedImage animationsImage) {
        return GameCalculator.calculate(
                animationsImage.getWidth(), animationsImage.getHeight(),
                this::scaleFunction);
    }
}
