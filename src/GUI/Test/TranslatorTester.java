package GUI.Test;

import java.awt.Graphics;
import java.io.IOException;
import java.util.logging.Logger;

import Game.GameCharacter;
import Game.GameSourceFilePath;
import Game.Loader.GameElementLoader;
import Game.PLUG.gameDrawer.GameAnimatedDrawer;
import Game.gameBackground.GameLevelManager;
import logic.input.Direction;
import main.Game;

public class TranslatorTester implements GameAnimatedDrawer {
    private static final Logger LOGGER = Logger.getLogger(TranslatorTester.class.getName());

    private Game game;
    private GameLevelManager gameMapLevelManager;
    private GameCharacter player;

    public TranslatorTester(Game game) throws IOException {
        LOGGER.info("Testing");

        this.game = game;
        // player = GameElementLoader.getTestingGameCharacter();

        // player.initWithPoint_testing(200, 200);
        gameMapLevelManager = new GameLevelManager(game);

        player = GameElementLoader.getTestingGameCharacter(GameSourceFilePath.PLAYER_MAIN_CHARACTER_TEXT_FILE);
        player.init(200, 200);
        player.setLevelData(gameMapLevelManager.getGameLevel().getLevel2D());
        player.setLevel(gameMapLevelManager.getGameLevel());

    }

    @Override
    public void update() {
        gameMapLevelManager.update();
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
        // first to render the background
        gameMapLevelManager.render(g);

        // second render the player
        player.render(g);
    }

    public void stopPlayerMoving() {
        this.player.stopDirection();
    }

    public void setPlayerJump(boolean isJump) {
        this.player.setJump(isJump);
    }
}
