package Game;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

final public class DataPass {

    /**
     * This is defining a record class named `AniData` with three fields: `aniTick`,
     * `aniIndex`, and
     * `aniSpeed`. The `record` keyword is a new feature in Java 16 that allows for
     * concise and immutable
     * classes that are primarily used for modeling data. In this case, `AniData` is
     * likely used to store
     * animation data for a game.
     */
    public record AniData(int aniTick, int aniIndex, int aniSpeed) {

    }

    /**
     * Defining a record class named `ImageScaleData` with three fields:
     * `imgScaleX`, `imgScaleY`, and
     * `imageScale`. This record class is likely used to store data related to image
     * scaling in a game.
     */
    public record ImageScaleData(int imgScaleX, int imgScaleY, int imageScale) {

    }

    // /**
    // * Defining a record class named `PlayerHitBox` with four fields: `x`, `y`,
    // * `width`, and `height`. This
    // * record class is likely used to store data related to the hit box of a
    // player
    // * in a game. The `record`
    // * keyword is a new feature in Java 16 that allows for concise and immutable
    // * classes that are primarily
    // * used for modeling data.
    // */
    // public record PlayerHitBox(float x, float y, int width, int height) {

    // }

    /**
     * Defining a record class named `GamePlayerSpeedData` with a single field
     * `playSpeed` of type `float`.
     * This record class is likely used to store data related to the speed of a
     * player in a game.
     */
    public record GamePlayerSpeedData(float playSpeed) {

    }

    /**
     * The function converts a string of space-separated integers into an integer
     * array.
     * 
     * @param strData A string containing space-separated integer values.
     * @return The method is returning an integer array.
     */
    public static int[] stringDataToIntArray(String strData) {
        var dataSplit = strData.split(" ");

        return Arrays.stream(dataSplit)
                .mapToInt(x -> Integer.parseInt(x))
                .toArray();
    }

    /**
     * The function converts a string of space-separated numerical data into an
     * array of doubles.
     * 
     * @param strData The input string containing space-separated numerical values
     *                that need to be
     *                converted to an array of doubles.
     * @return The method is returning an array of doubles.
     */
    public static double[] stringDataToDouble(String strData) {
        var dataSplit = strData.split(" ");

        return Arrays.stream(dataSplit)
                .mapToDouble(x -> Double.parseDouble(x))
                .toArray();
    }

    /**
     * The function builds an object of a given class using an array of integer data
     * as input.
     * 
     * @param dataIn an integer array containing three values that will be used as
     *               arguments for the
     *               constructor of the class being instantiated.
     * @param cls    The parameter "cls" is of type Class<T>, which is a generic
     *               class representing the class
     *               of the object to be instantiated. It is used to get the
     *               constructor of the class and create a new
     *               instance of that class using the constructor.
     * @return The method is returning an instance of the class specified by the
     *         `Class<T> cls` parameter,
     *         which is constructed using the `int[] dataIn` parameter as arguments
     *         for the constructor. The type
     *         of the returned object is determined by the `Class<T>` parameter.
     */
    public static <T> T build(int[] dataIn, Class<T> cls) throws NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Constructor<T> constructor = cls.getConstructor(int.class, int.class, int.class);

        return constructor.newInstance(dataIn[0], dataIn[1], dataIn[2]);

    }

}
