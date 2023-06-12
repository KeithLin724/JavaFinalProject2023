package Game.object;

public enum GameObjectType {
    BOX(3);

    public final int frameNumber;

    GameObjectType(int frameNumber) {
        this.frameNumber = frameNumber;
    }

}
