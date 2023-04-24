package Game;

import Game.GUI.ui.GameMenuButton;
import Game.Loader.ImageLoader;
import Game.gameBase.GamePoint;
import Game.state.GameState;

import static base.BaseGameConstant.GAME_WIDTH;
import static base.BaseGameConstant.GAME_HEIGHT;
import static base.BaseGameConstant.SCALE;

import java.io.IOException;

public class GameElementFactory {
    public GameElementFactory() {

    }

    // using file to load the game character data
    public GameCharacter gameCharacterFactory() {
        return new GameCharacter();
    }

    public static GameMenuButton getGameMenuButton(float xPos, float yPos, int index, GameState state)
            throws IOException {
        return new GameMenuButton(new GamePoint(xPos, yPos),
                ImageLoader.loadMenuButtonImage(GameSourceFilePath.MENU_BUTTON_IMAGE_1, index),
                state);
    }
}
