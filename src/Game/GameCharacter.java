package Game;

import java.awt.Graphics;

import Game.ABC.GameCharacterABC;
import Game.DataPass.AniData;
import Game.DataPass.GamePlayerSpeedData;
import Game.DataPass.ImageScaleData;
import Game.PLUG.GameCharacterInterface;

// for put the game character skin
public class GameCharacter extends GameCharacterABC implements GameCharacterInterface {
    public GameCharacter() {
        super(null, null, null);
    }

    public GameCharacter(AniData aid, ImageScaleData isd, GamePlayerSpeedData gps) {
        super(aid, isd, gps);
    }

    @Override
    public void updatePosition() {

        throw new UnsupportedOperationException("Unimplemented method 'updatePosition'");
    }

    @Override
    public void setUp(boolean up) {
        this.up = up;
    }

    @Override
    public void setDown(boolean down) {
        this.down = down;
    }

    @Override
    public void setLeft(boolean left) {
        this.left = left;
    }

    @Override
    public void setRight(boolean right) {
        this.right = right;
    }

    @Override
    public void render(Graphics g) {
        // None
    }

}
