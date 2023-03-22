package entities;

public abstract class Entity {
    protected float x, y;
    public static final String PLAYER_MAIN_CHARACTER = "../res/mainCharacter/";

    public Entity(float x, float y){
        this.x = x;
        this.y = y;
    }
}