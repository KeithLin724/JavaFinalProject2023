package Game.PLUG;

import java.awt.Graphics;
import java.io.IOException;

public interface GameCharacterInterface extends BasicMoveInterface {
    public void updatePosition();

    public void setAnimationImage() throws IOException;

    public void setAttacking(boolean attacking);

    public void setAnimationState();

    public void updateAnimationTick();

    public void render(Graphics g);
}
