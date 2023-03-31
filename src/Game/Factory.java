package Game;

public class Factory {
    public Factory() {

    }

    // using file to load the game character data
    public GameCharacter gameCharacterFactory() {
        return new GameCharacter();
    }
}
