package Game.GUI;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Game.GameSourceFilePath;
import Game.Loader.ImageLoader;
import Game.PLUG.GameStateMethod;
import Game.PLUG.gameDrawer.GameAnimatedDrawer;
import Game.PLUG.gameDrawer.GameUpdateInterface;
import Game.gameBase.GameCalculator;
import Game.gameBase.GamePoint;
import Game.gameBase.GameUnitPair;
import Game.state.GameCharacterState;
import Game.state.GameState;
import main.Game;

import static base.BaseGameConstant.SCALE;
import static base.BaseGameConstant.GAME_WIDTH;
import static base.BaseGameConstant.GAME_HEIGHT;

public class GameCredits extends GameStateBase implements GameStateMethod {
    private static final Logger LOGGER = Logger.getLogger(GameCredits.class.getName());

    private BufferedImage bgImage, creditsImage;
    private GamePoint cdPoint;
    private GameUnitPair cdWH;
    private float cdYFloat;

    private ArrayList<ShowGameCharacter> gameCharacterList;

    public GameCredits(Game game) {
        super(game);
        try {
            initBackground();
            initShowGameCharacter();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Game Credits loading error", e);
        }

    }

    private void initBackground() throws IOException {
        this.bgImage = ImageLoader.loadImage(GameSourceFilePath.MENU_SELECT_BACKGROUND_IMAGE_CITY);

        this.creditsImage = ImageLoader.loadImage(GameSourceFilePath.CREDITS_IMAGE);

        this.cdWH = GameCalculator.calculate(
                this.creditsImage.getWidth(), this.creditsImage.getHeight(),
                (x) -> (int) (x * SCALE));

        this.cdPoint = GamePoint.buildGamePoint(GAME_WIDTH / 2f - this.cdWH.getW() / 2f, GAME_HEIGHT);
    }

    private void initShowGameCharacter() throws IOException {
        this.gameCharacterList = new ArrayList<>();

        var cppPoint = GamePoint.buildGamePoint(GAME_WIDTH * 0.05f, GAME_HEIGHT * 0.8f);
        var javaPoint = GamePoint.buildGamePoint(GAME_WIDTH * 0.15f, GAME_HEIGHT * 0.75f);

        var pythonPoint = GamePoint.buildGamePoint(GAME_WIDTH * 0.8f, GAME_HEIGHT * 0.8f);

        this.gameCharacterList.add(new ShowGameCharacter(GameSourceFilePath.CPP_CHARACTER_FOLDER, cppPoint));
        this.gameCharacterList.add(new ShowGameCharacter(GameSourceFilePath.JAVA_CHARACTER_FOLDER, javaPoint));
        this.gameCharacterList.add(new ShowGameCharacter(GameSourceFilePath.PYTHON_CHARACTER_FOLDER, pythonPoint));
    }

    @Override
    public void update() {
        cdYFloat -= 0.2f;

        this.gameCharacterList.forEach(GameUpdateInterface::update);
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(this.bgImage, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        g.drawImage(this.creditsImage,
                this.cdPoint.getIntX(), (int) (this.cdPoint.getY() + cdYFloat),
                this.cdWH.getIntW(), this.cdWH.getIntH(),
                null);

        this.gameCharacterList.forEach(item -> item.render(g));
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cdYFloat = 0;
            this.setGameState(GameState.MENU);
        }
    }

    private class ShowGameCharacter implements GameAnimatedDrawer {
        private BufferedImage[] idleAnimationImage;
        private GamePoint point;
        private int aniIndex, aniTick;

        public ShowGameCharacter(String characterImageFolderPath, GamePoint point) throws IOException {
            var allStateImage = ImageLoader.loadCharacterImage(characterImageFolderPath, 0, 0);
            this.idleAnimationImage = allStateImage[GameCharacterState.IDLE.saveArrayIndex];
            this.point = point;
        }

        @Override
        public void update() {
            aniTick++;
            if (aniTick >= 25) {
                aniTick = 0;
                aniIndex++;
                if (aniIndex >= idleAnimationImage.length) {
                    aniIndex = 0;
                }

            }
        }

        @Override
        public void render(Graphics2D g) {
            g.drawImage(idleAnimationImage[aniIndex],
                    this.point.getIntX(), this.point.getIntY(),
                    (int) (idleAnimationImage[aniIndex].getWidth() * 4),
                    (int) (idleAnimationImage[aniIndex].getHeight() * 4),
                    null);
        }
    }

}
