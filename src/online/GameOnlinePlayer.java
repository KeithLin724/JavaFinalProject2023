package online;

import java.awt.Graphics;
import java.awt.Graphics2D;

import Game.Player;

import static base.BaseGameConstant.TILES_SIZE;

public class GameOnlinePlayer extends Player {

    @Override
    public void render(Graphics2D g) {
        var nowImage = this.getAnimationImage(this.gameCharacterState, this.aniIndex);
        var fromPoint = this.point.toIntPoint();

        g.drawImage(nowImage,
                (int) (fromPoint.x - drawXOffset + flipX),
                fromPoint.y,
                TILES_SIZE * flipW,
                TILES_SIZE,
                null);

        // this.drawHitBox(g, drawXOffset);

        // this.drawAttackBox(g);

        this.drawUI(g);
    }

    @Override
    protected void drawUI(Graphics g) {
        // g.drawImage(statusBarImage,
        // StatusBar.STATUS_BAR_X.value, StatusBar.STATUS_BAR_Y.value, // xy
        // StatusBar.STATUS_BAR_WIDTH.value, StatusBar.STATUS_BAR_HEIGHT.value, // w h
        // null);

        // g.setColor(Color.red);

        // g.fillRect(StatusBar.HEALTH_BAR_X_START.value + StatusBar.STATUS_BAR_X.value,
        // StatusBar.HEALTH_BAR_Y_START.value + StatusBar.STATUS_BAR_Y.value,
        // this.healthWidth,
        // StatusBar.HEALTH_BAR_HEIGHT.value);
    }

}
