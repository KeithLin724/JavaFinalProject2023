package Game.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import Game.gameBase.GamePoint;
import Game.gameBase.GameUnitPair;

public class GameObject {

    protected GamePoint point;

    protected GameObjectType objType;

    protected Rectangle2D.Float hitBox;

    protected boolean doAnimation, active = true;// use state

    protected int aniTick, aniIndex;

    protected int xDrawOffset, yDrawOffset;

    private int aniSpeed;

    public GameObject() {

    }

    public GameObject(GamePoint point, GameObjectType objType) {
        this.point = point;
        this.objType = objType;
    }

    protected void initHitBox(GameUnitPair gameGameUnitPair) {
        this.hitBox = new Rectangle2D.Float(point.x, point.y, gameGameUnitPair.getIntW(), gameGameUnitPair.getH());
    }

    public void drawHitBox(Graphics2D g, float xOffset) {
        // for debugging
        g.setColor(Color.PINK);
        g.drawRect((int) (hitBox.x - xOffset), (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }

    protected void updateAnimationTick() {
        this.aniTick++;

        if (this.aniTick >= this.aniSpeed) {
            this.aniTick = 0;
            this.aniIndex++;

            if (this.aniIndex >= objType.frameNumber) {
                this.aniIndex = 0;

                if (objType.equals(GameObjectType.BOX)) {

                }

            }
        }
    }

    public void resetAll() {
        this.aniIndex = 0;
        this.aniTick = 0;
        this.active = true;

        // TODO: add if
        this.doAnimation = true;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    public int getXDrawOffset() {
        return xDrawOffset;
    }

    public int getYDrawOffset() {
        return yDrawOffset;
    }

    public GameObjectType getObjType() {
        return objType;
    }

    public int getAniIndex() {
        return this.aniIndex;
    }
}
