package Game.Loader;

// import Game.GameElementFactory;
import Game.GameCharacter;
import Game.DataPass.AniData;
import Game.DataPass.GamePlayerSpeedData;
import Game.DataPass.ImageScaleData;
import Game.builder.GameCharacterBuilder;

// Factory 
public class GameElementLoader {

    public GameCharacter gameCharacter() {
        return null;
    }

    public static GameCharacter getTestingGameCharacter() {
        return new GameCharacterBuilder()
                .setAniData(new AniData(0, 0, 35))
                .setImageScale(new ImageScaleData(0, 0, 10))
                .setGamePlayerSpeedData(new GamePlayerSpeedData(5.0f))
                .build();
    }
}
