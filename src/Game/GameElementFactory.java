package Game;

public class GameElementFactory {
    public GameElementFactory() {

    }

    // using file to load the game character data
    public GameCharacter gameCharacterFactory() {
        return new GameCharacter();
    }
}
