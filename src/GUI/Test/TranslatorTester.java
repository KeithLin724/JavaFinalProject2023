package GUI.Test;

import java.awt.Graphics;
import java.util.logging.Logger;

import Game.GameCharacter;
import Game.Loader.GameElementLoader;
import Game.PLUG.GameRenderInterface;
import logic.input.Direction;

public class TranslatorTester implements GameRenderInterface {
    private static final Logger LOGGER = Logger.getLogger(TranslatorTester.class.getName());

    private GameCharacter player;

    public TranslatorTester() {
        LOGGER.info("Testing");
        // player = GameElementLoader.getTestingGameCharacter();

        // player.initWithPoint_testing(200, 200);

        String fileName = "../../characterDir/mainCharacter.txt";
        player = GameElementLoader.getTestingGameCharacter(fileName);
        player.init(200, 200);
    }

    public void updateLogic() {
        player.update();
    }

    public GameCharacter getPlayer() {
        return player;
    }

    public void setPlayMove(Direction moveCmd, boolean isMoving) {
        this.player.setDirection(moveCmd, isMoving);
    }

    @Override
    public void render(Graphics g) {
        player.render(g);
    }
}
