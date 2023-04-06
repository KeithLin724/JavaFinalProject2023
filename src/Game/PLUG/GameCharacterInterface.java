package Game.PLUG;

import java.io.IOException;

public interface GameCharacterInterface {
    public void setAnimationImage() throws IOException;

    public void setAnimationState();

    public void updateAnimationTick();
}
