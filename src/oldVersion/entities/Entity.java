package oldVersion.entities;

import base.loader.BaseFileNameFormatter;
import base.loader.FileNameType;

public abstract class Entity {
    protected float x, y;
    public static final String PLAYER_MAIN_CHARACTER = "../res/mainCharacter/";

    public static String imageName(String fileName) {
        return BaseFileNameFormatter.of(Entity.PLAYER_MAIN_CHARACTER + fileName, FileNameType.IMAGE);
    }

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }
}