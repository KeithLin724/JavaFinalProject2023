package Game.Loader;

import java.awt.image.BufferedImage;
import java.io.IOException;

import Game.gameConstant.PlayerConstants;
import base.loader.BaseLoader;

public class ImageLoader {

    // /**
    // * This function takes a folder name and a file name and returns a string that
    // * is the folder name and
    // * file name concatenated together.
    // *
    // * @param folderName The name of the folder where the image is stored.
    // * @param fileName The name of the file.
    // * @return The imagePathName method returns a String.
    // */
    // private static String imagePathName(String folderName, String fileName) {
    // String res = BaseFileNameFormatter.of(folderName + fileName,
    // FileNameType.IMAGE);
    // System.out.println(res);
    // return res;
    // }

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
            animations[PlayerConstants.IDLE][i] = BaseLoader.loadImage(ImageLoader.class,
                    ImageNamePath.imagePath(folderName, "IDLE_" + i));
        }

        for (int i = 0; i < PlayerConstants.GetAnimationFrameNumbs(PlayerConstants.ATTACKING); i++) {
            animations[PlayerConstants.ATTACKING][i] = BaseLoader.loadImage(ImageLoader.class,
                    ImageNamePath.imagePath(folderName, "ATTACK_" + i));
        }

        for (int i = 0; i < PlayerConstants.GetAnimationFrameNumbs(PlayerConstants.MOVING); i++) {
            animations[PlayerConstants.MOVING][i] = BaseLoader.loadImage(ImageLoader.class,
                    ImageNamePath.imagePath(folderName, "MOVE_" + i));
        }

        return animations;
    }
}
