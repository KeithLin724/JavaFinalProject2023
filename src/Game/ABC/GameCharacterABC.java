package Game.ABC;

import java.awt.image.BufferedImage;

import Game.DataPass.AniData;
import Game.DataPass.GamePlayerSpeedData;
import Game.DataPass.ImageScaleData;

public abstract class GameCharacterABC {

    protected BufferedImage[][] animations;
    // private InputStream imageInput;
    protected int aniTick, aniIndex, aniSpeed; // text
    protected int imgScaleX, imgScaleY, imageScale; // text
    protected float playerSpeed; // text

    protected int playerAction;
    protected boolean up, down, right, left;
    protected boolean moving, attacking;

    private void setAniThing(AniData aid) {
        this.aniTick = aid.aniTick();
        this.aniIndex = aid.aniIndex();
        this.aniSpeed = aid.aniSpeed();
    }

    private void setImageScale(ImageScaleData isd) {
        this.imgScaleX = isd.imgScaleX();
        this.imgScaleY = isd.imgScaleY();
        this.imageScale = isd.imageScale();
    }

    private void setGamePlayerSpeedData(GamePlayerSpeedData gps) {
        this.playerSpeed = gps.playSpeed();
    }

    public GameCharacterABC(AniData aid, ImageScaleData isd, GamePlayerSpeedData gps) {
        this.setAniThing(aid);
        this.setImageScale(isd);
        this.setGamePlayerSpeedData(gps);
    }
}
