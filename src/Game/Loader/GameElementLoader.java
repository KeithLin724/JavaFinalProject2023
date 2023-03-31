package Game.Loader;

// import Game.GameElementFactory;
import Game.GameCharacter;
import Game.DataPass.AniData;
import Game.DataPass.GamePlayerSpeedData;
import Game.DataPass.ImageScaleData;

// Factory 
public class GameElementLoader {

    public GameCharacter gameCharacter() {
        return null;
    }

    public static GameCharacter getTestingGameCharacter() {
        return new GameCharacter(
                new AniData(0, 0, 35),
                new ImageScaleData(0, 0, 10),
                new GamePlayerSpeedData(5.0f));
    }
}
