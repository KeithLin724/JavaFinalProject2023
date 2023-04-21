package logic.Controller;

import Game.gameBase.GamePoint;
import gameBackground.GameLevel;

import static base.BaseGameConstant.GAME_WIDTH;
import static base.BaseGameConstant.GAME_HEIGHT;
import static base.BaseGameConstant.TILES_SIZE;;
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
            return false;
        }

        if (outOfGameWindow(point.x + width, point.y + height, level)) {
            return false;
        }

        if (outOfGameWindow(point.x + width, point.y, level)) {
            return false;
        }

        if (outOfGameWindow(point.x, point.y + height, level)) {
            return false;
        }

        return true;

    }
}
