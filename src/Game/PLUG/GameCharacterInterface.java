package Game.PLUG;

import java.awt.Graphics;

public interface GameCharacterInterface extends BasicMoveInterface {
    public void updatePosition();

    public void render(Graphics g);
}
