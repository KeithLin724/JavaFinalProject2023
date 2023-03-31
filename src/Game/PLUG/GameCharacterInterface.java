package Game.PLUG;

import java.awt.Graphics;

public interface GameCharacterInterface extends BasicMoveInterface {
    public void updatePosition();

    public void setAnimationImage();

    public void setAttacking();

    public void setAnimationState();

    public void updateAnimationTick();

    public void render(Graphics g);
}
