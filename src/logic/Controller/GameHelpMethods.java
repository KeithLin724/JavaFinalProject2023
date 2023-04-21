package logic.Controller;

import Game.gameBase.GamePoint;
import gameBackground.GameLevel;

import static base.BaseGameConstant.GAME_WIDTH;
import static base.BaseGameConstant.GAME_HEIGHT;
import static base.BaseGameConstant.TILES_SIZE;

import org.w3c.dom.xpath.XPathNamespace;

import static Game.ABC.BasicMoveABC.HIT_BOX_WIDTH;
import static Game.ABC.BasicMoveABC.HIT_BOX_HEIGHT;
// this class for put some logic 

public class GameHelpMethods {
    private static boolean outOfGameWindow(float x, float y, GameLevel level) {

        if (x < 0 || x >= GAME_WIDTH) {
            return true;
        }

        if (y < 0 || y >= GAME_HEIGHT) {
            return true;
        }

        var pointIndex = new GamePoint(x, y).div(TILES_SIZE);
        int value = level.getImageIndex(pointIndex);

        if (value >= 48 || value < 0 || value != 11) {
            return true;
        }

        return false;
    }

    public static boolean canMoveHere(GamePoint point, float width, float height, GameLevel level) {

        if (outOfGameWindow(point.x, point.y, level)) {
            // System.out.println("this 1");
            return false;
        }

        if (outOfGameWindow(point.x + width, point.y + height, level)) {
            // System.out.println("this 2");
            return false;
        }

        if (outOfGameWindow(point.x + width, point.y, level)) {
            // System.out.println("this 3");
            return false;
        }

        if (outOfGameWindow(point.x, point.y + height, level)) {
            // System.out.println("this 4");
            return false;
        }

        return true;

    }

    public static boolean isOnTheFloor(GamePoint point, float width, float height, GameLevel level) {

        if (!outOfGameWindow(point.x, point.y + height, level)) {
            return false;
        }

        if (!outOfGameWindow(point.x + width, point.y + height, level)) {
            // System.out.println("this 2");
            return false;
        }

        return true;
    }

    // public static float getGameCharacterXPosNextToWall(GamePoint point, float
    // xSpeed) {
    // System.out.println(point);
    // if (xSpeed > 0) { // right
    // return point.x + 0.001f;
    // }
    // return point.x - 0.001f;
    // // int currentTile = (int) (point.x / TILES_SIZE);

    // // if (xSpeed > 0) { // right
    // // int tileXPos = currentTile * TILES_SIZE;
    // // int xOffset = (int) (TILES_SIZE - HIT_BOX_WIDTH);
    // // return tileXPos + xOffset - 1;
    // // }

    // // // left
    // // return currentTile * TILES_SIZE;
    // }
}
