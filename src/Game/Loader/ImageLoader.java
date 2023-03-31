package Game.Loader;

import java.awt.image.BufferedImage;
import java.io.IOException;

import Game.gameConstant.PlayerConstants;
import base.loader.BaseLoader;

public class ImageLoader {

    /**
     * It loads an image from a folder in the resources folder
     * 
     * @param folderName The folder name where the image is located.
     * @param fileName   The name of the file you want to load.
     * @return A BufferedImage object.
     */
    public static BufferedImage loadImage(String folderName, String fileName) throws IOException {
        return BaseLoader.loadImage(ImageLoader.class, ImageNamePath.imagePath(folderName, fileName));
    }

    /**
     * It loads a character's animations from a folder
     * 
     * @param characterState The number of states the character has.
     * @param frameNumber    The number of frames in the animation
     * @param folderName     The name of the folder where the images are stored.
     * @return A 2D array of BufferedImages.
     */
    public static BufferedImage[][] loadCharacter(String folderName, int characterState, int frameNumber)
            throws IOException {

        BufferedImage[][] animations = new BufferedImage[characterState][frameNumber];

        for (int i = 0; i < PlayerConstants.GetAnimationFrameNumbs(PlayerConstants.IDLE); i++) {
            animations[PlayerConstants.IDLE][i] = ImageLoader.loadImage(folderName, "IDLE_" + i);
        }

        for (int i = 0; i < PlayerConstants.GetAnimationFrameNumbs(PlayerConstants.ATTACKING); i++) {
            animations[PlayerConstants.ATTACKING][i] = ImageLoader.loadImage(folderName, "ATTACK_" + i);
        }

        for (int i = 0; i < PlayerConstants.GetAnimationFrameNumbs(PlayerConstants.MOVING); i++) {
            animations[PlayerConstants.MOVING][i] = ImageLoader.loadImage(folderName, "MOVE_" + i);
        }

        return animations;
    }
}
