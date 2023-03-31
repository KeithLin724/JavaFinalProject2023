package GUI.Test;

import java.awt.Graphics;
import java.util.logging.Logger;

import Game.GameCharacter;
import Game.Loader.GameElementLoader;
import Game.PLUG.GameRenderInterface;
import logic.input.MoveCommandConstant;

public class TranslatorTester implements GameRenderInterface {
    private static final Logger LOGGER = Logger.getLogger(TranslatorTester.class.getName());

    private GameCharacter player;

    public TranslatorTester() {
        LOGGER.info("Testing");
        player = GameElementLoader.getTestingGameCharacter();

        player.init(200, 200);
    }

    public void updateLogic() {
        player.update();
    }

    public GameCharacter getPlayer() {
        return player;
    }

    public void setPlayMove(String moveCmd, boolean move) {
        switch (moveCmd) {
            case MoveCommandConstant.UP -> {
                this.player.setUp(move);
            }
            case MoveCommandConstant.LEFT -> {
                this.player.setLeft(move);
            }
            case MoveCommandConstant.DOWN -> {
                this.player.setDown(move);
            }
            case MoveCommandConstant.RIGHT -> {
                this.player.setRight(move);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        player.render(g);
    }
}
