package Game.ABC;

import java.awt.image.BufferedImage;

import Game.DataPass.AniData;
import Game.DataPass.GamePlayerSpeedData;
import Game.DataPass.ImageScaleData;

public abstract class GameCharacterABC extends BasicMoveABC {

    protected BufferedImage[][] animations;

    protected int aniTick, aniIndex, aniSpeed; // text
    protected int imgScaleX, imgScaleY, imageScale; // text

    public void setAniThing(AniData aid) {
        this.aniTick = aid.aniTick();
        this.aniIndex = aid.aniIndex();
        this.aniSpeed = aid.aniSpeed();
    }

    public void setImageScale(ImageScaleData isd) {
        this.imgScaleX = isd.imgScaleX();
        this.imgScaleY = isd.imgScaleY();
        this.imageScale = isd.imageScale();
    }

    public void setGamePlayerSpeedData(GamePlayerSpeedData gps) {
        super.setPlayerSpeed(gps.playSpeed());
    }

    public GameCharacterABC() {
        super();
    }

    public GameCharacterABC(AniData aid, ImageScaleData isd, GamePlayerSpeedData gps) {
        super();

        this.setAniThing(aid);
        this.setImageScale(isd);
        this.setGamePlayerSpeedData(gps);
    }

    public void setAnimation(BufferedImage[][] image) {
        this.animations = image;
    }
}
