package Game;

import java.awt.Point;

import Game.ABC.GameCharacterABC;
import Game.DataPass.AniData;
import Game.DataPass.GamePlayerSpeedData;
import Game.DataPass.ImageScaleData;
import Game.PLUG.GameCharacterInterface;

// for put the game character skin
public class GameCharacter extends GameCharacterABC implements GameCharacterInterface {
    Point stayPoint;

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

        throw new UnsupportedOperationException("Unimplemented method 'setUp'");
    }

    @Override
    public void setDown(boolean down) {

        throw new UnsupportedOperationException("Unimplemented method 'setDown'");
    }

    @Override
    public void setLeft(boolean left) {

        throw new UnsupportedOperationException("Unimplemented method 'setLeft'");
    }

    @Override
    public void setRight(boolean right) {

        throw new UnsupportedOperationException("Unimplemented method 'setRight'");
    }

    @Override
    public void render() {

        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }
}
