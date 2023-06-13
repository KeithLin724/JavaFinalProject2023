package logic.Controller;

import Game.gameBackground.GameLevel;
import Game.gameBase.GamePoint;
import Game.gameBase.GameUnitPair;
import Game.role.ABC.GameCharacterABC;

import static base.BaseGameConstant.GAME_HEIGHT;
import static base.BaseGameConstant.TILES_SIZE;

public class GameHelpMethods {

    /**
     * The function checks if a given point is outside the game window.
     * 
     * @param x a float value representing the x-coordinate of a point in a 2D game
     *          world.
     * @param y The "y" parameter in the "outOfWindow" method is a float value
     *          representing the vertical
     *          position of an object in the game window. It is used to check if the
     *          object is outside the
     *          boundaries of the game window.
     * @return The method is returning a boolean value, which is either true or
     *         false. The method checks if
     *         the given coordinates (x, y) are outside the game window, and returns
     *         true if they are outside, and
     *         false if they are inside the window.
     */
    private static boolean outOfWindow(float x, float y, float maxLength) {
        if (x < 0 || x >= maxLength * TILES_SIZE) {
            return true;
        }

        if (y < 0 || y >= GAME_HEIGHT) {
            return true;
        }
        return false;
    }

    private static boolean IsSolid(float x, float y, GameLevel level) {
        if (outOfWindow(x, y, level.getMaxWidth())) {
            return true;
        }

        // var pointIndex = new GamePoint(x, y).div(TILES_SIZE);
        // int value = level.getImageIndex(pointIndex);

        // if (value >= 48 || value < 0 || value != 11) {
        // return true;
        // }

        // return false;
        return IsTileSolid(x, y, level, true);
    }

    public static boolean IsTileSolid(float x, float y, GameLevel level, boolean toTilePoint) {
        // to tiles point
        var pointIndex = GameUnitPair.buildGameUnitPair(x, y);

        if (toTilePoint) {
            pointIndex = pointIndex.div(TILES_SIZE);
        }

        int value = level.getImageIndex(pointIndex);

        if (value >= 72) { // new map
            return false;
        }

        if (value >= 48 || value < 0 || value != 11) { // GameLevelManager.MAX_NUMBER
            return true;
        }

        return false;
    }

    public static boolean canMoveHere(GamePoint point, float width, float height, GameLevel level) {

        if (IsSolid(point.x, point.y, level)) {
            // System.out.println("this 1");
            return false;
        }

        if (IsSolid(point.x + width, point.y + height, level)) {
            // System.out.println("this 2");
            return false;
        }

        if (IsSolid(point.x + width, point.y, level)) {
            // System.out.println("this 3");
            return false;
        }

        if (IsSolid(point.x, point.y + height, level)) {
            // System.out.println("this 4");
            return false;
        }

        return true;

    }

    public static boolean isOnTheFloor(GamePoint point, float width, float height, GameLevel level) {

        // check conner
        if (!IsSolid(point.x, point.y + height + 1, level)) {
            return false;
        }

        if (!IsSolid(point.x + width, point.y + height + 1, level)) {
            // System.out.println("this 2");
            return false;
        }

        return true;
    }

    public static boolean isFloor(GamePoint point, float xSpeed, int width, int height, GameLevel level) {

        return (xSpeed > 0 ? IsSolid(point.getX() + width + xSpeed, point.getY() + height + 1, level)
                : IsSolid(point.getX() + xSpeed, point.getY() + height + 1, level));
    }

    public static boolean isFloor(GamePoint point, float width, float height, GameLevel level) {
        return !IsSolid(point.x, point.y + height + 1, level);
    }

    public static boolean isAllTileWalkable(int startX, int endX, GameLevel levelData, int yTile) {
        for (int i = 0; i < endX - startX; i++) {
            if (IsTileSolid(startX + i, yTile, levelData, false)) {
                return false;
            }
            if (!IsTileSolid(startX + i, yTile + 1, levelData, false)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSightClear(GameLevel levelData, GameCharacterABC firstObj, GameCharacterABC secondObj) {
        var firstPoint = firstObj.getGamePoint();
        var secondPoint = secondObj.getGamePoint();

        int firstXTile = (int) (firstPoint.getX() / TILES_SIZE);
        int secondXTile = (int) (secondPoint.getX() / TILES_SIZE);

        int firstYTile = (int) (firstPoint.getY() / TILES_SIZE);
        int secondYTile = (int) (secondPoint.getY() / TILES_SIZE);

        if (firstYTile != secondYTile) {
            return false;
        }

        int yTile = firstYTile;

        if (firstXTile > secondXTile) {

            return isAllTileWalkable(secondXTile, firstXTile, levelData, yTile);
        } else {

            return isAllTileWalkable(firstXTile, secondXTile, levelData, yTile);
        }
    }

}
